package de.bo.aid.boese.json;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class UserConfirmUnits.
 */
public class UserConfirmUnits extends BoeseJson {

	/** The temp units. */
	private HashMap<Integer, Integer> tempUnits;
	
	/**
	 * Instantiates a new user confirm units.
	 *
	 * @param tempUnits the temp units
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserConfirmUnits(HashMap<Integer, Integer> tempUnits, int connectorId, int status, long timestamp) {
		super(MessageType.USERCONFIRMUNITS, connectorId, status, timestamp);
		this.tempUnits = tempUnits;
	}

	/**
	 * Gets the temp units.
	 *
	 * @return the temp units
	 */
	public HashMap<Integer, Integer> getTempUnits() {
		return tempUnits;
	}

}
