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

// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmConnection.
 */
public class ConfirmConnection extends BoeseJson {
	
	/** The password. */
	private String password;
	
	/**
	 * Instantiates a new confirm connection.
	 *
	 * @param password the password
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public ConfirmConnection(String password, int connectorId, int status, long timestamp) {
		super(MessageType.CONFIRMCONNECTION, connectorId, status, timestamp);
		this.password = password;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
}
