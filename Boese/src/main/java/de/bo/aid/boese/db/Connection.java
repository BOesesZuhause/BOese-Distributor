package de.bo.aid.boese.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.bo.aid.boese.hibernate.util.HibernateUtil;

public class Connection {

	private static Connection instance;
	
	private SessionFactory sessionFactory;
	private Session session;
	
	private Connection(){
		this.sessionFactory = HibernateUtil.getSessionFactory();
		this.session = sessionFactory.openSession();
	}
	
	public static Connection getConnection (){
		if(instance == null){
			instance = new Connection();
		}
		return instance;
	}
	
	public Session getSession(){
		return session;
	}
	
	public void close(){
		sessionFactory = null;
		session = null;
		instance = null;
	}
	
}