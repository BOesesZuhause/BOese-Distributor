

package de.bo.aid.boese.json;

import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class UserRequestDeviceComponents.
 */
public class UserRequestDeviceComponents extends BoeseJson{
	
	/** The device ids. */
	HashSet<Integer> deviceIds;
	
	/**
	 * Instantiates a new user request device components.
	 *
	 * @param deviceIds the device ids
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserRequestDeviceComponents(HashSet<Integer> deviceIds, int connectorId, int status, long timestamp) {
		super(MessageType.USERREQUESTDEVICECOMPONENTS, connectorId, status, timestamp);
		this.deviceIds = deviceIds;
	}
	
	/**
	 * Gets the device ids.
	 *
	 * @return the device ids
	 */
	public HashSet<Integer> getDeviceIds() {
		return deviceIds;
	}

}
