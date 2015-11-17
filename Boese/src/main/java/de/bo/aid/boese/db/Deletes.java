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

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import de.bo.aid.boese.model.ToDo;

// TODO: Auto-generated Javadoc
/**
 * The Class Deletes.
 */
public class Deletes {
	
	/** The connection. */
	private static Connection connection = Connection.getConnection();
	
	/**
	 * To do.
	 *
	 * @param toDo the to do
	 */
	public static void ToDo(ToDo toDo){
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			session.delete(toDo);
			session.getTransaction().commit();
		}
		catch (Exception e){
			session.getTransaction().rollback();
			session.close();
			throw e;
		}
		
		session.close();
	}

}
