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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * DeviceComponentRule Model for Hibernate.
 */
@Entity
@Table(name="\"DeviceComponentRule\"")
public class DeviceComponentRule {

	/** The DeviceComponentRule id. */
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	
	/** The linked devicecomponent. */
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "deviceComponent", nullable = false)
	private DeviceComponent deviceComponent;
	
	/** The linked rule. */
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "rule", nullable = false)
	private Rule rule;

	/**
	 * Instantiates a new device component rule.
	 */
	public DeviceComponentRule() {
	}

	/**
	 * Instantiates a new device component rule for DB Insert.
	 *
	 * @param devicecomponent the linked devicecomponent
	 * @param rule the linked rule
	 */
	public DeviceComponentRule(DeviceComponent devicecomponent, Rule rule) {
		this.deviceComponent = devicecomponent;
		this.rule = rule;
	}

	/**
	 * Instantiates a new device component rule.
	 *
	 * @param id the DeviceComponentRule id
	 * @param devicecomponent the linked devicecomponent
	 * @param rule the linked rule
	 */
	public DeviceComponentRule(int id, DeviceComponent devicecomponent, Rule rule) {
		this.id = id;
		this.deviceComponent = devicecomponent;
		this.rule = rule;
	}

	/**
	 * Gets the DeviceComponentRule id.
	 *
	 * @return the DeviceComponentRule id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the DeviceComponentRule id.
	 *
	 * @param id the new DeviceComponentRule id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the linked devicecomponent.
	 *
	 * @return the linked devicecomponent
	 */
	public DeviceComponent getDevicecomponent() {
		return this.deviceComponent;
	}

	/**
	 * Sets the linked devicecomponent.
	 *
	 * @param devicecomponent the new linked devicecomponent
	 */
	public void setDevicecomponent(DeviceComponent devicecomponent) {
		this.deviceComponent = devicecomponent;
	}

	/**
	 * Gets the linked rule.
	 *
	 * @return the linked rule
	 */
	public Rule getRule() {
		return this.rule;
	}

	/**
	 * Sets the linked rule.
	 *
	 * @param rule the new linked rule
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}

	/**
	 *  
	 * To compare two DeviceComponentRules.
	 *
	 * @param obj the DeviceComponentRule object to compare
	 * @return true if both DeviceComponentRules are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceComponentRule other = (DeviceComponentRule) obj;
		if (deviceComponent == null) {
			if (other.deviceComponent != null)
				return false;
		} else if (!deviceComponent.equals(other.deviceComponent))
			return false;
		if (id != other.id) {
			return false;
		}
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		return true;
	}

}
