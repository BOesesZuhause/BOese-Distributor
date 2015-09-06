
package de.bo.aid.boese.json;

import java.util.HashMap;


// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmDeviceComponents.
 */
public class ConfirmDeviceComponents extends BoeseJson {
	
	/** The components. */
	private HashMap<String, Integer> components;
	
	/** The device id. */
	private int deviceId;
	
	/**
	 * Instantiates a new confirm device components.
	 *
	 * @param deviceId the device id
	 * @param components the components
	 * @param connectorId the connector id
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public ConfirmDeviceComponents(int deviceId, HashMap<String, Integer> components, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		super(MessageType.CONFIRMDEVICECOMPONENTS, connectorId, seqNr, ackNr, status, timestamp);
		this.components = components;
		this.deviceId = deviceId;
	}

	/**
	 * Gets the components.
	 *
	 * @return the components
	 */
	public HashMap<String, Integer> getComponents() {
		return components;
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public int getDeviceId() {
		return deviceId;
	}
}
