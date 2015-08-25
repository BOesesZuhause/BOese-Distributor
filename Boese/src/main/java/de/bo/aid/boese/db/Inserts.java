package de.bo.aid.boese.db;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
			session.close();
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
			session.close();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		session.evict(con);
		session.evict(zone);
		session.evict(device);
		session.close();
		
		return device.getDeId();
	}
	
	public static int component(String name, int unitId, boolean sensor){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Unit unit = new Unit();
		try{
			session.load(unit, new Integer(unitId));
		}
		catch (ObjectNotFoundException onfe){
			session.evict(unit);
			session.getTransaction().rollback();
			session.close();
			return Errors.OBJECT_NOT_FOUND;
		}
		
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
			session.close();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		session.evict(unit);
		session.evict(comp);
		session.close();
		
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
			session.close();
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
			session.close();
			return Errors.NOT_NULL_VALUE_NULL;
		}		
		
		session.evict(dc);
		session.evict(device);
		session.evict(comp);
		session.close();
		
		
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
			session.close();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		session.evict(con);
		session.close();
		
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
			session.getTransaction().rollback();
			session.close();
			return Errors.OBJECT_NOT_FOUND;
		}
		try{
			session.save(deco);
		}
		catch(PropertyValueException pve){
			session.evict(deco);
			session.evict(timestamp);
			session.getTransaction().rollback();
			session.close();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		LogDeviceComponent logcomp = new LogDeviceComponent();
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
			session.getTransaction().rollback();
			session.close();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		session.evict(deco);
		session.evict(logcomp);
		session.close();
		
		return Errors.OK;
	}
	
	public static int unit(String name, String symbol){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Unit unit = new Unit();
		unit.setName(name);
		unit.setSymbol(symbol);
		
		try{
			session.save(unit);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.evict(unit);
			session.getTransaction().rollback();
			session.close();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		session.evict(unit);
		session.close();
		
		return unit.getUnId();
	}
	
	public static int rule(List<Integer> deCoID, String permissions, String conditions, String actions){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Rule rule = new Rule();
		rule.setActive(true);
		rule.setInsertDate(new Date());
		rule.setModifyDate(new Date());
		rule.setPermissions(permissions);
		rule.setConditions(conditions);
		rule.setActions(actions);
		
		try{
			session.save(rule);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.evict(rule);
			session.getTransaction().rollback();
			session.close();
			return Errors.NOT_NULL_VALUE_NULL;
		}
		
		session.evict(rule);
		
		int ruID = rule.getRuId();
		
		try{
			for(int decoid : deCoID){
				session.beginTransaction();
				DeviceComponent deco = Selects.deviceComponent(decoid);
				if (deco.getDeCoId() > -1){
					DeviceComponentRule decorule = new DeviceComponentRule();
					decorule.setDevicecomponent(deco);
					decorule.setRule(rule);
					session.save(decorule);
					session.getTransaction().commit();
					session.evict(rule);
					session.evict(deco);
					session.evict(decorule);
				}
				else{
					session.evict(rule);
					session.getTransaction().rollback();
					session.close();
					return deco.getDeCoId();
				}
			}
		}
		catch(PropertyValueException pve){
			session.evict(rule);
			session.getTransaction().rollback();
			session.close();
			return Errors.NOT_NULL_VALUE_NULL;
		}

		session.close();
		return ruID;
	}
}
