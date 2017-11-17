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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

// TODO: Auto-generated Javadoc
/**
 * Group Model for Hibernate generated by hbm2java.
 */
@Entity
@Table(name="\"Group\"")
public class Group implements java.io.Serializable, JPAModel {

	/** The Constant serialVersionUID. */
	@Transient
	private static final long serialVersionUID = 1L;

	/** The Group id. */
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int grId;
	
	/** The name. */
	@Column(nullable = true)
	private String name;
	
	/** The groupzone Foreign Keys. */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "grp")
	private Set<GroupZone> groupZones = new HashSet<GroupZone>(0);
	
	/** The devicegroup Foreign Keys. */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "grp")
	private Set<DeviceGroup> deviceGroups = new HashSet<DeviceGroup>(0);
	
	/** The groupuser Foreign Keys. */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "grp")
	private Set<GroupUser> groupUsers = new HashSet<GroupUser>(0);

	/**
	 * Instantiates a new group.
	 */
	public Group() {
	}

	/**
	 * Instantiates a new group to Insert in DB.
	 *
	 * @param name the name
	 */
	public Group(String name) {
		this.name = name;
	}

	/**
	 * Instantiates a new group.
	 *
	 * @param grId the Group id
	 * @param name the name
	 */
	public Group(int grId, String name) {
		this.grId = grId;
		this.name = name;
	}

	/**
	 * Instantiates a new group.
	 *
	 * @param grId the Group id
	 * @param name the name
	 * @param groupZones the group zones
	 * @param deviceGroups the device groups
	 * @param groupUsers the group users
	 */
	public Group(int grId, String name, Set<GroupZone> groupZones, Set<DeviceGroup> deviceGroups, Set<GroupUser> groupUsers) {
		this.grId = grId;
		this.name = name;
		this.groupZones = groupZones;
		this.deviceGroups = deviceGroups;
		this.groupUsers = groupUsers;
	}

	/**
	 * Gets the Group id.
	 *
	 * @return the Group id
	 */
	public int getGrId() {
		return this.grId;
	}

	/**
	 * Sets the Group id.
	 *
	 * @param grId the new Group id
	 */
	public void setGrId(int grId) {
		this.grId = grId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the group zones.
	 *
	 * @return the group zones
	 */
	public Set<GroupZone> getGroupZones() {
		return this.groupZones;
	}

	/**
	 * Sets the group zones.
	 *
	 * @param groupZones the new group zones
	 */
	public void setGroupZones(Set<GroupZone> groupZones) {
		this.groupZones = groupZones;
	}

	/**
	 * Gets the device groups.
	 *
	 * @return the device groups
	 */
	public Set<DeviceGroup> getDeviceGroups() {
		return this.deviceGroups;
	}

	/**
	 * Sets the device groups.
	 *
	 * @param deviceGroups the new device groups
	 */
	public void setDeviceGroups(Set<DeviceGroup> deviceGroups) {
		this.deviceGroups = deviceGroups;
	}

	/**
	 * Gets the group users.
	 *
	 * @return the group users
	 */
	public Set<GroupUser> getGroupUsers() {
		return this.groupUsers;
	}

	/**
	 * Sets the group users.
	 *
	 * @param groupUsers the new group users
	 */
	public void setGroupUsers(Set<GroupUser> groupUsers) {
		this.groupUsers = groupUsers;
	}

	/**
	 *  
	 * To compare two Groups.
	 *
	 * @param obj the Group object to compare
	 * @return true if both Groups are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (grId != other.grId)
			return false;
		return true;
	}

	/**
	 *  
	 * To compare two Groups.
	 *
	 * @param obj the Group object to compare
	 * @return true if both Groups are equal
	 */
	public boolean equalsWithoutIDAndFK(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
