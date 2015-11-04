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


package de.bo.aid.boese.cli;

import com.beust.jcommander.Parameter;

// TODO: Auto-generated Javadoc
/**
 * The Class Parameters.
 */
public class Parameters {
	
	/**  Describes the path to the config file. */
	@Parameter(names = "-config", description = "Path to the config-file", required=true)
	private String config;


	/**  If set a default config is generated. */
	@Parameter(names = "-genconfig", description = "Generates a default config file at the location configured with -config")
	private boolean genConfig = false;

	
	/**
	 * Gets the config.
	 *
	 * @return the config
	 */
	public String getConfig() {
		return config;
	}

	/**
	 * Sets the config.
	 *
	 * @param config the new config
	 */
	public void setConfig(String config) {
		this.config = config;
	}

	/**
	 * Checks if is gen config.
	 *
	 * @return true, if is gen config
	 */
	public boolean isGenConfig() {
		return genConfig;
	}

	/**
	 * Sets the gen config.
	 *
	 * @param genConfig the new gen config
	 */
	public void setGenConfig(boolean genConfig) {
		this.genConfig = genConfig;
	}
}
