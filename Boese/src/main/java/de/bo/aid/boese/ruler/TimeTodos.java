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
 * The Class TimeTodos.
 */
public class TimeTodos implements Comparable<TimeTodos> {

	/** The id. */
	private int id;
	
	/** The date. */
	private TimeFormat date;
	
	/** The value. */
	private double value;
	
	/** The deco. */
	private DeviceComponent deco;

	/**
	 * Instantiates a new time todos.
	 */
	public TimeTodos() {
		
	}

	/**
	 * Instantiates a new time todos.
	 *
	 * @param id the id
	 * @param date the date
	 * @param value the value
	 * @param deco the deco
	 */
	public TimeTodos(int id, TimeFormat date, double value, DeviceComponent deco) {
		this.id = id;
		this.date = date;
		this.value = value;
		this.deco = deco;
	}

	/**
	 * Instantiates a new time todos.
	 *
	 * @param id the id
	 * @param cron the cron
	 * @param value the value
	 * @param deco the deco
	 */
	public TimeTodos(int id, String cron, double value, DeviceComponent deco) {
		this.id = id;
		this.date = new TimeFormat(cron);
		this.value = value;
		this.deco = deco;
	}

	/**
	 * Instantiates a new time todos.
	 *
	 * @param id the id
	 * @param d the d
	 * @param value the value
	 * @param deco the deco
	 */
	public TimeTodos(int id, Date d, double value, DeviceComponent deco) {
		this.id = id;
		this.date = new TimeFormat(d);
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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TimeTodos tt) {
	    return this.getDate().compareTo(tt.getDate());
	}
}
