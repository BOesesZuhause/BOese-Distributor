
package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviceComponents.
 */
public class DeviceComponents {
	
	/** The device component id. */
	private int deviceComponentId;
	
	/** The component name. */
	private String componentName;
	
	/** The value. */
	private double value;
	
	/** The timestamp. */
	private long timestamp;
	
	/** The actor. */
	private boolean actor;
	
	/** The unit. */
	private String unit;
	
	/** The description. */
	private String description;
	
	private int status;
	
	/**
	 * Instantiates a new device components.
	 *
	 * @param deviceComponentId the device component id
	 * @param componentName the component name
	 * @param value the value
	 * @param timestamp the timestamp
	 * @param unit the unit
	 * @param description the description
	 * @param actor the actor
	 */
	public DeviceComponents(int deviceComponentId, String componentName, double value, long timestamp, String unit, String description, boolean actor) {
		this.deviceComponentId = deviceComponentId;
		this.componentName = componentName;
		this.value = value;
		this.timestamp = timestamp;
		this.actor = actor;
		this.unit = unit;
		this.description = description;
		this.status = -1;
	}
	
	public DeviceComponents(int deviceComponentId, String componentName, double value, String unit, String description, boolean actor, int status) {
		this.deviceComponentId = deviceComponentId;
		this.componentName = componentName;
		this.value = value;
		this.timestamp = -1;
		this.actor = actor;
		this.unit = unit;
		this.description = description;
		this.status = status;
	}
	
	public int getStatus(){
		return status;
	}
	
	/**
	 * Sets the device component id.
	 *
	 * @param deviceComponentId the new device component id
	 */
	public void setDeviceComponentId(int deviceComponentId) {
		this.deviceComponentId = deviceComponentId;
	}

	/**
	 * Sets the component name.
	 *
	 * @param componentName the new component name
	 */
	public void setComponentName(String componentName) {
		this.componentName = componentName;
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
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the device component id.
	 *
	 * @return the device component id
	 */
	public int getDeviceComponentId() {
		return deviceComponentId;
	}

	/**
	 * Gets the component name.
	 *
	 * @return the component name
	 */
	public String getComponentName() {
		return componentName;
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
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
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
}
