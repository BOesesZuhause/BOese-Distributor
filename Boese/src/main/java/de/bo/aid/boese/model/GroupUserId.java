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

/**
 * GroupUserId Model for Hibernate generated by hbm2java.
 */
public class GroupUserId implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Group id. */
	private short grId;
	
	/** The User id. */
	private int usId;

	/**
	 * Instantiates a new group user id.
	 */
	public GroupUserId() {
	}

	/**
	 * Instantiates a new group user id.
	 *
	 * @param grId the Group id
	 * @param usId the User id
	 */
	public GroupUserId(short grId, int usId) {
		this.grId = grId;
		this.usId = usId;
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
	 * Gets the User id.
	 *
	 * @return the User id
	 */
	public int getUsId() {
		return this.usId;
	}

	/**
	 * Sets the User id.
	 *
	 * @param usId the new User id
	 */
	public void setUsId(int usId) {
		this.usId = usId;
	}

	/** 
	 * To compare two GroupUsereIds
	 * 
	 * @param other the GroupUserId object to compare
	 * @return true if both GroupUserIds are equal
	 */
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GroupUserId))
			return false;
		GroupUserId castOther = (GroupUserId) other;

		return (this.getGrId() == castOther.getGrId()) && (this.getUsId() == castOther.getUsId());
	}

	/** 
	 * To create the hashCode
	 * 
	 * @return the hashCode
	 */
	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getGrId();
		result = 37 * result + this.getUsId();
		return result;
	}

}
