package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserSendRules extends BoeseJson {
	HashSet<Rule> rules;
	
	public UserSendRules(HashSet<Rule> rules, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.USERSENDRULES, connectorId, seqNr, ackNr, status, timestamp);
		this.rules = rules;
	}
	
	public HashSet<Rule> getRules() {
		return rules;
	}

}
