package de.bo.aid.boese.ruler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.sql.Update;

import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.xml.Action;
import de.bo.aid.boese.xml.Component;
import de.bo.aid.boese.xml.GateList;
import de.bo.aid.boese.xml.GateList.GateType;

public class Checker {

	public boolean deCoInCondition(int decoid, String condition){
		if(condition.contains("<ID>" + decoid + "</ID>")){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Boolean condition(GateList gate, HashMap<Integer, Double> deCoValue){
		Boolean b = null;
		GateType gt = gate.getGateType();
		Set<GateList> sg = gate.getGate();
		for(GateList gl : sg){			
			Boolean zwi = condition(gl, deCoValue);
			for(Component c : gl.getComponents()){
				if(zwi == null){
					zwi = condition(c, deCoValue);
				}
				else if (gl.getGateType() == GateType.AND_GATE){
					zwi = condition(c, deCoValue) && zwi;
				}
				else{
					zwi = condition(c, deCoValue) || zwi;
				}
			}
			if(b == null){
				b = new Boolean(zwi);
			}
			else if (gt == GateType.AND_GATE){
				b = new Boolean(zwi && b);
			}
			else if (gt == GateType.OR_GATE){
				b = new Boolean(zwi || b);
			}
			else{
				//b = b;
			}				
		}
		return b;

	}
	
	public Boolean condition(Component comp, HashMap<Integer, Double> deCoValue){
		if(comp == null){
			return false;
		}
		else{
			switch(comp.getComperator()){
			case EQUAL:
				return deCoValue.get(comp.getId()) == comp.getValue();
			case NOTEQUAL:
				return deCoValue.get(comp.getId()) != comp.getValue();
			case GREATEREQUAL:
				return deCoValue.get(comp.getId()) >= comp.getValue();
			case LOWEREQUAL:
				return deCoValue.get(comp.getId()) <= comp.getValue();
			case GREATERTHEN:
				return deCoValue.get(comp.getId()) > comp.getValue();
			case LOWERTHEN:
				return deCoValue.get(comp.getId()) < comp.getValue();
			default:
				return false;
			}
		}
	}

	public List<Component> action(Action action) {
		for(int i : action.getActivateRules()){
			Updates.activateRule(i);
		}
		for(int i : action.getDeactivateRules()){
			Updates.deactivateRule(i);
		}
		List<Component> toDos = new ArrayList<Component>();
		for(Component actor : action.getActors()){
			toDos.add(actor);
		}
		return toDos;
	}
}
