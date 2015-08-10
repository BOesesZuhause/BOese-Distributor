package de.bo.aid.boese.xml;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class BoeseXML {
	public enum XMLType {
		ACTION, PERMISSION, CONDITION
	}
	
	protected XMLType xmlType = null;
	protected Document doc = null;
	
	public BoeseXML() {
		
	}
	
	public XMLType getType() {
		return xmlType;
	}
	
	public static OutputStream parseXML(BoeseXML xml) {
		return null;
	}
	
	public static BoeseXML readXML(InputStream is) {
		BoeseXML returnXML = null;
		try {
			DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder xmlBuilder;
			xmlBuilder = xmlFactory.newDocumentBuilder();
			Document doc = xmlBuilder.parse(is);
			doc.getDocumentElement().normalize();
			switch(doc.getDocumentElement().getNodeName()) {
			case "Action":
				returnXML = new Action(XMLType.ACTION, doc);
				break;
			case "Condition":
				returnXML = new Condition(XMLType.CONDITION, doc);
				break;
				
			case "Permission":
				returnXML = new Permission(XMLType.PERMISSION, doc);
			default: 
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnXML;
	}
}
