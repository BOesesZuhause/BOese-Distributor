package de.bo.aid.boese.db.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import de.bo.aid.boese.DB.util.DBDefaults;
import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.dao.StandardDAO;
import de.bo.aid.boese.modelJPA.Component;
import de.bo.aid.boese.modelJPA.Connector;
import de.bo.aid.boese.modelJPA.Device;
import de.bo.aid.boese.modelJPA.ToDo;
import de.bo.aid.boese.modelJPA.Unit;
import de.bo.aid.boese.modelJPA.User;
import de.bo.aid.boese.modelJPA.Zone;

public class TestdataHelper {
	
	private static EntityManager em;
	private static DAOHandler daoHandler = DAOHandler.getInstance();
	private static StandardDAO<Object> std = new StandardDAO<Object>() {			
		@Override
		public Set<Object> getAll(EntityManager em) {return null;}
		@Override
		public Object get(EntityManager em, int id) {return null;}
		@Override
		public long count(EntityManager em) {return 0;}
	};
	
	public static void insertTestData(){
		em = JPAUtil.getEntityManager();
		
		DBDefaults.defaults();
		
		List<Object[]> objects = new ArrayList<Object[]>();
		
		Connector connectors[] = {
				new Connector("HomeMatic Connector", "secret", false),
				new Connector("Philips Hue Connector", "12345", false),
				new Connector("GUI Connector", "qwertz", true)		
		};
		objects.add(connectors);
		
		Set<Unit> unitSet = daoHandler.getUnitDAO().getAll(em);
		Unit[] units = unitSet.toArray(new Unit[unitSet.size()]);
		objects.add(units);
		
		Zone global = daoHandler.getZoneDAO().get(em, 1);
		Zone zones[] = {
				new Zone("First Floor"),
				new Zone("Second Floor"),
				new Zone("Kitchen"),
				new Zone("Bathroom"),
				new Zone("Livingroom"),
				new Zone("Bedroom")		
		};
		zones[0].setZone(global);
		zones[1].setZone(global);
		zones[2].setZone(zones[0]);
		zones[3].setZone(zones[1]);
		zones[4].setZone(zones[0]);
		zones[5].setZone(zones[1]);
		objects.add(zones);
		
		User[] users={
				new User("Pe", "erste", "123", true, new Date(), "first", "erste.person@first.de"),
				new User("rs", "zweite", "456", false, new Date(), "second", "zweite.person@second.de"),
				new User("on", "dritte", "789", false, new Date(), "third", "erste.person@third.de"),
				new User("Person", "vierte", "klo", true, new Date(), "fourth", "zweite.person@fourth.de"),
		};
		objects.add(users);
		
		ToDo todos[] = {
			new ToDo(),	
			new ToDo(),
			new ToDo(),
			new ToDo(),
			new ToDo()		
		};
//		objects.add(todos);
		
		for(Object[] o : objects){
			em.getTransaction().begin();
			std.createMore(em, o);
			em.getTransaction().commit();
		}
		em.close();
	}
}
