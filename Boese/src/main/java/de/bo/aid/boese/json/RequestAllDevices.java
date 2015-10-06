
package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestAllDevices.
 */
public class RequestAllDevices extends BoeseJson {
	boolean userRequest = false;
	/**
	 * Instantiates a new request all devices.
	 *
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public RequestAllDevices(int connectorId, int status, long timestamp) {
		super(MessageType.REQUESTALLDEVICES, connectorId, status, timestamp);
	}
	
	public RequestAllDevices(int connectorId, int status, long timestamp, boolean isUserRequest) {
		super(MessageType.USERREQUESTALLDEVICES, connectorId, status, timestamp);
		this.userRequest = isUserRequest;
	}
	
	public boolean isUserRequest() {
		return userRequest;
	}

}
