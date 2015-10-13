package de.bo.aid.boese.json;

import java.util.HashMap;
import java.util.HashSet;

public class UserConfirmTemps extends BoeseJson {
	HashSet<Integer> tempConnectors;
	HashMap<Integer, Integer> tempDevices;
	HashSet<UserTempComponent> tempDeviceComponents;

	protected UserConfirmTemps(HashSet<Integer> tempConnectors, HashMap<Integer, Integer> tempDevices, HashSet<UserTempComponent> tempDeviceComponents, 
			int connectorId, int status, long timestamp) {
		super(MessageType.USERCONFIRMTEMPS, connectorId, status, timestamp);
		this.tempConnectors = tempConnectors;
		this.tempDevices = tempDevices;
		this.tempDeviceComponents = tempDeviceComponents;
	}

	public HashSet<Integer> getTempConnectors() {
		return tempConnectors;
	}

	public HashMap<Integer, Integer> getTempDevices() {
		return tempDevices;
	}

	public HashSet<UserTempComponent> getTempDeviceComponents() {
		return tempDeviceComponents;
	}
}
