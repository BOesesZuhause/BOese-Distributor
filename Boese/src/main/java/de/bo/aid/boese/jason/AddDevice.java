package de.bo.aid.boese.jason;

import java.util.Set;

public class AddDevice extends BoeseJson {
	String alias;
	Set<DeviceComponent> components;
	
	public AddDevice(String alias) {
		this.alias = alias;
		this.messageType = MessageType.SENDLIST;
	}
	
	public AddDevice(String alias, Set<DeviceComponent> components) {
		this.alias = alias;
		this.components = components;
		this.messageType = MessageType.SENDLIST;
	}
	
	public void addComponent(String name, String description, int unitId) {
		this.components.add(new DeviceComponent(name, description, unitId));
	}
	
	public Set<DeviceComponent> getComponents() {
		return components;
	}
	
	// TODO
	public Set<String> getComponentNames() {
		return null;
	}
	
	// TODO
	public DeviceComponent getComponentByName(String name) {
		return null;
	}
	
	// TODO
	public DeviceComponent getComponentByUnit(int unitId) {
		return null;
	}

}
