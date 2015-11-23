package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.Group;
import de.bo.aid.boese.model.Rule;
import de.bo.aid.boese.model.Service;
import de.bo.aid.boese.model.Unit;
import de.bo.aid.boese.model.User;
import de.bo.aid.boese.model.Zone;

// TODO: Auto-generated Javadoc
/**
 * The Class DBDefaultTest.
 */
public class DBDefaultTest {

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp(){
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boeseTest", "localhost", "5432");
		HibernateUtil.setDBAuto("create");
		
		Inserts.defaults();
	}

	/**
	 * Service.
	 */
	@Test
	public void service() {
		try {
			Service service = Selects.service(0);
			assertTrue("Service ID not correct", service.getSeId() == 0);
			assertTrue("Service Description not correct", service.getDescription().equals("Default"));
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Unit.
	 */
	@Test
	public void unit(){
		try {
			Unit unit = Selects.unit(0);
			assertTrue("Unit ID not correct", unit.getUnId() == 0);
			assertTrue("Unit Name not correct", unit.getName().equals("Undefined"));
			assertTrue("Unit Symbol not correct", unit.getSymbol().equals("ud"));
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Zone.
	 */
	@Test
	public void zone(){
		try {
			Zone zone = Selects.zone(0);
			assertTrue("Zone ID not correct", zone.getZoId() == 0);
			assertTrue("Zone Name not correct", zone.getName().equals("Global"));
			System.out.println("ID: " + zone.getZoId() + ", Name: " + zone.getName());
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * User.
	 */
	@Test
	public void user(){
		try {
			User user = Selects.user(0);
			assertTrue("User ID not correct", user.getUsId() == 0);
			assertTrue("User Firstname not correct", user.getFirstName().equals("Super"));
			assertTrue("User Surname not correct", user.getSurname().equals("User"));
			assertTrue("User Password not correct", user.getPassword().equals("MasterPassword")); //TODO Masterpw anpassen fall nötig
			assertTrue("User Username not correct", user.getUserName().equals("root"));
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}		
	}
	
	/**
	 * Group.
	 */
	@Test
	public void group(){
		try {
			Group grp = Selects.group((short)0);
			assertTrue("Group ID not correct", grp.getGrId() == 0);
			assertTrue("Group Name not correct", grp.getName().equals("Users"));
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Rule.
	 */
	@Test
	public void rule(){
		try {
			Rule rule = Selects.rule(0);
			assertTrue("Rule ID not correct", rule.getRuId() == 0);
			assertTrue("Rule Actions not correct", rule.getActions().equals("<ACTION></ACTION>"));
			assertTrue("Rule Conditions not correct", rule.getConditions().equals("<CONDITION></CONDITION>"));
			assertTrue("Rule Permissions not correct", rule.getPermissions().equals("<PERMISSION></PERMISSION>"));
		} catch (DBObjectNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
