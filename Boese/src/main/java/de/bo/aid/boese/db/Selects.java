package de.bo.aid.boese.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import de.bo.aid.boese.constants.Errors;
import de.bo.aid.boese.model.*;

public class Selects {
	
	private static Connection connection = Connection.getConnection();
	
	public static Connector connector(int coid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Connector con = new Connector();
		try{
			session.load(con, new Integer(coid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.evict(con);
			session.getTransaction().rollback();
			return new Connector(Errors.OBJECT_NOT_FOUND);
		}
		
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
		
		return device;
	}
	
	public static double currentValue(int decoid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		DeviceComponent deco = new DeviceComponent();
		double d = 0.0;
		try{
			session.load(deco, new Integer(decoid));
			d = deco.getCurrentValue().doubleValue();
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.evict(deco);
			session.getTransaction().rollback();
			return Errors.OBJECT_NOT_FOUND;
		}
		
		session.evict(deco);
		
		return d;
	}
	
	public static Device device (int deid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Device dev = new Device();
		try{
			session.load(dev, new Integer(deid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.evict(dev);
			session.getTransaction().rollback();
			return new Device(Errors.OBJECT_NOT_FOUND);
		}
		
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
		
		return zone;
	}

}
