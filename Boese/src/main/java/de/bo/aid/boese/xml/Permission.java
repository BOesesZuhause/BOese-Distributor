
package de.bo.aid.boese.xml;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class Permission.
 */
public class Permission extends BoeseXML {
	
	/** The group ids. */
	private HashSet<Integer> groupIds;
	
	/**
	 * Instantiates a new permission.
	 *
	 * @param type the type
	 * @param doc the doc
	 */
	public Permission(XMLType type, Document doc) {
		this.xmlType = type;
		this.doc = doc;
		groupIds = new HashSet<>();
		NodeList nList = doc.getElementsByTagName("GROUP_ID");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				groupIds.add(Integer.valueOf(element.getTextContent()));
			}
		}
	}
	
	/**
	 * Gets the group id.
	 *
	 * @return the group id
	 */
	public Set<Integer> getGroupId() {
		return groupIds;
	}

}
