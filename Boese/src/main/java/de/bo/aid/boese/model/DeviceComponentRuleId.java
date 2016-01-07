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

	/** The de co id. */
	private int deCoId;
	
	/** The ru id. */
	private int ruId;

	/**
	 * Instantiates a new device component rule id.
	 */
	public DeviceComponentRuleId() {
	}

	/**
	 * Instantiates a new device component rule id.
	 *
	 * @param deCoId the de co id
	 * @param ruId the ru id
	 */
	public DeviceComponentRuleId(int deCoId, int ruId) {
		this.deCoId = deCoId;
		this.ruId = ruId;
	}

	/**
	 * Gets the de co id.
	 *
	 * @return the de co id
	 */
	public int getDeCoId() {
		return this.deCoId;
	}

	/**
	 * Sets the de co id.
	 *
	 * @param deCoId the new de co id
	 */
	public void setDeCoId(int deCoId) {
		this.deCoId = deCoId;
	}

	/**
	 * Gets the ru id.
	 *
	 * @return the ru id
	 */
	public int getRuId() {
		return this.ruId;
	}

	/**
	 * Sets the ru id.
	 *
	 * @param ruId the new ru id
	 */
	public void setRuId(int ruId) {
		this.ruId = ruId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getDeCoId();
		result = 37 * result + this.getRuId();
		return result;
	}
}
