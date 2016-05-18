package de.bo.aid.boese.db.util;

import javax.persistence.EntityManager;

import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.modelJPA.Component;
import de.bo.aid.boese.modelJPA.Connector;
import de.bo.aid.boese.modelJPA.ToDo;
import de.bo.aid.boese.modelJPA.Unit;
import de.bo.aid.boese.modelJPA.Zone;

public class TestdataHelper {
	
	private static EntityManager em;
	private static DAOHandler daoHandler = DAOHandler.getInstance();
	
	public static void insertTestData(){
		em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		
		Unit[] units = {
				new Unit("Volt", "V"),
				new Unit("milliAmpere", "mA"),
				new Unit("Percent", "%"),
				new Unit("OnOff", ""),
				new Unit("Temperature", "°C"),
				new Unit("Distance", "m"),	
				new Unit("ColorRGB", "rgb"),	
				new Unit("Watt", "W"),	
				new Unit("Time", "ms"),	
				new Unit("Velocity", "m/s"),	
				new Unit("Weight", "g")
		};
		
		Connector connectors[] = {
				new Connector("HomeMatic Connector", "secret", false),
				new Connector("Philips Hue Connector", "12345", false),
				new Connector("GUI Connector", "qwertz", true)		
		};

		
		
		Zone zones[] = {
				new Zone("Kitchen"),
				new Zone("Bathroom"),
				new Zone("Livingroom"),
				new Zone("Bedroom"),
				new Zone("Basement")				
		};
		
		ToDo todos[] = {
			new ToDo(),	
			new ToDo(),
			new ToDo(),
			new ToDo(),
			new ToDo()		
		};
		
	}

}
