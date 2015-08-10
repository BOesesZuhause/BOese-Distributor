package de.bo.aid.boese.json;

public class ConfirmConnection extends BoeseJson {
	private String password;
	
	protected ConfirmConnection(String password, int confirmConnectorId, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.CONFIRMCONNECTION, connectorId, seqNr, ackNr, status, timestamp);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
}
