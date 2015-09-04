package de.bo.aid.boese.xml;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * Class to handel XML rule parsing
 * Provides Methods to read xml in BoeseXML Java classes and to parse BoeseXML Java classes to xml
 */
public class BoeseXML {
	/**
	 * Enumeration to define the typ of a BoeseXML object
	 */
	public enum XMLType {
		ACTION, PERMISSION, CONDITION
	}
	
	protected XMLType xmlType = null;
	protected Document doc = null;
	
	/**
	 * Basic constructor
	 */
	public BoeseXML() {
		
	}
	
	/**
	 * Returns the xml type of this object
	 * @return
	 */
	public XMLType getType() {
		return xmlType;
	}
	
	/**
	 * Method to parse a BoeseXML object to xml.
	 * @param xml the BoeseXML object to parse into an xml output stream
	 * @param os the stream to write the xml in,if null a new stream is generated
	 * @return true if the parsing was successful
	 */
	public static boolean parseXML(BoeseXML xml, OutputStream os) {
		return false;
	}
	
	/**
	 * Method to read xml from an input stream and parse it to a BoeseXML object.
	 * @param is The input stream containing the xml code
	 * @return a Boese XML object.
	 */
	public static BoeseXML readXML(InputStream is) {
		BoeseXML returnXML = null;
		try {
			DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder xmlBuilder;
			xmlBuilder = xmlFactory.newDocumentBuilder();
			Document doc = xmlBuilder.parse(is);
			doc.getDocumentElement().normalize();
			switch(doc.getDocumentElement().getNodeName()) {
			case "ACTION":
				returnXML = new Action(XMLType.ACTION, doc);
				break;
			case "CONDITIONS":
				returnXML = new Condition(XMLType.CONDITION, doc);
				break;
				
			case "PERMISSION":
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
