package de.bo.aid.boese.json;

public class ConfirmValue extends BoeseJson {
	private int deviceId;
	private int deviceComponentId;

	protected ConfirmValue(int deviceId, int deviceComponentId, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		super(MessageType.CONFIRMVALUE, connectorId, seqNr, ackNr, status, timestamp);
		this.deviceId = deviceId;
		this.deviceComponentId = deviceComponentId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public int getDeviceComponentId() {
		return deviceComponentId;
	}
}
