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

public class TimeTodos implements Comparable<TimeTodos> {

	private int id;
	
	private TimeFormat date;
	
	private double value;
	
	private DeviceComponent deco;

	public TimeTodos() {
		
	}

	public TimeTodos(int id, TimeFormat date, double value, DeviceComponent deco) {
		this.id = id;
		this.date = date;
		this.value = value;
		this.deco = deco;
	}

	public TimeTodos(int id, String cron, double value, DeviceComponent deco) {
		this.id = id;
		this.date = new TimeFormat(cron);
		this.value = value;
		this.deco = deco;
	}

	public TimeTodos(int id, Date d, double value, DeviceComponent deco) {
		this.id = id;
		this.date = new TimeFormat(d);
		this.value = value;
		this.deco = deco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TimeFormat getDate() {
		return date;
	}

	public void setDate(TimeFormat date) {
		this.date = date;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public DeviceComponent getDeco() {
		return deco;
	}

	public void setDeco(DeviceComponent deco) {
		this.deco = deco;
	}

	@Override
	public int compareTo(TimeTodos tt) {
	    return this.getDate().compareTo(tt.getDate());
	}
}
