

package de.bo.aid.boese.json;

import java.util.HashMap;
import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class UserConfirmTemps.
 */
public class UserConfirmTemps extends BoeseJson {
	
	/** The temp connectors. */
	HashSet<Integer> tempConnectors;
	
	/** The temp devices. */
	HashMap<Integer, Integer> tempDevices;
	
	/** The temp device components. */
	HashSet<UserTempComponent> tempDeviceComponents;

	/**
	 * Instantiates a new user confirm temps.
	 *
	 * @param tempConnectors the temp connectors
	 * @param tempDevices the temp devices
	 * @param tempDeviceComponents the temp device components
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	protected UserConfirmTemps(HashSet<Integer> tempConnectors, HashMap<Integer, Integer> tempDevices, HashSet<UserTempComponent> tempDeviceComponents, 
			int connectorId, int status, long timestamp) {
		super(MessageType.USERCONFIRMTEMPS, connectorId, status, timestamp);
		this.tempConnectors = tempConnectors;
		this.tempDevices = tempDevices;
		this.tempDeviceComponents = tempDeviceComponents;
	}

	/**
	 * Gets the temp connectors.
	 *
	 * @return the temp connectors
	 */
	public HashSet<Integer> getTempConnectors() {
		return tempConnectors;
	}

	/**
	 * Gets the temp devices.
	 *
	 * @return the temp devices
	 */
	public HashMap<Integer, Integer> getTempDevices() {
		return tempDevices;
	}

	/**
	 * Gets the temp device components.
	 *
	 * @return the temp device components
	 */
	public HashSet<UserTempComponent> getTempDeviceComponents() {
		return tempDeviceComponents;
	}
}
