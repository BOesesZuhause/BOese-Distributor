

package de.bo.aid.boese.json;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSendConnectors.
 */
public class UserSendConnectors extends BoeseJson{
	
	/** The connectors. */
	HashMap<Integer, String> connectors;

	/**
	 * Instantiates a new user send connectors.
	 *
	 * @param connectors the connectors
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserSendConnectors(HashMap<Integer, String> connectors, int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDCONNETORS, connectorId, status, timestamp);
		this.connectors = connectors;
	}
	
	/**
	 * Gets the connectors.
	 *
	 * @return the connectors
	 */
	public HashMap<Integer, String> getConnectors() {
		return connectors;
	}

}
