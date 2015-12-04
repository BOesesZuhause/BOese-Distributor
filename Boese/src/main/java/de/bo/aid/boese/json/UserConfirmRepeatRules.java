package de.bo.aid.boese.json;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class UserConfirmRepeatRules.
 */
public class UserConfirmRepeatRules extends BoeseJson{
    
    /** The temp rules. */
    HashMap<Integer, Integer> tempRules;


    /**
     * Instantiates a new user confirm repeat rules.
     *
     * @param messageType the message type
     * @param connectorId the connector id
     * @param status the status
     * @param timestamp the timestamp
     */
    protected UserConfirmRepeatRules(MessageType messageType, int connectorId, int status, long timestamp) {
        super(messageType, connectorId, status, timestamp);
        // TODO Auto-generated constructor stub
    }
    
    

    /**
     * Instantiates a new user confirm repeat rules.
     *
     * @param tempRules the temp rules
     * @param connectorId the connector id
     * @param status the status
     * @param timestamp the timestamp
     */
    public UserConfirmRepeatRules(HashMap<Integer, Integer> tempRules, int connectorId, int status, long timestamp){
        super(MessageType.USERCONFIRMREPEATRULES, connectorId, status, timestamp);
        this.tempRules = tempRules;
    }



    /**
     * Gets the temp rules.
     *
     * @return the temp rules
     */
    public HashMap<Integer, Integer> getTempRules() {
        return tempRules;
    }
    
    
    
    }
