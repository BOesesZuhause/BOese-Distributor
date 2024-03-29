/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 */



package de.bo.aid.boese.modelJPA;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

// TODO: Auto-generated Javadoc
/**
 * Component Model for Hibernate generated by hbm2java.
 */
@Entity
public class Component implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Component id. */
	@Id
	@GeneratedValue
	private int coId;
	
	/** The unit. */
	private Unit unit;
	
	/** The name. */
	@Column(nullable = false)
	private String name;
	
	/**  Is this a Actor?. */
	@Column(nullable = false)
	private boolean actor;
	
	/** The connected deviceComponents. */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "component")
	private Set<DeviceComponent> deviceComponents = new HashSet<DeviceComponent>(0);

	/**
	 * Instantiates a new component.
	 */
	public Component() {
	
	}

	/**
	 * Instantiates a new component for DB Insert.
	 *
	 * @param name the name
	 * @param actor Is this a Actor
	 */
	public Component(String name, boolean actor) {
		this.name = name;
		this.actor = actor;
	}

	/**
	 * Instantiates a new component.
	 *
	 * @param coId the Component id
	 * @param unit the unit
	 * @param name the name
	 * @param actor Is this a Actor
	 */
	public Component(int coId, Unit unit, String name, boolean actor) {
		this.coId = coId;
		this.unit = unit;
		this.name = name;
		this.actor = actor;
	}

	/**
	 * Instantiates a new component.
	 *
	 * @param coId the Component id
	 * @param unit the unit
	 * @param name the name
	 * @param actor Is this a Actor
	 * @param deviceComponents the connected deviceComponents
	 */
	public Component(int coId, Unit unit, String name, boolean actor, Set<DeviceComponent> deviceComponents) {
		this.coId = coId;
		this.unit = unit;
		this.name = name;
		this.actor = actor;
		this.deviceComponents = deviceComponents;
	}

	/**
	 * Gets the Component id.
	 *
	 * @return the Component id
	 */
	public int getCoId() {
		return this.coId;
	}

	/**
	 * Sets the Component id.
	 *
	 * @param coId the new Component id
	 */
	public void setCoId(int coId) {
		this.coId = coId;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public Unit getUnit() {
		return this.unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit the new unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
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
	 * Checks if this is a Actor.
	 *
	 * @return true, if this is a Actor
	 */
	public boolean isActor() {
		return this.actor;
	}

	/**
	 * Sets if this a Actor.
	 *
	 * @param actor true if this is a Actor
	 */
	public void setActor(boolean actor) {
		this.actor = actor;
	}

	/**
	 * Gets the connected deviceComponents.
	 *
	 * @return the connected deviceComponents
	 */
	public Set<DeviceComponent> getDeviceComponents() {
		return this.deviceComponents;
	}

	/**
	 * Sets the connected deviceComponents.
	 *
	 * @param deviceComponents the new connected deviceComponents
	 */
	public void setDeviceComponents(Set<DeviceComponent> deviceComponents) {
		this.deviceComponents = deviceComponents;
	}

	/**
	 *  
	 * To compare two Components.
	 *
	 * @param obj the Component object to compare
	 * @return true if both Components are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		if (actor != other.actor)
			return false;
		if (coId != other.coId)
			return false;
		if (deviceComponents == null) {
			if (other.deviceComponents != null)
				return false;
		} else if (!deviceComponents.equals(other.deviceComponents))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}

}
