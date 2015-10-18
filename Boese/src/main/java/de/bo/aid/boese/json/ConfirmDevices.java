


package de.bo.aid.boese.json;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmDevices.
 */
public class ConfirmDevices extends BoeseJson {

/** The devices. */
private HashMap<String, Integer> devices;
	
	/**
	 * Instantiates a new confirm devices.
	 *
	 * @param devices the devices
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public ConfirmDevices(HashMap<String, Integer> devices, int connectorId, int status, long timestamp) {
		super(MessageType.CONFIRMDEVICES, connectorId, status, timestamp);
		this.devices = devices;
	}

	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public HashMap<String, Integer> getDevices() {
		return devices;
	}

}
