package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import de.bo.aid.boese.model.Rule;
import de.bo.aid.boese.model.Unit;
import de.bo.aid.boese.model.Zone;

public class RuleTest {

	private Rule rule1; 
	private Rule rule2;
	private Rule rule1Update;
	private Rule rule2Update;
	
	private RepeatRule testRr;
	private Rule testRule;
	private DeviceComponent testDeco;
	private Connector testCon;
	private Zone testZone;
	private Device testDev;
	private Unit testUnit;
	private Component testComp;
	private List<DeviceComponent> decolist;

	@Before
	public void setUp() throws Exception {
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boese", "localhost", "5432");
		
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
		
		testDeco = new DeviceComponent("test");
		Inserts.deviceComponent(testDev.getDeId(), testComp.getCoId(), testDeco);
		
		decolist = new ArrayList<>();
		decolist.add(testDeco);
		
		rule1 = new Rule("", "", "");
		rule2 = new Rule("<PERMISSION></PERMISSION>", "<CONDITION></CONDITION>", "<ACTION></ACTION>");
		rule1Update = new Rule("<PERMISSION></PERMISSION>", "<CONDITION></CONDITION>", "<ACTION></ACTION>");
		rule2Update = new Rule("", "", "");
	}

	@Test
	public void test() {
		
		//Insert Rule 1
		insert(rule1);
		rule1Update.setRuId(rule1.getRuId());
		rule1Update.setActive(false);
		//Insert Rule 2
		insert(rule2);
		rule2Update.setRuId(rule2.getRuId());
		rule2Update.setActive(false);

		//Rule 1 equal Rule in DB?
		equal(rule1, "1");
		//Rule 2 equal Rule in DB?
		equal(rule2, "2");
		
		//Update Rule 1
		update(rule1, rule1Update);
		//Update Rule 2
		update(rule2, rule2Update);
		
		//Rule 1 after update equal Rule in DB?
		equal(rule1Update, "1Update");
		//Rule 1 after update equal Rule in DB?
		equal(rule2Update, "2Update");
		
	}
	
	private void insert(Rule rule){
		try {
			Inserts.rule(decolist, rule, null);
		} catch (DBForeignKeyNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	private Rule select(int id){
		Rule rule = null;
		try {
			rule = Selects.rule(id);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + id);
		}
		return rule;
	}
	
	private void equal(Rule rule, String name){
		Rule ruleTest = select(rule.getRuId());
		assertTrue("Rule " + name + " Active not equal", rule.getActive() == ruleTest.getActive());
		assertTrue("Rule " + name + " Actions not equal", rule.getActions().equals(ruleTest.getActions()));
		assertTrue("Rule " + name + " Permissions not equal", rule.getPermissions().equals(ruleTest.getPermissions()));
		assertTrue("Rule " + name + " Conditions not equal", rule.getConditions().equals(ruleTest.getConditions()));
		assertTrue("Rule " + name + " ID not equal", rule.getRuId() == ruleTest.getRuId());
	}
	
	private void update(Rule rule, Rule ruleUpdate){
		try {
			Updates.ruleForTest(rule, ruleUpdate.getActive(), ruleUpdate.getPermissions(), ruleUpdate.getConditions(), ruleUpdate.getActions());
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + rule.getRuId());
		}
	}
}
