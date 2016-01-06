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


package de.bo.aid.boese.constants;

/**
 * This Class displays the possible Status of DeviceComponents or Connectors.
 * @author Fabio
 */
public class Status {
	
	/** The Constant NO_STATUS will be used, if No Status is set.
	 * If No Status is set, this value will be ignored or the Interpretor returns false.
	 */
	public static final int NO_STATUS = -1; // Ignore bzw return false

	/** The Constant ACTIVE will be used, if the DeviceComponent or the Connector is active.
	 * If Active is set, everything will work normal.
	 */
	public static final int ACTIVE = 1; //Normal Reaktion

	/** The Constant INACTIVE will be used, if the DeviceComponent or the Connector is inactive.
	 * If inactive is set, this value will be ignored or the Interpretor returns false.
	 */
	public static final int INACTIVE = 2; //return false

	/** The Constant DEFECT will be used, if the DeviceComponent or the Connector is defect.
	 * If defect is set, this value will be ignored or the Interpretor returns false.
	 */
	public static final int DEFECT = 3; //return false

	/** The Constant UNAVAILABE will be used, if the DeviceComponent or the Connector is unavailable.
	 * If unavailable is set, this value will be ignored or the Interpretor returns false.
	 */
	public static final int UNAVAILABLE = 4; //return false

	/** The Constant COMMUNICATION_FAILURE will be used, if the Connector send undefined Message.
	 * If communication failure is set, this value will be ignored or the Interpretor returns false.
	 */
	public static final int COMMUNICATION_FAILURE = 5; //return false

	/** The Constant UNKNOWN will be used, if a status was set, but now the status is unknown.
	 * If unknown is set, this value will be ignored or the Interpretor returns false.
	 */
	public static final int UNKNOWN = 6; //return false

	/** The Constant DELETED will be used, if the DeviceComponent or the Connector is deleted.
	 * If deleted is set, this value will be ignored or the Interpretor returns false.
	 */
	public static final int DELETED = 7; //return false

	/** The Constant UNDEFINED will be used, if a status will be set, which is undefined.
	 * If undefined is set, this value will be ignored or the Interpretor returns false.
	 */
	public static final int UNDEFINED = 8; //return false

	/** The Constant BATTERY will be used, if the battery of a Connector or a DeviceComponent is low.
	 * If battery is set, everything will work normal, but the User-Interface should show this.
	 */
	public static final int BATTERY = 100; //Normal Reaktion

	/** The Constant ACTOR_DOES_NOT_REACT will be used, if an actor doesn´t react.
	 * If actor does not react is set, everything will work normal.
	 */
	public static final int ACTOR_DOES_NOT_REACT = 110;  //Normal Reaktion
	   
    /**
     * You shouldn't create a instance of this Object
     */
    private Status(){
        
    }
}
