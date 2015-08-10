package de.bo.aid.boese.json;


public class SendValue extends BoeseJson {
	private int deviceId;
	private int deviceComponentId;
	private double value;
	private long valueTimestamp;
	
	
	public SendValue(int deviceId, int deviceComponentId, double value, long timestamp, 
			int idConnector, int seqNr, int ackNr, int status, long headerTimestamp) {
		super(MessageType.SENDVALUE, idConnector, seqNr, ackNr, status, headerTimestamp);
		this.deviceId = deviceId;
		this.deviceComponentId = deviceComponentId;
		this.value = value;
		this.valueTimestamp = timestamp;
	}

	public int getIdDevice() {
		return deviceId;
	}

	public int getIdDeviceComponent() {
		return deviceComponentId;
	}

	public double getValue() {
		return value;
	}

	public long getValueTimestamp() {
		return valueTimestamp;
	}
}
