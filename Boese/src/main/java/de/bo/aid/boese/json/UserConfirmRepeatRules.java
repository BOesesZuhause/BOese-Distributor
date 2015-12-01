package de.bo.aid.boese.json;

import java.util.HashMap;

public class UserConfirmRepeatRules extends BoeseJson{
    
    HashMap<Integer, Integer> tempRules;


    protected UserConfirmRepeatRules(MessageType messageType, int connectorId, int status, long timestamp) {
        super(messageType, connectorId, status, timestamp);
        // TODO Auto-generated constructor stub
    }
    
    

    public UserConfirmRepeatRules(HashMap<Integer, Integer> tempRules, int connectorId, int status, long timestamp){
        super(MessageType.USERCONFIRMREPEATRULES, connectorId, status, timestamp);
        this.tempRules = tempRules;
    }



    public HashMap<Integer, Integer> getTempRules() {
        return tempRules;
    }
    
    
    
    }
