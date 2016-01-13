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
 * DeviceGroupId Model for Hibernate generated by hbm2java.
 */
public class DeviceGroupId implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Device id. */
	private int deId;
	
	/** The Group id. */
	private short grId;

	/**
	 * Instantiates a new device group id.
	 */
	public DeviceGroupId() {
	}

	/**
	 * Instantiates a new device group id.
	 *
	 * @param deId the Device id
	 * @param grId the Group id
	 */
	public DeviceGroupId(int deId, short grId) {
		this.deId = deId;
		this.grId = grId;
	}

	/**
	 * Gets the Device id.
	 *
	 * @return the Device id
	 */
	public int getDeId() {
		return this.deId;
	}

	/**
	 * Sets the Device id.
	 *
	 * @param deId the new Device id
	 */
	public void setDeId(int deId) {
		this.deId = deId;
	}

	/**
	 * Gets the Group id.
	 *
	 * @return the Group id
	 */
	public short getGrId() {
		return this.grId;
	}

	/**
	 * Sets the Group id.
	 *
	 * @param grId the new Group id
	 */
	public void setGrId(short grId) {
		this.grId = grId;
	}

	/**
	 *  
	 * To compare two DeviceGroupIds.
	 *
	 * @param other the DeviceGroupId object to compare
	 * @return true if both DeviceGroupIds are equal
	 */
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DeviceGroupId))
			return false;
		DeviceGroupId castOther = (DeviceGroupId) other;

		return (this.getDeId() == castOther.getDeId()) && (this.getGrId() == castOther.getGrId());
	}

	/**
	 *  
	 * To create the hashCode.
	 *
	 * @return the hashCode
	 */
	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getDeId();
		result = 37 * result + this.getGrId();
		return result;
	}

}
