package de.bo.aid.boese.model;
// Generated 30.07.2015 11:14:41 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * Group generated by hbm2java
 */
public class Group implements java.io.Serializable {

	private short grId;
	private String name;
	private Set groupZones = new HashSet(0);
	private Set deviceGroups = new HashSet(0);
	private Set groupUsers = new HashSet(0);

	public Group() {
	}

	public Group(short grId, String name) {
		this.grId = grId;
		this.name = name;
	}

	public Group(short grId, String name, Set groupZones, Set deviceGroups, Set groupUsers) {
		this.grId = grId;
		this.name = name;
		this.groupZones = groupZones;
		this.deviceGroups = deviceGroups;
		this.groupUsers = groupUsers;
	}

	public short getGrId() {
		return this.grId;
	}

	public void setGrId(short grId) {
		this.grId = grId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getGroupZones() {
		return this.groupZones;
	}

	public void setGroupZones(Set groupZones) {
		this.groupZones = groupZones;
	}

	public Set getDeviceGroups() {
		return this.deviceGroups;
	}

	public void setDeviceGroups(Set deviceGroups) {
		this.deviceGroups = deviceGroups;
	}

	public Set getGroupUsers() {
		return this.groupUsers;
	}

	public void setGroupUsers(Set groupUsers) {
		this.groupUsers = groupUsers;
	}

}
