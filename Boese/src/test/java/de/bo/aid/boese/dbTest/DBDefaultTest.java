package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.bo.aid.boese.DB.util.DBDefaults;
import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.dao.GroupDAO;
import de.bo.aid.boese.dao.RuleDAO;
import de.bo.aid.boese.dao.ServiceDAO;
import de.bo.aid.boese.dao.UnitDAO;
import de.bo.aid.boese.dao.UserDAO;
import de.bo.aid.boese.dao.ZoneDAO;
import de.bo.aid.boese.modelJPA.Group;
import de.bo.aid.boese.modelJPA.Rule;
import de.bo.aid.boese.modelJPA.Service;
import de.bo.aid.boese.modelJPA.Unit;
import de.bo.aid.boese.modelJPA.User;
import de.bo.aid.boese.modelJPA.Zone;

// TODO: Auto-generated Javadoc
/**
 * The Class DBDefaultTest.
 */
public class DBDefaultTest {
	
	static DAOHandler daoHandler;
	static EntityManager em;
	
	@BeforeClass
	public static void setUpBeforClass(){
		JPAUtil.setDBUser("postgres");
		JPAUtil.setDBPassword("Di0bPWfw");
		JPAUtil.setDBURL("boeseTest", "localhost", 5432);
		JPAUtil.init();
		DBDefaults dbdef = new DBDefaults();
		dbdef.defaults();
		daoHandler = DAOHandler.getInstance();
		em = JPAUtil.getEntityManager();
	}

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp(){
		
	}

	/**
	 * Service.
	 */
	@Test
	public void service() {
		ServiceDAO dao = daoHandler.getSer();
		Service service = dao.get(em, 1);
		if(service == null){
			fail("DefaultService nicht erstellt");
		}
		else{
			assertTrue("Service ID not correct", service.getSeId() == 1);
			assertTrue("Service Description not correct", service.getDescription().equals("Default"));
		} 
	}
	
	/**
	 * Unit.
	 */
	@Test
	public void unit(){
		UnitDAO dao = daoHandler.getUnit();
		Unit unit = dao.get(em, 1);
		if(unit == null){
			fail("DefaultUnit nicht erstellt");
		}
		else{
			assertTrue("Unit ID not correct", unit.getUnId() == 1);
			assertTrue("Unit Name not correct", unit.getName().equals("Undefined"));
			assertTrue("Unit Symbol not correct", unit.getSymbol().equals("ud"));
		} 
	}
	
	/**
	 * Zone.
	 */
	@Test
	public void zone(){
		ZoneDAO dao = daoHandler.getZone();
		Zone zone = dao.get(em, 1);
		if(zone == null){
			fail("DefaultZone nicht erstellt");
		}
		else{
			assertTrue("Zone ID not correct", zone.getZoId() == 1);
			assertTrue("Zone Name not correct", zone.getName().equals("Global"));
		} 
	}
	
	/**
	 * User.
	 */
	@Test
	public void user(){
		UserDAO dao = daoHandler.getUser();
		User user = dao.get(em, 1);
		if(user == null){
			fail("DefaultUser nicht erstellt");
		}
		else{
			assertTrue("User ID not correct", user.getUsId() == 1);
			assertTrue("User Firstname not correct", user.getFirstName().equals("Super"));
			assertTrue("User Surname not correct", user.getSurname().equals("User"));
			assertTrue("User Password not correct", user.getPassword().equals("MasterPassword")); //TODO Masterpw anpassen fall nötig
			assertTrue("User Username not correct", user.getUserName().equals("root"));
		} 	
	}
	
	/**
	 * Group.
	 */
	@Test
	public void group(){
		GroupDAO dao = daoHandler.getGrp();
		Group grp = dao.get(em, 1);
		if(grp == null){
			fail("DefaultGroup nicht erstellt");
		}
		else{
			assertTrue("Group ID not correct", grp.getGrId() == 1);
			assertTrue("Group Name not correct", grp.getName().equals("Users"));
		} 
	}
	
	/**
	 * Rule.
	 */
	@Test
	public void rule(){
		RuleDAO dao = daoHandler.getRu();
		Rule rule = dao.get(em, 1);
		if(rule == null){
			fail("DefaultRule nicht erstellt");
		}
		else{
			assertTrue("Rule ID not correct", rule.getRuId() == 1);
			assertTrue("Rule Actions not correct", rule.getActions().equals("<ACTION></ACTION>"));
			assertTrue("Rule Conditions not correct", rule.getConditions().equals("<CONDITION></CONDITION>"));
			assertTrue("Rule Permissions not correct", rule.getPermissions().equals("<PERMISSION></PERMISSION>"));
		} 
	}

}
