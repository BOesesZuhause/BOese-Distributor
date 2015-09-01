package de.bo.aid.boese.ruler;

import java.util.ArrayList;
import java.util.List;

import de.bo.aid.boese.xml.Action;
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.Component;
import de.bo.aid.boese.xml.Condition;

public class Interpretor {
	
	public List<Integer> getAllDeCoIdsCondition(BoeseXML conditions){
		List<Integer> list = new ArrayList<Integer>();
		for (Component comp : ((Condition)conditions).getRule().getComponents()) {
			list.add(new Integer(comp.getId()));
		}
		return list;
	}
	
//	public List<Integer> getAllDeCoIdsAction(BoeseXML actions){
//		List<Integer> list = new ArrayList<Integer>();
//		for (Component comp : ((Action)actions).getRule().getComponents()) {
//			list.add(new Integer(comp.getId()));
//		}
//		return list;
//	}

}
