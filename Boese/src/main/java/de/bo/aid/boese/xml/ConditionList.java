package de.bo.aid.boese.xml;

import java.util.HashSet;
import java.util.Set;

public class ConditionList {
	HashSet<ConditionList> andConditions;
	HashSet<ConditionList> orConditions;
	HashSet<ConditionList> xorConditions;
	HashSet<Sensor> sensors;
	
	public ConditionList() {
		andConditions = new HashSet<>();
		orConditions = new HashSet<>();
		xorConditions = new HashSet<>();
		sensors = new HashSet<>();
	};
	
	public void addSensor(Sensor sensor) {
		sensors.add(sensor);
	}
	
	public Set<Sensor> getSensors() {
		return sensors;
	}
	
	public void addAndCondition(ConditionList andCondition) {
		andConditions.add(andCondition);
	}
	
	public Set<ConditionList> getAndConditions() {
		return andConditions;
	}
	
	public void addOrCondition(ConditionList orCondition) {
		orConditions.add(orCondition);
	}
	
	public Set<ConditionList> getOrConditions() {
		return orConditions;
	}
	
	public void addXorCondition(ConditionList xorCondition) {
		xorConditions.add(xorCondition);
	}
	
	public Set<ConditionList> getXorConditions() {
		return xorConditions;
	}

}
