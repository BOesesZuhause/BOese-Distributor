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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

// TODO: Auto-generated Javadoc
/**
 * HistoryLogRule Model for Hibernate generated by hbm2java.
 */
public class HistoryLogRule implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	@Transient
	private static final long serialVersionUID = 1L;

	/** The HistoryLogRule id. */
	@Id
	@GeneratedValue
	private int hiLoRuId;
	
	/** The timestamp when the rule was executed. */
	@Column(nullable = false)
	private Date timestamp;
	
	/** The rule which is locked. */
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "rule", nullable = false)
	private Rule rule;

	/**
	 * Instantiates a new history log rule.
	 */
	public HistoryLogRule() {
	}

	/**
	 * Instantiates a new history log rule.
	 *
	 * @param hiLoRuId the HistoryLogRule id
	 * @param rule the rule
	 */
	public HistoryLogRule(int hiLoRuId, Rule rule) {
		this.hiLoRuId = hiLoRuId;
		this.rule = rule;
	}

	/**
	 * Gets the HistoryLogRule id.
	 *
	 * @return the HistoryLogRule id
	 */
	public int getHiLoRuId() {
		return this.hiLoRuId;
	}

	/**
	 * Sets the HistoryLogRule id.
	 *
	 * @param hiLoRuId the new HistoryLogRule id
	 */
	public void setHiLoRuId(int hiLoRuId) {
		this.hiLoRuId = hiLoRuId;
	}

	/**
	 * Gets the timestamp when the rule was executed.
	 *
	 * @return the timestamp when the rule was executed
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Sets the timestamp when the rule was executed.
	 *
	 * @param timestamp the new timestamp when the rule was executed
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the rule which is locked.
	 *
	 * @return the rule which is locked
	 */
	public Rule getRule() {
		return this.rule;
	}

	/**
	 * Sets the rule which is locked.
	 *
	 * @param rule the new rule which is locked
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}

	/**
	 *  
	 * To compare two HistoryLogRules.
	 *
	 * @param obj the HistoryLogRule object to compare
	 * @return true if both HistoryLogRule are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoryLogRule other = (HistoryLogRule) obj;
		if (hiLoRuId != other.hiLoRuId)
			return false;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
}