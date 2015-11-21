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
// Generated 30.07.2015 11:14:41 by Hibernate Tools 4.3.1

/**
 * GroupUser generated by hbm2java.
 */
public class GroupUser implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private GroupUserId id;
	
	/** The group. */
	private Group group;
	
	/** The user. */
	private User user;
	
	/** The position. */
	private Short position;

	/**
	 * Instantiates a new group user.
	 */
	public GroupUser() {
	}

	/**
	 * Instantiates a new group user.
	 *
	 * @param id the id
	 * @param group the group
	 * @param user the user
	 */
	public GroupUser(GroupUserId id, Group group, User user) {
		this.id = id;
		this.group = group;
		this.user = user;
	}

	/**
	 * Instantiates a new group user.
	 *
	 * @param id the id
	 * @param group the group
	 * @param user the user
	 * @param position the position
	 */
	public GroupUser(GroupUserId id, Group group, User user, Short position) {
		this.id = id;
		this.group = group;
		this.user = user;
		this.position = position;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public GroupUserId getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(GroupUserId id) {
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
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public Short getPosition() {
		return this.position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(Short position) {
		this.position = position;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupUser other = (GroupUser) obj;
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
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
