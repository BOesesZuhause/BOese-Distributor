package de.bo.aid.boese.simulation;

import java.util.ArrayList;
import java.util.List;

public class Device {
	
	private int id;
	
	private String name;
	
	private List<Component> components = new ArrayList<>();

	public Device(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.components = components;
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

	public List<Component> getComponents() {
		return components;
	}

	public void addComponent(Component component) {
		components.add(component);
	}
	

}
