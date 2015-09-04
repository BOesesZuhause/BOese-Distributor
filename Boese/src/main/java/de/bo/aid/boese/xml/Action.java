package de.bo.aid.boese.xml;

import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Action extends BoeseXML {
	private HashSet<Integer> activateRuleList;
	private HashSet<Integer> deactivateRuleList;
	private HashSet<Integer> deactivateTodoList;
	private HashSet<Integer> activateTodoList;
	private HashSet<Integer> deleteTodoList;
	private HashSet<Component> actorList;
	
	public Action(XMLType type, Document doc) {
		this.xmlType = type;
		this.doc = doc;
		activateRuleList = new HashSet<>();
		deactivateRuleList = new HashSet<>();
		activateTodoList = new HashSet<>();
		deactivateTodoList = new HashSet<>();
		deleteTodoList = new HashSet<>();
		actorList = new HashSet<>();
		NodeList nList = doc.getElementsByTagName("ACTIVATE_RULE_ID");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				activateRuleList.add(Integer.valueOf(element.getTextContent()));
			}
		}
		nList = doc.getElementsByTagName("DEACTIVATE_RULE_ID");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				deactivateRuleList.add(Integer.valueOf(element.getTextContent()));
			}
		}
		nList = doc.getElementsByTagName("DEACTIVATE_TODO_ID");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				deactivateTodoList.add(Integer.valueOf(element.getTextContent()));
			}
		}
		nList = doc.getElementsByTagName("ACTIVATE_TODO_ID");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				activateTodoList.add(Integer.valueOf(element.getTextContent()));
			}
		}
		nList = doc.getElementsByTagName("DELETE_TODO_ID");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				deleteTodoList.add(Integer.valueOf(element.getTextContent()));
			}
		}
		nList = doc.getElementsByTagName("ACTOR");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.hasChildNodes()) {
				NodeList nActorList = node.getChildNodes();
				int actorID = -1;
				double actorValue = -1;
				double actorResetValue = -1;
				long actorStartTime = -1;
				long actorDuration = -1;
				int actorRepeatAfterEnd = 0;
				
				for (int j = 0; j < nActorList.getLength(); j++) {
					Node nActor = nActorList.item(j);
					switch (nActor.getNodeName()) {
					case "ID":
						actorID = new Integer(nActor.getTextContent()).intValue();
						break;
					case "VALUE":
						actorValue = new Double(nActor.getTextContent()).doubleValue();
						break;
					case "RESET_VALUE":
						actorResetValue = new Double(nActor.getTextContent()).doubleValue();
						break;
					case "START_TIME":
						actorStartTime = new Long(nActor.getTextContent()).longValue();
						break;
					case "DURATION":
						actorDuration = new Long(nActor.getTextContent()).longValue();
						break;
					case "REPEAT_AFTER_END":
						actorRepeatAfterEnd = new Integer(nActor.getTextContent()).intValue();
						break;
					default:
						break;
					}
				}
				actorList.add(new Component(actorID, actorValue, actorResetValue, actorStartTime, actorDuration, actorRepeatAfterEnd));
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
	
	public HashSet<Integer> getActivateTodos() {
		return activateTodoList;
	}
	
	public HashSet<Integer> getDeactivateTodos() {
		return deactivateTodoList;
	}
	
	public HashSet<Integer> getDeleteTodos() {
		return deleteTodoList;
	}
}
