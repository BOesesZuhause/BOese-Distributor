
package de.bo.aid.boese.xml;

import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class Action.
 */
public class Action extends BoeseXML {
	
	/** The activate rule list. */
	private HashSet<Integer> activateRuleList;
	
	/** The deactivate rule list. */
	private HashSet<Integer> deactivateRuleList;
	
	/** The deactivate todo list. */
	private HashSet<Integer> deactivateTodoList;
	
	/** The activate todo list. */
	private HashSet<Integer> activateTodoList;
	
	/** The delete todo list. */
	private HashSet<Integer> deleteTodoList;
	
	/** The actor list. */
	private HashSet<Component> actorList;
	
	/**
	 * Instantiates a new action.
	 *
	 * @param type the type
	 * @param doc the doc
	 */
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
		nList = doc.getElementsByTagName("AKTOR");
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
	
	/**
	 * Gets the activate rules.
	 *
	 * @return the activate rules
	 */
	public HashSet<Integer> getActivateRules() {
		return activateRuleList;
	}
	
	/**
	 * Gets the deactivate rules.
	 *
	 * @return the deactivate rules
	 */
	public HashSet<Integer> getDeactivateRules() {
		return deactivateRuleList;
	}
	
	/**
	 * Gets the actors.
	 *
	 * @return the actors
	 */
	public HashSet<Component> getActors() {
		return actorList;
	}
	
	/**
	 * Gets the activate todos.
	 *
	 * @return the activate todos
	 */
	public HashSet<Integer> getActivateTodos() {
		return activateTodoList;
	}
	
	/**
	 * Gets the deactivate todos.
	 *
	 * @return the deactivate todos
	 */
	public HashSet<Integer> getDeactivateTodos() {
		return deactivateTodoList;
	}
	
	/**
	 * Gets the delete todos.
	 *
	 * @return the delete todos
	 */
	public HashSet<Integer> getDeleteTodos() {
		return deleteTodoList;
	}
}
