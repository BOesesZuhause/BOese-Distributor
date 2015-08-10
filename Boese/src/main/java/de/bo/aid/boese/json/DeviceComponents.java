package de.bo.aid.boese.json;

public class DeviceComponents {
	private int deviceComponentId;
	private String componentName;
	private double value;
	private long timestamp;
	
	public DeviceComponents(int deviceComponentId, String componentName, double value, long timestamp) {
		this.deviceComponentId = deviceComponentId;
		this.componentName = componentName;
		this.value = value;
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
}
