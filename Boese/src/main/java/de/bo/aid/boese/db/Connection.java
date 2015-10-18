


package de.bo.aid.boese.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.bo.aid.boese.hibernate.util.HibernateUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Connection.
 */
public class Connection {
	
	/** The instance. */
	private static Connection instance = new Connection();

	/** The session factory. */
	//SessionFactory is Expensive and Threadsafe
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	
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
	 * Close.
	 */
	public void close(){
		sessionFactory = null;
	}
	
}