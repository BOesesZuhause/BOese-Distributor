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

	/**
	 * Adds a new component to this Gate
	 * @param component
	 */
	public void addComponent(Component component) {
		sensors.add(component);
	}
	
	/**
	 * Returns a Set with all components of this Gate
	 * @return
	 */
	public Set<Component> getComponents() {
		return sensors;
	}
	
	/**
	 * Adds a new gate to this Gate
	 * @param gate
	 */
	public void addGate(GateList gate) {
		gates.add(gate);
	}
	
	/**
	 * Returns a Set with all subgates
	 * @return
	 */
	public Set<GateList> getGate() {
		return gates;
	}

	/**
	 * Returns the type of this gate.
	 * Null for no type
	 * @return
	 */
	public GateType getGateType() {
		return gateType;
	}
	
	/**
	 * Set the type of this gate.
	 * Null for no type
	 * @param gateType
	 */
	public void setGateType(GateType gateType) {
		this.gateType = gateType;
	}
}
