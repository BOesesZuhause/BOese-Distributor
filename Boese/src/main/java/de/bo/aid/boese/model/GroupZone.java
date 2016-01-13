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
 * GroupZone Model for Hibernate generated by hbm2java.
 */
public class GroupZone implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The GroupZoneId. */
	private GroupZoneId id;
	
	/** The linked Group. */
	private Group group;
	
	/** The linked Zone. */
	private Zone zone;
	
	/** The rights in of the Group in this Zone. */
	private Short rights;

	/**
	 * Instantiates a new group zone.
	 */
	public GroupZone() {
	}

	/**
	 * Instantiates a new group zone.
	 *
	 * @param id the id
	 * @param group the group
	 * @param zone the zone
	 */
	public GroupZone(GroupZoneId id, Group group, Zone zone) {
		this.id = id;
		this.group = group;
		this.zone = zone;
	}

	/**
	 * Instantiates a new group zone.
	 *
	 * @param id the id
	 * @param group the group
	 * @param zone the zone
	 * @param rights the rights
	 */
	public GroupZone(GroupZoneId id, Group group, Zone zone, Short rights) {
		this.id = id;
		this.group = group;
		this.zone = zone;
		this.rights = rights;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public GroupZoneId getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(GroupZoneId id) {
		this.id = id;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public Group getGroup() {
		return this.group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * Gets the zone.
	 *
	 * @return the zone
	 */
	public Zone getZone() {
		return this.zone;
	}

	/**
	 * Sets the zone.
	 *
	 * @param zone the new zone
	 */
	public void setZone(Zone zone) {
		this.zone = zone;
	}

	/**
	 * Gets the rights.
	 *
	 * @return the rights
	 */
	public Short getRights() {
		return this.rights;
	}

	/**
	 * Sets the rights.
	 *
	 * @param rights the new rights
	 */
	public void setRights(Short rights) {
		this.rights = rights;
	}

	/**
	 *  
	 * To compare two GroupZones.
	 *
	 * @param obj the GroupZone object to compare
	 * @return true if both GroupZones are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupZone other = (GroupZone) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rights == null) {
			if (other.rights != null)
				return false;
		} else if (!rights.equals(other.rights))
			return false;
		if (zone == null) {
			if (other.zone != null)
				return false;
		} else if (!zone.equals(other.zone))
			return false;
		return true;
	}
}