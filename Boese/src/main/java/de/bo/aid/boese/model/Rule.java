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
import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * Rule generated by hbm2java.
 */
public class Rule implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ru id. */
	private int ruId;
	
	/** The active. */
	private Boolean active;
	
	/** The insert date. */
	private Date insertDate;
	
	/** The modify date. */
	private Date modifyDate;
	
	/** The permissions. */
	private String permissions;
	
	/** The conditions. */
	private String conditions;
	
	/** The actions. */
	private String actions;
	
	/** The log rules. */
	private Set<LogRule> logRules = new HashSet<LogRule>(0);
	
	/** The history log rules. */
	private Set<HistoryLogRule> historyLogRules = new HashSet<HistoryLogRule>(0);
	
	/** The toDos. */
	private Set<RepeatRule> repeatRule = new HashSet<RepeatRule>(0);

	/**
	 * Instantiates a new rule.
	 */
	public Rule() {
	}

	/**
	 * Instantiates a new rule.
	 *
	 * @param ruId the ru id
	 */
	public Rule(int ruId) {
		this.ruId = ruId;
	}

	/**
	 * Instantiates a new rule.
	 *
	 * @param permissions the permissions
	 * @param conditions the conditions
	 * @param actions the actions
	 */
	public Rule(String permissions, String conditions, String actions) {
		this.permissions = permissions;
		this.conditions = conditions;
		this.actions = actions;
	}

	/**
	 * Instantiates a new rule.
	 *
	 * @param ruId the ru id
	 * @param active the active
	 * @param insertDate the insert date
	 * @param modifyDate the modify date
	 * @param permissions the permissions
	 * @param conditions the conditions
	 * @param actions the actions
	 * @param logRules the log rules
	 * @param historyLogRules the history log rules
	 * @param repeatRule the Repeat Rule
	 */
	public Rule(int ruId, Boolean active, Date insertDate, Date modifyDate, String permissions,
			String conditions, String actions, Set<LogRule> logRules, Set<HistoryLogRule> historyLogRules, Set<RepeatRule> repeatRule) {
		this.ruId = ruId;
		this.active = active;
		this.insertDate = insertDate;
		this.modifyDate = modifyDate;
		this.permissions = permissions;
		this.conditions = conditions;
		this.actions = actions;
		this.logRules = logRules;
		this.historyLogRules = historyLogRules;
		this.repeatRule = repeatRule;
	}

	/**
	 * Gets the ru id.
	 *
	 * @return the ru id
	 */
	public int getRuId() {
		return this.ruId;
	}

	/**
	 * Sets the ru id.
	 *
	 * @param ruId the new ru id
	 */
	public void setRuId(int ruId) {
		this.ruId = ruId;
	}

	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public Boolean getActive() {
		return this.active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Gets the insert date.
	 *
	 * @return the insert date
	 */
	public Date getInsertDate() {
		return this.insertDate;
	}

	/**
	 * Sets the insert date.
	 *
	 * @param insertDate the new insert date
	 */
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	/**
	 * Gets the modify date.
	 *
	 * @return the modify date
	 */
	public Date getModifyDate() {
		return this.modifyDate;
	}

	/**
	 * Sets the modify date.
	 *
	 * @param modifyDate the new modify date
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * Gets the permissions.
	 *
	 * @return the permissions
	 */
	public String getPermissions() {
		return this.permissions;
	}

	/**
	 * Sets the permissions.
	 *
	 * @param permissions the new permissions
	 */
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	/**
	 * Gets the conditions.
	 *
	 * @return the conditions
	 */
	public String getConditions() {
		return this.conditions;
	}

	/**
	 * Sets the conditions.
	 *
	 * @param conditions the new conditions
	 */
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	/**
	 * Gets the actions.
	 *
	 * @return the actions
	 */
	public String getActions() {
		return this.actions;
	}

	/**
	 * Sets the actions.
	 *
	 * @param actions the new actions
	 */
	public void setActions(String actions) {
		this.actions = actions;
	}

	/**
	 * Gets the log rules.
	 *
	 * @return the log rules
	 */
	public Set<LogRule> getLogRules() {
		return this.logRules;
	}

	/**
	 * Sets the log rules.
	 *
	 * @param logRules the new log rules
	 */
	public void setLogRules(Set<LogRule> logRules) {
		this.logRules = logRules;
	}

	/**
	 * Gets the history log rules.
	 *
	 * @return the history log rules
	 */
	public Set<HistoryLogRule> getHistoryLogRules() {
		return this.historyLogRules;
	}

	/**
	 * Sets the history log rules.
	 *
	 * @param historyLogRules the new history log rules
	 */
	public void setHistoryLogRules(Set<HistoryLogRule> historyLogRules) {
		this.historyLogRules = historyLogRules;
	}

	/**
	 * Gets the repeat rule.
	 *
	 * @return the repeat rule
	 */
	public Set<RepeatRule> getRepeatRule() {
		return repeatRule;
	}

	/**
	 * Sets the repeat rule.
	 *
	 * @param repeatRule the new repeat rule
	 */
	public void setRepeatRule(Set<RepeatRule> repeatRule) {
		this.repeatRule = repeatRule;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		if (actions == null) {
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (conditions == null) {
			if (other.conditions != null)
				return false;
		} else if (!conditions.equals(other.conditions))
			return false;
		if (historyLogRules == null) {
			if (other.historyLogRules != null)
				return false;
		} else if (!historyLogRules.equals(other.historyLogRules))
			return false;
		if (insertDate == null) {
			if (other.insertDate != null)
				return false;
		} else if (!insertDate.equals(other.insertDate))
			return false;
		if (logRules == null) {
			if (other.logRules != null)
				return false;
		} else if (!logRules.equals(other.logRules))
			return false;
		if (modifyDate == null) {
			if (other.modifyDate != null)
				return false;
		} else if (!modifyDate.equals(other.modifyDate))
			return false;
		if (permissions == null) {
			if (other.permissions != null)
				return false;
		} else if (!permissions.equals(other.permissions))
			return false;
		if (repeatRule == null) {
			if (other.repeatRule != null)
				return false;
		} else if (!repeatRule.equals(other.repeatRule))
			return false;
		if (ruId != other.ruId)
			return false;
		return true;
	}

}
