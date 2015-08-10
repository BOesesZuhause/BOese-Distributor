package de.bo.aid.boese.json;


public class RequestConnection extends BoeseJson {
	private String name;
	
	public RequestConnection(String name, 
			int idConnector, int seqNr, int ackNr, int status, long headerTimestamp) {
		super(MessageType.REQUESTCONNECTION, idConnector, seqNr, ackNr, status, headerTimestamp);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}