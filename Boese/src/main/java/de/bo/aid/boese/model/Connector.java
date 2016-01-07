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

import java.util.HashSet;
import java.util.Set;

import de.bo.aid.boese.constants.Status;

// TODO: Auto-generated Javadoc
/**
 * Connector Model for Hibernate generated by hbm2java.
 */
public class Connector implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Connector id. */
	private int coId;
	
	/** The name. */
	private String name;
	
	/** The password. */
	private String password;

	/** The status. */
	private int status;

	/** is this a User Connector?. */
	private boolean userConnector;
	
	/** The history log of this connectors. */
	private Set<HistoryLogConnector> historyLogConnectors = new HashSet<HistoryLogConnector>(0);
	
	/** The connected devices. */
	private Set<Device> devices = new HashSet<Device>(0);
	
	/** The log of this connectors. */
	private Set<LogConnector> logConnectors = new HashSet<LogConnector>(0);

	/**
	 * Instantiates a new connector.
	 */
	public Connector() {
	
	}
	
	/**
	 * Instantiates a new connector.
	 *
	 * @param coId the Connector id
	 */
	public Connector(int coId) {
		this.coId = coId;
	}

	/**
	 * Instantiates a new connector for DB Insert.
	 *
	 * @param name the name
	 * @param password the password
	 * @param userConnector the is this a User Connector?
	 */
	public Connector(String name, String password, boolean userConnector) {
		this.name = name;
		this.password = password;
		this.status = Status.ACTIVE;
		this.userConnector = userConnector;
	}

	/**
	 * Instantiates a new connector.
	 *
	 * @param coId the connector id
	 * @param name the name
	 * @param password the password
	 * @param status the status
	 * @param userConnector the is this a User Connector
	 */
	public Connector(int coId, String name, String password, int status, boolean userConnector) {
		this.coId = coId;
		this.name = name;
		this.password = password;
		this.status = status;
		this.userConnector = userConnector;
	}

	/**
	 * Instantiates a new connector.
	 *
	 * @param coId the Connector id
	 * @param name the name
	 * @param password the password
	 * @param status the status
	 * @param userConnector the is this a User Connector
	 * @param historyLogConnectors the history log of this connector
	 * @param devices the connected devices
	 * @param logConnectors the log of this connector
	 */
	public Connector(int coId, String name, String password, int status, boolean userConnector,
			Set<HistoryLogConnector> historyLogConnectors, Set<Device> devices, Set<LogConnector> logConnectors) {
		this.coId = coId;
		this.name = name;
		this.password = password;
		this.status = status;
		this.userConnector = userConnector;
		this.historyLogConnectors = historyLogConnectors;
		this.devices = devices;
		this.logConnectors = logConnectors;
	}

	/**
	 * Gets the Connector id.
	 *
	 * @return the Connector id
	 */
	public int getCoId() {
		return this.coId;
	}

	/**
	 * Sets the Connector id.
	 *
	 * @param coId the new Connector id
	 */
	public void setCoId(int coId) {
		this.coId = coId;
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
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Checks if this is a User Connector.
	 *
	 * @return true, if this is a user connector
	 */
	public boolean isUserConnector() {
		return userConnector;
	}

	/**
	 * Sets if it is a user connector.
	 *
	 * @param userConnector true for is a UserConnector
	 */
	public void setUserConnector(boolean userConnector) {
		this.userConnector = userConnector;
	}

	/**
	 * Gets the history log of this connector.
	 *
	 * @return the history log of this connector
	 */
	public Set<HistoryLogConnector> getHistoryLogConnectors() {
		return this.historyLogConnectors;
	}

	/**
	 * Sets the history log of this connector.
	 *
	 * @param historyLogConnectors the new history log of this connector
	 */
	public void setHistoryLogConnectors(Set<HistoryLogConnector> historyLogConnectors) {
		this.historyLogConnectors = historyLogConnectors;
	}

	/**
	 * Gets the connected devices.
	 *
	 * @return the connected devices
	 */
	public Set<Device> getDevices() {
		return this.devices;
	}

	/**
	 * Sets the connected devices.
	 *
	 * @param devices the new connected devices
	 */
	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	/**
	 * Gets the log of this connector.
	 *
	 * @return the log of this connector
	 */
	public Set<LogConnector> getLogConnectors() {
		return this.logConnectors;
	}

	/**
	 * Sets the log of this connector.
	 *
	 * @param logConnectors the new log of this connector
	 */
	public void setLogConnectors(Set<LogConnector> logConnectors) {
		this.logConnectors = logConnectors;
	}

	/** 
	 * To compare two Connectors
	 * 
	 * @param obj the Connector object to compare
	 * @return true if both Connector are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connector other = (Connector) obj;
		if (coId != other.coId)
			return false;
		if (devices == null) {
			if (other.devices != null)
				return false;
		} else if (!devices.equals(other.devices))
			return false;
		if (historyLogConnectors == null) {
			if (other.historyLogConnectors != null)
				return false;
		} else if (!historyLogConnectors.equals(other.historyLogConnectors))
			return false;
		if (logConnectors == null) {
			if (other.logConnectors != null)
				return false;
		} else if (!logConnectors.equals(other.logConnectors))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

}
