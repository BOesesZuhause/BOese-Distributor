package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserSendDevices extends BoeseJson {
	HashSet<UserDevice> deviceList;

	public UserSendDevices(int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDDEVICES, connectorId, status, timestamp);
		deviceList = new HashSet<>();
	}
	
	public UserSendDevices(HashSet<UserDevice> deviceList, int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDDEVICES, connectorId, status, timestamp);
		this.deviceList = deviceList;
	}
	
	public void addDevice(UserDevice device) {
		deviceList.add(device);
	}
	
	public HashSet<UserDevice> getDevices() {
		return deviceList;
	}
}
