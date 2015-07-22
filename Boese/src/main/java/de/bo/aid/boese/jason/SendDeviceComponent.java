package de.bo.aid.boese.jason;

import java.util.HashMap;
import java.util.Set;


public class SendDeviceComponent extends BoeseJson {
	private HashMap<Integer, String> deviceComponents;
	
	public SendDeviceComponent(HashMap<Integer, String> deviceComponents) {
		this.deviceComponents = deviceComponents;
		this.messageType = MessageType.SENDDEVICECOMPONENT;
	}
	
	public HashMap<Integer, String> getDeviceComponents() {
		return deviceComponents;
	}
	
	public Set<Integer> getComponentIds() {
		return deviceComponents.keySet();
	}
	
	public String getComponentName(int id) {
		return deviceComponents.get(Integer.valueOf(id));
	}

}
