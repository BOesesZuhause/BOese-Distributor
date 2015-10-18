

package de.bo.aid.boese.json;

import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSendDevices.
 */
public class UserSendDevices extends BoeseJson {
	
	/** The device list. */
	HashSet<UserDevice> deviceList;

	/**
	 * Instantiates a new user send devices.
	 *
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserSendDevices(int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDDEVICES, connectorId, status, timestamp);
		deviceList = new HashSet<>();
	}
	
	/**
	 * Instantiates a new user send devices.
	 *
	 * @param deviceList the device list
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserSendDevices(HashSet<UserDevice> deviceList, int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDDEVICES, connectorId, status, timestamp);
		this.deviceList = deviceList;
	}
	
	/**
	 * Adds the device.
	 *
	 * @param device the device
	 */
	public void addDevice(UserDevice device) {
		deviceList.add(device);
	}
	
	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public HashSet<UserDevice> getDevices() {
		return deviceList;
	}
}
