package de.bo.aid.boese.rulerTest;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.DB.util.HibernateUtil;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.model.Component;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.ruler.Checker;
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.Condition;

// TODO: Auto-generated Javadoc
/**
 * The Class NoGate.
 *
 * @author Fabio Spiekermann
 */
public class NoGate {

	/** The rule. */
	String rule;
	
	/** The c. */
	Checker c;
	
	/** The b xml. */
	BoeseXML bXML;
	
	/** The con. */
	Connector con;
	
	/** The dev. */
	Device dev;
	
	/** The comp. */
	Component comp;
	
	/** The deco1. */
	DeviceComponent deco1;
	
	/** The value1. */
	double value1;
	
	/** The valuefalse. */
	double valuefalse;
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp(){
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boeseTest", "localhost", 5432);
		HibernateUtil.setDBAuto("create");
		
		con = new Connector("test", "123", false);
		dev = new Device("test", "123");
		comp = new Component("licht", false);
		deco1 = new DeviceComponent("1", -1000.0, 1000.0, false);
		
		try{
			Inserts.defaults();
			Inserts.component(1, comp);
			Inserts.connector(con);
			Inserts.device(con.getCoId(), 1, dev);
			Inserts.deviceComponent(dev.getDeId(), comp.getCoId(), deco1);
		}
		catch(DBObjectNotFoundException e){
			fail(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}

		value1 = 11.1;
		valuefalse = 1.8;
		
		rule = "<CONDITION>"
				+ 	"<COMPONENT>"
				+ 		"<ID>" + deco1.getDeCoId() + "</ID>"
				+ 		"<VALUE>"
				+ 			"<CONSTANT>" + value1 + "</CONSTANT>"
				+ 		"</VALUE>"
				+ 		"<RESET_VALUE>10</RESET_VALUE>"
				+ 		"<COMPARATOR>==</COMPARATOR>"
				+ 	"</COMPONENT>"
				+ "</CONDITION>";
		
		c = new Checker();
		
		InputStream is = new ByteArrayInputStream(rule.getBytes());
		bXML = BoeseXML.readXML(is);
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		//(deco1 == value1)
		//Fall f = f
		insertValue(valuefalse);
		assertTrue("Fall (f = f stimmt nicht", !c.condition(((Condition)bXML).getRule()));
		
		//Fall t = t
		insertValue(value1);
		assertTrue("Fall t = t stimmt nicht", c.condition(((Condition)bXML).getRule()));		
	}
	
	/**
	 * Insert value.
	 *
	 * @param vdeco1 the vdeco1
	 */
	private void insertValue(double vdeco1){
		try {
			Inserts.value(deco1.getDeCoId(), new Date(), vdeco1);
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
}
