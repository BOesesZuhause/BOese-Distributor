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
 * The Class MultiMessage.
 */
public class MultiMessage extends BoeseJson {
	
	/** The boeses. */
	private HashSet<BoeseJson> boeses;
	
	/**
	 * Instantiates a new multi message.
	 *
	 * @param boese the boese
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public MultiMessage(HashSet<BoeseJson> boese, int connectorId, int status, long timestamp) {
		super(MessageType.MULTI, connectorId, status, timestamp);
		this.boeses = boese;
	}
	
	/**
	 * Instantiates a new multi message.
	 *
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public MultiMessage(int connectorId, int status, long timestamp) {
		super(MessageType.MULTI, connectorId, status, timestamp);
		this.boeses = new HashSet<>();
	}
	
	/**
	 * Adds the message.
	 *
	 * @param message the message
	 */
	public void addMessage(BoeseJson message ){
		boeses.add(message);
	}
	
	/**
	 * Gets the messages.
	 *
	 * @return the messages
	 */
	public HashSet<BoeseJson> getMessages() {
		return boeses;
	}


}
