package de.bo.aid.boese.DB.util;

import javax.persistence.EntityManager;

import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.dao.GroupDAO;
import de.bo.aid.boese.dao.RuleDAO;
import de.bo.aid.boese.dao.ServiceDAO;
import de.bo.aid.boese.dao.UnitDAO;
import de.bo.aid.boese.dao.UserDAO;
import de.bo.aid.boese.dao.ZoneDAO;
import de.bo.aid.boese.modelJPA.Group;
import de.bo.aid.boese.modelJPA.Rule;
import de.bo.aid.boese.modelJPA.User;
import de.bo.aid.boese.modelJPA.Service;
import de.bo.aid.boese.modelJPA.Unit;
import de.bo.aid.boese.modelJPA.Zone;

public class DBDefaults {
	
	DAOHandler daoHandler;
	
	public DBDefaults(){
		daoHandler = DAOHandler.getInstance();
	}
	
	public void defaults(){
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		defaultUnit(em);
		defaultZone(em);
		defaultService(em);
		defaultUser(em);
		defaultGroup(em);
		defaultRule(em);
		em.getTransaction().commit();
		em.close();
	}
	
	private void defaultUnit(EntityManager em){
		UnitDAO dao = daoHandler.getUnit();
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
	
	private void defaultZone(EntityManager em){
		ZoneDAO dao = daoHandler.getZone();
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
	
	private void defaultService(EntityManager em){
		ServiceDAO dao = daoHandler.getSer();
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
	
	private void defaultUser(EntityManager em){
		UserDAO dao = daoHandler.getUser();
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
	
	private void defaultGroup(EntityManager em){
		GroupDAO dao = daoHandler.getGrp();
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
	
	private void defaultRule(EntityManager em){
		RuleDAO dao = daoHandler.getRu();
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

}
