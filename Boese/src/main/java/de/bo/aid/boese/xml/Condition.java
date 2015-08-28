package de.bo.aid.boese.xml;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.bo.aid.boese.xml.Component.Comperator;
import de.bo.aid.boese.xml.GateList.GateType;


public class Condition extends BoeseXML {
	GateList conditions;
	HashSet<Integer> componentIds;
	
	private GateList parseNodeList(NodeList nList) {
		GateList gateList = new GateList();
		GateList gate = null;
		for (int j = 0; j < nList.getLength(); j++) {
			Node nChild = nList.item(j);
			switch (nChild.getNodeName()) {
			case "COMPONENT":
				NodeList nComponentList = nList.item(j).getChildNodes();
				int compID = -1;
				double compValue = -1;
				long compStartTime = -1;
				long compDuration = -1;
				Comperator compComperator = null;
				for (int k = 0; k < nComponentList.getLength(); k++) {
					Node nComponent = nComponentList.item(k);
					switch (nComponent.getNodeName()) {
					case "ID":
						compID = new Integer(nComponent.getTextContent()).intValue();
						componentIds.add(compID);
						break;
					case "COMPERATOR":
						switch (nComponent.getTextContent()) {
						case "==":
							compComperator = Comperator.EQUAL;
							break;
						case ">=":
							compComperator = Comperator.GREATEREQUAL;
							break;
						case "<=":
							compComperator = Comperator.LOWEREQUAL;
							break;
						case "<":
							compComperator = Comperator.LOWERTHEN;
							break;
						case ">":
							compComperator = Comperator.GREATERTHEN;
							break;
						case "!=":
							compComperator = Comperator.NOTEQUAL;
							break;

						default:
							break;
						}
						break;
					case "VALUE":
						compValue = new Double(nComponent.getTextContent()).doubleValue();
						break;
					case "START_TIME":
						compStartTime = new Long(nComponent.getTextContent()).longValue();
						break;
					case "DURATION":
						compDuration = new Long(nComponent.getTextContent()).longValue();
						break;

					default:
						break;
					}
				}
				gateList.addComponent(new Component(compID, compValue, -1, compStartTime, compDuration, compComperator));
				break;
			case "AND":
				gate = parseNodeList(nChild.getChildNodes());
				gate.setGateType(GateType.AND_GATE);
				gateList.addGate(gate);
				break;
			case "OR":
				gate = parseNodeList(nChild.getChildNodes());
				gate.setGateType(GateType.OR_GATE);
				gateList.addGate(gate);
				break;
			default:
				break;
			}
		}
		
		return gateList;
	}
	
	public Condition(XMLType type, Document doc) {
		this.xmlType = type;
		this.doc = doc;
		componentIds = new HashSet<>();
		NodeList nList = doc.getChildNodes().item(0).getChildNodes();
		conditions = parseNodeList(nList);
	}
	
	public boolean containsComponent(int componentId) {
		return componentIds.contains(componentId);
	}
	
	public GateList getRule() {
		return conditions;
	}
}
