
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
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public RequestAllDevices(int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.REQUESTALLDEVICES, connectorId, seqNr, ackNr, status, timestamp);
	}
	
	public RequestAllDevices(int connectorId, int seqNr, int ackNr, int status,
			long timestamp, boolean isUserRequest) {
		super(MessageType.USERREQUESTALLDEVICES, connectorId, seqNr, ackNr, status, timestamp);
		this.userRequest = isUserRequest;
	}
	
	public boolean isUserRequest() {
		return userRequest;
	}

}
