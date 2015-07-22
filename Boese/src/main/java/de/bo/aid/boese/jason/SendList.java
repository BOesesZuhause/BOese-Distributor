package de.bo.aid.boese.jason;

import java.util.HashMap;
import java.util.Set;


public class SendList extends BoeseJson{
	private String deviceName;
	private int serviceId;
	private HashMap<Integer, String> devices;
	
	public SendList(String deviceName, int serviceId, HashMap<Integer, String> devices) {
		this.deviceName = deviceName;
		this.serviceId = serviceId;
		this.devices = devices;
		this.messageType = MessageType.SENDDEVICE;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	
	public int getServiceId() {
		return serviceId;
	}
	
	public HashMap<Integer, String> getDevices() {
		return devices;
	}
	
	public Set<Integer> getDeviceIds() {
		return devices.keySet();
	}
	
	public String getDeviceDescription(int id) {
		return devices.get(Integer.valueOf(id));
	}
}
