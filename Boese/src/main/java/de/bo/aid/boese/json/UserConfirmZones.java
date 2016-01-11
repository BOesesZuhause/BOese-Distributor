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

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class UserConfirmZones.
 */
public class UserConfirmZones extends BoeseJson {

	/** The temp zones. */
	private HashMap<Integer, Integer> tempZones;
	
	/**
	 * Instantiates a new user confirm zones.
	 *
	 * @param tempZones the temp zones
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserConfirmZones(HashMap<Integer, Integer> tempZones, int connectorId, int status, long timestamp) {
		super(MessageType.USERCONFIRMZONES, connectorId, status, timestamp);
		this.tempZones = tempZones;
	}

	/**
	 * Gets the temp zones.
	 *
	 * @return the temp zones
	 */
	public HashMap<Integer, Integer> getTempZones() {
		return tempZones;
	}

}
