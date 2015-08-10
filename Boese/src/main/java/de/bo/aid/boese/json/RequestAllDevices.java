package de.bo.aid.boese.json;

public class RequestAllDevices extends BoeseJson {

	public RequestAllDevices(int connectorId, int seqNr, int ackNr, int status,
			long timestamp) {
		super(MessageType.REQUESTALLDEVICES, connectorId, seqNr, ackNr, status, timestamp);
	}

}
