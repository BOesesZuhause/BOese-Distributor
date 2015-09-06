
package de.bo.aid.boese.model;
// Generated 30.07.2015 11:14:41 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * Zone generated by hbm2java.
 */
public class Zone implements java.io.Serializable {

	/** The zo id. */
	private int zoId;
	
	/** The zone. */
	private Zone zone;
	
	/** The name. */
	private String name;
	
	/** The group zones. */
	private Set groupZones = new HashSet(0);
	
	/** The zones. */
	private Set zones = new HashSet(0);
	
	/** The devices. */
	private Set devices = new HashSet(0);

	/**
	 * Instantiates a new zone.
	 */
	public Zone() {
	}

	/**
	 * Instantiates a new zone.
	 *
	 * @param zoId the zo id
	 * @param zone the zone
	 * @param name the name
	 */
	public Zone(int zoId, Zone zone, String name) {
		this.zoId = zoId;
		this.zone = zone;
		this.name = name;
	}

	/**
	 * Instantiates a new zone.
	 *
	 * @param zoId the zo id
	 * @param zone the zone
	 * @param name the name
	 * @param groupZones the group zones
	 * @param zones the zones
	 * @param devices the devices
	 */
	public Zone(int zoId, Zone zone, String name, Set groupZones, Set zones, Set devices) {
		this.zoId = zoId;
		this.zone = zone;
		this.name = name;
		this.groupZones = groupZones;
		this.zones = zones;
		this.devices = devices;
	}

	/**
	 * Gets the zo id.
	 *
	 * @return the zo id
	 */
	public int getZoId() {
		return this.zoId;
	}

	/**
	 * Sets the zo id.
	 *
	 * @param zoId the new zo id
	 */
	public void setZoId(int zoId) {
		this.zoId = zoId;
	}

	/**
	 * Gets the zone.
	 *
	 * @return the zone
	 */
	public Zone getZone() {
		return this.zone;
	}

	/**
	 * Sets the zone.
	 *
	 * @param zone the new zone
	 */
	public void setZone(Zone zone) {
		this.zone = zone;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
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
	 * Gets the group zones.
	 *
	 * @return the group zones
	 */
	public Set getGroupZones() {
		return this.groupZones;
	}

	/**
	 * Sets the group zones.
	 *
	 * @param groupZones the new group zones
	 */
	public void setGroupZones(Set groupZones) {
		this.groupZones = groupZones;
	}

	/**
	 * Gets the zones.
	 *
	 * @return the zones
	 */
	public Set getZones() {
		return this.zones;
	}

	/**
	 * Sets the zones.
	 *
	 * @param zones the new zones
	 */
	public void setZones(Set zones) {
		this.zones = zones;
	}

	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public Set getDevices() {
		return this.devices;
	}

	/**
	 * Sets the devices.
	 *
	 * @param devices the new devices
	 */
	public void setDevices(Set devices) {
		this.devices = devices;
	}

}
