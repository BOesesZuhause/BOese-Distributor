


package de.bo.aid.boese.xml;

import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class GateList.
 */
public class GateList {
	
	/** The gates. */
	private HashSet<GateList> gates;
	
	/** The sensors. */
	private HashSet<Component> sensors;
	
	/** The gate type. */
	private GateType gateType;
	
	/**
	 * The Enum GateType.
	 */
	public enum GateType {
		
		/** The and gate. */
		AND_GATE, 
 /** The or gate. */
 OR_GATE
	};
	
	/**
	 * Instantiates a new gate list.
	 */
	public GateList() {
		gates = new HashSet<>();
		sensors = new HashSet<>();
		this.gateType = null;
	}

	/**
	 * Adds a new component to this Gate.
	 *
	 * @param component the component
	 */
	public void addComponent(Component component) {
		sensors.add(component);
	}
	
	/**
	 * Returns a Set with all components of this Gate.
	 *
	 * @return the components
	 */
	public Set<Component> getComponents() {
		return sensors;
	}
	
	/**
	 * Adds a new gate to this Gate.
	 *
	 * @param gate the gate
	 */
	public void addGate(GateList gate) {
		gates.add(gate);
	}
	
	/**
	 * Returns a Set with all subgates.
	 *
	 * @return the gate
	 */
	public Set<GateList> getGate() {
		return gates;
	}

	/**
	 * Returns the type of this gate.
	 * Null for no type
	 *
	 * @return the gate type
	 */
	public GateType getGateType() {
		return gateType;
	}
	
	/**
	 * Set the type of this gate.
	 * Null for no type
	 *
	 * @param gateType the new gate type
	 */
	public void setGateType(GateType gateType) {
		this.gateType = gateType;
	}
}
