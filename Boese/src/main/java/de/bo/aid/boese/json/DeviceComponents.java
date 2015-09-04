package de.bo.aid.boese.json;

public class DeviceComponents {
	private int deviceComponentId;
	private String componentName;
	private double value;
	private long timestamp;
	private boolean actor;
	private String unit;
	private String description;
	
	public DeviceComponents(int deviceComponentId, String componentName, double value, long timestamp, String unit, String description, boolean actor) {
		this.deviceComponentId = deviceComponentId;
		this.componentName = componentName;
		this.value = value;
		this.timestamp = timestamp;
		this.actor = actor;
		this.unit = unit;
		this.description = description;
	}
	
	public void setDeviceComponentId(int deviceComponentId) {
		this.deviceComponentId = deviceComponentId;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getDeviceComponentId() {
		return deviceComponentId;
	}

	public String getComponentName() {
		return componentName;
	}

	public double getValue() {
		return value;
	}

	public long getTimestamp() {
		return timestamp;
	}



	public boolean isActor() {
		return actor;
	}



	public void setActor(boolean actor) {
		this.actor = actor;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}
}
