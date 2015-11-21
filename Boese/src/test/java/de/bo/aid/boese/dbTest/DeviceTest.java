/*
 * 
 */
package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.Zone;
import de.bo.aid.boese.model.Connector;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviceTest.
 */
public class DeviceTest {

	/** The dev1. */
	private Device dev1;
	
	/** The dev2. */
	private Device dev2;
	
	/** The dev1 update. */
	private Device dev1Update;
	
	/** The dev2 update. */
	private Device dev2Update;
	
	/** The test con. */
	private Connector testCon;
	
	/** The test con update. */
	private Connector testConUpdate;
	
	/** The test zone. */
	private Zone testZone;
	
	/** The test zone update. */
	private Zone testZoneUpdate;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boese", "localhost", "5432");
		
		testCon = new Connector("test", "test");
		Inserts.connector(testCon);
		
		testConUpdate = new Connector("update", "update");
		Inserts.connector(testConUpdate);
		
		testZone = new Zone("1");
		Inserts.zone(testZone, testZone);
		
		testZoneUpdate = new Zone("2");
		Inserts.zone(testZoneUpdate, testZoneUpdate);
		
		dev1 = new Device("dev1", "12345");
		
		dev2 = new Device("dev2", "56789");
		
		dev1Update = new Device("dev1Update", "54321");
		
		dev2Update = new Device("dev2Update", "98765");
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		
		//Insert Device 1
		insert(dev1);
		dev1Update.setDeId(dev1.getDeId());
		dev1Update.setConnector(testConUpdate);
		dev1Update.setZone(testZoneUpdate);
		//Insert Device 2
		insert(dev2);
		dev2Update.setDeId(dev2.getDeId());
		dev2Update.setConnector(testConUpdate);
		dev2Update.setZone(testZoneUpdate);

		//Device 1 equal Unit in DB?
		equal(dev1, "1");
		//Device 2 equal Unit in DB?
		equal(dev2, "2");
		
		//Update Device 1
		update(dev1, dev1Update);
		//Update Device 2
		update(dev2, dev2Update);
		
		//Device 1 after update equal Device in DB?
		equal(dev1Update, "1Update");
		//Device 1 after update equal Device in DB?
		equal(dev2Update, "2Update");
	}

	/**
	 * Insert.
	 *
	 * @param dev the dev
	 */
	private void insert(Device dev){
		try {
			Inserts.device(testCon.getCoId(), testZone.getZoId(), dev);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	/**
	 * Select.
	 *
	 * @param id the id
	 * @return the device
	 */
	private Device select(int id){
		Device comp = null;
		try {
			comp = Selects.device(id);
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage() + " with ID: " + id);
			e.printStackTrace();
		}
		return comp;
	}
	
	/**
	 * Equal.
	 *
	 * @param dev the dev
	 * @param name the name
	 */
	private void equal(Device dev, String name){
		Device devTest = select(dev.getDeId());
		assertTrue("Device " + name + " Alias not equal", dev.getAlias().equals(devTest.getAlias()));
		assertTrue("Device " + name + " SerialNumber not equal", dev.getSerialNumber().equals(devTest.getSerialNumber()));
		assertTrue("Device " + name + " Connector not equal", dev.getConnector().getCoId() == devTest.getConnector().getCoId());
		assertTrue("Device " + name + " Zone not equal", dev.getZone().getZoId() == devTest.getZone().getZoId());
		assertTrue("Device " + name + " ID not equal", dev.getDeId() == devTest.getDeId());
	}
	
	/**
	 * Update.
	 *
	 * @param dev the dev
	 * @param devUpdate the dev update
	 */
	private void update(Device dev, Device devUpdate){
		try {
			Updates.device(dev, devUpdate.getAlias(), devUpdate.getSerialNumber(), null, testZoneUpdate, testConUpdate);
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage() + "with ID: " + dev.getDeId());
			e.printStackTrace();
		}
	}
}
