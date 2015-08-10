package de.bo.aid.boese.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import de.bo.aid.boese.model.*;

public class Selects {
	
	private static Connection connection = Connection.getConnection();
	
	public static Connector connector(int coid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Connector con = new Connector();
		session.load(con, new Integer(coid));
		session.getTransaction().commit();
		
		return con;
	}
	
	public static List<Device> device(int coid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Device where coid = " + coid).list();
		List<Device> device = new ArrayList<Device>();
		for(Object o: erg){
			device.add((Device) o);
		}
		
		session.getTransaction().commit();
		
		return device;
	}
	
	public static double currentValue(int decoid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		double d = 0.0;
		session.load(d, new Integer(decoid));
		session.getTransaction().commit();
		
		return d;
	}

}
