package de.bo.aid.boese.xml;

import java.util.HashSet;

public class CalculationList {
	public enum CalculationTypes {
		ADD, SUB, MUL, DIV, MOD, ABS
	}
	
	private CalculationList first;
	private HashSet<CalculationList> calculations;
	private HashSet<Integer> variables;
	private HashSet<Double> constants;
	private CalculationTypes calculationType;
	
	public CalculationList() {
		first = null;
		calculations = new HashSet<>();
		variables = new HashSet<>();
		constants = new HashSet<>();
		this.calculationType = null;
	}
	
	public void setFirst(CalculationList first) {
		this.first = first;
	}
	
	public void addCalculation(CalculationList calcList) {
		calculations.add(calcList);
	}
	
	public void addValiable(Integer var) {
		variables.add(var);
	}
	
	public void addConstant(double constant) {
		constants.add(constant);
	}
	
	public void setCalculationType(CalculationTypes type) {
		calculationType = type;
	}
	
	public CalculationList getFirst() {
		return first;
	}

	public HashSet<CalculationList> getCalculations() {
		return calculations;
	}

	public HashSet<Integer> getVariables() {
		return variables;
	}

	public HashSet<Double> getConstants() {
		return constants;
	}

	public CalculationTypes getCalculationType() {
		return calculationType;
	}
	
	
}
