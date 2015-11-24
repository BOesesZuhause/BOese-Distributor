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
import de.bo.aid.boese.model.Zone;

// TODO: Auto-generated Javadoc
/**
 * The Class ZoneTest.
 */
public class ZoneTest {

	/** The zone1. */
	private Zone zone1; 
	
	/** The zone2. */
	private Zone zone2;
	
	/** The zone1 update. */
	private Zone zone1Update;
	
	/** The zone2 update. */
	private Zone zone2Update;

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
		zone1 = new Zone("test1");
		zone2 = new Zone("test2");
		zone1Update = new Zone("update1");
		zone2Update = new Zone("update2");
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		
		//Insert Zone 1
		insert(zone1, zone1);
		zone1Update.setZoId(zone1.getZoId());
		zone1Update.setZone(zone1Update);
		//Insert Zone 2
		insert(zone2, zone1);
		zone2Update.setZoId(zone2.getZoId());
		zone2Update.setZone(zone2Update);

		//Zone 1 equal Zone in DB?
		equal(zone1, "1");
		//Zone 2 equal Zone in DB?
		equal(zone2, "2");
		
		//Update Zone 1
		update(zone1, zone1Update);
		//Update Zone 2
		update(zone2, zone2Update);
		
		//Zone 1 after update equal Zone in DB?
		equal(zone1Update, "1Update");
		//Zone 1 after update equal Zone in DB?
		equal(zone2Update, "2Update");
		
	}
	
	/**
	 * Insert.
	 *
	 * @param zone the zone
	 * @param suzone the suzone
	 */
	private void insert(Zone zone, Zone suzone){
		Inserts.zone(zone, suzone);
	}
	
	/**
	 * Select.
	 *
	 * @param id the id
	 * @return the zone
	 */
	private Zone select(int id){
		Zone zone = null;
		try {
			zone = Selects.zone(id);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + id);
		}
		return zone;
	}
	
	/**
	 * Equal.
	 *
	 * @param zone the zone
	 * @param name the name
	 */
	private void equal(Zone zone, String name){
		Zone zoneTest = select(zone.getZoId());
		assertTrue("Zone " + name + " Name not equal", zone.getName().equals(zoneTest.getName()));
		assertTrue("Zone " + name + " SubZone not equal", zone.getZone().getZoId() == zoneTest.getZone().getZoId());
		assertTrue("Zone " + name + " ID not equal", zone.getZoId() == zoneTest.getZoId());
	}
	
	/**
	 * Update.
	 *
	 * @param zone the zone
	 * @param zoneUpdate the zone update
	 */
	private void update(Zone zone, Zone zoneUpdate){
		try {
			Updates.zone(zone, zoneUpdate.getName(), zone);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + zone.getZoId());
		}
	}
}
