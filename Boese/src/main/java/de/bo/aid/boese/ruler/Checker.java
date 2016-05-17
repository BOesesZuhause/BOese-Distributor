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
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.constants.Status;
import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.dao.DeviceComponentDAO;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.exceptions.NoCalculationTypeException;
import de.bo.aid.boese.exceptions.NoFirstCalculationException;
import de.bo.aid.boese.exceptions.OnlyTwoObjectsForModuloException;
import de.bo.aid.boese.main.Distributor;
import de.bo.aid.boese.modelJPA.DeviceComponent;
import de.bo.aid.boese.modelJPA.Rule;
import de.bo.aid.boese.xml.Action;
import de.bo.aid.boese.xml.CalculationList;
import de.bo.aid.boese.xml.ComponentXML;
import de.bo.aid.boese.xml.GateList;
import de.bo.aid.boese.xml.GateList.GateType;

/**
 * The Class Checker checks the XML-Rules.
 */
public class Checker {
	

	private DAOHandler daoHandler;
	
	public Checker(){
		daoHandler = DAOHandler.getInstance();
	}

	/** The Constant logger for log4j. */
	private static final  Logger logger = LogManager.getLogger(Distributor.class);

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
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		DeviceComponent deco = daoHandler.getDeviceComponentDAO().get(em, comp.getId());
		em.getTransaction().commit();
		em.close();
		if (deco == null){
			logger.error("DeviceComponte mit der ID " + comp.getId() + " wurde nicht in der DB gefunden");
			return false;
		}
		int status = deco.getStatus();
		em.getTransaction().commit();
		if(status == Status.NO_STATUS || status == Status.INACTIVE || status == Status.DEFECT || status == Status.UNAVAILABLE || status == Status.COMMUNICATION_FAILURE || status == Status.UNKNOWN || status == Status.DELETED ){
			return false;
		}
		else{
			double isValue = deco.getCurrentValue().doubleValue();
			double comparativeValue = 0.0;
			try {
				comparativeValue = calculate(comp.getCalculation());
			}
			catch (NoCalculationTypeException ncte){
				logger.error("Es wurde kein Calculationtype angegeben");
				ncte.printStackTrace();
			}
			catch (NoFirstCalculationException nfce){
				logger.error("Es wurde kein erster Rechenwert(FIRST) angegeben");
				nfce.printStackTrace();
			}
			catch (OnlyTwoObjectsForModuloException otofme){
				logger.error("Es wurden mehr als zwei Werte bei einer Moduloberechnung");
				otofme.printStackTrace();
			} 
			catch (Exception e) {
				logger.error("Ein unerklicher Fehler in Calculate ist aufgetreten");
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
				logger.info("Kein Comperator angegeben");
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
		//TODO Queries angucken ober erst Select schlau ist
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Map<Integer, Rule> rules = daoHandler.getRuleDAO().getAllAsMap(em);
		for(int i : action.getActivateRules()){
			Rule rule = rules.get(i);
			if(rule != null){
				rule.setActive(true);
			}
		}
		for(int i : action.getDeactivateRules()){
			Rule rule = rules.get(i);
			if(rule != null){
				rule.setActive(false);
			}
		}
		em.getTransaction().commit();
		em.close();
		List<ComponentXML> toDos = new ArrayList<ComponentXML>();
		for(ComponentXML actor : action.getActors()){
			try {
				actor.setValue(calculate(actor.getCalculation()));
			} catch (NoCalculationTypeException ncte){
				logger.error("Es wurde kein Calculationtype angegeben");
				ncte.printStackTrace();
			}
			catch (NoFirstCalculationException nfce){
				logger.error("Es wurde kein erster Rechenwert(FIRST) angegeben");
				nfce.printStackTrace();
			}
			catch (OnlyTwoObjectsForModuloException otofme){
				logger.error("Es wurden mehr als zwei Werte bei einer Moduloberechnung");
				otofme.printStackTrace();
			} 
			catch (Exception e) {
				logger.error("Ein unerklicher Fehler in Calculate ist aufgetreten");
				System.err.println("Bad XML: " + e.getMessage());
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
		EntityManager em = JPAUtil.getEntityManager();
		for(int i : cl.getVariables()){
			em.getTransaction().begin();
			DeviceComponent deco = daoHandler.getDeviceComponentDAO().get(em, i);
			if(deco != null){
				values.add(deco.getCurrentValue().doubleValue());
			}
			else{
				//TODO Exception!!!
				logger.error("Der Wert der Komponente mit der ID: " + i + " konnte nicht in der Datenbank gefunden werden.");
			}
			em.getTransaction().commit();
		}
		em.close();
		for(CalculationList next : cl.getCalculations()){
			try{
				values.add(calculate(next));
			}
			catch (NoCalculationTypeException ncte){
				logger.error("Es wurde kein Calculationtype angegeben");
				ncte.printStackTrace();
			}
			catch (NoFirstCalculationException nfce){
				logger.error("Es wurde kein erster Rechenwert(FIRST) angegeben");
				nfce.printStackTrace();
			}
			catch (OnlyTwoObjectsForModuloException otofme){
				logger.error("Es wurden mehr als zwei Werte bei einer Moduloberechnung");
				otofme.printStackTrace();
			} 
			catch (Exception e) {
				logger.error("Ein unerklicher Fehler in Calculate ist aufgetreten");
				System.err.println("Bad XML: " + e.getMessage());
				e.printStackTrace();
			}
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
