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

import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class UserRequestConnectors.
 */
public class UserRequestConnectors extends BoeseJson{
	
	/** The connector ids. */
	HashSet<Integer> connectorIds;

	/**
	 * Instantiates a new user request connectors.
	 *
	 * @param connectorIds the connector ids
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserRequestConnectors(HashSet<Integer> connectorIds, int connectorId, int status,
			long timestamp) {
		super(MessageType.USERREQUESTCONNECTORS, connectorId, status, timestamp);
		this.connectorIds = connectorIds;
	}
	
	/**
	 * Instantiates a new user request connectors.
	 *
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserRequestConnectors(int connectorId, int status,
			long timestamp) {
		super(MessageType.USERREQUESTALLCONNECTORS, connectorId, status, timestamp);
		this.connectorIds = null;
	}
	
	/**
	 * Gets the connector ids.
	 *
	 * @return the connector ids
	 */
	public HashSet<Integer> getConnectorIds() {
		return connectorIds;
	}

}
