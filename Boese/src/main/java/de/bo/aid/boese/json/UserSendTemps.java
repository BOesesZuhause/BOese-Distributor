

package de.bo.aid.boese.json;

import java.util.HashMap;

import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSendTemps.
 */
public class UserSendTemps extends BoeseJson{
	
	/** The temp connectors. */
	private HashMap<Integer, String> tempConnectors;
	
	/** The temp devices. */
	private HashMap<Integer, TempDevice> tempDevices;
	
	/** The temp device components. */
	private HashMap<Integer, TempComponent> tempDeviceComponents;

	/**
	 * Instantiates a new user send temps.
	 *
	 * @param tempConnectors the temp connectors
	 * @param tempDevices the temp devices
	 * @param tempDeviceComponents the temp device components
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserSendTemps(HashMap<Integer, String> tempConnectors, HashMap<Integer, TempDevice> tempDevices, 
			HashMap<Integer, TempComponent> tempDeviceComponents,
			int connectorId, int status, long timestamp) {
		super(MessageType.USERSENDTEMPS, connectorId, status, timestamp);
		this.tempConnectors = tempConnectors;
		this.tempDeviceComponents = tempDeviceComponents;
		this.tempDevices = tempDevices;
	}

	/**
	 * Gets the temp connectors.
	 *
	 * @return the temp connectors
	 */
	public HashMap<Integer, String> getTempConnectors() {
		return tempConnectors;
	}

	/**
	 * Gets the temp devices.
	 *
	 * @return the temp devices
	 */
	public HashMap<Integer, TempDevice> getTempDevices() {
		return tempDevices;
	}

	/**
	 * Gets the temp device components.
	 *
	 * @return the temp device components
	 */
	public HashMap<Integer, TempComponent> getTempDeviceComponents() {
		return tempDeviceComponents;
	}
}
