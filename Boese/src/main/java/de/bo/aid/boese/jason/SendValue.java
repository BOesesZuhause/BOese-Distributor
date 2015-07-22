package de.bo.aid.boese.jason;

import java.util.HashMap;
import java.util.Set;

public class SendValue extends BoeseJson {
	private int idDeviceComponent;
	private HashMap<Long, Double> values;
	
	public SendValue(int id, HashMap<Long, Double> values) {
		this.idDeviceComponent = id;
		this.values = values;
		this.messageType = MessageType.SENDVALUE;
	}
	
	public int getDeviceComponentId() {
		return idDeviceComponent;
	}
	
	public HashMap<Long, Double> getValues() {
		return values;
	}
	
	public double getValue(long timestamp) {
		return values.get(Long.valueOf(timestamp));
	}
	
	public Set<Long> getTimestamps() {
		return values.keySet();
	}
}
