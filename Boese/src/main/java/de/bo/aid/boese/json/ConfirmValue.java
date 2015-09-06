
package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmValue.
 */
public class ConfirmValue extends BoeseJson {
	
	/** The device id. */
	private int deviceId;
	
	/** The device component id. */
	private int deviceComponentId;

	/**
	 * Instantiates a new confirm value.
	 *
	 * @param deviceId the device id
	 * @param deviceComponentId the device component id
	 * @param connectorId the connector id
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public ConfirmValue(int deviceId, int deviceComponentId, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		super(MessageType.CONFIRMVALUE, connectorId, seqNr, ackNr, status, timestamp);
		this.deviceId = deviceId;
		this.deviceComponentId = deviceComponentId;
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public int getDeviceId() {
		return deviceId;
	}

	/**
	 * Gets the device component id.
	 *
	 * @return the device component id
	 */
	public int getDeviceComponentId() {
		return deviceComponentId;
	}
}
