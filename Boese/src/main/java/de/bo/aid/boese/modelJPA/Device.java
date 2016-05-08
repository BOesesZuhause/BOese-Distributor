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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// TODO: Auto-generated Javadoc
/**
 * Device Model for Hibernate generated by hbm2java.
 */
@Entity
public class Device implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Device id. */
	@Id
	@GeneratedValue
	private int deId;
	
	/** The linked connector. */
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "connector", nullable = false)
	private Connector connector;
	
	/** The zone this Device belonging to. */
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "zone", nullable = false)
	private Zone zone;
	
	/** The alias. */
	@Column(nullable = false)
	private String alias;
	
	/** The serial number. */
	@Column(nullable = false)
	private String serialNumber;
	
	/** The purchase date. */
	private Date purchaseDate;
	
	/** The device groups. */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "device")
	private Set<DeviceGroup> deviceGroups = new HashSet<DeviceGroup>(0);
	
	/** The services which this Device offers. */
	@ManyToMany()
	private Set<Service> services = new HashSet<Service>(0);
	
	/** The connected DeviceComponents. */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "device")
	  @JoinTable(
		      name="service_device",
		      joinColumns=@JoinColumn(name="EMP_ID", referencedColumnName="ID"),
		      inverseJoinColumns=@JoinColumn(name="PROJ_ID", referencedColumnName="ID"))
	private Set<DeviceComponent> deviceComponents = new HashSet<DeviceComponent>(0);

	/**
	 * Instantiates a new device.
	 */
	public Device() {
		
	}
	
	/**
	 * Instantiates a new device with id.
	 *
	 * @param deId the Device id
	 */
	public Device(int deId) {
		this.deId = deId;
	}

	/**
	 * Instantiates a new device for DB Insert.
	 *
	 * @param alias the alias
	 * @param serialNumber the serial number
	 */
	public Device(String alias, String serialNumber) {
		this.alias = alias;
		this.serialNumber = serialNumber;
	}

	/**
	 * Instantiates a new device with all parameters.
	 *
	 * @param deId the Device id
	 * @param connector the connector
	 * @param zone the zone
	 * @param alias the alias
	 * @param serialNumber the serial number
	 */
	public Device(int deId, Connector connector, Zone zone, String alias, String serialNumber) {
		this.deId = deId;
		this.connector = connector;
		this.zone = zone;
		this.alias = alias;
		this.serialNumber = serialNumber;
	}

	/**
	 * Instantiates a new device with all parameter and foreign keys.
	 *
	 * @param deId the Device id
	 * @param connector the connector
	 * @param zone the zone
	 * @param alias the alias
	 * @param serialNumber the serial number
	 * @param purchaseDate the purchase date
	 * @param deviceGroups the device groups
	 * @param services the services
	 * @param deviceComponents the device components
	 */
	public Device(int deId, Connector connector, Zone zone, String alias, String serialNumber, Date purchaseDate,
			Set<DeviceGroup> deviceGroups, Set<Service> services, Set<DeviceComponent> deviceComponents) {
		this.deId = deId;
		this.connector = connector;
		this.zone = zone;
		this.alias = alias;
		this.serialNumber = serialNumber;
		this.purchaseDate = purchaseDate;
		this.deviceGroups = deviceGroups;
		this.services = services;
		this.deviceComponents = deviceComponents;
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
	 * Gets the linked connector.
	 *
	 * @return the linked connector
	 */
	public Connector getConnector() {
		return this.connector;
	}

	/**
	 * Sets the linked connector.
	 *
	 * @param connector the new linked connector
	 */
	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	/**
	 * Gets the zone this Device belonging to.
	 *
	 * @return the zone this Device belonging to
	 */
	public Zone getZone() {
		return this.zone;
	}

	/**
	 * Sets the zone this Device belonging to.
	 *
	 * @param zone the new zone this Device belonging to
	 */
	public void setZone(Zone zone) {
		this.zone = zone;
	}

	/**
	 * Gets the alias.
	 *
	 * @return the alias
	 */
	public String getAlias() {
		return this.alias;
	}

	/**
	 * Sets the alias.
	 *
	 * @param alias the new alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Gets the serial number.
	 *
	 * @return the serial number
	 */
	public String getSerialNumber() {
		return this.serialNumber;
	}

	/**
	 * Sets the serial number.
	 *
	 * @param serialNumber the new serial number
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * Gets the purchase date.
	 *
	 * @return the purchase date
	 */
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	/**
	 * Sets the purchase date.
	 *
	 * @param purchaseDate the new purchase date
	 */
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
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
	 * Gets the services which this Device offers.
	 *
	 * @return the services which this Device offers
	 */
	public Set<Service> getServices() {
		return this.services;
	}

	/**
	 * Sets the services which this Device offers.
	 *
	 * @param services the new services which this Device offers
	 */
	public void setServices(Set<Service> services) {
		this.services = services;
	}

	/**
	 * Gets the connected deviceComponents.
	 *
	 * @return the connected deviceComponents
	 */
	public Set<DeviceComponent> getDeviceComponents() {
		return this.deviceComponents;
	}

	/**
	 * Sets the connected deviceComponents.
	 *
	 * @param deviceComponents the new connected deviceComponents
	 */
	public void setDeviceComponents(Set<DeviceComponent> deviceComponents) {
		this.deviceComponents = deviceComponents;
	}

	/**
	 *  
	 * To compare two Devices.
	 *
	 * @param obj the Device object to compare
	 * @return true if both Devices are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (connector == null) {
			if (other.connector != null)
				return false;
		} else if (!connector.equals(other.connector))
			return false;
		if (deId != other.deId)
			return false;
		if (deviceComponents == null) {
			if (other.deviceComponents != null)
				return false;
		} else if (!deviceComponents.equals(other.deviceComponents))
			return false;
		if (deviceGroups == null) {
			if (other.deviceGroups != null)
				return false;
		} else if (!deviceGroups.equals(other.deviceGroups))
			return false;
		if (purchaseDate == null) {
			if (other.purchaseDate != null)
				return false;
		} else if (!purchaseDate.equals(other.purchaseDate))
			return false;
		if (serialNumber == null) {
			if (other.serialNumber != null)
				return false;
		} else if (!serialNumber.equals(other.serialNumber))
			return false;
		if (services == null) {
			if (other.services != null)
				return false;
		} else if (!services.equals(other.services))
			return false;
		if (zone == null) {
			if (other.zone != null)
				return false;
		} else if (!zone.equals(other.zone))
			return false;
		return true;
	}

}
