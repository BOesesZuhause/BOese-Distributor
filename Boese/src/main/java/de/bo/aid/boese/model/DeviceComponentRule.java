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



package de.bo.aid.boese.model;

// TODO: Auto-generated Javadoc
/**
 * DeviceComponentRule Model for Hibernate.
 */
public class DeviceComponentRule {

	/** The DeviceComponentRule id. */
	private DeviceComponentRuleId id;
	
	/** The linked devicecomponent. */
	private DeviceComponent devicecomponent;
	
	/** The linked rule. */
	private Rule rule;

	/**
	 * Instantiates a new device component rule.
	 */
	public DeviceComponentRule() {
	}

	/**
	 * Instantiates a new device component rule.
	 *
	 * @param id the DeviceComponentRule id
	 * @param devicecomponent the linked devicecomponent
	 * @param rule the linked rule
	 */
	public DeviceComponentRule(DeviceComponentRuleId id, DeviceComponent devicecomponent, Rule rule) {
		this.id = id;
		this.devicecomponent = devicecomponent;
		this.rule = rule;
	}

	/**
	 * Gets the DeviceComponentRule id.
	 *
	 * @return the DeviceComponentRule id
	 */
	public DeviceComponentRuleId getId() {
		return this.id;
	}

	/**
	 * Sets the DeviceComponentRule id.
	 *
	 * @param id the new DeviceComponentRule id
	 */
	public void setId(DeviceComponentRuleId id) {
		this.id = id;
	}

	/**
	 * Gets the linked devicecomponent.
	 *
	 * @return the linked devicecomponent
	 */
	public DeviceComponent getDevicecomponent() {
		return this.devicecomponent;
	}

	/**
	 * Sets the linked devicecomponent.
	 *
	 * @param devicecomponent the new linked devicecomponent
	 */
	public void setDevicecomponent(DeviceComponent devicecomponent) {
		this.devicecomponent = devicecomponent;
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
		if (devicecomponent == null) {
			if (other.devicecomponent != null)
				return false;
		} else if (!devicecomponent.equals(other.devicecomponent))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		return true;
	}

}
