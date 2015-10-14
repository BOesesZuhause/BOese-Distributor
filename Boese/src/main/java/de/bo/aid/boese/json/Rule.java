package de.bo.aid.boese.json;

public class Rule {
	private int ruleId;
	private int tempRuleId;
	private boolean active;
	private long insertDate;
	private long modifyDate;
	private String permissions;
	private String conditions;
	private String actions;
	
	public Rule(int ruleId, boolean active, long insertDate, long modifyDate, String permissions, String conditions,
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
	
	public Rule(int ruleId, int tempRuleId, boolean active, long insertDate, long modifyDate, String permissions, 
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
	
	public int getTempRuleId() {
		return tempRuleId;
	}

	public int getRuleId() {
		return ruleId;
	}

	public boolean isActive() {
		return active;
	}

	public long getInsertDate() {
		return insertDate;
	}

	public long getModifyDate() {
		return modifyDate;
	}

	public String getPermissions() {
		return permissions;
	}

	public String getConditions() {
		return conditions;
	}

	public String getActions() {
		return actions;
	}
	
	
	
}
