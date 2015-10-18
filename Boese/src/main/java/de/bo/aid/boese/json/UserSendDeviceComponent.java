

package de.bo.aid.boese.json;

import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSendDeviceComponent.
 */
public class UserSendDeviceComponent extends BoeseJson {
	
	/** The component list. */
	HashSet<DeviceComponents> componentList;
	
	/** The device id. */
	int deviceId;
	
	/**
	 * Instantiates a new user send device component.
	 *
	 * @param deviceId the device id
	 * @param decoSet the deco set
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserSendDeviceComponent(int deviceId, HashSet<DeviceComponents> decoSet, int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDDEVICECOMPONENT, connectorId, status, timestamp);
		this.componentList = decoSet;
		this.deviceId = deviceId;
	}

	/**
	 * Gets the component list.
	 *
	 * @return the component list
	 */
	public HashSet<DeviceComponents> getComponentList() {
		return componentList;
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
