package de.bo.aid.boese.json;

import java.util.HashMap;


public class ConfirmDeviceComponents extends BoeseJson {
	private HashMap<String, Integer> components;
	
	protected ConfirmDeviceComponents(HashMap<String, Integer> components, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		super(MessageType.CONFIRMDEVICECOMPONENTS, connectorId, seqNr, ackNr, status, timestamp);
		this.components = components;
	}

	public HashMap<String, Integer> getComponents() {
		return components;
	}
}
