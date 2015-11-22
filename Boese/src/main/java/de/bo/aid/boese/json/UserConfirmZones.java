package de.bo.aid.boese.json;

import java.util.HashMap;

public class UserConfirmZones extends BoeseJson {

	private HashMap<Integer, Integer> tempZones;
	
	protected UserConfirmZones(HashMap<Integer, Integer> tempZones, int connectorId, int status, long timestamp) {
		super(MessageType.USERCONFIRMZONES, connectorId, status, timestamp);
		this.tempZones = tempZones;
	}

	public HashMap<Integer, Integer> getTempZones() {
		return tempZones;
	}

}
