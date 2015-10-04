package de.bo.aid.boese.json;

import java.util.HashSet;

public class UserRequestConnectors extends BoeseJson{
	HashSet<Integer> connectorIds;

	public UserRequestConnectors(HashSet<Integer> connectorIds, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.USERREQUESTCONNECTORS, connectorId, seqNr, ackNr, status, timestamp);
		this.connectorIds = connectorIds;
	}
	
	public UserRequestConnectors(int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.USERREQUESTALLCONNECTORS, connectorId, seqNr, ackNr, status, timestamp);
		this.connectorIds = null;
	}
	
	public HashSet<Integer> getConnectorIds() {
		return connectorIds;
	}

}
