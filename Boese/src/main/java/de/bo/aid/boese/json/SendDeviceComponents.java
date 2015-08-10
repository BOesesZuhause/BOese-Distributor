package de.bo.aid.boese.json;

import java.util.HashSet;

public class SendDeviceComponents extends BoeseJson {
	private HashSet<DeviceComponents> components;
	private int deviceId;

	public SendDeviceComponents(int deviceId, HashSet<DeviceComponents> components, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.SENDDEVICECOMPONENTS, connectorId, seqNr, ackNr, status, timestamp);
		this.components = components;
		this.deviceId = deviceId;
	}

	public HashSet<DeviceComponents> getComponents() {
		return components;
	}

	public int getDeviceId() {
		return deviceId;
	}
}
