package de.bo.aid.boese.main.model;

public class TempComponent {
	
	private int deviceId;
	private int connectorId;
	private String name;
	private long valueTimestamp;
	private double value;
	
	public TempComponent(int deviceId, String deviceName, double value, long valueTimestamp, int connectorId) {
		this.connectorId = connectorId;
		this.deviceId = deviceId;
		this.name = deviceName;
		this.value = value;
		this.valueTimestamp = valueTimestamp;
	}

	public long getValueTimestamp() {
		return valueTimestamp;
	}
	
	public double getValue() {
		return value;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(int connectorId) {
		this.connectorId = connectorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
