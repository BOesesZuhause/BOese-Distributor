package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserCreateZones extends BoeseJson {
	
	private HashSet<ZoneJSON> zones;

	protected UserCreateZones(HashSet<ZoneJSON> zones, int connectorId, int status, long timestamp) {
		super(MessageType.USERCREATEZONES, connectorId, status, timestamp);
		this.zones = zones;
	}

	public HashSet<ZoneJSON> getZones() {
		return zones;
	}

}
