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
		HibernateUtil.setDBURL("boese", "localhost", "5432");
		con1 = new Connector();
		con1.setName("first");
		con1.setPassword("123");
		con2 = new Connector();
		con2.setName("second");
		con2.setPassword("456");
		con1Update = new Connector();
		con1Update.setName("firstup");
		con1Update.setPassword("123up");
		con1Update.setStatus(Status.INACTIVE);
		con2Update = new Connector();
		con2Update.setName("secondup");
		con2Update.setPassword("456up");
		con2Update.setStatus(Status.INACTIVE);
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		
		//Insert Connector 1
		con1.setCoId(insert(con1));
		con1Update.setCoId(con1.getCoId());
		//Insert Connector 2
		con2.setCoId(insert(con2));
		con2Update.setCoId(con2.getCoId());

		//Connector 1 equal Connector in DB?
		equal(con1, "1");
		//Connector 2 equal Connector in DB?
		equal(con2, "2");
		
		//Update Connector 1
		update(con1.getCoId(), con1Update);
		//Update Connector 2
		update(con2.getCoId(), con2Update);
		
		//Connector 1 after update equal Connector in DB?
		equal(con1Update, "1Update");
		//Connector 1 after update equal Connector in DB?
		equal(con2Update, "2Update");
		
	}
	
	/**
	 * Insert.
	 *
	 * @param con the con
	 * @return the int
	 */
	private int insert(Connector con){
		return Inserts.connector(con.getName(), con.getPassword());
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
		assertTrue("Connector " + name + " ID not equal", con.getCoId() == conTest.getCoId());
	}
	
	/**
	 * Update.
	 *
	 * @param id the id
	 * @param conUpdate the con update
	 */
	private void update(int id, Connector conUpdate){
		try {
			Updates.connector(id, conUpdate.getName(), conUpdate.getPassword(), conUpdate.getStatus());
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + id);
		}
	}

}
