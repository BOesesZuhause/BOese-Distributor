package de.bo.aid.boese.dbTest;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.dao.StandardDAO;
import de.bo.aid.boese.db.util.TestdataHelper;
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
		JPAUtil.initForTesting();
		em = JPAUtil.getEntityManager();
		TestdataHelper.insertTestData();
	}
	
	@Test
	public void test(){
		em.getTransaction().begin();
		
		daoHandler.getDeviceComponentDAO().getBelongingConnector(em, 1);
		
		em.getTransaction().commit();
	}
	
	
	
	@After
	public void tearDown(){
		em.close();
	}
	 
	
}
