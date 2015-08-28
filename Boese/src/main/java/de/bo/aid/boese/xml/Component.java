package de.bo.aid.boese.xml;

import de.bo.aid.boese.xml.Component.Comperator;

public class Component {
	protected int id;
	protected double value;
	protected double resetValue;
	protected long startTime;
	protected long duration;
	protected Comperator comperator;
	
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
	}
	
	public Component(int id, double value, double resetValue,long startTime, long duration) {
		this.id = id;
		this.value = value;
		this.resetValue = resetValue;
		this.startTime = startTime;
		this.duration = duration;
		this.comperator = null;
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
