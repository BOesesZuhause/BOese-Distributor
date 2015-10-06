package de.bo.aid.boese.json;

import java.util.HashMap;

import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;

public class UserSendTemps extends BoeseJson{
	private HashMap<Integer, String> tempConnectors;
	private HashMap<Integer, TempDevice> tempDevices;
	private HashMap<Integer, TempComponent> tempDeviceComponents;

	public UserSendTemps(HashMap<Integer, String> tempConnectors, HashMap<Integer, TempDevice> tempDevices, 
			HashMap<Integer, TempComponent> tempDeviceComponents,
			int connectorId, int status, long timestamp) {
		super(MessageType.USERSENDTEMPS, connectorId, status, timestamp);
		this.tempConnectors = tempConnectors;
		this.tempDeviceComponents = tempDeviceComponents;
		this.tempDevices = tempDevices;
	}

	public HashMap<Integer, String> getTempConnectors() {
		return tempConnectors;
	}

	public HashMap<Integer, TempDevice> getTempDevices() {
		return tempDevices;
	}

	public HashMap<Integer, TempComponent> getTempDeviceComponents() {
		return tempDeviceComponents;
	}
}
