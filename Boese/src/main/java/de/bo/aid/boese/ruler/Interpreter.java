/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 */



package de.bo.aid.boese.ruler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.bo.aid.boese.db.AllSelects;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.exceptions.DBForeignKeyNotFoundException;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.model.RepeatRule;
import de.bo.aid.boese.model.Rule;
import de.bo.aid.boese.model.ToDo;
import de.bo.aid.boese.xml.Action;
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.ComponentXML;
import de.bo.aid.boese.xml.Condition;

// TODO: Auto-generated Javadoc
/**
 * The Class Interpretor is the Controller of the Rule Engine.
 */
public class Interpreter {

    /** The instance of the logger. */
    private static final Logger logger = LogManager.getLogger(Interpreter.class);
    
    /** The instance of the Checker. */
	private Checker check;
	
	/**
	 * Instantiates a new Interpreter.
	 */
	public Interpreter(){
		check = new Checker();
	}

	/**
	 * Produce a List with ToDos after checking all Rules of effected DeviceComponents.
	 *
	 * @param inquirys a List with all infected DeviceComponents and their new Values
	 * @return a List with ToDos
	 * @throws Exception if something goes wrong
	 */
	public List<ComponentXML> getToDos(List<Inquiry> inquirys) throws Exception{
		List<ComponentXML> toDo = new ArrayList<ComponentXML>();
		
		for(Inquiry inquiry : inquirys){
			int id = inquiry.getDeviceComponentId();
			List<Rule> rules = Selects.rulesByDeviceComponent(id);
			for(Rule rule : rules){
				if(check.deCoInCondition(id, rule.getConditions())){
					InputStream is = new ByteArrayInputStream(rule.getConditions().getBytes());
					BoeseXML conBXML = BoeseXML.readXML(is);
//					is = new ByteArrayInputStream(rule.getPermissions().getBytes());
//					BoeseXML perBXML = BoeseXML.readXML(is);
					Boolean con = check.condition(((Condition)conBXML).getRule());
					if(con != null && con){
						is = new ByteArrayInputStream(rule.getActions().getBytes());
						BoeseXML actBXML = BoeseXML.readXML(is);
						try {
							toDo.addAll(toDo.size(), (check.action((Action)actBXML)));
							
						} catch (Exception e) {
							System.err.println("Bad XML: " + e.getMessage());
							e.printStackTrace();
							throw new Exception(e.getMessage());
						}
					}
				}
			}
		}
		
		return toDo;
	}
	
	/**
	 * Gets the all DeviceComponentIDs of a Condition.
	 *
	 * @param conditions the condition String
	 * @return a List with all DeviceComponentIDs
	 */
	public List<DeviceComponent> getAllDeCosCondition(BoeseXML conditions){
		List<DeviceComponent> list = new ArrayList<DeviceComponent>();
		for (ComponentXML comp : ((Condition)conditions).getRule().getComponents()) {
			try {
				list.add(Selects.deviceComponent(comp.getId()));
			} catch (DBObjectNotFoundException e) {
			    logger.error(e);
				e.printStackTrace();
			}
		}
		return list;
	}
	
/**
 * Creates a List with ToDos in the future if somthing has changed.
 *
 * @param tdc the ToDoChecker instance of the Distributor
 */
	public static void createTodos(ToDoChecker tdc){
		List<ToDo> todos = AllSelects.toDos();
		List<RepeatRule> rule = AllSelects.repeatRules();
		for(ToDo todo : todos){
			rule.remove(todo.getRepeatRule());
		}
		for(RepeatRule rr : rule){
			try {
				ToDo todo = new ToDo(new TimeFormat(rr.getRepeat()).getDate());
				Inserts.toDoWithoutChange(todo, rr.getRrId());
			} catch (DBForeignKeyNotFoundException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
		tdc.changeInToDo();
	}

}
