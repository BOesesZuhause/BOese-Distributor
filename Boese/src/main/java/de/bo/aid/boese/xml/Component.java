package de.bo.aid.boese.xml;


public class Component {
	protected int id;
	protected double value;
	protected double resetValue;
	protected long startTime;
	protected long duration;
	protected Comperator comperator;
	protected int repeatAfterEnd;
	
	public enum Comperator {
		LOWERTHEN, GREATERTHEN, LOWEREQUAL, GREATEREQUAL, EQUAL, NOTEQUAL
	}
	
	public Component(int id, double value, double resetValue,long startTime, long duration, Comperator comperator) {
		this.id = id;
		this.value = value;
		this.resetValue = resetValue;
		this.startTime = startTime;
		this.duration = duration;
		this.comperator = comperator;
		repeatAfterEnd = 0;
	}
	
	public Component(int id, double value, double resetValue,long startTime, long duration, int repeatAfterEnd) {
		this.id = id;
		this.value = value;
		this.resetValue = resetValue;
		this.startTime = startTime;
		this.duration = duration;
		this.repeatAfterEnd = repeatAfterEnd;
		this.comperator = null;
	}
	
	public int getReatAfterEnd() {
		return repeatAfterEnd;
	}
	
	public Comperator getComperator() {
		return comperator;
	}
	
	public int getId() {
		return id;
	}
	
	public double getValue() {
		return value;
	}
	
	public double getResetValue() {
		return resetValue;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public long getDuration() {
		return duration;
	}
}
