package de.bo.aid.boese.jason;

import java.util.HashMap;
import java.util.Set;


public class SendDevice extends BoeseJson {
	private int idDevice;
	HashMap<Integer, String> devices;
	
	public SendDevice(int id, HashMap<Integer, String> devices) {
		this.idDevice = id;
		this.devices = devices;
		this.messageType = MessageType.SENDDEVICE;
	}
	
	public int getDeviceId() {
		return idDevice;
	}
	
	public HashMap<Integer, String> getDevices() {
		return devices;
	}
	
	public Set<Integer> getComponentIDs() {
		return devices.keySet();
	}
	
	public String getComponentName(int id) {
		return devices.get(Integer.valueOf(id));
	}

}
