package de.bo.aid.boese.json;

import java.util.HashMap;

public class UserSendConnectors extends BoeseJson{
	HashMap<Integer, String> connectors;

	public UserSendConnectors(HashMap<Integer, String> connectors, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.USERSENDCONNETORS, connectorId, seqNr, ackNr, status, timestamp);
		this.connectors = connectors;
	}
	
	public HashMap<Integer, String> getConnectors() {
		return connectors;
	}

}
