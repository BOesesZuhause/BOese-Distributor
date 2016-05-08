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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

// TODO: Auto-generated Javadoc
/**
 * The Class Zone.
 *
 * @author Fabio
 * Zone Model for Hibernate generated by hbm2java.
 */
@Entity
public class Zone implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	@Transient
	private static final long serialVersionUID = 1L;

	/** The Zone id. */
	@Id
	@GeneratedValue
	private int zoId;
	
	/** The Super Zone. */
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "zone", nullable = true)
	private Zone zone;
	
	/** The name of the Zone. */
	@Column(nullable = false)
	private String name;
	
	/** The Groups linked with this Zone. */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "zone")
	private Set<GroupZone> groupZones = new HashSet<GroupZone>(0);
	
	/** The Sub Zones. */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "zone")
	private Set<Zone> zones = new HashSet<Zone>(0);
	
	/** The Devices in this Zone. */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "zone")
	private Set<Device> devices = new HashSet<Device>(0);

	/**
	 * Instantiates a new zone.
	 */
	public Zone() {
	
	}

	/**
	 * Instantiates a new zone for DB Insert.
	 *
	 * @param name the name
	 */
	public Zone(String name) {
		this.name = name;
	}

	/**
	 * Instantiates a new zone with all parameters.
	 *
	 * @param zoId the Zone id
	 * @param zone the super Zone
	 * @param name the name of the Zone
	 */
	public Zone(int zoId, Zone zone, String name) {
		this.zoId = zoId;
		this.zone = zone;
		this.name = name;
	}

	/**
	 * Instantiates a new zone with all parameters and Foreigen Keys.
	 *
	 * @param zoId the Zone id
	 * @param zone the super Zone
	 * @param name the name of the Zone
	 * @param groupZones the Groups linked with this zone
	 * @param zones the Sub Zones
	 * @param devices the Devices in this Zone
	 */
	public Zone(int zoId, Zone zone, String name, Set<GroupZone> groupZones, Set<Zone> zones, Set<Device> devices) {
		this.zoId = zoId;
		this.zone = zone;
		this.name = name;
		this.groupZones = groupZones;
		this.zones = zones;
		this.devices = devices;
	}

	/**
	 * Gets the Zone id.
	 *
	 * @return the Zone id
	 */
	public int getZoId() {
		return this.zoId;
	}

	/**
	 * Sets the Zone id.
	 *
	 * @param zoId the new Zone id
	 */
	public void setZoId(int zoId) {
		this.zoId = zoId;
	}

	/**
	 * Gets the Super Zone.
	 *
	 * @return the Super Zone
	 */
	public Zone getZone() {
		return this.zone;
	}

	/**
	 * Sets the Super Zone.
	 *
	 * @param zone the new Super Zone
	 */
	public void setZone(Zone zone) {
		this.zone = zone;
	}

	/**
	 * Gets the name of this Zone.
	 *
	 * @return the name of this Zone
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this Zone.
	 *
	 * @param name the new name of this Zone
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the Groups linked with this Zone.
	 *
	 * @return the Groups linked with this Zone
	 */
	public Set<GroupZone> getGroupZones() {
		return this.groupZones;
	}

	/**
	 * Sets the Groups linked with this Zone.
	 *
	 * @param groupZones the new Groups linked with this Zone
	 */
	public void setGroupZones(Set<GroupZone> groupZones) {
		this.groupZones = groupZones;
	}

	/**
	 * Gets the Sub Zones.
	 *
	 * @return the Sub Zones
	 */
	public Set<Zone> getZones() {
		return this.zones;
	}

	/**
	 * Sets the Sub Zones.
	 *
	 * @param zones the new Sub Zones
	 */
	public void setZones(Set<Zone> zones) {
		this.zones = zones;
	}

	/**
	 * Gets the Devices in this Zone.
	 *
	 * @return the Devices in this Zone
	 */
	public Set<Device> getDevices() {
		return this.devices;
	}

	/**
	 * Sets the Devices in this Zone.
	 *
	 * @param devices the new Devices in this Zone
	 */
	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	/**
	 *  
	 * To compare two Zones.
	 *
	 * @param obj the Zone object to compare
	 * @return true if both Zones are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zone other = (Zone) obj;
		if (devices == null) {
			if (other.devices != null)
				return false;
		} else if (!devices.equals(other.devices))
			return false;
		if (groupZones == null) {
			if (other.groupZones != null)
				return false;
		} else if (!groupZones.equals(other.groupZones))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (zoId != other.zoId)
			return false;
		if (zone == null) {
			if (other.zone != null)
				return false;
		} else if (!zone.equals(other.zone))
			return false;
		if (zones == null) {
			if (other.zones != null)
				return false;
		} else if (!zones.equals(other.zones))
			return false;
		return true;
	}

}
