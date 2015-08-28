package de.bo.aid.boese.xml;

import java.util.HashSet;
import java.util.Set;

public class GateList {
	private HashSet<GateList> gates;
	private HashSet<Component> sensors;
	private GateType gateType;
	
	public enum GateType {
		AND_GATE, OR_GATE
	};
	
	public GateList() {
		gates = new HashSet<>();
		sensors = new HashSet<>();
		this.gateType = null;
	}

	public void addComponent(Component component) {
		sensors.add(component);
	}
	
	public Set<Component> getComponents() {
		return sensors;
	}
	
	public void addGate(GateList gate) {
		gates.add(gate);
	}
	
	public Set<GateList> getGate() {
		return gates;
	}

	public GateType getGateType() {
		return gateType;
	}
	
	public void setGateType(GateType gateType) {
		this.gateType = gateType;
	}
}
