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



package de.bo.aid.boese.db;

import org.hibernate.Session;

import de.bo.aid.boese.model.DeviceComponent;

// TODO: Auto-generated Javadoc
/**
 * The Class Checks.
 */
public class Checks {
	
	/** The connection. */
	private static Connection connection = Connection.getConnection();
	
	/**
	 * Instantiates a new checks.
	 */
	private Checks(){
		
	}
	
	/**
	 * Device component id.
	 *
	 * @param decoid the decoid
	 * @return true, if successful
	 */
	public static boolean deviceComponentID (int decoid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			//TODO @Fabio nachgucken was das macht
			session.get(DeviceComponent.class, new Integer(decoid));
			session.getTransaction().commit();
			session.close();
			return true;
		}
		catch(org.hibernate.ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return false;
		}
	}
}