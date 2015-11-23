package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserCreateUnits extends BoeseJson {
	
	private HashSet<UnitJSON> units;

	public UserCreateUnits(HashSet<UnitJSON> units, int connectorId, int status, long timestamp) {
		super(MessageType.USERCREATEUNITS, connectorId, status, timestamp);
		this.units = units;
	}

	public HashSet<UnitJSON> getUnits() {
		return units;
	}
}
