package de.bo.aid.boese.jason;
import java.io.InputStream;
import java.io.OutputStream;

import javax.json.*;

public class BoeseJson {
	protected MessageType messageType = null;
	
	public enum MessageType {
		SENDVALUE, REQUESTVALUE, ADDDEVICE, REMOVEDEVICE, SENDDEVICE, SENDDEVICECOMPONENT, REQUESTLIST, SENDLIST
	}

	public MessageType getType() {
		return messageType;
	}
	
	public static BoeseJson readMessage(InputStream is) {
		return null;
	}
	
	public static OutputStream parseMessage(BoeseJson message) {
		return null;
	}
}
