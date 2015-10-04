package de.bo.aid.boese.json;

public class UserRequestGeneral extends BoeseJson {

	public UserRequestGeneral(MessageType messageType, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(messageType, connectorId, seqNr, ackNr, status, timestamp);
	}
	
}
