package de.bo.aid.boese.ruler;

public class Inquiry {
	
	private int deviceComponentId;
	
	private long timestamp;
	
	private double value;

	public Inquiry(int deviceComponentId, long timestamp, double value) {
		super();
		this.deviceComponentId = deviceComponentId;
		this.timestamp = timestamp;
		this.value = value;
	}

	public int getDeviceComponentId() {
		return deviceComponentId;
	}

	public void setDeviceComponentId(int deviceComponentId) {
		this.deviceComponentId = deviceComponentId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
