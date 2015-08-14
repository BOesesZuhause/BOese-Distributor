package de.bo.aid.boese.simulation;

public class Component {
	
	private String name;
	private int id;
	private double value;
	private long timestamp;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public Component(String name, int id, double value, long timestamp) {
		super();
		this.name = name;
		this.id = id;
		this.value = value;
		this.timestamp = timestamp;
	}

	
}
