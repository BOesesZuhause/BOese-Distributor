package de.bo.aid.boese.db;

import org.hibernate.Session;

import de.bo.aid.boese.model.*;

public class Inserts {
	private static Connection connection = Connection.getConnection();
	
	//TODO andere möglichkeit der Nutzung von sessions suchen oder immer alles aufraeumen
	
	public static int device(int coid, int zoid, String name, String serial){
		Session session = connection.getSession();
		session.beginTransaction();
 
		Device device = new Device();
		
		Connector con = new Connector();
		session.load(con, new Integer(coid));
		device.setConnector(con);
		
		Zone zone = new Zone();
		session.load(zone, new Integer(zoid));
		device.setZone(zone);
		
		device.setAlias(name);
		
		device.setSerialNumber(serial);
 
		session.save(device);
		session.getTransaction().commit();
		
		//clean up 
		//Wenn dies nicht gemacht wird, bleiben die Objekte mit ihrer zugehörigen Id in der Session gespeichert
		//und es gibt einen Fehler beim laden eines Objekts wenn schon eines in der Session mit derselben Id vorhanden ist
		session.evict(con);
		session.evict(zone);
		session.evict(device);
		
		return device.getDeId();
	}
	
	public static int deviceComponent(int deid, int coid, String name){
		Session session = connection.getSession();
		session.beginTransaction();
 
		DeviceComponent dc = new DeviceComponent();
		
		Device device = new Device();
		session.load(device, new Integer(deid));
		dc.setDevice(device);
		
		Component comp = new Component();
		session.load(comp, new Integer(coid));
		dc.setComponent(comp);
		
		dc.setStatus((short) 1);
 
		session.save(dc);
		session.getTransaction().commit();
		
		//clean up 
		//Wenn dies nicht gemacht wird, bleiben die Objekte mit ihrer zugehörigen Id in der Session gespeichert
		//und es gibt einen Fehler beim laden eines Objekts wenn schon eines in der Session mit derselben Id vorhanden ist
		session.evict(dc);
		session.evict(device);
		session.evict(comp);
		
		
		return dc.getDeCoId();
	}
	
	public static int connector(String name, String pw){
		Session session = connection.getSession();
		session.beginTransaction();
 
		Connector con = new Connector();
		con.setName(name);
		con.setPassword(pw);
 
		session.save(con);
		session.getTransaction().commit();
		
		//clean up 
		//Wenn dies nicht gemacht wird, bleiben die Objekte mit ihrer zugehörigen Id in der Session gespeichert
		//und es gibt einen Fehler beim laden eines Objekts wenn schon eines in der Session mit derselben Id vorhanden ist
		session.evict(con);
		
		return con.getCoId();
	}
}
