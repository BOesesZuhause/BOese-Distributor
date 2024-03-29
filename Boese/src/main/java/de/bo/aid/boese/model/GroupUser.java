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
 * GroupUser Model for Hibernate generated by hbm2java.
 */
public class GroupUser implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The GroupUser id. */
	private GroupUserId id;
	
	/** The linked Group. */
	private Group group;
	
	/** The linked User. */
	private User user;
	
	/** The position of the User in the Group. */
	private Short position;

	/**
	 * Instantiates a new group user.
	 */
	public GroupUser() {
	}

	/**
	 * Instantiates a new group user.
	 *
	 * @param id the GroupUser id
	 * @param group the linked Group
	 * @param user the linked User
	 */
	public GroupUser(GroupUserId id, Group group, User user) {
		this.id = id;
		this.group = group;
		this.user = user;
	}

	/**
	 * Instantiates a new group user for DB insert.
	 *
	 * @param group the linked Group
	 * @param user the linked User
	 * @param position the position of the User in the Group
	 */
	public GroupUser(Group group, User user, Short position) {
		this.group = group;
		this.user = user;
		this.position = position;
	}

	/**
	 * Instantiates a new group user.
	 *
	 * @param id the GroupUser id
	 * @param group the linked Group
	 * @param user the linked User
	 * @param position the position of the User in the Group
	 */
	public GroupUser(GroupUserId id, Group group, User user, Short position) {
		this.id = id;
		this.group = group;
		this.user = user;
		this.position = position;
	}

	/**
	 * Gets the GroupUser id.
	 *
	 * @return the GroupUser id
	 */
	public GroupUserId getId() {
		return this.id;
	}

	/**
	 * Sets the GroupUser id.
	 *
	 * @param id the new GroupUser id
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
	 * Gets the position of the User in the Group.
	 *
	 * @return the position of the User in the Group
	 */
	public Short getPosition() {
		return this.position;
	}

	/**
	 * Sets the position of the User in the Group.
	 *
	 * @param position the new position of the User in the Group
	 */
	public void setPosition(Short position) {
		this.position = position;
	}



	/**
	 *  
	 * To compare two GroupUsers.
	 *
	 * @param obj the GroupUser object to compare
	 * @return true if both GroupUsers are equal
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
