package de.bo.aid.boese.json;

import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSendRepeatRules.
 */
public class UserSendRepeatRules extends BoeseJson {
	
	/** The rules. */
	private HashSet<RepeatRuleJSON> rules;
	
	/**
	 * Instantiates a new user send repeat rules.
	 *
	 * @param rules the rules
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserSendRepeatRules(HashSet<RepeatRuleJSON> rules, int connectorId, int status, long timestamp) {
		super(MessageType.USERSENDREPEATRULES, connectorId, status, timestamp);
		this.rules = rules;
	}

	/**
	 * Gets the rules.
	 *
	 * @return the rules
	 */
	public HashSet<RepeatRuleJSON> getRules() {
		return rules;
	}	

}
