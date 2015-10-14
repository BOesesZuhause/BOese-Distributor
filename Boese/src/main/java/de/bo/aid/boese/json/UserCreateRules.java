package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserCreateRules extends BoeseJson {
	HashSet<Rule> rules;

	public UserCreateRules(HashSet<Rule> rules, int connectorId, int status, long timestamp) {
		super(MessageType.USERCREATERULES, connectorId, status, timestamp);
		this.rules = rules;
	}
	
	public HashSet<Rule> getRules() {
		return rules;
	}

}
