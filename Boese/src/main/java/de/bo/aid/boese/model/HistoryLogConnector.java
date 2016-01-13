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
 * HistoryLogConnector Model for Hibernate generated by hbm2java.
 */
public class HistoryLogConnector implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The HistoryLogConnectorid. */
	private int hiLoCoId;
	
	/** The connector which send/get a Message. */
	private Connector connector;
	
	/** The timestap when it was switched. */
	private Date timestap;
	
	/** The data which was send. */
	private Serializable data;

	/**
	 * Instantiates a new history log connector.
	 */
	public HistoryLogConnector() {
	}

	/**
	 * Instantiates a new history log connector.
	 *
	 * @param hiLoCoId the HistoryLogConnectorid
	 * @param connector the connector which send/get a Message
	 * @param timestap the timestap when it was switched
	 * @param data the data which was send
	 */
	public HistoryLogConnector(int hiLoCoId, Connector connector, Date timestap, Serializable data) {
		this.hiLoCoId = hiLoCoId;
		this.connector = connector;
		this.timestap = timestap;
		this.data = data;
	}

	/**
	 * Gets the HistoryLogConnectorid.
	 *
	 * @return the HistoryLogConnectorid
	 */
	public int getHiLoCoId() {
		return this.hiLoCoId;
	}

	/**
	 * Sets the HistoryLogConnectorid.
	 *
	 * @param hiLoCoId the new HistoryLogConnectorid
	 */
	public void setHiLoCoId(int hiLoCoId) {
		this.hiLoCoId = hiLoCoId;
	}

	/**
	 * Gets the connector which send/get a Message.
	 *
	 * @return the connector which send/get a Message
	 */
	public Connector getConnector() {
		return this.connector;
	}

	/**
	 * Sets the connector which send/get a Message.
	 *
	 * @param connector the new connector which send/get a Message
	 */
	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	/**
	 * Gets the timestap when it was switched.
	 *
	 * @return the timestap when it was switched
	 */
	public Date getTimestap() {
		return this.timestap;
	}

	/**
	 * Sets the timestap when it was switched.
	 *
	 * @param timestap the new timestap when it was switched
	 */
	public void setTimestap(Date timestap) {
		this.timestap = timestap;
	}

	/**
	 * Gets the data which was send.
	 *
	 * @return the data which was send
	 */
	public Serializable getData() {
		return this.data;
	}

	/**
	 * Sets the data which was send.
	 *
	 * @param data the new data which was send
	 */
	public void setData(Serializable data) {
		this.data = data;
	}

	/**
	 *  
	 * To compare two HistoryLogConnectors.
	 *
	 * @param obj the HistoryLogConnector object to compare
	 * @return true if both HistoryLogConnector are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoryLogConnector other = (HistoryLogConnector) obj;
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
		if (hiLoCoId != other.hiLoCoId)
			return false;
		if (timestap == null) {
			if (other.timestap != null)
				return false;
		} else if (!timestap.equals(other.timestap))
			return false;
		return true;
	}
}
