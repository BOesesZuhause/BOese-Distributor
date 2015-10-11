
package de.bo.aid.boese.xml;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.bo.aid.boese.xml.CalculationList.CalculationTypes;
import de.bo.aid.boese.xml.Component.Comperator;
import de.bo.aid.boese.xml.GateList.GateType;

// TODO: Auto-generated Javadoc
/**
 * Class to handel XML rule parsing
 * Provides Methods to read xml in BoeseXML Java classes and to parse BoeseXML Java classes to xml.
 */
public class BoeseXML {
	
	/**
	 * Enumeration to define the typ of a BoeseXML object.
	 */
	public enum XMLType {
		/** The action. */
		ACTION, 
		/** The permission. */
		PERMISSION, 
		/** The condition. */
		CONDITION
	}
	
	/** The xml type. */
	protected XMLType xmlType = null;
	
	/** The doc. */
	protected Document doc = null;
	
	/**
	 * Basic constructor.
	 */
	public BoeseXML() {
		
	}
	
	/**
	 * Returns the xml type of this object.
	 *
	 * @return the type
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
			case "CONDITION":
				returnXML = new Condition(XMLType.CONDITION, doc);
				break;
			case "PERMISSION":
				returnXML = new Permission(XMLType.PERMISSION, doc);
			default: 
				break;
			}
		} catch (Exception e) {
			System.out.println();
		}
		
		return returnXML;
	}
	
	protected static CalculationList parseCalculation(NodeList nList) {
		CalculationList calcList = new CalculationList();
		CalculationList calculation = null;
		for (int j = 0; j < nList.getLength(); j++) {
			Node nChild = nList.item(j);
			switch (nChild.getNodeName()) {
			case "CONSTANT":
				calcList.addConstant(new Double(nChild.getTextContent()).doubleValue());
				break;
			case "VARIABLE":
				calcList.addValiable(new Integer(nChild.getTextContent()).intValue());
				break;
			case "FIRST":
				calculation = parseCalculation(nChild.getChildNodes());
				calcList.setFirst(calculation);
				break;
			case "ADD":
				calculation = parseCalculation(nChild.getChildNodes());
				calculation.setCalculationType(CalculationTypes.ADD);
				calcList.addCalculation(calculation);
				break;
			case "SUB":
				calculation = parseCalculation(nChild.getChildNodes());
				calculation.setCalculationType(CalculationTypes.SUB);
				calcList.addCalculation(calculation);
				break;
			case "MUL":
				calculation = parseCalculation(nChild.getChildNodes());
				calculation.setCalculationType(CalculationTypes.MUL);
				calcList.addCalculation(calculation);
				break;
			case "DIV":
				calculation = parseCalculation(nChild.getChildNodes());
				calculation.setCalculationType(CalculationTypes.DIV);
				calcList.addCalculation(calculation);
				break;
			case "MOD":
				calculation = parseCalculation(nChild.getChildNodes());
				calculation.setCalculationType(CalculationTypes.MOD);
				calcList.addCalculation(calculation);
				break;
			case "ABS":
				calculation = parseCalculation(nChild.getChildNodes());
				calculation.setCalculationType(CalculationTypes.ABS);
				calcList.addCalculation(calculation);
				break;
			default:
				break;
			}
		}
		return calcList;
	}
}
