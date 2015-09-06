
package de.bo.aid.boese.xml;


// TODO: Auto-generated Javadoc
/**
 * The Class Component.
 */
public class Component {
	
	/** The id. */
	protected int id;
	
	/** The value. */
	protected double value;
	
	/** The reset value. */
	protected double resetValue;
	
	/** The start time. */
	protected long startTime;
	
	/** The duration. */
	protected long duration;
	
	/** The comperator. */
	protected Comperator comperator;
	
	/** The repeat after end. */
	protected int repeatAfterEnd;
	
	/**
	 * The Enum Comperator.
	 */
	public enum Comperator {
		
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
	 * @param value the value
	 * @param resetValue the reset value
	 * @param startTime the start time
	 * @param duration the duration
	 * @param comperator the comperator
	 */
	public Component(int id, double value, double resetValue,long startTime, long duration, Comperator comperator) {
		this.id = id;
		this.value = value;
		this.resetValue = resetValue;
		this.startTime = startTime;
		this.duration = duration;
		this.comperator = comperator;
		repeatAfterEnd = 0;
	}
	
	/**
	 * Instantiates a new component.
	 *
	 * @param id the id
	 * @param value the value
	 * @param resetValue the reset value
	 * @param startTime the start time
	 * @param duration the duration
	 * @param repeatAfterEnd the repeat after end
	 */
	public Component(int id, double value, double resetValue,long startTime, long duration, int repeatAfterEnd) {
		this.id = id;
		this.value = value;
		this.resetValue = resetValue;
		this.startTime = startTime;
		this.duration = duration;
		this.repeatAfterEnd = repeatAfterEnd;
		this.comperator = null;
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
	 * Gets the comperator.
	 *
	 * @return the comperator
	 */
	public Comperator getComperator() {
		return comperator;
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
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
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
	public long getStartTime() {
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
}
