package de.bo.aid.boese.rulerTest;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.Component;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.ruler.Checker;
import de.bo.aid.boese.xml.CalculationList;
import de.bo.aid.boese.xml.ComponentXML;
import de.bo.aid.boese.xml.ComponentXML.Comperator;

/**
 * @author Fabio Spiekermann
 *
 */
public class ConditionComponentTest {
	
	/** The c. */
	Checker c;
	
	/** The cXML */
	ComponentXML cXML;
	
	/** The cList */
	CalculationList cList;
	
	/** The con. */
	Connector con;
	
	/** The dev. */
	Device dev;
	
	/** The comp. */
	Component comp;
	
	/** The deco1. */
	DeviceComponent deco;
	
	/** The value1. */
	double valuetrue;

	/** The valuefalse. */
	double valuefalse;
	
	/** The value. */
	double value;

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boeseTest", "localhost", "5432");
		HibernateUtil.setDBAuto("create");
		
		con = new Connector("test", "123", false);
		dev = new Device("test", "123");
		comp = new Component("licht", false);
		deco = new DeviceComponent("1", -1000.0, 1000.0, false);
		
		try{
			Inserts.defaults();
			Inserts.component(1, comp);
			Inserts.connector(con);
			Inserts.device(con.getCoId(), 1, dev);
			Inserts.deviceComponent(dev.getDeId(), comp.getCoId(), deco);
		}
		catch(DBObjectNotFoundException e){
			fail(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		value = 123.4;
		c = new Checker();
		cList = new CalculationList();
		cList.addConstant(value);
		cXML = new ComponentXML(deco.getDeCoId(), cList, 0, null, 5l, 0);
	}

	/**
	 * Equal.
	 */
	@Test
	public void equal() {
		cXML.setValue(Comperator.EQUAL);
		//true
		valuetrue = 123.4;
		insert(valuetrue);
		assertTrue("True Equal stimmt nicht", c.condition(cXML));
		//false
		valuefalse = 122;
		insert(valuefalse);
		assertTrue("False Equal stimmt nicht", !c.condition(cXML));
	}

	/**
	 * NotEqual.
	 */
	@Test
	public void notEqual() {
		cXML.setValue(Comperator.NOTEQUAL);
		//true
		valuetrue = 123;
		insert(valuetrue);
		assertTrue("True NotEqual stimmt nicht", c.condition(cXML));
		//false
		valuefalse = 123.4;
		insert(valuefalse);
		assertTrue("False NotEqual stimmt nicht", !c.condition(cXML));
	}

	/**
	 * GreaterEqual.
	 */
	@Test
	public void greaterEqual() {
		cXML.setValue(Comperator.GREATEREQUAL);
		//true greater
		valuetrue = 123.5;
		insert(valuetrue);
		assertTrue("True GreaterEqual(greater) stimmt nicht", c.condition(cXML));
		//true equal
		valuetrue = 123.4;
		insert(valuetrue);
		assertTrue("True GreaterEqual(equal) stimmt nicht", c.condition(cXML));
		//false
		valuefalse = 123.3;
		insert(valuefalse);
		assertTrue("False GreaterEqual stimmt nicht", !c.condition(cXML));
	}

	/**
	 * GreaterThen.
	 */
	@Test
	public void greaterThen() {
		cXML.setValue(Comperator.GREATERTHEN);
		//true
		valuetrue = 123.5;
		insert(valuetrue);
		assertTrue("True GreaterThen stimmt nicht", c.condition(cXML));
		//true equal
		valuetrue = 123.4;
		insert(valuetrue);
		assertTrue("False GreaterEqual(equal) stimmt nicht", !c.condition(cXML));
		//false lower
		valuefalse = 123.3;
		insert(valuefalse);
		assertTrue("False GreaterThen(Lower) stimmt nicht", !c.condition(cXML));
	}

	/**
	 * LowerEqual.
	 */
	@Test
	public void lowerEqual() {
		cXML.setValue(Comperator.LOWEREQUAL);
		//true lower
		valuetrue = 123.3;
		insert(valuetrue);
		assertTrue("True LowerEqual(lower) stimmt nicht", c.condition(cXML));
		//true equal
		valuetrue = 123.4;
		insert(valuetrue);
		assertTrue("True LowerEqual(equal) stimmt nicht", c.condition(cXML));
		//false
		valuefalse = 123.5;
		insert(valuefalse);
		assertTrue("False LowerEqual stimmt nicht", !c.condition(cXML));
	}

	/**
	 * LowerThen.
	 */
	@Test
	public void lowerThen() {
		cXML.setValue(Comperator.LOWERTHEN);
		//true
		valuetrue = 123.3;
		insert(valuetrue);
		assertTrue("True LowerThen stimmt nicht", c.condition(cXML));
		//true equal
		valuetrue = 123.4;
		insert(valuetrue);
		assertTrue("False Lower(equal) stimmt nicht", !c.condition(cXML));
		//false greater
		valuefalse = 123.5;
		insert(valuefalse);
		assertTrue("False LowerThen(Greater) stimmt nicht", !c.condition(cXML));
	}
	
	private void insert(double value){
		try {
			Inserts.value(deco.getDeCoId(), new Date(), value);
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
