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


package de.bo.aid.boese.json;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class UserConfirmRules.
 */
public class UserConfirmRules extends BoeseJson {
	
	/** The temp rules. */
	HashMap<Integer, Integer> tempRules;
	
	/**
	 * Instantiates a new user confirm rules.
	 *
	 * @param tempRules the temp rules
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserConfirmRules(HashMap<Integer, Integer> tempRules, int connectorId, int status, long timestamp) {
		super(MessageType.USERCONFIRMRULES, connectorId, status, timestamp);
		this.tempRules = tempRules;
	}
	
	/**
	 * Gets the temp rules.
	 *
	 * @return the temp rules
	 */
	public HashMap<Integer, Integer> getTempRules() {
		return tempRules;
	}

}
