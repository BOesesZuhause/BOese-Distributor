
package de.bo.aid.boese.model;
// Generated 30.07.2015 11:14:41 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * Group generated by hbm2java.
 */
public class Group implements java.io.Serializable {

	/** The gr id. */
	private short grId;
	
	/** The name. */
	private String name;
	
	/** The group zones. */
	private Set groupZones = new HashSet(0);
	
	/** The device groups. */
	private Set deviceGroups = new HashSet(0);
	
	/** The group users. */
	private Set groupUsers = new HashSet(0);

	/**
	 * Instantiates a new group.
	 */
	public Group() {
	}

	/**
	 * Instantiates a new group.
	 *
	 * @param grId the gr id
	 * @param name the name
	 */
	public Group(short grId, String name) {
		this.grId = grId;
		this.name = name;
	}

	/**
	 * Instantiates a new group.
	 *
	 * @param grId the gr id
	 * @param name the name
	 * @param groupZones the group zones
	 * @param deviceGroups the device groups
	 * @param groupUsers the group users
	 */
	public Group(short grId, String name, Set groupZones, Set deviceGroups, Set groupUsers) {
		this.grId = grId;
		this.name = name;
		this.groupZones = groupZones;
		this.deviceGroups = deviceGroups;
		this.groupUsers = groupUsers;
	}

	/**
	 * Gets the gr id.
	 *
	 * @return the gr id
	 */
	public short getGrId() {
		return this.grId;
	}

	/**
	 * Sets the gr id.
	 *
	 * @param grId the new gr id
	 */
	public void setGrId(short grId) {
		this.grId = grId;
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
	 * Gets the device groups.
	 *
	 * @return the device groups
	 */
	public Set getDeviceGroups() {
		return this.deviceGroups;
	}

	/**
	 * Sets the device groups.
	 *
	 * @param deviceGroups the new device groups
	 */
	public void setDeviceGroups(Set deviceGroups) {
		this.deviceGroups = deviceGroups;
	}

	/**
	 * Gets the group users.
	 *
	 * @return the group users
	 */
	public Set getGroupUsers() {
		return this.groupUsers;
	}

	/**
	 * Sets the group users.
	 *
	 * @param groupUsers the new group users
	 */
	public void setGroupUsers(Set groupUsers) {
		this.groupUsers = groupUsers;
	}

}
