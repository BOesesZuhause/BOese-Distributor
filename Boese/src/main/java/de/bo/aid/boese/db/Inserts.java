package de.bo.aid.boese.db;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;

import de.bo.aid.boese.constants.Errors;
import de.bo.aid.boese.model.*;

public class Inserts {
	private static Connection connection = Connection.getConnection();
	
	public static int device(int coid, int zoid, String name, String serial){
		Session session = connection.getSession();
		session.beginTransaction();
 
		Device device = new Device();
		
		Connector con = new Connector();
		Zone zone = new Zone();
		try{
			session.load(con, new Integer(coid));
			device.setConnector(con);
			session.load(zone, new Integer(zoid));
			device.setZone(zone);
		}
		catch (ObjectNotFoundException onfe){
			session.evict(con);
			session.evict(zone);
			session.evict(device);
			session.getTransaction().rollback();
			return Errors.OBJECT_NOT_FOUND;
		}
		
		device.setAlias(name);
		device.setSerialNumber(serial);
 
		try{
			session.save(device);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.evict(con);
			session.evict(zone);
			session.evict(device);
			session.getTransaction().rollback();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		session.evict(con);
		session.evict(zone);
		session.evict(device);
		
		return device.getDeId();
	}
	
	public static int component(String name, int unitId, boolean sensor){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Unit unit = new Unit();
		session.load(unit, new Integer(unitId));
		
		Component comp = new Component();
		comp.setName(name);
		comp.setSensor(sensor);
		comp.setUnit(unit);
		
		try{
			session.save(comp);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.evict(unit);
			session.evict(comp);
			session.getTransaction().rollback();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		session.evict(unit);
		session.evict(comp);
		
		return comp.getCoId();	
	}
	
	public static int deviceComponent(int deid, int coid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		DeviceComponent dc = new DeviceComponent();
		
		Device device = new Device();
		Component comp = new Component();
		try{
			session.load(device, new Integer(deid));
			dc.setDevice(device);
			session.load(comp, new Integer(coid));
			dc.setComponent(comp);
		}
		catch (ObjectNotFoundException onfe){
			session.evict(dc);
			session.evict(device);
			session.evict(comp);
			session.getTransaction().rollback();
			return Errors.OBJECT_NOT_FOUND;
		}
		
		dc.setStatus((short) 1);
 
		try{
			session.save(dc);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.evict(dc);
			session.evict(device);
			session.evict(comp);
			session.getTransaction().rollback();
			return Errors.NOT_NULL_VALUE_NULL;
		}		
		
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
 
		try{
			session.save(con);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.evict(con);
			session.getTransaction().rollback();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		session.evict(con);
		
		return con.getCoId();
	}
	
	public static int value(int decoid, Date timestamp, double value){
		Session session = connection.getSession();
		session.beginTransaction();
 
		DeviceComponent deco = new DeviceComponent();
		try{
			session.load(deco, new Integer(decoid));
			deco.setCurrentValue(new BigDecimal(value));
		}
		catch (ObjectNotFoundException onfe){
			session.evict(deco);
			//session.evict(timestamp);
			session.getTransaction().rollback();
			return Errors.OBJECT_NOT_FOUND;
		}
		try{
			session.save(deco);
		}
		catch(PropertyValueException pve){
			session.evict(deco);
			session.evict(timestamp);
			session.getTransaction().rollback();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		LogComponent logcomp = new LogComponent();
		logcomp.setDeviceComponent(deco);
		logcomp.setTimestamp(timestamp);
		logcomp.setValue(new BigDecimal(value));
		
		try{
			session.save(logcomp);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.evict(deco);
			session.evict(logcomp);
			session.evict(timestamp);
			session.getTransaction().rollback();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		session.evict(deco);
		session.evict(logcomp);
		//session.evict(timestamp);
		
		//ToDo Fehlerbehandlung
		return Errors.OK;
	}
}
