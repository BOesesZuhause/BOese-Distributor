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

// TODO: Auto-generated Javadoc
/**
 * The Class discribs the Inquiry for the RuleEngine
 */
public class Inquiry {
	
	/** The ID of the affected deviceComponent. */
	private int deviceComponentId;
	
	/** The timestamp when it was effected. */
	private long timestamp;
	
	/** The new value. */
	private double value;

	/**
	 * Instantiates a new inquiry.
	 *
	 * @param deviceComponentId the ID of the affected deviceComponent
	 * @param timestamp the timestamp when it was effected
	 * @param value the new value
	 */
	public Inquiry(int deviceComponentId, long timestamp, double value) {
		super();
		this.deviceComponentId = deviceComponentId;
		this.timestamp = timestamp;
		this.value = value;
	}

	/**
	 * Gets the DeviceComponentID.
	 *
	 * @return the DeviceComponentID
	 */
	public int getDeviceComponentId() {
		return deviceComponentId;
	}

	/**
	 * Sets the DeviceComponentID.
	 *
	 * @param deviceComponentId the new DeviceComponentID
	 */
	public void setDeviceComponentId(int deviceComponentId) {
		this.deviceComponentId = deviceComponentId;
	}

	/**
	 * Gets the timestamp when it was effected.
	 *
	 * @return the timestamp when it was effected as a long
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp when it was effected.
	 *
	 * @param timestamp the new timestamp when it was effected
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the new value.
	 *
	 * @return the new value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the new value.
	 *
	 * @param value the new new value
	 */
	public void setValue(double value) {
		this.value = value;
	}

}
