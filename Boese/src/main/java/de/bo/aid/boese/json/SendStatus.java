package de.bo.aid.boese.json;

public class SendStatus extends BoeseJson {
	private int statusCode;
	private int deviceComponentIs;
	private long statusTmestamp;

	public SendStatus(int deviceComponentId, int statusCode, long statusTimestamp, boolean isSendStatus,
			int connectorId, int status, long timestamp) {
		super(MessageType.SENDSTATUS, connectorId, status, timestamp);
		if (!isSendStatus) {
			this.messageType = MessageType.CONFIRMSTATUS;
		}
		this.statusCode = statusCode;
		this.deviceComponentIs = deviceComponentId;
		this.statusTmestamp = statusTimestamp;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public int getDeviceComponentId() {
		return deviceComponentIs;
	}
	
	public long getStatusTimestamp() {
		return statusTmestamp;
	}
}
