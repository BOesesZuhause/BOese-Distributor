/*
 * 
 */



package de.bo.aid.boese.simulation;

import java.util.HashSet;

import de.bo.aid.boese.json.DeviceComponents;

// TODO: Auto-generated Javadoc
/**
 * The Class Device.
 */
public class Device {
	
	/** The id. */
	private int id;
	
	/** The name. */
	private String name;
	
	/** The components. */
	private HashSet<DeviceComponents> components = new HashSet<>();

	/**
	 * Instantiates a new device.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public Device(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * Gets the components.
	 *
	 * @return the components
	 */
	public HashSet<DeviceComponents> getComponents() {
		return components;
	}

	/**
	 * Adds the component.
	 *
	 * @param component the component
	 */
	public void addComponent(DeviceComponents component) {
		components.add(component);
	}
	

}
