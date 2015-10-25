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



package de.bo.aid.boese.model;
// Generated 30.07.2015 11:14:41 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * Unit generated by hbm2java.
 */
public class Unit implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The un id. */
	private int unId;
	
	/** The name. */
	private String name;
	
	/** The symbol. */
	private String symbol;
	
	/** The components. */
	private Set<Component> components = new HashSet<Component>(0);

	/**
	 * Instantiates a new unit.
	 */
	public Unit() {
	}

	/**
	 * Instantiates a new unit.
	 *
	 * @param unId the un id
	 */
	public Unit(int unId) {
		this.unId = unId;
	}

	/**
	 * Instantiates a new unit.
	 *
	 * @param unId the un id
	 * @param name the name
	 * @param symbol the symbol
	 * @param components the components
	 */
	public Unit(int unId, String name, String symbol, Set<Component> components) {
		this.unId = unId;
		this.name = name;
		this.symbol = symbol;
		this.components = components;
	}

	/**
	 * Gets the un id.
	 *
	 * @return the un id
	 */
	public int getUnId() {
		return this.unId;
	}

	/**
	 * Sets the un id.
	 *
	 * @param unId the new un id
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
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public String getSymbol() {
		return this.symbol;
	}

	/**
	 * Sets the symbol.
	 *
	 * @param symbol the new symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the components.
	 *
	 * @return the components
	 */
	public Set<Component> getComponents() {
		return this.components;
	}

	/**
	 * Sets the components.
	 *
	 * @param components the new components
	 */
	public void setComponents(Set<Component> components) {
		this.components = components;
	}

}
