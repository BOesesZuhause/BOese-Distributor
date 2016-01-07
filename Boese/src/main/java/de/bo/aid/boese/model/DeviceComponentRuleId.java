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
 * DeviceComponentRuleId Model for Hibernate.
 */
public class DeviceComponentRuleId implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The DeviceComponentid. */
	private int deCoId;
	
	/** The Rule id. */
	private int ruId;

	/**
	 * Instantiates a new device component rule id.
	 */
	public DeviceComponentRuleId() {
	}

	/**
	 * Instantiates a new device component rule id.
	 *
	 * @param deCoId the DeviceComponentid
	 * @param ruId the Rule id
	 */
	public DeviceComponentRuleId(int deCoId, int ruId) {
		this.deCoId = deCoId;
		this.ruId = ruId;
	}

	/**
	 * Gets the DeviceComponentid.
	 *
	 * @return the DeviceComponentid
	 */
	public int getDeCoId() {
		return this.deCoId;
	}

	/**
	 * Sets the DeviceComponentid.
	 *
	 * @param deCoId the new DeviceComponentid
	 */
	public void setDeCoId(int deCoId) {
		this.deCoId = deCoId;
	}

	/**
	 * Gets the Rule id.
	 *
	 * @return the Rule id
	 */
	public int getRuId() {
		return this.ruId;
	}

	/**
	 * Sets the Rule id.
	 *
	 * @param ruId the new Rule id
	 */
	public void setRuId(int ruId) {
		this.ruId = ruId;
	}

	/** 
	 * To compare two DeviceComponentRuleIds
	 * 
	 * @param other the DeviceComponentRuleId object to compare
	 * @return true if both DeviceComponentRuleIds are equal
	 */
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DeviceComponentRuleId))
			return false;
		DeviceComponentRuleId castOther = (DeviceComponentRuleId) other;

		return (this.getDeCoId() == castOther.getDeCoId()) && (this.getRuId() == castOther.getRuId());
	}

	/** 
	 * To create the hashCode
	 * 
	 * @return the hashCode
	 */
	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getDeCoId();
		result = 37 * result + this.getRuId();
		return result;
	}
}
