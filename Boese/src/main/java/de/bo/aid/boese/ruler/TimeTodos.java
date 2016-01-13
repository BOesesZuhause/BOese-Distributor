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

import java.util.Date;

import de.bo.aid.boese.model.DeviceComponent;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeTodos defines ToDos in the Future.
 */
public class TimeTodos implements Comparable<TimeTodos> {

	/** The id of the ToDo. */
	private int id;
	
	/** The date when it will be executed. */
	private TimeFormat date;
	
	/** The value which will be executed. */
	private double value;
	
	/** The affected DeviceComponent. */
	private DeviceComponent deco;

	/**
	 * Instantiates a new time todos.
	 */
	public TimeTodos() {
		
	}

	/**
	 * Instantiates a new time todos with all Attributes.
	 *
	 * @param id the id of the ToDo
	 * @param date the date when it will be executed as TimeFormat
	 * @param value the value which will be executed
	 * @param deco the affected DeviceComponent
	 */
	public TimeTodos(int id, TimeFormat date, double value, DeviceComponent deco) {
		this.id = id;
		this.date = date;
		this.value = value;
		this.deco = deco;
	}

	/**
	 * Instantiates a new time todos with all Attributes.
	 *
	 * @param id the id of the ToDo
	 * @param cron the date when it will be executed as Cron-String
	 * @param value the value which will be executed
	 * @param deco the affected DeviceComponent
	 */
	public TimeTodos(int id, String cron, double value, DeviceComponent deco) {
		this.id = id;
		this.date = new TimeFormat(cron);
		this.value = value;
		this.deco = deco;
	}

	/**
	 * Instantiates a new time todos with all Attributes.
	 *
	 * @param id the id of the ToDo
	 * @param date the date when it will be executed as Date
	 * @param value the value which will be executed
	 * @param deco the affected DeviceComponent
	 */
	public TimeTodos(int id, Date date, double value, DeviceComponent deco) {
		this.id = id;
		this.date = new TimeFormat(date);
		this.value = value;
		this.deco = deco;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public TimeFormat getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(TimeFormat date) {
		this.date = date;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * Gets the deco.
	 *
	 * @return the deco
	 */
	public DeviceComponent getDeco() {
		return deco;
	}

	/**
	 * Sets the deco.
	 *
	 * @param deco the new deco
	 */
	public void setDeco(DeviceComponent deco) {
		this.deco = deco;
	}

	/**
	 * compare which TimeToDo has to be executed earlier.
	 *
	 * @param tt the other TimeToDo wich will be compared
	 * @return the int
	 */
	@Override
	public int compareTo(TimeTodos tt) {
	    return this.getDate().compareTo(tt.getDate());
	}
}
