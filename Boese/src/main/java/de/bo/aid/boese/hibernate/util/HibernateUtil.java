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



package de.bo.aid.boese.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

// TODO: Auto-generated Javadoc
/**
 * The Class HibernateUtil.
 */
public class HibernateUtil {
	
	 /** The Constant sessionFactory. */
 	private static final SessionFactory sessionFactory = buildSessionFactory();

	    /**
    	 * Builds the session factory.
    	 *
    	 * @return the session factory
    	 */
    	private static SessionFactory buildSessionFactory() {
	        try {
	            // Create the SessionFactory from hibernate.cfg.xml
	        	Configuration configuration = new Configuration().configure();
	        	configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
	        	configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/boese");        	
	        	configuration.setProperty("hibernate.connection.username", "postgres");
	        	configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
	        	configuration.setProperty("hibernate.connection.password", "Di0bPWfw");	        	
	        	
	        	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
	        	applySettings(configuration.getProperties());
	        	SessionFactory factory = configuration.buildSessionFactory(builder.build());
	        	return factory;
	        }
	        catch (Throwable ex) {
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }

	    /**
    	 * Gets the session factory.
    	 *
    	 * @return the session factory
    	 */
    	public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }


}
