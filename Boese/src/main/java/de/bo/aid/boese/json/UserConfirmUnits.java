package de.bo.aid.boese.json;

import java.util.HashMap;

public class UserConfirmUnits extends BoeseJson {

	private HashMap<Integer, Integer> tempUnits;
	
	public UserConfirmUnits(HashMap<Integer, Integer> tempUnits, int connectorId, int status, long timestamp) {
		super(MessageType.USERCONFIRMUNITS, connectorId, status, timestamp);
		this.tempUnits = tempUnits;
	}

	public HashMap<Integer, Integer> getTempUnits() {
		return tempUnits;
	}

}
