


package de.bo.aid.boese.ruler;

// TODO: Auto-generated Javadoc
/**
 * The Class Inquiry.
 */
public class Inquiry {
	
	/** The device component id. */
	private int deviceComponentId;
	
	/** The timestamp. */
	private long timestamp;
	
	/** The value. */
	private double value;

	/**
	 * Instantiates a new inquiry.
	 *
	 * @param deviceComponentId the device component id
	 * @param timestamp the timestamp
	 * @param value the value
	 */
	public Inquiry(int deviceComponentId, long timestamp, double value) {
		super();
		this.deviceComponentId = deviceComponentId;
		this.timestamp = timestamp;
		this.value = value;
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
	 * Sets the device component id.
	 *
	 * @param deviceComponentId the new device component id
	 */
	public void setDeviceComponentId(int deviceComponentId) {
		this.deviceComponentId = deviceComponentId;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
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
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(double value) {
		this.value = value;
	}

}
