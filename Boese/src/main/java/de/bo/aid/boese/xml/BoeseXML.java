package de.bo.aid.boese.xml;

import java.io.InputStream;
import java.io.OutputStream;

public class BoeseXML {
	public enum XMLType {
		ACTION, PERMISSION, CONDITION
	}
	
	private XMLType xmlType = null;
	
	public BoeseXML() {
		
	}
	
	public XMLType getType() {
		return xmlType;
	}
	
	public static OutputStream parseXML(BoeseXML xml) {
		return null;
	}
	
	public static BoeseXML readXML(InputStream is) {
		return null;
	}
}
