package de.bo.aid.boese.json;

import java.util.HashMap;

public class UserConfirmRules extends BoeseJson {
	HashMap<Integer, Integer> tempRules;
	
	public UserConfirmRules(HashMap<Integer, Integer> tempRules, int connectorId, int status, long timestamp) {
		super(MessageType.USERCONFIRMRULES, connectorId, status, timestamp);
		this.tempRules = tempRules;
	}
	
	public HashMap<Integer, Integer> getTempRules() {
		return tempRules;
	}

}
