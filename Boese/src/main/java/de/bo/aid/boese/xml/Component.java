package de.bo.aid.boese.xml;

public class Component {
	protected int id;
	protected double value;
	protected long startTime;
	protected long duration;
	
	public Component(int id, double value, long startTime, long duration) {
		this.id = id;
		this.value = value;
		this.startTime = startTime;
		this.duration = duration;
	}
	
	public int getId() {
		return id;
	}
	
	public double getValue() {
		return value;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public long getDuration() {
		return duration;
	}
}
