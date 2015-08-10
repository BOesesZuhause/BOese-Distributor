package de.bo.aid.boese.json;

public class RequestDeviceComponents extends BoeseJson {
	private int deviceId;
	
	protected RequestDeviceComponents(int deviceId, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.REQUESTDEVICECOMPONENTS, connectorId, seqNr, ackNr, status, timestamp);
		this.deviceId = deviceId;
	}

	public int getDeviceId() {
		return deviceId;
	}
}
