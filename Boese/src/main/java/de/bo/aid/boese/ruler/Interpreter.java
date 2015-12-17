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
 * The Class Interpretor.
 */
public class Interpreter {

    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger(Interpreter.class);
    
    /** The check. */
	Checker check;
	
	/**
	 * Instantiates a new Interpreter.
	 */
	public Interpreter(){
		check = new Checker();
	}

	/**
	 * Gets the to dos.
	 *
	 * @param inquirys the inquirys
	 * @return the to dos
	 * @throws Exception the exception
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
	 * Gets the all de co ids condition.
	 *
	 * @param conditions the conditions
	 * @return the all de co ids condition
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
	
//	public List<Integer> getAllDeCoIdsAction(BoeseXML actions){
//		List<Integer> list = new ArrayList<Integer>();
//		for (Component comp : ((Action)actions).getRule().getComponents()) {
//			list.add(new Integer(comp.getId()));
//		}
//		return list;
//	}
	
/**
 * Creates the todos.
 *
 * @param tdc the tdc
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
