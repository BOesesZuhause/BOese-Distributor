/*
 * 
 */
package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.constants.Status;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.Component;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.model.Unit;
import de.bo.aid.boese.model.Zone;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviceComponentTest.
 */
public class DeviceComponentTest {

	/** The deco1. */
	private DeviceComponent deco1;
	
	/** The deco2. */
	private DeviceComponent deco2;
	
	/** The deco1 update. */
	private DeviceComponent deco1Update;
	
	/** The deco2 update. */
	private DeviceComponent deco2Update;
	
	/** The test con. */
	private Connector testCon;
	
	/** The test zone. */
	private Zone testZone;

	/** The test dev. */
	private Device testDev;
	
	/** The test dev update. */
	private Device testDevUpdate;
	
	/** The test unit. */
	private Unit testUnit;

	/** The test comp. */
	private Component testComp;
	
	/** The test comp update. */
	private Component testCompUpdate;
	
	/** The value. */
	private double value;
	
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
		
		value = 100.0;
		
		testCon = new Connector("test", "test");
		Inserts.connector(testCon);
		
		testZone = new Zone("1");
		Inserts.zone(testZone, testZone);
		
		testUnit = new Unit("test", "kg");
		Inserts.unit(testUnit);
		
		testDev = new Device("dev1", "12345");
		Inserts.device(testCon.getCoId(), testZone.getZoId(), testDev);
		testDevUpdate = new Device("dev2", "56789");
		Inserts.device(testCon.getCoId(), testZone.getZoId(), testDevUpdate);

		testComp = new Component("Taster", true);
		Inserts.component(testUnit.getUnId(), testComp);
		testCompUpdate = new Component("Lichtsensor", false);
		Inserts.component(testUnit.getUnId(), testCompUpdate);
		
		deco1 = new DeviceComponent("1");
		deco2 = new DeviceComponent("2");
		deco1Update = new DeviceComponent("1Update");
		deco2Update = new DeviceComponent("2Update");
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		
		//Insert DeviceComponent 1
		insert(deco1);
		deco1Update.setDeCoId(deco1.getDeCoId());
		deco1Update.setStatus(Status.INACTIVE);
		deco1Update.setCurrentValue(new BigDecimal(value));
		deco1Update.setComponent(testCompUpdate);
		deco1Update.setDevice(testDevUpdate);
		//Insert DeviceComponent 2
		insert(deco2);
		deco2Update.setDeCoId(deco2.getDeCoId());
		deco2Update.setStatus(Status.DEFECT);
		deco2Update.setCurrentValue(new BigDecimal(value));
		deco2Update.setComponent(testCompUpdate);
		deco2Update.setDevice(testDevUpdate);
		
		//Value equal DB Value?
		try {
			Inserts.value(deco1.getDeCoId(), new Date(), value);
			deco1.setCurrentValue(new BigDecimal(value));
			Inserts.value(deco2.getDeCoId(), new Date(), value);
			deco2.setCurrentValue(new BigDecimal(value));
			if(value != Selects.currentValue(deco1.getDeCoId()))
				fail("Value not Equal");
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		//DeviceComponent 1 equal Unit in DB?
		equal(deco1, "1");
		//DeviceComponent 2 equal Unit in DB?
		equal(deco2, "2");
		
		//Update DeviceComponent 1
		update(deco1, deco1Update);
		//Update DeviceComponent 2
		update(deco2, deco2Update);
		
		//DeviceComponent 1 after update equal DeviceComponent in DB?
		equal(deco1Update, "1Update");
		//DeviceComponent 1 after update equal DeviceComponent in DB?
		equal(deco2Update, "2Update");
	}

	/**
	 * Insert.
	 *
	 * @param deco the deco
	 */
	private void insert(DeviceComponent deco){
		try {
			Inserts.deviceComponent(testDev.getDeId(), testComp.getCoId(), deco);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	/**
	 * Select.
	 *
	 * @param id the id
	 * @return the device component
	 */
	private DeviceComponent select(int id){
		DeviceComponent comp = null;
		try {
			comp = Selects.deviceComponent(id);
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage() + " with ID: " + id);
			e.printStackTrace();
		}
		return comp;
	}
	
	/**
	 * Equal.
	 *
	 * @param deco the deco
	 * @param name the name
	 */
	private void equal(DeviceComponent deco, String name){
		DeviceComponent decoTest = select(deco.getDeCoId());
		assertTrue("DeviceComponent " + name + " Description not equal", deco.getDescription().equals(decoTest.getDescription()));
		assertTrue("DeviceComponent " + name + " Status not equal", deco.getStatus().equals(decoTest.getStatus()));
		assertTrue("DeviceComponent " + name + " Component not equal", deco.getComponent().getCoId() == decoTest.getComponent().getCoId());
		assertTrue("DeviceComponent " + name + " Value not equal", deco.getCurrentValue().equals(decoTest.getCurrentValue()));
		assertTrue("DeviceComponent " + name + " Device not equal", deco.getDevice().getDeId() == decoTest.getDevice().getDeId());
		assertTrue("DeviceComponent " + name + " ID not equal", deco.getDeCoId() == decoTest.getDeCoId());
	}
	
	/**
	 * Update.
	 *
	 * @param deco the deco
	 * @param decoUpdate the deco update
	 */
	private void update(DeviceComponent deco, DeviceComponent decoUpdate){
		try {
			Updates.DeviceComponent(deco, decoUpdate.getDevice(), decoUpdate.getComponent(), decoUpdate.getStatus(), decoUpdate.getDescription(), 0.0);
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage() + "with ID: " + deco.getDeCoId());
			e.printStackTrace();
		}
	}
}
