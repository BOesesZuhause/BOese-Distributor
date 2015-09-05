package de.bo.aid.boese.main.model;

public class TempComponent {
	
	private int deviceId;
	private int connectorId;
	private String name;
	private long valueTimestamp;
	private double value;
	private String description;
	private boolean actor;
	private String unit;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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
