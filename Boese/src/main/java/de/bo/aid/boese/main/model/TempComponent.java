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



package de.bo.aid.boese.main.model;

// TODO: Auto-generated Javadoc
/**
 * The Class TempComponent.
 */
public class TempComponent {
	
	/** The device id. */
	private int deviceId;
	
	/** The connector id. */
	private int connectorId;
	
	/** The name. */
	private String name;
	
	/** The value timestamp. */
	private long valueTimestamp;
	
	/** The value. */
	private double value;
	
	/** The description. */
	private String description;
	
	/** The actor. */
	private boolean actor;
	
	/** The unit. */
	private String unit;
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Instantiates a new temp component.
	 *
	 * @param deviceId the device id
	 * @param deviceName the device name
	 * @param value the value
	 * @param valueTimestamp the value timestamp
	 * @param connectorId the connector id
	 * @param description the description
	 * @param unit the unit
	 * @param actor the actor
	 */
	public TempComponent(int deviceId, String deviceName, double value, long valueTimestamp, int connectorId, String description, String unit, boolean actor) {
		this.connectorId = connectorId;
		this.deviceId = deviceId;
		this.name = deviceName;
		this.value = value;
		this.valueTimestamp = valueTimestamp;
		this.description = description;
		this.actor = actor;
		this.unit = unit;
	}

	/**
	 * Checks if is actor.
	 *
	 * @return true, if is actor
	 */
	public boolean isActor() {
		return actor;
	}

	/**
	 * Sets the actor.
	 *
	 * @param actor the new actor
	 */
	public void setActor(boolean actor) {
		this.actor = actor;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit the new unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Gets the value timestamp.
	 *
	 * @return the value timestamp
	 */
	public long getValueTimestamp() {
		return valueTimestamp;
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
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public int getDeviceId() {
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId the new device id
	 */
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * Gets the connector id.
	 *
	 * @return the connector id
	 */
	public int getConnectorId() {
		return connectorId;
	}

	/**
	 * Sets the connector id.
	 *
	 * @param connectorId the new connector id
	 */
	public void setConnectorId(int connectorId) {
		this.connectorId = connectorId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
