
package de.bo.aid.boese.ruler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.sql.Update;

import de.bo.aid.boese.constants.Status;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.xml.Action;
import de.bo.aid.boese.xml.Component;
import de.bo.aid.boese.xml.GateList;
import de.bo.aid.boese.xml.GateList.GateType;

// TODO: Auto-generated Javadoc
/**
 * The Class Checker.
 */
public class Checker {

	/**
	 * De co in condition.
	 *
	 * @param decoid the decoid
	 * @param condition the condition
	 * @return true, if successful
	 */
	public boolean deCoInCondition(int decoid, String condition){
		if(condition.contains("<ID>" + decoid + "</ID>")){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Condition.
	 *
	 * @param gate the gate
	 * @param deCoValue the de co value
	 * @return the boolean
	 */
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
	
	/**
	 * Condition.
	 *
	 * @param comp the comp
	 * @param deCoValue the de co value
	 * @return the boolean
	 */
	public Boolean condition(Component comp, HashMap<Integer, Double> deCoValue){
		if(comp == null){
			return false;
		}
		int status = Selects.deviceComponent(comp.getId()).getStatus();
		if(status == Status.INACTIVE || status == Status.DEFECT || status == Status.UNAVAILABLE || status == Status.COMMUNICATION_FAILURE || status == Status.UNKNOWN || status == Status.DELETED ){
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

	/**
	 * Action.
	 *
	 * @param action the action
	 * @return the list
	 */
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
