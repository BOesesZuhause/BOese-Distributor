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

import java.io.Serializable;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * LogConnector Model for Hibernate generated by hbm2java.
 */
public class LogConnector implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The LogConnector id. */
	private int loCoId;
	
	/** The connector. */
	private Connector connector;
	
	/** The timestap. */
	private Date timestap;
	
	/** The data. */
	private Serializable data;

	/**
	 * Instantiates a new log connector.
	 */
	public LogConnector() {
	}

	/**
	 * Instantiates a new log connector.
	 *
	 * @param loCoId the LogConnector id
	 * @param connector the connector
	 * @param timestap the timestap
	 * @param data the data
	 */
	public LogConnector(int loCoId, Connector connector, Date timestap, Serializable data) {
		this.loCoId = loCoId;
		this.connector = connector;
		this.timestap = timestap;
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
	 * Gets the timestap.
	 *
	 * @return the timestap
	 */
	public Date getTimestap() {
		return this.timestap;
	}

	/**
	 * Sets the timestap.
	 *
	 * @param timestap the new timestap
	 */
	public void setTimestap(Date timestap) {
		this.timestap = timestap;
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
		if (timestap == null) {
			if (other.timestap != null)
				return false;
		} else if (!timestap.equals(other.timestap))
			return false;
		return true;
	}
}