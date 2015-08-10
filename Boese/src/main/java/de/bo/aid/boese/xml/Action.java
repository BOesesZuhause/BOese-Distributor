package de.bo.aid.boese.xml;

import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Action extends BoeseXML {
	private HashSet<Integer> activateRuleList;
	private HashSet<Integer> deactivateRuleList;
	private HashSet<Component> actorList;
	
	public Action(XMLType type, Document doc) {
		this.xmlType = type;
		this.doc = doc;
		activateRuleList = new HashSet<>();
		deactivateRuleList = new HashSet<>();
		actorList = new HashSet<>();
		NodeList nList = doc.getElementsByTagName("ACTIVATERULES");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				activateRuleList.add(Integer.valueOf(element.getElementsByTagName("ACTIVATE_RULEID").item(0).getTextContent()));
			}
		}
		nList = doc.getElementsByTagName("DEACTIVATERULES");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				deactivateRuleList.add(Integer.valueOf(element.getElementsByTagName("DEACTIVATE_RULEID").item(0).getTextContent()));
			}
		}
		nList = doc.getElementsByTagName("ACTORS");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				actorList.add(new Component(
						Integer.valueOf(element.getAttribute("ACTORID")), 
						Double.valueOf(element.getAttribute("VALUE")), 
						Long.valueOf(element.getAttribute("STARTTIME")),
						Long.valueOf(element.getAttribute("ENDTIME")),
						Long.valueOf(element.getAttribute("DURATION"))
						));
			}
		}
	}
	
	public HashSet<Integer> getActivateRules() {
		return activateRuleList;
	}
	
	public HashSet<Integer> getDeactivateRules() {
		return deactivateRuleList;
	}
	
	public HashSet<Component> getActors() {
		return actorList;
	}
	
	
}
