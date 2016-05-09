/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 */



package de.bo.aid.boese.modelJPA;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

// TODO: Auto-generated Javadoc
/**
 * Unit Model for Hibernate generated by hbm2java.
 */
@Entity
@Table(name="\"Unit\"")
public class Unit implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	@Transient
	private static final long serialVersionUID = 1L;

	/** The Unit id. */
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int unId;
	
	/** The name. */
	private String name;
	
	/** The unit character. */
	private String symbol;
	
	/** The Components using this Unit. */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "unit")
	private Set<Component> components = new HashSet<Component>(0);

	/**
	 * Instantiates a new unit.
	 */
	public Unit() {
	}

	/**
	 * Instantiates a new unit only with ID.
	 *
	 * @param unId the Unit id
	 */
	public Unit(int unId) {
		this.unId = unId;
	}

	/**
	 * Instantiates a new unit for DB Insert.
	 *
	 * @param name the name
	 * @param symbol the unit character
	 */
	public Unit(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	/**
	 * Instantiates a new unit with all Parameters and foreign keys.
	 *
	 * @param unId the Unit id
	 * @param name the name
	 * @param symbol the unit character
	 * @param components the Components using this Unit
	 */
	public Unit(int unId, String name, String symbol, Set<Component> components) {
		this.unId = unId;
		this.name = name;
		this.symbol = symbol;
		this.components = components;
	}

	/**
	 * Gets the Unit id.
	 *
	 * @return the Unit id
	 */
	public int getUnId() {
		return this.unId;
	}

	/**
	 * Sets the Unit id.
	 *
	 * @param unId the new Unit id
	 */
	public void setUnId(int unId) {
		this.unId = unId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the unit character.
	 *
	 * @return the unit character
	 */
	public String getSymbol() {
		return this.symbol;
	}

	/**
	 * Sets the unit character.
	 *
	 * @param symbol the new unit character
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the components using this Unit.
	 *
	 * @return the components using this Unit
	 */
	public Set<Component> getComponents() {
		return this.components;
	}

	/**
	 * Sets the components using this Unit.
	 *
	 * @param components the new components using this Unit
	 */
	public void setComponents(Set<Component> components) {
		this.components = components;
	}

	/**
	 *  
	 * To compare two Units.
	 *
	 * @param obj the Unit object to compare
	 * @return true if both Units are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unit other = (Unit) obj;
		if (components == null) {
			System.out.println("hier");
			if (other.components != null)
				return false;
		} else if (!components.equals(other.components))
			return false;//System.out.println(components.size() + "\t" + other.components.size());
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (unId != other.unId)
			return false;
		return true;
	}
	
	/**
	 *  
	 * To compare two Units.
	 *
	 * @param obj the Unit object to compare
	 * @return true if both Units are equal
	 */
	public boolean equalsWithoutID(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unit other = (Unit) obj;
		if (components == null) {
			System.out.println("hier");
			if (other.components != null)
				return false;
		} else if (!components.equals(other.components))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

}
