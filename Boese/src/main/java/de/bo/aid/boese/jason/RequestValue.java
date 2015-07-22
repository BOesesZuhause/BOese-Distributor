package de.bo.aid.boese.jason;


public class RequestValue extends BoeseJson {
	private int idDeviceComponent;
	private long tsBegin;
	private long tsEnd;
	
	public RequestValue(int id, long tsBegin, long tsEnd) {
		this.idDeviceComponent = id;
		this.tsBegin = tsBegin;
		this.tsEnd = tsEnd;
		this.messageType = MessageType.REQUESTVALUE;
	}
	
	public int getDeviceComponentId() {
		return idDeviceComponent;
	}
	
	public long getBegin() {
		return tsBegin;
	}
	
	public long getEnd() {
		return tsEnd;
	}
}
