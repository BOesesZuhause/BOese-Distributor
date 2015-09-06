
package de.bo.aid.boese.json;


// TODO: Auto-generated Javadoc
/**
 * The Class SendValue.
 */
public class SendValue extends BoeseJson {
	
	/** The device id. */
	private int deviceId;
	
	/** The device component id. */
	private int deviceComponentId;
	
	/** The value. */
	private double value;
	
	/** The value timestamp. */
	private long valueTimestamp;
	
	
	/**
	 * Instantiates a new send value.
	 *
	 * @param deviceId the device id
	 * @param deviceComponentId the device component id
	 * @param value the value
	 * @param timestamp the timestamp
	 * @param idConnector the id connector
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param headerTimestamp the header timestamp
	 */
	public SendValue(int deviceId, int deviceComponentId, double value, long timestamp, 
			int idConnector, int seqNr, int ackNr, int status, long headerTimestamp) {
		super(MessageType.SENDVALUE, idConnector, seqNr, ackNr, status, headerTimestamp);
		this.deviceId = deviceId;
		this.deviceComponentId = deviceComponentId;
		this.value = value;
		this.valueTimestamp = timestamp;
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

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Gets the value timestamp.
	 *
	 * @return the value timestamp
	 */
	public long getValueTimestamp() {
		return valueTimestamp;
	}
}
