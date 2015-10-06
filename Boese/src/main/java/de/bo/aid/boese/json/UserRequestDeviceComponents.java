package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserRequestDeviceComponents extends BoeseJson{
	HashSet<Integer> deviceIds;
	
	public UserRequestDeviceComponents(HashSet<Integer> deviceIds, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.USERREQUESTDEVICECOMPONENTS, connectorId, seqNr, ackNr, status, timestamp);
		this.deviceIds = deviceIds;
	}
	
	public HashSet<Integer> getDeviceIds() {
		return deviceIds;
	}

}
