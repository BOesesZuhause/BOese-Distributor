


package de.bo.aid.boese.main.model;

// TODO: Auto-generated Javadoc
/**
 * The Class TempDevice.
 */
public class TempDevice {
	
	/** The name. */
	private String name;
	
	/** The connector id. */
	private int connectorID;
	
	/**
	 * Instantiates a new temp device.
	 *
	 * @param connectorId the connector id
	 * @param name the name
	 */
	public TempDevice(int connectorId, String name) {
		this.name = name;
		this.connectorID = connectorId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the connector id.
	 *
	 * @return the connector id
	 */
	public int getConnectorID() {
		return connectorID;
	}

	/**
	 * Sets the connector id.
	 *
	 * @param connectorID the new connector id
	 */
	public void setConnectorID(int connectorID) {
		this.connectorID = connectorID;
	}

}
