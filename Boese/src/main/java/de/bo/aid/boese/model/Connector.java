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

// TODO: Auto-generated Javadoc
/**
 * Connector generated by hbm2java.
 */
public class Connector implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The co id. */
	private int coId;
	
	/** The name. */
	private String name;
	
	/** The password. */
	private String password;

	/** The status. */
	private int status;
	
	/** The history log connectors. */
	private Set<HistoryLogConnector> historyLogConnectors = new HashSet<HistoryLogConnector>(0);
	
	/** The devices. */
	private Set<Device> devices = new HashSet<Device>(0);
	
	/** The log connectors. */
	private Set<LogConnector> logConnectors = new HashSet<LogConnector>(0);

	/**
	 * Instantiates a new connector.
	 */
	public Connector() {
	
	}
	
	/**
	 * Instantiates a new connector.
	 *
	 * @param coId the co id
	 */
	public Connector(int coId) {
		this.coId = coId;
	}

	/**
	 * Instantiates a new connector.
	 *
	 * @param coId the connectorid
	 * @param name the name
	 * @param password the password
	 * @param status the status
	 */
	public Connector(int coId, String name, String password, int status) {
		this.coId = coId;
		this.name = name;
		this.password = password;
		this.status = status;
	}

	/**
	 * Instantiates a new connector.
	 *
	 * @param coId the co id
	 * @param name the name
	 * @param password the password
	 * @param status the status
	 * @param historyLogConnectors the history log connectors
	 * @param devices the devices
	 * @param logConnectors the log connectors
	 */
	public Connector(int coId, String name, String password, int status,
			Set<HistoryLogConnector> historyLogConnectors, Set<Device> devices, Set<LogConnector> logConnectors) {
		this.coId = coId;
		this.name = name;
		this.password = password;
		this.status = status;
		this.historyLogConnectors = historyLogConnectors;
		this.devices = devices;
		this.logConnectors = logConnectors;
	}

	/**
	 * Gets the co id.
	 *
	 * @return the co id
	 */
	public int getCoId() {
		return this.coId;
	}

	/**
	 * Sets the co id.
	 *
	 * @param coId the new co id
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
	 * Gets the history log connectors.
	 *
	 * @return the history log connectors
	 */
	public Set<HistoryLogConnector> getHistoryLogConnectors() {
		return this.historyLogConnectors;
	}

	/**
	 * Sets the history log connectors.
	 *
	 * @param historyLogConnectors the new history log connectors
	 */
	public void setHistoryLogConnectors(Set<HistoryLogConnector> historyLogConnectors) {
		this.historyLogConnectors = historyLogConnectors;
	}

	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public Set<Device> getDevices() {
		return this.devices;
	}

	/**
	 * Sets the devices.
	 *
	 * @param devices the new devices
	 */
	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	/**
	 * Gets the log connectors.
	 *
	 * @return the log connectors
	 */
	public Set<LogConnector> getLogConnectors() {
		return this.logConnectors;
	}

	/**
	 * Sets the log connectors.
	 *
	 * @param logConnectors the new log connectors
	 */
	public void setLogConnectors(Set<LogConnector> logConnectors) {
		this.logConnectors = logConnectors;
	}
  
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
