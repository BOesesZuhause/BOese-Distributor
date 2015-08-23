package de.bo.aid.boese.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.bo.aid.boese.hibernate.util.HibernateUtil;

public class Connection {
	
	private static Connection instance = new Connection();

	//SessionFactory is Expensive and Threadsafe
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	
	public static Connection getConnection(){
		return instance;
	}
	
	//Session is not Threadsafe (One Session per Transaction)
	public Session getSession(){
		return sessionFactory.openSession();
	}
	
	public void close(){
		sessionFactory = null;
	}
	
}