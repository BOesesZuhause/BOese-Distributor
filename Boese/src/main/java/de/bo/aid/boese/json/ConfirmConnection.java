package de.bo.aid.boese.json;

public class ConfirmConnection extends BoeseJson {
	private String password;
	private int connectorId;
	
	protected ConfirmConnection(String password, int confirmConnectorId, int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.CONFIRMCONNECTION, connectorId, seqNr, ackNr, status, timestamp);
		this.password = password;
		this.connectorId = connectorId;
	}

	public String getPassword() {
		return password;
	}
	
	public int getConnectorId() {
		return connectorId;
	}
}
