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



package de.bo.aid.boese.xml;

import de.bo.aid.boese.ruler.TimeFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class Component.
 */
public class ComponentXML {
	
	/** The id. */
	protected int id;
	
	/** The value. */
	protected double value;
	
	/** The calculation. */
	protected CalculationList calculation;
	
	/** The reset value. */
	protected double resetValue;
	
	/** The start time. */
	protected TimeFormat startTime;
	
	/** The duration. */
	protected long duration;
	
	/** The comparator. */
	protected Comparator comparator;
	
	/** The repeat after end. */
	protected int repeatAfterEnd;
	
	/**
	 * The Enum Comparator.
	 */
	public enum Comparator {
		
		/** The lowerthen. */
		LOWERTHEN, 
 /** The greaterthen. */
 GREATERTHEN, 
 /** The lowerequal. */
 LOWEREQUAL, 
 /** The greaterequal. */
 GREATEREQUAL, 
 /** The equal. */
 EQUAL, 
 /** The notequal. */
 NOTEQUAL
	}
	
	/**
	 * Instantiates a new component.
	 *
	 * @param id the id
	 * @param calculation the calculation
	 * @param resetValue the reset value
	 * @param startTime the start time
	 * @param duration the duration
	 * @param comparator the comparator
	 */
	public ComponentXML(int id, CalculationList calculation, double resetValue, TimeFormat startTime, long duration, Comparator comparator) {
		this.id = id;
		this.value = -1;
		this.calculation = calculation;
		this.resetValue = resetValue;
		this.startTime = startTime;
		this.duration = duration;
		this.comparator = comparator;
		repeatAfterEnd = 0;
		value = -1;
	}
	
	/**
	 * Instantiates a new component.
	 *
	 * @param id the id
	 * @param calculation the calculation
	 * @param resetValue the reset value
	 * @param startTime the start time
	 * @param duration the duration
	 * @param repeatAfterEnd the repeat after end
	 */
	public ComponentXML(int id, CalculationList calculation, double resetValue, TimeFormat startTime, long duration, int repeatAfterEnd) {
		this.id = id;
		this.value = -1;
		this.calculation = calculation;
		this.resetValue = resetValue;
		this.startTime = startTime;
		this.duration = duration;
		this.repeatAfterEnd = repeatAfterEnd;
		this.comparator = null;
		value = -1;
	}
	
	/**
	 * Instantiates a new component.
	 *
	 * @param id the id
	 * @param value the value
	 */
	public ComponentXML(int id, double value) {
		this.id = id;
		this.value = value;
	}
	
	/**
	 * Gets the reat after end.
	 *
	 * @return the reat after end
	 */
	public int getReatAfterEnd() {
		return repeatAfterEnd;
	}
	
	/**
	 * Sets the comparator.
	 *
	 * @param comparator the new comparator
	 */
	public void setValue(Comparator comparator) {
		this.comparator = comparator;
	}
	
	/**
	 * Gets the comparator.
	 *
	 * @return the comparator
	 */
	public Comparator getComparator() {
		return comparator;
	}
	
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	
	/**
	 * Gets the calculation.
	 *
	 * @return the calculation
	 */
	public CalculationList getCalculation() {
		return calculation;
	}
	
	/**
	 * Gets the reset value.
	 *
	 * @return the reset value
	 */
	public double getResetValue() {
		return resetValue;
	}
	
	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public TimeFormat getStartTime() {
		return startTime;
	}
	
	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}
	
	/**
	 * To string for to do checker.
	 *
	 * @return the string
	 */
	public String toStringForToDoChecker(){
		return "DeviceComponent " + this.id + " wird auf den Wert " + this.value + " geschaltet!";
	}
}
