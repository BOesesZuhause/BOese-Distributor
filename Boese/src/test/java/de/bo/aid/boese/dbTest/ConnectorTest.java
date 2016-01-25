/*
 * 
 */
package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.constants.Status;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.Connector;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectorTest.
 */
public class ConnectorTest {

	/** The con1. */
	private Connector con1;
	
	/** The con2. */
	private Connector con2;
	
	/** The con1 update. */
	private Connector con1Update;
	
	/** The con2 update. */
	private Connector con2Update;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
        HibernateUtil.setDBURL("boeseTest", "localhost", 5432);
		HibernateUtil.setDBAuto("create");

		Inserts.defaults();
		con1 = new Connector("first", "123", true);
		con2 = new Connector("second", "456", false);
		con1Update = new Connector("firstup", "123up", false);
		con1Update.setStatus(Status.INACTIVE);
		con2Update = new Connector("secondup", "456up", true);
		con2Update.setStatus(Status.INACTIVE);
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		
		//Insert Connector 1
		insert(con1);
		con1Update.setCoId(con1.getCoId());
		//Insert Connector 2
		insert(con2);
		con2Update.setCoId(con2.getCoId());

		//Connector 1 equal Connector in DB?
		equal(con1, "1");
		//Connector 2 equal Connector in DB?
		equal(con2, "2");
		
		//Update Connector 1
		update(con1, con1Update);
		//Update Connector 2
		update(con2, con2Update);
		
		//Connector 1 after update equal Connector in DB?
		equal(con1Update, "1Update");
		//Connector 1 after update equal Connector in DB?
		equal(con2Update, "2Update");
		
	}
	
	/**
	 * Insert.
	 *
	 * @param con the con
	 */
	private void insert(Connector con){
		Inserts.connector(con);
	}
	
	/**
	 * Select.
	 *
	 * @param id the id
	 * @return the connector
	 */
	private Connector select(int id){
		Connector con = null;
		try {
			con = Selects.connector(id);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + id);
		}
		return con;
	}
	
	/**
	 * Equal.
	 *
	 * @param con the con
	 * @param name the name
	 */
	private void equal(Connector con, String name){
		Connector conTest = select(con.getCoId());
		assertTrue("Connector " + name + " Name not equal", con.getName().equals(conTest.getName()));
		assertTrue("Connector " + name + " Password not equal", con.getPassword().equals(conTest.getPassword()));
		assertTrue("Connector " + name + " Status not equal", con.getStatus() == conTest.getStatus());
		assertTrue("Connector " + name + " UserConnector not equal", con.isUserConnector() == conTest.isUserConnector());
		assertTrue("Connector " + name + " ID not equal", con.getCoId() == conTest.getCoId());
	}
	
	/**
	 * Update.
	 *
	 * @param con the con
	 * @param conUpdate the con update
	 */
	private void update(Connector con, Connector conUpdate){
		try {
			Updates.connector(con, conUpdate.getName(), conUpdate.getPassword(), conUpdate.getStatus(),conUpdate.isUserConnector());
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + con.getCoId());
		}
	}

}
