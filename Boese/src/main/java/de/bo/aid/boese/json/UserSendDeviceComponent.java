package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserSendDeviceComponent extends BoeseJson {
	HashSet<DeviceComponents> componentList;
	int deviceId;
	
	public UserSendDeviceComponent(int deviceId, HashSet<DeviceComponents> decoSet, int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDDEVICECOMPONENT, connectorId, status, timestamp);
		this.componentList = decoSet;
		this.deviceId = deviceId;
	}

	public HashSet<DeviceComponents> getComponentList() {
		return componentList;
	}

	public int getDeviceId() {
		return deviceId;
	}
}
