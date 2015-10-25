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



package de.bo.aid.boese.ruler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.bo.aid.boese.constants.Status;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.xml.Action;
import de.bo.aid.boese.xml.CalculationList;
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
		if( b == null && gate.getGate().isEmpty() && !gate.getComponents().isEmpty()){
			b = condition(gate.getComponents().iterator().next(), deCoValue);
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
		if(status == Status.NO_STATUS || status == Status.INACTIVE || status == Status.DEFECT || status == Status.UNAVAILABLE || status == Status.COMMUNICATION_FAILURE || status == Status.UNKNOWN || status == Status.DELETED ){
			return false;
		}
		else{
			double isValue = Selects.currentValue(comp.getId());
			double comparativeValue = 0.0;
			try {
				comparativeValue = calculate(comp.getCalculation());
			} catch (Exception e) {
				System.err.println("Bad XML: " + e.getMessage());
				return false;
			}
			switch(comp.getComperator()){
			case EQUAL:
				return isValue == comparativeValue;
			case NOTEQUAL:
				return isValue != comparativeValue;
			case GREATEREQUAL:
				return isValue >= comparativeValue;
			case LOWEREQUAL:
				return isValue <= comparativeValue;
			case GREATERTHEN:
				return isValue > comparativeValue;
			case LOWERTHEN:
				return isValue < comparativeValue;
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
	 * @throws Exception the exception
	 */
	public List<Component> action(Action action) throws Exception {
		for(int i : action.getActivateRules()){
			Updates.activateRule(i);
		}
		for(int i : action.getDeactivateRules()){
			Updates.deactivateRule(i);
		}
		List<Component> toDos = new ArrayList<Component>();
		for(Component actor : action.getActors()){
			try {
				actor.setValue(calculate(actor.getCalculation()));
			} catch (Exception e) {
				System.err.println("Bad XML: " + e.getMessage());
				throw new Exception(e.getMessage());
			}
			toDos.add(actor);
		}
		return toDos;
	}

	/**
	 * Calculate.
	 *
	 * @param cl the cl
	 * @return the double
	 * @throws Exception the exception
	 */
	public double calculate(CalculationList cl) throws Exception{
		double erg = 0.0;
		HashSet<Double> values = new HashSet<>();
		for (double d : cl.getConstants()){
			values.add(d);
		}
		for(int i : cl.getVariables()){
			values.add(Selects.currentValue(i));
		}
		for(CalculationList next : cl.getCalculations()){
			values.add(calculate(next));
		}
		switch(cl.getCalculationType()){
		case ADD:
			erg = 0.0;
			for(double d : values){
				erg += d;
			}
			break;
		case SUB:
			if(cl.getFirst() != null){
				erg = calculate(cl.getFirst());
				for(double d : values){
					erg -= d;
				}
			}
			else{
				throw new Exception("No First for a SUB");
			}
			break;
		case MUL:
			erg = 1.0;
			for(double d : values){
				erg *= d;
			}
			break;
		case DIV:
			if(cl.getFirst() != null){
				erg = calculate(cl.getFirst());
				for(double d : values){
					erg /= d;
				}
			}
			else{
				throw new Exception("No First for a DIV");
			}
			break;
		case MOD:
			if(cl.getFirst() != null){
				erg = calculate(cl.getFirst()) % values.iterator().next();
			}
			else{
				throw new Exception("No First for a MOD");
			}
			break;
		case ABS:
			erg = values.iterator().next();
			if (erg < 0){
				erg *= -1;
			}
			break;
		}
		return erg;
	}

}
