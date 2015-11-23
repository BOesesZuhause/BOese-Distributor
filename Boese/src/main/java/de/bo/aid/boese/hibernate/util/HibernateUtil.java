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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


// TODO: Auto-generated Javadoc
/**
 * The Class HibernateUtil.
 */
public class HibernateUtil {
	
	 /** The Constant sessionFactory. */
 	private static SessionFactory sessionFactory;
 	
 	/** The Constant logger. */
	 final static  Logger logger = LogManager.getLogger(HibernateUtil.class);
	

 	
 	/** The configuration. */
	 static Configuration configuration = new Configuration().configure();
	    /**
    	 * Builds the session factory.
    	 *
    	 * @return the session factory
    	 */
    	private static SessionFactory buildSessionFactory() {
	        try {
	            // Create the SessionFactory from hibernate.cfg.xml
	        	
	        	
	        	
	        	configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");   	
	        	configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");	
	        	
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
    		if(sessionFactory == null){
    			sessionFactory = buildSessionFactory();
    		}
	        return sessionFactory;
	    }
    	
    	/**
	     * Sets the DB user.
	     *
	     * @param user the new DB user
	     */
	    public static void setDBUser(String user){
    		configuration.setProperty("hibernate.connection.username", user);
    	}
    	
    	/**
	     * Sets the DB password.
	     *
	     * @param password the new DB password
	     */
	    public static void setDBPassword(String password){
        	configuration.setProperty("hibernate.connection.password", password);	
    	}
    	
    	/**
	     * Sets the dburl.
	     *
	     * @param name the name
	     * @param host the host
	     * @param port the port
	     */
	    public static void setDBURL(String name, String host, String port){
    		String url = "jdbc:postgresql://" + host+ ":" +port+"/"+name;
    		configuration.setProperty("hibernate.connection.url", url); 
      	}
	    
	    public static void setDBAuto(String auto){
	    	configuration.setProperty("hibernate.hbm2ddl.auto", auto);
	    }


}
