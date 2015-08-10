package de.bo.aid.boese.xml;

public class Sensor extends Component{
	public enum Comperator {
		LOWERTHEN, GREATERTHEN, LOWEREQUAL, GREATEREQUAL, EQUAL, NOTEQUAL
	}
	
	protected Comperator comperator;
	
	public Sensor(int id, double value, long startTime, long endTime, long duration, Comperator comperator) {
		super(id, value, startTime, endTime, duration);
		this.comperator = comperator;
	}
	
	public Comperator getComperator() {
		return comperator;
	}

}
