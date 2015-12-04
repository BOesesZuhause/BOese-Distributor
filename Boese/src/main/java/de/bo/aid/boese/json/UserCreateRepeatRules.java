package de.bo.aid.boese.json;

import java.util.HashSet;


// TODO: Auto-generated Javadoc
/**
 * The Class UserCreateRepeatRules.
 */
public class UserCreateRepeatRules extends BoeseJson{

    /** The rules. */
    HashSet<RepeatRuleJSON> rules;
    
    /**
     * Instantiates a new user create repeat rules.
     *
     * @param rules the rules
     * @param connectorId the connector id
     * @param status the status
     * @param timestamp the timestamp
     */
    public UserCreateRepeatRules(HashSet<RepeatRuleJSON> rules, int connectorId, int status, long timestamp) {
        super(MessageType.USERCREATEREPEATRULES, connectorId, status, timestamp);
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
