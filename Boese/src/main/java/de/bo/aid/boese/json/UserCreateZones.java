package de.bo.aid.boese.json;

import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class UserCreateZones.
 */
public class UserCreateZones extends BoeseJson {
	
	/** The zones. */
	private HashSet<ZoneJSON> zones;

	/**
	 * Instantiates a new user create zones.
	 *
	 * @param zones the zones
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	protected UserCreateZones(HashSet<ZoneJSON> zones, int connectorId, int status, long timestamp) {
		super(MessageType.USERCREATEZONES, connectorId, status, timestamp);
		this.zones = zones;
	}

	/**
	 * Gets the zones.
	 *
	 * @return the zones
	 */
	public HashSet<ZoneJSON> getZones() {
		return zones;
	}

}
