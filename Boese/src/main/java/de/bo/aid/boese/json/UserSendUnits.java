package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserSendUnits extends BoeseJson {
	
	private HashSet<UnitJSON> units;

	public UserSendUnits(HashSet<UnitJSON> units, int connectorId, int status, long timestamp) {
		super(MessageType.USERSENDUNITS, connectorId, status, timestamp);
		this.units = units;
	}

	public HashSet<UnitJSON> getUnits() {
		return units;
	}

}
