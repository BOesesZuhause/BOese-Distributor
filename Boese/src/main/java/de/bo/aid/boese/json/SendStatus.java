

package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class SendStatus.
 */
public class SendStatus extends BoeseJson {
	
	/** The status code. */
	private int statusCode;
	
	/** The device component is. */
	private int deviceComponentIs;
	
	/** The status tmestamp. */
	private long statusTmestamp;

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

	/**
	 * Gets the status code.
	 *
	 * @return the status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Gets the device component id.
	 *
	 * @return the device component id
	 */
	public int getDeviceComponentId() {
		return deviceComponentIs;
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
