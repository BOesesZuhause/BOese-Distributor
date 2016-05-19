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
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.dao.ToDoDAO;
import de.bo.aid.boese.exceptions.DBForeignKeyNotFoundException;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.modelJPA.DeviceComponent;
import de.bo.aid.boese.modelJPA.RepeatRule;
import de.bo.aid.boese.modelJPA.Rule;
import de.bo.aid.boese.modelJPA.ToDo;
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
	
	private DAOHandler daoHandler;
	
	/**
	 * Instantiates a new Interpreter.
	 */
	public Interpreter(){
		check = new Checker();
		daoHandler = DAOHandler.getInstance();
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
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			Set<Rule> rules = daoHandler.getRuleDAO().getAll(em);
			em.getTransaction().commit();
			em.close();
			for(Rule rule : rules){
				if(rule.getActive() && check.deCoInCondition(id, rule.getConditions())){
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
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Map<Integer, DeviceComponent> deviceComponents = daoHandler.getDeviceComponentDAO().getAllAsMap(em);
		em.getTransaction().commit();
		em.close();
		for (int decoID : ((Condition)conditions).getComponentIds()) {
			DeviceComponent deco = deviceComponents.get(decoID);
			if(deco != null) {
				list.add(deco);
			}
			else{
			    logger.error("DeviceCompent mit der ID " + decoID + " konnte in der DB nicht gefunden werden");
			    em.getTransaction().rollback();
				//TODO Exception
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
		DAOHandler daoh = DAOHandler.getInstance();
		ToDoDAO tododao = daoh.getToDoDAO();
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Set<ToDo> todos = tododao.getAll(em);
		Set<RepeatRule> repeatrule = daoh.getRepeatRuleDAO().getAll(em);
		em.getTransaction().commit();
		for(ToDo todo : todos){
			repeatrule.remove(todo.getRepeatRule());
		}
		for(RepeatRule rr : repeatrule){
			ToDo todo = new ToDo(new TimeFormat(rr.getRepeat()).getDateForRepeatRule());
			todo.setRepeatRule(rr);
			tododao.create(em, todo);
		}
		tdc.changeInToDo();
		em.close();
	}

}
