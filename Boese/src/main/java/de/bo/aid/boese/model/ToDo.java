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


package de.bo.aid.boese.model;

import java.util.Date;

import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;

public class ToDo {
	
	/** The ToDoid. */
	private int toDoId;
	
	/** The time when it will be executed */
	private Date date;
	
	/** Is it active */
	private boolean active;
	
	/** The Repeat Rule */
	private RepeatRule repeatRule;

	public ToDo() {
	}

	public ToDo(int toDoId, Date date, boolean active){
		this.toDoId = toDoId;
		this.date = date;
		this.active = active;
		try {
			this.repeatRule = Selects.repeatRule(0);
		} catch (DBObjectNotFoundException e) {
			// TODO default RepeatRule 0
			e.printStackTrace();
		}
	}

	public ToDo(int toDoId, Date date, boolean active, RepeatRule repeatRule) {
		this.toDoId = toDoId;
		this.date = date;
		this.active = active;
		this.repeatRule = repeatRule;
	}

	public int getToDoId() {
		return toDoId;
	}

	public void setToDoId(int toDoId) {
		this.toDoId = toDoId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public RepeatRule getRepeatRule() {
		return repeatRule;
	}

	public void setRepeatRule(RepeatRule repeatRule) {
		this.repeatRule = repeatRule;
	}

}
