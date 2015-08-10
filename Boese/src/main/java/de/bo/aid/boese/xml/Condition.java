package de.bo.aid.boese.xml;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Condition extends BoeseXML {
	ConditionList conditions;
	
	private ConditionList parseNodeList(NodeList nList) {
		ConditionList conditionList = new ConditionList();
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			switch (node.getNodeName()) {
			case "SENSOR":
				Sensor sensor = null;
				//TODO create Sensor
				conditionList.addSensor(sensor);
				break;
			case "AND":
				conditionList.andConditions.add(parseNodeList(node.getChildNodes()));
				break;
			case "OR":
				conditionList.orConditions.add(parseNodeList(node.getChildNodes()));
				break;
			case "XOR":
				conditionList.xorConditions.add(parseNodeList(node.getChildNodes()));
				break;
			default:
				break;
			}
		}
		
		return conditionList;
	}
	
	public Condition(XMLType type, Document doc) {
		this.xmlType = type;
		this.doc = doc;
		NodeList nList = doc.getChildNodes();
		conditions = parseNodeList(nList);
	}
	
	// Diese miese Funktion hat ne mieserable Laufzeit!!!!
	private boolean containsSensor(int sensorID, ConditionList list) {
		boolean returnValue = false;
		Iterator<Sensor> itSensor = list.getSensors().iterator();
		while (itSensor.hasNext()) {
			if (itSensor.next().getId() == sensorID) {
				return true;
			} else {}
		}
		Iterator<ConditionList> itAnd = list.getAndConditions().iterator();
		while (itAnd.hasNext()) {
			return containsSensor(sensorID, itAnd.next());
		}
		Iterator<ConditionList> itOr = list.getOrConditions().iterator();
		while (itOr.hasNext()) {
			return containsSensor(sensorID, itOr.next());
		}
		Iterator<ConditionList> itXor = list.getXorConditions().iterator();
		while (itXor.hasNext()) {
			return containsSensor(sensorID, itXor.next());
		}
		return returnValue;
	}
	
	public boolean containsSensor(int sensorID) {
		return containsSensor(sensorID, conditions);
	}
	

}
