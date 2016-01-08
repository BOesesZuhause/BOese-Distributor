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
import java.util.List;
import java.util.Set;

import de.bo.aid.boese.constants.Status;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.exceptions.NoCalculationTypeException;
import de.bo.aid.boese.exceptions.NoFirstCalculationException;
import de.bo.aid.boese.exceptions.OnlyTwoObjectsForModuloException;
import de.bo.aid.boese.xml.Action;
import de.bo.aid.boese.xml.CalculationList;
import de.bo.aid.boese.xml.ComponentXML;
import de.bo.aid.boese.xml.GateList;
import de.bo.aid.boese.xml.GateList.GateType;

/**
 * The Class Checker checks the XML-Rules.
 */
public class Checker {

	/**
	 * Checks if the given DeviceCompoent is part of the Condition.
	 *
	 * @param decoid the id of the to be tested DeviceComponent
	 * @param condition the condition String
	 * @return true, if the DeviceComponent is part of the Condition
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
	 * This Method checks if the Condition is True.
	 *
	 * @param gate a GateList object. It builds the Condition in the XML-Rules in Java-Objects, parsed by the XML-Parser
	 * @return true, if the Condition is true
	 */
	public Boolean condition(GateList gate){
		Boolean ergebnis = null;
		GateType gatetype = gate.getGateType();
		Set<GateList> setGateList = gate.getGate();
		for(GateList gatelist : setGateList){
			Boolean zwischenErgebnis = condition(gatelist);
			for(ComponentXML c : gatelist.getComponents()){
				if(zwischenErgebnis == null){
					zwischenErgebnis = condition(c);
				}
				else if (gatelist.getGateType() == GateType.AND_GATE){
					zwischenErgebnis =  zwischenErgebnis && condition(c);
				}
				else{
					zwischenErgebnis = zwischenErgebnis || condition(c);
				}
			}
			if(ergebnis == null){
				ergebnis = new Boolean(zwischenErgebnis);
			}
			else if (gatetype == GateType.AND_GATE){
				ergebnis = new Boolean(zwischenErgebnis && ergebnis);
			}
			else if (gatetype == GateType.OR_GATE){
				ergebnis = new Boolean(zwischenErgebnis || ergebnis);
			}
			else{
				//b = b;
			}				
		}
		if( ergebnis == null && gate.getGate().isEmpty() && !gate.getComponents().isEmpty()){
			ergebnis = condition(gate.getComponents().iterator().next());
		}
		return ergebnis;
	}
	
	/**
	 * This Method checks if the Condition is True.
	 *
	 * @param comp a ComponentXML object. It Defines the Comparison of a Component
	 * @return true, if the Condition is true
	 */
	public Boolean condition(ComponentXML comp){
		if(comp == null){
			return false;
		}
		int status = Status.NO_STATUS;
		try {
			status = Selects.deviceComponent(comp.getId()).getStatus();
		} catch (DBObjectNotFoundException e1) {
			// TODO Logger
			e1.printStackTrace();
		}
		if(status == Status.NO_STATUS || status == Status.INACTIVE || status == Status.DEFECT || status == Status.UNAVAILABLE || status == Status.COMMUNICATION_FAILURE || status == Status.UNKNOWN || status == Status.DELETED ){
			return false;
		}
		else{
			double isValue = 0.0;
			try {
				isValue = Selects.currentValue(comp.getId());
			} catch (DBObjectNotFoundException e1) {
				// TODO logger
				e1.printStackTrace();
			}
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
	 * Creates a List of ToDos.
	 *
	 * @param action It builds the Action in the XML-Rules in Java-Objects, parsed by the XML-Parser
	 * @return the a List of ToDos
	 * @throws Exception when a Value can not be set
	 */
	public List<ComponentXML> action(Action action) throws Exception {
		for(int i : action.getActivateRules()){
			Updates.activateRule(i);
		}
		for(int i : action.getDeactivateRules()){
			Updates.deactivateRule(i);
		}
		List<ComponentXML> toDos = new ArrayList<ComponentXML>();
		for(ComponentXML actor : action.getActors()){
			try {
				actor.setValue(calculate(actor.getCalculation()));
			} catch (Exception e) {
				System.err.println("Bad XML: " + e.getMessage());
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
			toDos.add(actor);
		}
		return toDos;
	}

	/**
	 * Calculates Values for Condition or Actions.
	 *
	 * @param cl a CalculationList, which builds the Calculation in the XML-Rules in Java-Objects, parsed by the XML-Parser
	 * @return the calculation Result as double
	 * @throws NoCalculationTypeException when no Calculation type was defined
	 * @throws NoFirstCalculationException when no First Value was defined
	 * @throws OnlyTwoObjectsForModuloException when more or less Objects was defined in a modulo calculation
	 */
	public double calculate(CalculationList cl) throws NoCalculationTypeException, NoFirstCalculationException, OnlyTwoObjectsForModuloException{
		double erg = 0.0;
		List<Double> values = new ArrayList<>();
		for (double d : cl.getConstants()){
			values.add(d);
		}
		for(int i : cl.getVariables()){
			try {
				values.add(Selects.currentValue(i));
			} catch (DBObjectNotFoundException e) {
				// TODO Logger
				e.printStackTrace();
			}
		}
		for(CalculationList next : cl.getCalculations()){
			values.add(calculate(next));
		}
		if(cl.getCalculationType() == null && values.size() == 1){
			for(double d : values){
				erg = d;
			}
		}
		else if(cl.getCalculationType() == null){
			throw new NoCalculationTypeException("No Calculationtype");
		}
		else{
			switch(cl.getCalculationType()){
			case ADD:
				if(cl.getFirst() != null){
					erg = calculate(cl.getFirst());
				}
				else{
					erg = 0.0;
				}
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
					throw new NoFirstCalculationException("No First for a SUB");
				}
				break;
			case MUL:
				if(cl.getFirst() != null){
					erg = calculate(cl.getFirst());
				}
				else{
					erg = 1.0;
				}
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
					throw new NoFirstCalculationException("No First for a DIV");
				}
				break;
			case MOD:
				if(cl.getFirst() != null && values.size() == 1){
					erg = calculate(cl.getFirst()) % values.iterator().next();
				}
				else if(cl.getFirst() == null && values.size() == 1){
					throw new NoFirstCalculationException("No First for a MOD");
				}
				else if(cl.getFirst() != null && values.size() != 1){
					throw new OnlyTwoObjectsForModuloException("No First for a MOD");
				}
				else{
					OnlyTwoObjectsForModuloException otofme = new OnlyTwoObjectsForModuloException("No First for a MOD");
					NoFirstCalculationException nfce = new NoFirstCalculationException("No First for a MOD");
					otofme.addSuppressed(nfce);
					throw otofme;
				}
				break;
			case ABS:
				erg = values.iterator().next();
				if (erg < 0){
					erg *= -1;
				}
				break;
			}
		}
		return erg;
	}

}
