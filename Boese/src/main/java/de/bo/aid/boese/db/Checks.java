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
 * @author Fabio
 * The Class Checks checks that the Object exist in the Database .
 */
public class Checks {
	
	/** The connection to the Database. */
	private static Connection connection = Connection.getConnection();
	
	 /**
     * You shouldn't create a instance of this Object
     */
	private Checks(){
		
	}
	
	/**
	 * Checks that the Device component id exist in the Database.
	 *
	 * @param decoid the DeviceComponentID
	 * @return true, if the DeviceComponentID exist
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