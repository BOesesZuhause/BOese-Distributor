package de.bo.aid.boese.xml;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Permission extends BoeseXML {
	private HashSet<Integer> groupIds;
	
	public Permission(XMLType type, Document doc) {
		this.xmlType = type;
		this.doc = doc;
		groupIds = new HashSet<>();
		NodeList nList = doc.getElementsByTagName("GROUPIDS");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				groupIds.add(Integer.valueOf(element.getElementsByTagName("GROUPID").item(0).getTextContent()));
			}
		}
	}
	
	public Set<Integer> getGroupId() {
		return groupIds;
	}

}
