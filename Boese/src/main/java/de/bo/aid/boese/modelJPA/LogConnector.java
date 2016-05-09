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

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

// TODO: Auto-generated Javadoc
/**
 * LogConnector Model for Hibernate generated by hbm2java.
 */
@Entity
@Table(name="\"LogConnector\"")
public class LogConnector implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	@Transient
	private static final long serialVersionUID = 1L;

	/** The LogConnector id. */
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int loCoId;
	
	/** The connector. */
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "connector", nullable = false)
	private Connector connector;
	
	/** The timestamp. */
	@Column(nullable = false)
	private Date timestamp;
	
	/** The data. */
	@Column(nullable = false)
	private Serializable data;

	/**
	 * Instantiates a new log connector.
	 */
	public LogConnector() {
	}

	/**
	 * Instantiates a new log connector.
	 *
	 * @param connector the connector
	 * @param timestamp the timestamp
	 * @param data the data
	 */
	public LogConnector(Connector connector, Date timestamp, Serializable data) {
		this.connector = connector;
		this.timestamp = timestamp;
		this.data = data;
	}

	/**
	 * Instantiates a new log connector.
	 *
	 * @param loCoId the LogConnector id
	 * @param connector the connector
	 * @param timestamp the timestamp
	 * @param data the data
	 */
	public LogConnector(int loCoId, Connector connector, Date timestamp, Serializable data) {
		this.loCoId = loCoId;
		this.connector = connector;
		this.timestamp = timestamp;
		this.data = data;
	}

	/**
	 * Gets the LogConnector id.
	 *
	 * @return the LogConnector id
	 */
	public int getLoCoId() {
		return this.loCoId;
	}

	/**
	 * Sets the LogConnector id.
	 *
	 * @param loCoId the new LogConnector id
	 */
	public void setLoCoId(int loCoId) {
		this.loCoId = loCoId;
	}

	/**
	 * Gets the connector.
	 *
	 * @return the connector
	 */
	public Connector getConnector() {
		return this.connector;
	}

	/**
	 * Sets the connector.
	 *
	 * @param connector the new connector
	 */
	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Serializable getData() {
		return this.data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(Serializable data) {
		this.data = data;
	}

	/**
	 *  
	 * To compare two LogConnectors.
	 *
	 * @param obj the LogConnector object to compare
	 * @return true if both LogConnector are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogConnector other = (LogConnector) obj;
		if (connector == null) {
			if (other.connector != null)
				return false;
		} else if (!connector.equals(other.connector))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (loCoId != other.loCoId)
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
}