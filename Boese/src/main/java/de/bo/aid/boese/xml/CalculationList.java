

package de.bo.aid.boese.xml;

import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class CalculationList.
 */
public class CalculationList {
	
	/**
	 * The Enum CalculationTypes.
	 */
	public enum CalculationTypes {
		
		/** The add. */
		ADD, 
 /** The sub. */
 SUB, 
 /** The mul. */
 MUL, 
 /** The div. */
 DIV, 
 /** The mod. */
 MOD, 
 /** The abs. */
 ABS
	}
	
	/** The first. */
	private CalculationList first;
	
	/** The calculations. */
	private HashSet<CalculationList> calculations;
	
	/** The variables. */
	private HashSet<Integer> variables;
	
	/** The constants. */
	private HashSet<Double> constants;
	
	/** The calculation type. */
	private CalculationTypes calculationType;
	
	/**
	 * Instantiates a new calculation list.
	 */
	public CalculationList() {
		first = null;
		calculations = new HashSet<>();
		variables = new HashSet<>();
		constants = new HashSet<>();
		this.calculationType = null;
	}
	
	/**
	 * Sets the first.
	 *
	 * @param first the new first
	 */
	public void setFirst(CalculationList first) {
		this.first = first;
	}
	
	/**
	 * Adds the calculation.
	 *
	 * @param calcList the calc list
	 */
	public void addCalculation(CalculationList calcList) {
		calculations.add(calcList);
	}
	
	/**
	 * Adds the valiable.
	 *
	 * @param var the var
	 */
	public void addValiable(Integer var) {
		variables.add(var);
	}
	
	/**
	 * Adds the constant.
	 *
	 * @param constant the constant
	 */
	public void addConstant(double constant) {
		constants.add(constant);
	}
	
	/**
	 * Sets the calculation type.
	 *
	 * @param type the new calculation type
	 */
	public void setCalculationType(CalculationTypes type) {
		calculationType = type;
	}
	
	/**
	 * Gets the first.
	 *
	 * @return the first
	 */
	public CalculationList getFirst() {
		return first;
	}

	/**
	 * Gets the calculations.
	 *
	 * @return the calculations
	 */
	public HashSet<CalculationList> getCalculations() {
		return calculations;
	}

	/**
	 * Gets the variables.
	 *
	 * @return the variables
	 */
	public HashSet<Integer> getVariables() {
		return variables;
	}

	/**
	 * Gets the constants.
	 *
	 * @return the constants
	 */
	public HashSet<Double> getConstants() {
		return constants;
	}

	/**
	 * Gets the calculation type.
	 *
	 * @return the calculation type
	 */
	public CalculationTypes getCalculationType() {
		return calculationType;
	}
	
	
}
