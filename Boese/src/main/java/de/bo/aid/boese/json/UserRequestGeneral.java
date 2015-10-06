package de.bo.aid.boese.json;

public class UserRequestGeneral extends BoeseJson {

	public UserRequestGeneral(MessageType messageType, int connectorId, int status,
			long timestamp) {
		super(messageType, connectorId, status, timestamp);
	}
	
}
