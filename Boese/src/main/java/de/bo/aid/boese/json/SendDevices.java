package de.bo.aid.boese.json;

import java.util.HashMap;

public class SendDevices extends BoeseJson {
	private HashMap<String, Integer> devices;
	
	protected SendDevices(HashMap<String, Integer> devices, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		super(MessageType.SENDDEVICES, connectorId, seqNr, ackNr, status, timestamp);
		this.devices = devices;
	}

	public HashMap<String, Integer> getDevices() {
		return devices;
	}
}
