
package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmConnection.
 */
public class ConfirmConnection extends BoeseJson {
	
	/** The password. */
	private String password;
	
	/**
	 * Instantiates a new confirm connection.
	 *
	 * @param password the password
	 * @param connectorId the connector id
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public ConfirmConnection(String password, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.CONFIRMCONNECTION, connectorId, seqNr, ackNr, status, timestamp);
		this.password = password;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
}
