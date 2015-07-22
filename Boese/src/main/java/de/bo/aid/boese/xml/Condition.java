package de.bo.aid.boese.xml;

import java.util.Set;

public class Condition extends BoeseXML {
	
	/**
	 * Method to check if an condition is valid
	 * @param sensorId
	 * @param value
	 * @param timestamp
	 * @param duration
	 * @return null if no condition is valid, otherwise the rule ids
	 */
	public Set<Integer> checkCondition(int sensorId, double value, long timestamp, long duration) {
		return null;
	}

}
