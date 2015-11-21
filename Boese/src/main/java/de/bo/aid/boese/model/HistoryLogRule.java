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
// Generated 30.07.2015 11:14:41 by Hibernate Tools 4.3.1

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * HistoryLogRule generated by hbm2java.
 */
public class HistoryLogRule implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The hi lo ru id. */
	private int hiLoRuId;
	
	/** The timestamp. */
	private Date timestamp;
	
	/** The rule. */
	private Rule rule;

	/**
	 * Instantiates a new history log rule.
	 */
	public HistoryLogRule() {
	}

	/**
	 * Instantiates a new history log rule.
	 *
	 * @param hiLoRuId the hi lo ru id
	 * @param rule the rule
	 */
	public HistoryLogRule(int hiLoRuId, Rule rule) {
		this.hiLoRuId = hiLoRuId;
		this.rule = rule;
	}

	/**
	 * Gets the hi lo ru id.
	 *
	 * @return the hi lo ru id
	 */
	public int getHiLoRuId() {
		return this.hiLoRuId;
	}

	/**
	 * Sets the hi lo ru id.
	 *
	 * @param hiLoRuId the new hi lo ru id
	 */
	public void setHiLoRuId(int hiLoRuId) {
		this.hiLoRuId = hiLoRuId;
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
	 * Gets the rule.
	 *
	 * @return the rule
	 */
	public Rule getRule() {
		return this.rule;
	}

	/**
	 * Sets the rule.
	 *
	 * @param rule the new rule
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
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
