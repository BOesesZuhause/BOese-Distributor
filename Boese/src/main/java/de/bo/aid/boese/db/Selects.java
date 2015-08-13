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
		session.evict(con);
		
		return con;
	}
	
	public static List<Device> connectorDeviceList(int coid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Device where coid = " + coid).list();
		List<Device> device = new ArrayList<Device>();
		for(Object o: erg){
			device.add((Device) o);
		}
		session.getTransaction().commit();
		session.evict(device);
		
		return device;
	}
	
	public static double currentValue(int decoid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		DeviceComponent deco = new DeviceComponent();
		session.load(deco, new Integer(decoid));
		double d = deco.getCurrentValue().doubleValue();
		session.getTransaction().commit();
		session.evict(deco);
		
		return d;
	}
	
	public static Device device (int deid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Device dev = new Device();
		session.load(dev, new Integer(deid));
		session.getTransaction().commit();
		session.evict(dev);
		
		return dev;
	}
	
	public static List<Zone> allZones(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Zone").list();
		List<Zone> zone = new ArrayList<Zone>();
		for(Object o: erg){
			zone.add((Zone) o);
		}
		
		session.getTransaction().commit();
		session.evict(zone);
		
		return zone;
	}

}
