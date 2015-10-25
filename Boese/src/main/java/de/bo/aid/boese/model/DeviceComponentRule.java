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
 * The Class DeviceComponentRule.
 */
public class DeviceComponentRule {

	/** The id. */
	private DeviceComponentRuleId id;
	
	/** The devicecomponent. */
	private DeviceComponent devicecomponent;
	
	/** The rule. */
	private Rule rule;

	/**
	 * Instantiates a new device component rule.
	 */
	public DeviceComponentRule() {
	}

	/**
	 * Instantiates a new device component rule.
	 *
	 * @param id the id
	 * @param devicecomponent the devicecomponent
	 * @param rule the rule
	 */
	public DeviceComponentRule(DeviceComponentRuleId id, DeviceComponent devicecomponent, Rule rule) {
		this.id = id;
		this.devicecomponent = devicecomponent;
		this.rule = rule;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public DeviceComponentRuleId getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(DeviceComponentRuleId id) {
		this.id = id;
	}

	/**
	 * Gets the devicecomponent.
	 *
	 * @return the devicecomponent
	 */
	public DeviceComponent getDevicecomponent() {
		return this.devicecomponent;
	}

	/**
	 * Sets the devicecomponent.
	 *
	 * @param devicecomponent the new devicecomponent
	 */
	public void setDevicecomponent(DeviceComponent devicecomponent) {
		this.devicecomponent = devicecomponent;
	}

	/**
	 * Gets the rule.
	 *
	 * @return the rule
	 */
	public Rule getRule() {
		return this.rule;
	}

	/**
	 * Sets the rule.
	 *
	 * @param rule the new rule
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}

}
