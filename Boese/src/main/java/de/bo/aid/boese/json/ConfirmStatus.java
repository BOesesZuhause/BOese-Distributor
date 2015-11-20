package de.bo.aid.boese.json;


public class ConfirmStatus extends BoeseJson {
	
	
	/** The device component is. */
	private int deviceComponentIs;
	
	/** The status tmestamp. */
	private long statusTmestamp;

	private int statusCode;

	/**
	 * Instantiates a new send status.
	 *
	 * @param deviceComponentId the device component id
	 * @param statusCode the status code
	 * @param statusTimestamp the status timestamp
	 * @param isSendStatus the is send status
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public ConfirmStatus(int deviceComponentId, int statusCode, long statusTimestamp, boolean isSendStatus,
			int connectorId, int status, long timestamp) {
		super(MessageType.CONFIRMSTATUS, connectorId, status, timestamp);
		if (!isSendStatus) {
			this.messageType = MessageType.CONFIRMSTATUS;
		}
		this.deviceComponentIs = deviceComponentId;
		this.statusCode = statusCode;
		this.statusTmestamp = statusTimestamp;
	}


	/**
	 * Gets the device component id.
	 *
	 * @return the device component id
	 */
	public int getDeviceComponentId() {
		return deviceComponentIs;
	}
	
	public int getStatusCode(){
		return statusCode;
	}
	
	/**
	 * Gets the status timestamp.
	 *
	 * @return the status timestamp
	 */
	public long getStatusTimestamp() {
		return statusTmestamp;
	}
}
