package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserSendRepeatRules extends BoeseJson {
	private HashSet<RepeatRuleJSON> rules;
	
	public UserSendRepeatRules(HashSet<RepeatRuleJSON> rules, int connectorId, int status, long timestamp) {
		super(MessageType.USERSENDREPEATRULES, connectorId, status, timestamp);
		this.rules = rules;
	}

	public HashSet<RepeatRuleJSON> getRules() {
		return rules;
	}	

}
