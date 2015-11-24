/*
 * 
 */
package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.exceptions.DBForeignKeyNotFoundException;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.Component;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.model.RepeatRule;
import de.bo.aid.boese.model.Rule;
import de.bo.aid.boese.model.Unit;
import de.bo.aid.boese.model.Zone;

// TODO: Auto-generated Javadoc
/**
 * The Class RepeatRuleTest.
 */
public class RepeatRuleTest {

	/** The rr1. */
	private RepeatRule rr1; 
	
	/** The rr2. */
	private RepeatRule rr2;
	
	/** The rr1 update. */
	private RepeatRule rr1Update;
	
	/** The rr2 update. */
	private RepeatRule rr2Update;
	
	/** The test rule. */
	private Rule testRule;
	
	/** The test deco. */
	private DeviceComponent testDeco;
	
	/** The test con. */
	private Connector testCon;
	
	/** The test zone. */
	private Zone testZone;
	
	/** The test dev. */
	private Device testDev;
	
	/** The test unit. */
	private Unit testUnit;
	
	/** The test comp. */
	private Component testComp;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boeseTest", "localhost", "5432");
		HibernateUtil.setDBAuto("create");

		Inserts.defaults();
		testCon = new Connector("test", "test");
		Inserts.connector(testCon);
		
		testZone = new Zone("1");
		Inserts.zone(testZone, testZone);
		
		testUnit = new Unit("test", "kg");
		Inserts.unit(testUnit);
		
		testDev = new Device("dev1", "12345");
		Inserts.device(testCon.getCoId(), testZone.getZoId(), testDev);

		testComp = new Component("Taster", true);
		Inserts.component(testUnit.getUnId(), testComp);
		
		testDeco = new DeviceComponent("test", -1000.0, 1000.0);
		Inserts.deviceComponent(testDev.getDeId(), testComp.getCoId(), testDeco);
		
		testRule = new Rule("", "", "");
		List<DeviceComponent> decolist = new ArrayList<>();
		decolist.add(testDeco);
		Inserts.rule(decolist, testRule, null);
		
		rr1 = new RepeatRule("*;*;*;*;*;*", new BigDecimal(10.0), 1);
		rr2 = new RepeatRule("*;*;*;*;*;*", new BigDecimal(20.0), 2);
		rr1Update = new RepeatRule("1;1;1;1;2070;ttttttt", new BigDecimal(15.0), 3);
		rr2Update = new RepeatRule("1;2;3;4;2075;fffffff", new BigDecimal(25.0), 4);
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		
		//Insert RepeatRule 1
		insert(rr1);
		rr1Update.setRrId(rr1.getRrId());
		rr1Update.setRule(testRule);
		rr1Update.setDeviceComponent(testDeco);
		//Insert RepeatRule 2
		insert(rr2);
		rr2Update.setRrId(rr2.getRrId());
		rr2Update.setRule(testRule);
		rr2Update.setDeviceComponent(testDeco);

		//RepeatRule 1 equal RepeatRule in DB?
		equal(rr1, "1");
		//RepeatRule 2 equal RepeatRule in DB?
		equal(rr2, "2");
		
		//Update RepeatRule 1
		update(rr1, rr1Update);
		//Update RepeatRule 2
		update(rr2, rr2Update);
		
		//RepeatRule 1 after update equal RepeatRule in DB?
		equal(rr1Update, "1Update");
		//RepeatRule 1 after update equal RepeatRule in DB?
		equal(rr2Update, "2Update");
		
	}
	
	/**
	 * Insert.
	 *
	 * @param rr the rr
	 */
	private void insert(RepeatRule rr){
		try {
			Inserts.repeatRule(rr, testRule.getRuId(), testDeco.getDeCoId(), null);
		} catch (DBForeignKeyNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	/**
	 * Select.
	 *
	 * @param id the id
	 * @return the repeat rule
	 */
	private RepeatRule select(int id){
		RepeatRule rr = null;
		try {
			rr = Selects.repeatRule(id);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + id);
		}
		return rr;
	}
	
	/**
	 * Equal.
	 *
	 * @param rr the rr
	 * @param name the name
	 */
	private void equal(RepeatRule rr, String name){
		RepeatRule rrTest = select(rr.getRrId());
		assertTrue("RepeatRule " + name + " Cron not equal", rr.getRepeat().equals(rrTest.getRepeat()));
		assertTrue("RepeatRule " + name + " Value not equal", rr.getValue().equals(rrTest.getValue()));
		assertTrue("RepeatRule " + name + " RepeatsAfterEnd not equal", rr.getRepeatsAfterEnd() == rrTest.getRepeatsAfterEnd());
		assertTrue("RepeatRule " + name + " Rule not equal", rr.getRule().getRuId() == rrTest.getRule().getRuId());
		assertTrue("RepeatRule " + name + " DeviceComponent not equal", rr.getDeviceComponent().getDeCoId() == rrTest.getDeviceComponent().getDeCoId());
		assertTrue("RepeatRule " + name + " ID not equal", rr.getRrId() == rrTest.getRrId());
	}
	
	/**
	 * Update.
	 *
	 * @param rr the rr
	 * @param rrUpdate the rr update
	 */
	private void update(RepeatRule rr, RepeatRule rrUpdate){
		try {
			Updates.repeatRule(rr, rrUpdate.getRepeat(), rrUpdate.getValue().doubleValue(), rrUpdate.getRepeatsAfterEnd(), null);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + rr.getRrId());
		}
	}
}
