

package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class Zone.
 */
public class Zone {
	
	/** The zone id. */
	private int zoneId;
	
	/** The super zone id. */
	private int superZoneId;
	
	/** The zone name. */
	private String zoneName;
	
	/**
	 * Instantiates a new zone.
	 *
	 * @param zoneId the zone id
	 * @param superZoneId the super zone id
	 * @param name the name
	 */
	public Zone(int zoneId, int superZoneId, String name) {
		this.zoneId = zoneId;
		this.superZoneId = superZoneId;
		this.zoneName = name;
	}

	/**
	 * Gets the zone id.
	 *
	 * @return the zone id
	 */
	public int getZoneId() {
		return zoneId;
	}

	/**
	 * Gets the super zone id.
	 *
	 * @return the super zone id
	 */
	public int getSuperZoneId() {
		return superZoneId;
	}

	/**
	 * Gets the zone name.
	 *
	 * @return the zone name
	 */
	public String getZoneName() {
		return zoneName;
	}
	
}
