package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserSendZones extends BoeseJson {
	private HashSet<Zone> zones;
	
	public UserSendZones(HashSet<Zone> zones, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.USERSENDZONES, connectorId, seqNr, ackNr, status, timestamp);
		this.zones = zones;
	}
	
	public HashSet<Zone> getZones() {
		return zones;
	}

}
