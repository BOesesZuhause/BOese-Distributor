package de.bo.aid.boese.json;


public class RequestConnection extends BoeseJson {
	private String connectorName;
	private String password;
	
	public RequestConnection(String name, String password,
			int idConnector, int seqNr, int ackNr, int status, long headerTimestamp) {
		super(MessageType.REQUESTCONNECTION, idConnector, seqNr, ackNr, status, headerTimestamp);
		this.connectorName = name;
		this.password = password;
	}
	
	public String getConnectorName() {
		return connectorName;
	}
	
	public String getPassword() {
		return password;
	}
}