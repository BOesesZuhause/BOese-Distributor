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
 * HistoryLogConnector Model for Hibernate generated by hbm2java.
 */ 
@Entity
@Table(name="\"HistoryLogConnector\"")
public class HistoryLogConnector implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	@Transient
	private static final long serialVersionUID = 1L;

	/** The HistoryLogConnectorid. */
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int hiLoCoId;
	
	/** The connector which send/get a Message. */
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "connector", nullable = false)
	private Connector connector;
	
	/** The  when it was switched. */
	@Column(nullable = false)
	private Date timestamp;
	
	/** The data which was send. */
	@Column(nullable = false)
	private Serializable data;

	/**
	 * Instantiates a new history log connector.
	 */
	public HistoryLogConnector() {
	}

	/**
	 * Instantiates a new history log connector.
	 *
	 * @param connector the connector which send/get a Message
	 * @param  the timestamp when it was switched
	 * @param data the data which was send
	 */
	public HistoryLogConnector(Connector connector, Date timestamp, Serializable data) {
		this.connector = connector;
		this.timestamp = timestamp;
		this.data = data;
	}

	/**
	 * Instantiates a new history log connector.
	 *
	 * @param hiLoCoId the HistoryLogConnectorid
	 * @param connector the connector which send/get a Message
	 * @param  the timestamp when it was switched
	 * @param data the data which was send
	 */
	public HistoryLogConnector(int hiLoCoId, Connector connector, Date timestamp, Serializable data) {
		this.hiLoCoId = hiLoCoId;
		this.connector = connector;
		this.timestamp = timestamp;
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
	 * Gets the  when it was switched.
	 *
	 * @return the  when it was switched
	 */
	public Date getTimestamp() {
		return timestamp;
	}


	/**
	 * Sets the  when it was switched.
	 *
	 * @param  the new  when it was switched
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
}
