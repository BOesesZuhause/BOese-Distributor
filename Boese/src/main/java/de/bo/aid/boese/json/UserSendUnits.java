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
 * The Class UserSendUnits.
 */
public class UserSendUnits extends BoeseJson {
	
	/** The units. */
	private HashSet<UnitJSON> units;

	/**
	 * Instantiates a new user send units.
	 *
	 * @param units the units
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserSendUnits(HashSet<UnitJSON> units, int connectorId, int status, long timestamp) {
		super(MessageType.USERSENDUNITS, connectorId, status, timestamp);
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
