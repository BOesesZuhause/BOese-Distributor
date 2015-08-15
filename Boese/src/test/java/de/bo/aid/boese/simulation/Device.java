package de.bo.aid.boese.simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import de.bo.aid.boese.json.DeviceComponents;

public class Device {
	
	private int id;
	
	private String name;
	
	private HashSet<DeviceComponents> components = new HashSet<>();

	public Device(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashSet<DeviceComponents> getComponents() {
		return components;
	}

	public void addComponent(DeviceComponents component) {
		components.add(component);
	}
	

}
