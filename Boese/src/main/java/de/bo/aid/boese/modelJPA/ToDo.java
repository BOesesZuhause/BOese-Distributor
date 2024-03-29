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


package de.bo.aid.boese.modelJPA;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// TODO: Auto-generated Javadoc
/**
 * ToDo Model for Hibernate.
 */
public class ToDo {
	
	/** The ToDoid. */
	@Id
	@GeneratedValue
	private int toDoId;
	
	/**  The time when it will be executed. */
	private Date date;
	
	/**  Is it active. */
	@Column(columnDefinition="boolean default true")
	private boolean active;
	
	/**  The Repeat Rule this ToDo is belonging to. */
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "repeatRule", nullable = false, unique=true)
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
	 * Instantiates a new to do with all parameters.
	 *
	 * @param toDoId the to do id
	 * @param date the date
	 * @param active the active
	 */
	public ToDo(int toDoId, Date date, boolean active){
		this.toDoId = toDoId;
		this.date = date;
		this.active = active;
		//TODO
		/*
		try {
			this.repeatRule = Selects.repeatRule(0);
		} catch (DBObjectNotFoundException e) {
			// TODO default RepeatRule 0
			e.printStackTrace();
		}
		*/
	}

	/**
	 * Instantiates a new to do with all parameters and foreign keys.
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
	 * Gets the ToDo id.
	 *
	 * @return the ToDo id
	 */
	public int getToDoId() {
		return toDoId;
	}

	/**
	 * Sets the ToDo id.
	 *
	 * @param toDoId the new ToDo id
	 */
	public void setToDoId(int toDoId) {
		this.toDoId = toDoId;
	}

	/**
	 * Gets the date when this ToDo will be executed.
	 *
	 * @return the date when this ToDo will be executed
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date when this ToDo will be executed.
	 *
	 * @param date the new date when this ToDo will be executed
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Checks if this ToDo is active.
	 *
	 * @return true, if this ToDo is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * activate or deactivate this ToDo.
	 *
	 * @param active True for activate and false for deactivate
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the RepeatRule this ToDo is belonging to.
	 *
	 * @return the RepeatRule this ToDo is belonging to
	 */
	public RepeatRule getRepeatRule() {
		return repeatRule;
	}

	/**
	 * Sets the RepeatRule this ToDo is belonging to.
	 *
	 * @param repeatRule the new RepeatRule this ToDo is belonging to
	 */
	public void setRepeatRule(RepeatRule repeatRule) {
		this.repeatRule = repeatRule;
	}

	/**
	 *  
	 * To compare two ToDos.
	 *
	 * @param obj the ToDo object to compare
	 * @return true if both ToDos are equal
	 */
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
