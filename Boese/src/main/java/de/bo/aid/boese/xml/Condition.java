/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 */



package de.bo.aid.boese.xml;

import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.bo.aid.boese.ruler.TimeFormat;
import de.bo.aid.boese.xml.ComponentXML.Comperator;
import de.bo.aid.boese.xml.GateList.GateType;

// TODO: Auto-generated Javadoc
/**
 * Class that handles rule conditions.
 */
public class Condition extends BoeseXML {
	
	/** The conditions. */
	GateList conditions;
	
	/** The component ids. */
	HashSet<Integer> componentIds;
	
	/**
	 * Gets the component ids.
	 *
	 * @return the component ids
	 */
	public HashSet<Integer> getComponentIds() {
		return componentIds;
	}

	/**
	 * Parses the node list.
	 *
	 * @param nList the n list
	 * @return the gate list
	 */
	private GateList parseNodeList(NodeList nList) {
		GateList gateList = new GateList();
		GateList gate = null;
		for (int j = 0; j < nList.getLength(); j++) {
			Node nChild = nList.item(j);
			switch (nChild.getNodeName()) {
			case "COMPONENT":
				NodeList nComponentList = nList.item(j).getChildNodes();
				int compID = -1;
				CalculationList compValue = null;
				TimeFormat compStartTime = null;
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
						compValue = parseCalculation(nComponent.getChildNodes());
						break;
					case "START_TIME":
						compStartTime = new TimeFormat(nComponent.getTextContent());
						break;
					case "DURATION":
						compDuration = new Long(nComponent.getTextContent()).longValue();
						break;

					default:
						break;
					}
				}
				gateList.addComponent(new ComponentXML(compID, compValue, -1, compStartTime, compDuration, compComperator));
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
	
	/**
	 * Constructor that will be called by BoeseXML.readXML()
	 *
	 * @param type the type
	 * @param doc the doc
	 */
	public Condition(XMLType type, Document doc) {
		this.xmlType = type;
		this.doc = doc;
		componentIds = new HashSet<>();
		NodeList nList = doc.getChildNodes().item(0).getChildNodes();
		conditions = parseNodeList(nList);
	}
	
	/**
	 * Method to check if a certain device component is used in this rule condition.
	 *
	 * @param componentId the component id
	 * @return true, if successful
	 */
	public boolean containsComponent(int componentId) {
		return componentIds.contains(componentId);
	}
	
	/**
	 * Returns the rules as GateList
	 * A GateList contains a List of GateLists and an List of Components, as well as the type of the list.
	 * @return the rules as GateList
	 */
	public GateList getRule() {
		return conditions;
	}
}
