package de.bo.aid.boese.json;

public class UserDevice {
	private String deviceName;
	private int deviceId;
	private int zoneId;
	private int connectorId;
	
	public UserDevice(String name, int id, int zone, int connector) {
		this.connectorId = connector;
		this.deviceId = id;
		this.deviceName = name;
		this.zoneId = zone;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public int getZoneId() {
		return zoneId;
	}

	public int getConnectorId() {
		return connectorId;
	}
	
	
	
}
