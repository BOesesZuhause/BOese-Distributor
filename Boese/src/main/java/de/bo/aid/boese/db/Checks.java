package de.bo.aid.boese.db;

import org.hibernate.Session;

import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;

public class Checks {
	
	private static Connection connection = Connection.getConnection();
	
	public static boolean deviceComponentID (int decoid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			DeviceComponent devcomp = new DeviceComponent();
			session.load(devcomp, new Integer(decoid));
			session.getTransaction().commit();
			session.evict(devcomp);
			return true;
		}
		catch(org.hibernate.ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			return false;
		}
	}

}
