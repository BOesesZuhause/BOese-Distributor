


package de.bo.aid.boese.json;


// TODO: Auto-generated Javadoc
/**
 * The Class RequestConnection.
 */
public class RequestConnection extends BoeseJson {
	
	/** The connector name. */
	private String connectorName;
	
	/** The password. */
	private String password;
	
	/** The user connector. */
	private boolean userConnector = false;
	
	/**
	 * Instantiates a new request connection.
	 *
	 * @param name the name
	 * @param password the password
	 * @param idConnector the id connector
	 * @param status the status
	 * @param headerTimestamp the header timestamp
	 * @param userConnector if it is an user connector
	 */
	public RequestConnection(String name, String password,
			int idConnector, int status, long headerTimestamp, boolean userConnector) {
		super(MessageType.REQUESTCONNECTION, idConnector, status, headerTimestamp);
		this.connectorName = name;
		this.password = password;
		this.userConnector = userConnector;
	}
	
	/**
	 * Instantiates a new request connection.
	 *
	 * @param name the name
	 * @param password the password
	 * @param idConnector the id connector
	 * @param status the status
	 * @param headerTimestamp the header timestamp
	 */
	public RequestConnection(String name, String password,
			int idConnector, int status, long headerTimestamp) {
		super(MessageType.REQUESTCONNECTION, idConnector, status, headerTimestamp);
		this.connectorName = name;
		this.password = password;
		this.userConnector = false;
	}
	
	/**
	 * Checks if is user connector.
	 *
	 * @return true, if is user connector
	 */
	public boolean isUserConnector() {
		return userConnector;
	}
	
	/**
	 * Gets the connector name.
	 *
	 * @return the connector name
	 */
	public String getConnectorName() {
		return connectorName;
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