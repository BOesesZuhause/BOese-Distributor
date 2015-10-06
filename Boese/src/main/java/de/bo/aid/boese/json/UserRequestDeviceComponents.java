package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserRequestDeviceComponents extends BoeseJson{
	HashSet<Integer> deviceIds;
	
	public UserRequestDeviceComponents(HashSet<Integer> deviceIds, int connectorId, int status, long timestamp) {
		super(MessageType.USERREQUESTDEVICECOMPONENTS, connectorId, status, timestamp);
		this.deviceIds = deviceIds;
	}
	
	public HashSet<Integer> getDeviceIds() {
		return deviceIds;
	}

}
