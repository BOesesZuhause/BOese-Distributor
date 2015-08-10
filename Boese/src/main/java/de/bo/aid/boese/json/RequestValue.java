package de.bo.aid.boese.json;

public class RequestValue extends BoeseJson {
	private int deviceId;
	private int deviceComponentId;

	public RequestValue(int deviceId, int deviceComponentId, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		super(MessageType.REQUESTVALUE, connectorId, seqNr, ackNr, status, timestamp);
	}

	public int getDeviceId() {
		return deviceId;
	}
	
	public int getDeviceComponentId() {
		return deviceComponentId;
	}
}
