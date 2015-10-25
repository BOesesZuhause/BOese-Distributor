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
 * The Class UserSendZones.
 */
public class UserSendZones extends BoeseJson {
	
	/** The zones. */
	private HashSet<Zone> zones;
	
	/**
	 * Instantiates a new user send zones.
	 *
	 * @param zones the zones
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public UserSendZones(HashSet<Zone> zones, int connectorId, int status,
			long timestamp) {
		super(MessageType.USERSENDZONES, connectorId, status, timestamp);
		this.zones = zones;
	}
	
	/**
	 * Gets the zones.
	 *
	 * @return the zones
	 */
	public HashSet<Zone> getZones() {
		return zones;
	}

}
