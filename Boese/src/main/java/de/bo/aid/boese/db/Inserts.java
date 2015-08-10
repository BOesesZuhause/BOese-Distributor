package de.bo.aid.boese.db;

import org.hibernate.Session;

import de.bo.aid.boese.model.*;

public class Inserts {
	private static Connection connection = Connection.getConnection();
	
	public static int device(int coid, int zoid, String name){
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
 
		session.save(device);
		session.getTransaction().commit();
		
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
		
		return dc.getDeCoId();
	}
	
	public static int connector(String name, String adress){
		Session session = connection.getSession();
		session.beginTransaction();
 
		Connector con = new Connector();
		con.setName(name);
		con.setWebadress(adress);
 
		session.save(con);
		session.getTransaction().commit();
		
		return con.getCoId();
	}
}
