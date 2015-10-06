package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserSendRules extends BoeseJson {
	HashSet<Rule> rules;
	
	public UserSendRules(HashSet<Rule> rules, int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDRULES, connectorId, status, timestamp);
		this.rules = rules;
	}
	
	public HashSet<Rule> getRules() {
		return rules;
	}

}
