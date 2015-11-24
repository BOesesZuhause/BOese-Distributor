package de.bo.aid.boese.rulerTest;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
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
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.Condition;

public class GatesTest {
	
	String rule;
	
	Checker c;
	
	BoeseXML bXML;
	
	Connector con;
	Device dev;
	Component comp;
	DeviceComponent deco1;
	DeviceComponent deco2;
	DeviceComponent deco3;
	DeviceComponent deco4;
	
	double value1;
	double value2;
	double valuefalse;
	
	@Before
	public void setUp(){
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boeseTest", "localhost", "5432");
		HibernateUtil.setDBAuto("create");
		
		con = new Connector("test", "123", false);
		dev = new Device("test", "123");
		comp = new Component("licht", false);
		deco1 = new DeviceComponent("1", -1000.0, 1000.0, false);
		deco2 = new DeviceComponent("2", -1000.0, 1000.0, false);
		deco3 = new DeviceComponent("3", -1000.0, 1000.0, false);
		deco4 = new DeviceComponent("4", -1000.0, 1000.0, false);
		
		try{
			Inserts.defaults();
			Inserts.component(1, comp);
			Inserts.connector(con);
			Inserts.device(con.getCoId(), 1, dev);
			Inserts.deviceComponent(dev.getDeId(), comp.getCoId(), deco1);
			Inserts.deviceComponent(dev.getDeId(), comp.getCoId(), deco2);
			Inserts.deviceComponent(dev.getDeId(), comp.getCoId(), deco3);
			Inserts.deviceComponent(dev.getDeId(), comp.getCoId(), deco4);
		}
		catch(DBObjectNotFoundException e){
			fail(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}

		value1 = 11.1;
		value2 = 21.1;
		valuefalse = 1;
		
		rule = "<CONDITION>"
				+ "<OR>"
				+ 	"<AND>"
				+ 		"<COMPONENT>"
				+ 			"<ID>" + deco1.getDeCoId() + "</ID>"
				+ 			"<VALUE>" + value1 + "</VALUE>"
				+ 			"<RESET_VALUE>10</RESET_VALUE>"
//				+ 			"<START_TIME>*;*;*;*;*;*</START_TIME>"
				+ 			"<DURATION>15</DURATION>"
				+ 			"<COMPERATOR>==</COMPERATOR>"
				+ 		"</COMPONENT>"
				+ 		"<COMPONENT>"
				+ 			"<ID>" + deco2.getDeCoId() + "</ID>"
				+ 			"<VALUE>" + value2 + "</VALUE>"
				+ 			"<RESET_VALUE>20</RESET_VALUE>"
//				+ 			"<START_TIME>*;*;*;*;*;*</START_TIME>"
				+ 			"<DURATION>25</DURATION>"
				+ 			"<COMPERATOR>!=</COMPERATOR>"
				+ 		"</COMPONENT>"
				+ 	"</AND>"
				+ 	"<AND>"
				+ 		"<COMPONENT>"
				+ 			"<ID>" + deco3.getDeCoId() + "</ID>"
				+ 			"<VALUE>" + value1 + "</VALUE>"
				+ 			"<RESET_VALUE>10</RESET_VALUE>"
//				+ 			"<START_TIME>*;*;*;*;*;*</START_TIME>"
				+ 			"<DURATION>15</DURATION>"
				+ 			"<COMPERATOR>!=</COMPERATOR>"
				+ 		"</COMPONENT>"
				+ 		"<COMPONENT>"
				+ 			"<ID>" + deco4.getDeCoId() + "</ID>"
				+ 			"<VALUE>" + value2 + "</VALUE>"
				+ 			"<RESET_VALUE>20</RESET_VALUE>"
//				+ 			"<START_TIME>*;*;*;*;*;*</START_TIME>"
				+ 			"<DURATION>25</DURATION>"
				+ 			"<COMPERATOR>==</COMPERATOR>"
				+ 		"</COMPONENT>"
				+ 	"</AND>"
				+ "</OR>"
				+ "</CONDITION>";
		
		c = new Checker();
		
		InputStream is = new ByteArrayInputStream(rule.getBytes());
		bXML = BoeseXML.readXML(is);
	}

	@Test
	public void test() {
		//Fall (f ^ f) v (f ^ f) = f
		try {
			Inserts.value(deco1.getDeCoId(), new Date(), valuefalse);
			Inserts.value(deco2.getDeCoId(), new Date(), value2);
			Inserts.value(deco3.getDeCoId(), new Date(), value1);
			Inserts.value(deco4.getDeCoId(), new Date(), valuefalse);
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
//TODO Bad XML: No Calculationtype
		assertTrue("Fall (f ^ f) v (f ^ f) = f stimmt nicht", !c.condition(((Condition)bXML).getRule()));
	}

}
