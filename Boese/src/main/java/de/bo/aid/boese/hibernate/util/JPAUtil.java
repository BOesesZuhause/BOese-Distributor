package de.bo.aid.boese.hibernate.util;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	static Properties jpaProps = new Properties();

	 	private static EntityManagerFactory emFactory;
	 	
	 	public static void init(){
	 		emFactory = Persistence.createEntityManagerFactory("BOese", jpaProps);
	 	}
	 	

		public static EntityManager getEntityManager(){
			return emFactory.createEntityManager();
		}
		public static void close(){
			emFactory.close();
		}
		
		/**
	     * Sets the DB user.
	     *
	     * @param user the new DB user
	     */
	    public static void setDBUser(String user){
    		jpaProps.setProperty("hibernate.connection.username", user);
    	}
    	
    	/**
	     * Sets the DB password.
	     *
	     * @param password the new DB password
	     */
	    public static void setDBPassword(String password){
        	jpaProps.setProperty("hibernate.connection.password", password);	
    	}
    	
    	/**
	     * Sets the dburl.
	     *
	     * @param name the name
	     * @param host the host
	     * @param port the port
	     */
	    public static void setDBURL(String name, String host, int port){
    		String url = "jdbc:postgresql://" + host+ ":" +port+"/"+name;
    		jpaProps.setProperty("hibernate.connection.url", url); 
      	}
	    
	    /**
    	 * Sets the DB auto.
    	 *
    	 * @param auto the new DB auto
    	 */
    	public static void setDBAuto(String auto){
	    	jpaProps.setProperty("hibernate.hbm2ddl.auto", auto);
	    }

}
