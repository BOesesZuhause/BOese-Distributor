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


package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class Rule.
 */
public class RuleJSON {
	
	/** The rule id. */
	private int ruleId;
	
	/** The temp rule id. */
	private int tempRuleId;
	
	/** The active. */
	private boolean active;
	
	/** The insert date. */
	private long insertDate;
	
	/** The modify date. */
	private long modifyDate;
	
	/** The permissions. */
	private String permissions;
	
	/** The conditions. */
	private String conditions;
	
	/** The actions. */
	private String actions;
	
	/**
	 * Instantiates a new rule.
	 *
	 * @param ruleId the rule id
	 * @param active the active
	 * @param insertDate the insert date
	 * @param modifyDate the modify date
	 * @param permissions the permissions
	 * @param conditions the conditions
	 * @param actions the actions
	 */
	public RuleJSON(int ruleId, boolean active, long insertDate, long modifyDate, String permissions, String conditions,
			String actions) {
		this.ruleId = ruleId;
		this.active = active;
		this.insertDate = insertDate;
		this.modifyDate = modifyDate;
		this.permissions = permissions;
		this.conditions = conditions;
		this.actions = actions;
		this.tempRuleId = -1;
	}
	
	/**
	 * Instantiates a new rule.
	 *
	 * @param ruleId the rule id
	 * @param tempRuleId the temp rule id
	 * @param active the active
	 * @param insertDate the insert date
	 * @param modifyDate the modify date
	 * @param permissions the permissions
	 * @param conditions the conditions
	 * @param actions the actions
	 */
	public RuleJSON(int ruleId, int tempRuleId, boolean active, long insertDate, long modifyDate, String permissions, 
			String conditions, String actions) {
		this.ruleId = ruleId;
		this.active = active;
		this.insertDate = insertDate;
		this.modifyDate = modifyDate;
		this.permissions = permissions;
		this.conditions = conditions;
		this.actions = actions;
		this.tempRuleId = tempRuleId;
	}
	
	/**
	 * Gets the temp rule id.
	 *
	 * @return the temp rule id
	 */
	public int getTempRuleId() {
		return tempRuleId;
	}

	/**
	 * Gets the rule id.
	 *
	 * @return the rule id
	 */
	public int getRuleId() {
		return ruleId;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Gets the insert date.
	 *
	 * @return the insert date
	 */
	public long getInsertDate() {
		return insertDate;
	}

	/**
	 * Gets the modify date.
	 *
	 * @return the modify date
	 */
	public long getModifyDate() {
		return modifyDate;
	}

	/**
	 * Gets the permissions.
	 *
	 * @return the permissions
	 */
	public String getPermissions() {
		return permissions;
	}

	/**
	 * Gets the conditions.
	 *
	 * @return the conditions
	 */
	public String getConditions() {
		return conditions;
	}

	/**
	 * Gets the actions.
	 *
	 * @return the actions
	 */
	public String getActions() {
		return actions;
	}
	
	
	
}
