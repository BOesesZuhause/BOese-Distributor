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

import java.util.ArrayList;
import java.util.List;

import de.bo.aid.boese.db.AllSelects;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.exceptions.DBForeignKeyNotFoundException;
import de.bo.aid.boese.model.RepeatRule;
import de.bo.aid.boese.model.ToDo;
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.Component;
import de.bo.aid.boese.xml.Condition;

// TODO: Auto-generated Javadoc
/**
 * The Class Interpretor.
 */
public class Interpretor {
	
	/**
	 * Gets the all de co ids condition.
	 *
	 * @param conditions the conditions
	 * @return the all de co ids condition
	 */
	public List<Integer> getAllDeCoIdsCondition(BoeseXML conditions){
		List<Integer> list = new ArrayList<Integer>();
		for (Component comp : ((Condition)conditions).getRule().getComponents()) {
			list.add(new Integer(comp.getId()));
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
				Inserts.toDoWithoutChange(new TimeFormat(rr.getRepeat()).getDate(), rr.getRrId());
			} catch (DBForeignKeyNotFoundException e) {
				// TODO Logger
				e.printStackTrace();
			}
		}
		tdc.changeInToDo();
	}

}
