package de.bo.aid.boese.json;

import java.util.HashSet;


public class UserCreateRepeatRules extends BoeseJson{

    HashSet<RepeatRuleJSON> rules;
    
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
