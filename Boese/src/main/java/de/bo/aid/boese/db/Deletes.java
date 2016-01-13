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

import de.bo.aid.boese.model.ToDo;

// TODO: Auto-generated Javadoc
/**
 * The Class Deletes.
 *
 * @author Fabio
 * The Class Deletes offers Methods to delete Enetities in the Database.
 */
public class Deletes {
	
	/**
	 * You shouldn't create a instance of this Object.
	 */
	private Deletes(){
		
	}
	
	/** The connection to the Database. */
	private static Connection connection = Connection.getConnection();
	
	/**
	 * delete a ToDo in the Database.
	 *
	 * @param toDo the Object of the ToDo to be deleted
	 */
	public static void toDo(ToDo toDo){
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
