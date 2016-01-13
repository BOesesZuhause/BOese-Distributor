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
import org.hibernate.SessionFactory;

import de.bo.aid.boese.hibernate.util.HibernateUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Connection.
 *
 * @author The Class Connection build the Hibernate Connection between Java and the Database.
 */
public class Connection {
	
	/** The instance of the Connection. */
	private static Connection instance = new Connection();

	/** The session factory. */
	//SessionFactory is Expensive and Threadsafe
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	/**
	 * You shouldn't create a instance of this Object
	 * Singelton.
	 */
	private Connection(){
		
	}
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public static Connection getConnection(){
		return instance;
	}
	
	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	//Session is not Threadsafe (One Session per Transaction)
	public Session getSession(){
		return sessionFactory.openSession();
	}
	
	/**
	 * Close the Session.
	 */
	public void close(){
		sessionFactory = null;
	}
	
}