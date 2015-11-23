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
import de.bo.aid.boese.model.Component;
import de.bo.aid.boese.model.Unit;

// TODO: Auto-generated Javadoc
/**
 * The Class ComponentTest.
 */
public class ComponentTest {

	/** The comp1. */
	private Component comp1;
	
	/** The comp2. */
	private Component comp2;
	
	/** The comp1 update. */
	private Component comp1Update;
	
	/** The comp2 update. */
	private Component comp2Update;
	
	/** The test unit. */
	private Unit testUnit;
	
	/** The test unit update. */
	private Unit testUnitUpdate;

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
		
		testUnit = new Unit("test", "test");
		Inserts.unit(testUnit);
		
		testUnitUpdate = new Unit("update", "update");
		Inserts.unit(testUnitUpdate);
		
		comp1 = new Component("Schalter", true);
		comp1.setUnit(testUnit);
		
		comp2 = new Component("LichtSensor", false);
		comp2.setUnit(testUnit);
		
		comp1Update = new Component("TempSensor", false);
		comp1Update.setUnit(testUnitUpdate);
		
		comp2Update = new Component("Taster", true);
		comp2Update.setUnit(testUnitUpdate);
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		
		//Insert Component 1
		insert(comp1);
		comp1Update.setCoId(comp1.getCoId());
		//Insert Component 2
		insert(comp2);
		comp2Update.setCoId(comp2.getCoId());

		//Component 1 equal Unit in DB?
		equal(comp1, "1");
		//Component 2 equal Unit in DB?
		equal(comp2, "2");
		
		//Update Component 1
		update(comp1, comp1Update);
		//Update Component 2
		update(comp2, comp2Update);
		
		//Component 1 after update equal Component in DB?
		equal(comp1Update, "1Update");
		//Component 1 after update equal Component in DB?
		equal(comp2Update, "2Update");
	}

	/**
	 * Insert.
	 *
	 * @param comp the comp
	 */
	private void insert(Component comp){
		try {
			Inserts.component(comp.getUnit().getUnId(), comp);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	/**
	 * Select.
	 *
	 * @param id the id
	 * @return the component
	 */
	private Component select(int id){
		Component comp = null;
		try {
			comp = Selects.component(id);
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage() + " with ID: " + id);
			e.printStackTrace();
		}
		return comp;
	}
	
	/**
	 * Equal.
	 *
	 * @param comp the comp
	 * @param name the name
	 */
	private void equal(Component comp, String name){
		Component compTest = select(comp.getCoId());
		assertTrue("Component " + name + " Name not equal", comp.getName().equals(compTest.getName()));
		assertTrue("Component " + name + " Unit not equal", comp.getUnit().getUnId() == compTest.getUnit().getUnId());
		assertTrue("Component " + name + " ID not equal", comp.getCoId() == compTest.getCoId());
	}
	
	/**
	 * Update.
	 *
	 * @param comp the comp
	 * @param compUpdate the comp update
	 */
	private void update(Component comp, Component compUpdate){
		try {
			Updates.component(comp, compUpdate.getUnit(), compUpdate.getName(), compUpdate.isActor());
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage() + "with ID: " + comp.getCoId());
			e.printStackTrace();
		}
	}

}
