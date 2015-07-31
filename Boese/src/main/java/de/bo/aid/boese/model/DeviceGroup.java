package de.bo.aid.boese.model;
// Generated 30.07.2015 11:14:41 by Hibernate Tools 4.3.1

/**
 * DeviceGroup generated by hbm2java
 */
public class DeviceGroup implements java.io.Serializable {

	private DeviceGroupId id;
	private Device device;
	private Group group;
	private short rights;

	public DeviceGroup() {
	}

	public DeviceGroup(DeviceGroupId id, Device device, Group group, short rights) {
		this.id = id;
		this.device = device;
		this.group = group;
		this.rights = rights;
	}

	public DeviceGroupId getId() {
		return this.id;
	}

	public void setId(DeviceGroupId id) {
		this.id = id;
	}

	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public short getRights() {
		return this.rights;
	}

	public void setRights(short rights) {
		this.rights = rights;
	}

}
