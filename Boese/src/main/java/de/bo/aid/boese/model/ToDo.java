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

// TODO: Auto-generated Javadoc
/**
 * The Class ToDo.
 */
public class ToDo {
	
	/** The ToDoid. */
	private int toDoId;
	
	/**  The time when it will be executed. */
	private Date date;
	
	/**  Is it active. */
	private boolean active;
	
	/**  The Repeat Rule. */
	private RepeatRule repeatRule;

	/**
	 * Instantiates a new to do.
	 */
	public ToDo() {
		
	}

	/**
	 * Instantiates a new todo for DB Insert.
	 *
	 * @param date the date
	 */
	public ToDo(Date date){
		this.date = date;
		this.active = true;
	}

	/**
	 * Instantiates a new to do.
	 *
	 * @param toDoId the to do id
	 * @param date the date
	 * @param active the active
	 */
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

	/**
	 * Instantiates a new to do.
	 *
	 * @param toDoId the to do id
	 * @param date the date
	 * @param active the active
	 * @param repeatRule the repeat rule
	 */
	public ToDo(int toDoId, Date date, boolean active, RepeatRule repeatRule) {
		this.toDoId = toDoId;
		this.date = date;
		this.active = active;
		this.repeatRule = repeatRule;
	}

	/**
	 * Gets the to do id.
	 *
	 * @return the to do id
	 */
	public int getToDoId() {
		return toDoId;
	}

	/**
	 * Sets the to do id.
	 *
	 * @param toDoId the new to do id
	 */
	public void setToDoId(int toDoId) {
		this.toDoId = toDoId;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the repeat rule.
	 *
	 * @return the repeat rule
	 */
	public RepeatRule getRepeatRule() {
		return repeatRule;
	}

	/**
	 * Sets the repeat rule.
	 *
	 * @param repeatRule the new repeat rule
	 */
	public void setRepeatRule(RepeatRule repeatRule) {
		this.repeatRule = repeatRule;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToDo other = (ToDo) obj;
		if (active != other.active)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (repeatRule == null) {
			if (other.repeatRule != null)
				return false;
		} else if (!repeatRule.equals(other.repeatRule))
			return false;
		if (toDoId != other.toDoId)
			return false;
		return true;
	}

}
