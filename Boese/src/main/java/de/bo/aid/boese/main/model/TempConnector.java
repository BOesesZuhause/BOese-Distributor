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
package de.bo.aid.boese.main.model;

// TODO: Auto-generated Javadoc
/**
 * The Class TempConnector.
 */
public class TempConnector {
	
	/** The name. */
	private String name;
	
	/** The user connector. */
	private boolean userConnector;

	    /**
    	 * Instantiates a new temp connector.
    	 *
    	 * @param name the name
    	 * @param userConnector the user connector
    	 */
    	public TempConnector(String name, boolean userConnector) {
	        super();
	        this.name = name;
	        this.userConnector = userConnector;
	    }
	    
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * Checks if is user connector.
	 *
	 * @return true, if is user connector
	 */
	public boolean isUserConnector() {
		return userConnector;
	}
	
	/**
	 * Sets the user connector.
	 *
	 * @param userConnector the new user connector
	 */
	public void setUserConnector(boolean userConnector) {
		this.userConnector = userConnector;
	}

	
	

}
