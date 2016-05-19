package de.bo.aid.boese.DB.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.dao.GroupDAO;
import de.bo.aid.boese.dao.RuleDAO;
import de.bo.aid.boese.dao.ServiceDAO;
import de.bo.aid.boese.dao.UnitDAO;
import de.bo.aid.boese.dao.UserDAO;
import de.bo.aid.boese.dao.ZoneDAO;
import de.bo.aid.boese.main.ProtocolHandler;
import de.bo.aid.boese.modelJPA.Group;
import de.bo.aid.boese.modelJPA.Rule;
import de.bo.aid.boese.modelJPA.User;
import de.bo.aid.boese.modelJPA.Service;
import de.bo.aid.boese.modelJPA.Unit;
import de.bo.aid.boese.modelJPA.Zone;

public class DBDefaults {
	
	private static DAOHandler daoHandler = DAOHandler.getInstance();
	private static Logger logger = LogManager.getLogger(ProtocolHandler.class);
	
	public static void defaults(){
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		defaultUnit(em);
		defaultZone(em);
		defaultService(em);
		defaultUser(em);
		defaultGroup(em);
		defaultRule(em);
		em.getTransaction().commit();
		em.getTransaction().begin();
		defaultUnits(em);
		em.getTransaction().commit();
		em.close();
	}
	
	private static void defaultUnit(EntityManager em){
//		System.out.println(daoHandler);
		UnitDAO dao = daoHandler.getUnitDAO();
		Unit unew = new Unit("Undefined", "ud");
		Unit db = dao.get(em, 1);
		if(db == null){
			dao.create(em, unew);
		}
		else if (!db.equalsWithoutID(unew)) {
			dao.delete(em, db);
			dao.create(em, unew);
		}
	}
	
	private static void defaultZone(EntityManager em){
		ZoneDAO dao = daoHandler.getZoneDAO();
		Zone znew = new Zone("Global");
		znew.setZone(znew);
		Zone db = dao.get(em, 1);
		if(db == null){
			dao.create(em, znew);
		}
		else if (!db.equalsWithoutID(znew)) {
			dao.delete(em, db);
			dao.create(em, znew);
		}
	}
	
	private static void defaultService(EntityManager em){
		ServiceDAO dao = daoHandler.getServiceDAO();
		Service snew = new Service("Default");
		Service db = dao.get(em, 1);
		if(db == null){
			dao.create(em, snew);
		}
		else if (!db.equalsWithoutID(snew)) {
			dao.delete(em, db);
			dao.create(em, snew);
		}
	}
	
	private static void defaultUser(EntityManager em){
		UserDAO dao = daoHandler.getUserDAO();
		User unew = new User("User", "Super", "MasterPassword", true, null, "root", null);
		User db = dao.get(em, 1);
		if(db == null){
			dao.create(em, unew);
		}
		else if (!db.equalsWithoutID(unew)) {
			dao.delete(em, db);
			dao.create(em, unew);
		}
	}
	
	private static void defaultGroup(EntityManager em){
		GroupDAO dao = daoHandler.getGroupDAO();
		Group gnew = new Group("Users");
		Group db = dao.get(em, 1);
		if(db == null){
			dao.create(em, gnew);
		}
		else if (!db.equalsWithoutID(gnew)) {
			dao.delete(em, db);
			dao.create(em, gnew);
		}
	}
	
	private static void defaultRule(EntityManager em){
		RuleDAO dao = daoHandler.getRuleDAO();
		Rule rnew = new Rule("<PERMISSION></PERMISSION>", "<CONDITION></CONDITION>", "<ACTION></ACTION>");
		Rule db = dao.get(em, 1);
		if(db == null){
			dao.create(em, rnew);
		}
		else if (!db.equalsWithoutID(rnew)) {
			dao.delete(em, db);
			dao.create(em, rnew);
		}
	}
	
	private static void defaultUnits(EntityManager em){
		UnitDAO dao = daoHandler.getUnitDAO();
		Map<Integer, Unit> unitsDb = dao.getAllAsMap(em);
		
		Map<String, String> defaults = getDefaultUnits();	//Name is Key and Symbol is value
		if(unitsDb.size() == 1){
			for(Entry<String, String> entry : defaults.entrySet()){
				dao.create(em, entry.getKey(), entry.getValue());
			}
		}
		else{
			for(Entry<String, String> entry : defaults.entrySet()){
				int nameId = -1;
				int symbolId = -1;
				for(Entry<Integer, Unit> unit : unitsDb.entrySet()){
					if(entry.getKey() == unit.getValue().getName()){
						nameId = unit.getKey();
					}
					if(entry.getValue() == unit.getValue().getSymbol()){
						symbolId = unit.getKey();
					}
					if(nameId != -1 && symbolId != -1){
						break;
					}
				}				
				if(nameId == -1 && symbolId == -1){
					dao.create(em, entry.getKey(), entry.getValue());
				}				
				else if(nameId != -1 && symbolId == -1){
					Unit unit = unitsDb.get(nameId);
					logger.warn("Unit: " + unit.getName() + "(" + unit.getSymbol() + ") is replaced by default Unit: " + entry.getKey() + "(" + entry.getValue() + ")");
					unit.setSymbol(entry.getValue());
					dao.merge(em, unit);
				}				
				else if(nameId == -1 && symbolId != -1){
					Unit unit = unitsDb.get(symbolId);
					unit.setName(entry.getKey());
					dao.merge(em, unit);
					logger.warn("Unit: " + unit.getName() + "(" + unit.getSymbol() + ") is replaced by default Unit: " + entry.getKey() + "(" + entry.getValue() + ")");
				}				
				else if(nameId != symbolId){
					Unit unit = null;
					Unit delete = null;
					if(nameId < symbolId){
						unit = unitsDb.get(nameId);
						logger.warn("Unit: " + unit.getName() + "(" + unit.getSymbol() + ") is replaced by default Unit: " + entry.getKey() + "(" + entry.getValue() + ")");
						unit.setSymbol(entry.getValue());
						delete = unitsDb.get(symbolId);
					}
					else{
						unit = unitsDb.get(symbolId);
						logger.warn("Unit: " + unit.getName() + "(" + unit.getSymbol() + ") is replaced by default Unit: " + entry.getKey() + "(" + entry.getValue() + ")");
						unit.setName(entry.getKey());
						delete = unitsDb.get(nameId);
					}
					logger.warn("Unit: " + delete.getName() + "(" + delete.getSymbol() + ") is deleted cause of default Units");
					dao.merge(em, unit);
					dao.delete(em, delete);
				}
				else{
					break;
				}
			}
		}
	}
	
	private static Map<String, String> getDefaultUnits(){
		Map<String, String> defaults = new HashMap<>(); 
		defaults.put("Volt", "V"); 
		defaults.put("milliAmpere", "mA"); 
		defaults.put("Percent", "%"); 
		defaults.put("OnOff", "Zeichen fehlt"); 
		defaults.put("Temperature", "°C"); 
		defaults.put("Distance", "m"); 
		defaults.put("ColorRGB", "rgb"); 
		defaults.put("Watt", "W"); 
		defaults.put("Time", "ms"); 
		defaults.put("Velocity", "m/s"); 
		defaults.put("Weight", "g");
		return defaults;
	}

}
