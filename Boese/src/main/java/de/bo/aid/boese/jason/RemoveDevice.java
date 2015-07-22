package de.bo.aid.boese.jason;


public class RemoveDevice extends BoeseJson {
	private int idDevice;
	
	public RemoveDevice(int id) {
		this.idDevice = id;
		this.messageType = MessageType.REMOVEDEVICE;
	}
	
	public int getDeviceId() {
		return idDevice;
	}

}
