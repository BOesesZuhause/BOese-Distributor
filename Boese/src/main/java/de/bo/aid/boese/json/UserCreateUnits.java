/*
 * 				     )                  
 *		      (   ( /(                  
 *		    ( )\  )\())    (        (   
 *		    )((_)((_)\    ))\ (    ))\  
 *		   ((_)_   ((_)  /((_))\  /((_) 
 *			| _ ) / _ \ (_)) ((_)(_))   
 *			| _ \| (_) |/ -_)(_-</ -_)  
 *			|___/ \___/ \___|/__/\___|  
 *                            
 *
 *
 *
 *            			;            
 * 		      +        ;;;        + 
 * 			  +       ;;;;;       + 
 * 			  +      ;;;;;;;      + 
 * 			  ++    ;;;;;;;;;    ++ 
 * 			  +++++;;;;;;;;;;;+++++  
 * 			   ++++;;;;;;;;;;;++++  
 * 				++;;;;;;;;;;;;;++    
 * 			     ;;;;;;;;;;;;;;;     
 * 			    ;;;;;;;;;;;;;;;;;     
 * 				:::::::::::::::::    
 * 				:::::::::::::::::      
 *				:::::::::::::::::    
 *    			::::::@@@@@::::::    
 * 				:::::@:::::@:::::    
 * 				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *				:::::::::::::::::  
 */
package de.bo.aid.boese.json;

import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class UserCreateUnits.
 */
public class UserCreateUnits extends BoeseJson {
	
	/** The units. */
	private HashSet<UnitJSON> units;

	/**
	 * Instantiates a new user create units.
	 *
	 * @param units the units
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserCreateUnits(HashSet<UnitJSON> units, int connectorId, int status, long timestamp) {
		super(MessageType.USERCREATEUNITS, connectorId, status, timestamp);
		this.units = units;
	}

	/**
	 * Gets the units.
	 *
	 * @return the units
	 */
	public HashSet<UnitJSON> getUnits() {
		return units;
	}
}
