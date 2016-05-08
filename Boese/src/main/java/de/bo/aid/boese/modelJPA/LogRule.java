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
 * LogRule Model for Hibernate generated by hbm2java.
 */
public class LogRule implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	@Transient
	private static final long serialVersionUID = 1L;

	/** The LogRule id. */
	@Id
	@GeneratedValue
	private int loRuId;
	
	/** The timestamp when the Rule was executed. */
	@Column(nullable = false)
	private Date timestamp;
	
	/** The belonging Rule. */
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "rule", nullable = false)
	private Rule rule;

	/**
	 * Instantiates a new log rule.
	 */
	public LogRule() {
	}

	/**
	 * Instantiates a new log rule.
	 *
	 * @param loRuId the LogRule id
	 * @param rule the belonging rule
	 */
	public LogRule(int loRuId, Rule rule) {
		this.loRuId = loRuId;
		this.rule = rule;
	}

	/**
	 * Gets the LogRule id.
	 *
	 * @return the LogRule id
	 */
	public int getLoRuId() {
		return this.loRuId;
	}

	/**
	 * Sets the LogRule id.
	 *
	 * @param loRuId the new LogRule id
	 */
	public void setLoRuId(int loRuId) {
		this.loRuId = loRuId;
	}

	/**
	 * Gets the timestamp when the Rule was executed.
	 *
	 * @return the timestamp when the Rule was executed
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Sets the timestamp when the Rule was executed.
	 *
	 * @param timestamp the new timestamp when the Rule was executed
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the belonging rule.
	 *
	 * @return the belonging rule
	 */
	public Rule getRule() {
		return this.rule;
	}

	/**
	 * Sets the belonging rule.
	 *
	 * @param rule the new belonging rule
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}

	/**
	 *  
	 * To compare two LogRules.
	 *
	 * @param obj the LogRule object to compare
	 * @return true if both LogRules are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogRule other = (LogRule) obj;
		if (loRuId != other.loRuId)
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