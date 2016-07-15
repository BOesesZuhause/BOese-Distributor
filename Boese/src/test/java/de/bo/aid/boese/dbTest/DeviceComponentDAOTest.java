package de.bo.aid.boese.dbTest;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.dao.StandardDAO;
import de.bo.aid.boese.modelJPA.Component;
import de.bo.aid.boese.modelJPA.Connector;
import de.bo.aid.boese.modelJPA.Device;
import de.bo.aid.boese.modelJPA.DeviceComponent;
import de.bo.aid.boese.modelJPA.Unit;
import de.bo.aid.boese.modelJPA.Zone;

public class DeviceComponentDAOTest {
	
	private EntityManager em;
	private DAOHandler daoHandler = DAOHandler.getInstance();
	
	@Before
	public void setUp(){
		JPAUtil.setDBUser("postgres");
		JPAUtil.setDBPassword("Di0bPWfw");
		JPAUtil.setDBURL("boeseTest", "localhost", 5432);
		JPAUtil.init();
		em = JPAUtil.getEntityManager();	
	}
	
	@Test
	public void test(){
		em.getTransaction().begin();
		
		//Component
		Unit unit = daoHandler.getUnitDAO().create(em, "Quadratfuß", "f³");
		Component component = daoHandler.getComponentDAO().create(em, "Raummesssensor", false);
		component.setUnit(unit);
		
		//Device
		Zone zone = daoHandler.getZoneDAO().create(em, "Garten");
		Connector connector = daoHandler.getConnectorDAO().create(em, "Hauskonnektor", "geheim", false);
		Device device = daoHandler.getDeviceDAO().create(em, "Sensor", "12345");
		device.setZone(zone);
		device.setConnector(connector);
		
		//DeviceComponent
		DeviceComponent deviceComponent = daoHandler.getDeviceComponentDAO().create(em, "Text Text Text", 4, 7, 3, false);
		deviceComponent.setComponent(component);
		deviceComponent.setDevice(device);
		
		
		
		em.getTransaction().commit();
	}
	
	
	
	@After
	public void tearDown(){
		em.close();
	}
	 
	
}
