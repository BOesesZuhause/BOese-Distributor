package de.bo.aid.boese.json;

import java.util.HashMap;

public class ConfirmDevices extends BoeseJson {
private HashMap<String, Integer> devices;
	
	public ConfirmDevices(HashMap<String, Integer> devices, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		super(MessageType.CONFIRMDEVICES, connectorId, seqNr, ackNr, status, timestamp);
		this.devices = devices;
	}

	public HashMap<String, Integer> getDevices() {
		return devices;
	}

}
