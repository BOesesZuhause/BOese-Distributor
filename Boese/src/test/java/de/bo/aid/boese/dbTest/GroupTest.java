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
import de.bo.aid.boese.model.Group;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupTest.
 */
public class GroupTest {

	/** The group1. */
	private Group group1; 
	
	/** The group2. */
	private Group group2;
	
	/** The group1 update. */
	private Group group1Update;
	
	/** The group2 update. */
	private Group group2Update;

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
		group1 = new Group("test1");
		group2 = new Group("test2");
		group1Update = new Group("update1");
		group2Update = new Group("update2");
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		
		//Insert Group 1
		insert(group1);
		group1Update.setGrId(group1.getGrId());
		//Insert Group 2
		insert(group2);
		group2Update.setGrId(group2.getGrId());

		//Group 1 equal Group in DB?
		equal(group1, "1");
		//Group 2 equal Group in DB?
		equal(group2, "2");
		
		//Update Group 1
		update(group1, group1Update);
		//Update Group 2
		update(group2, group2Update);
		
		//Group 1 after update equal Group in DB?
		equal(group1Update, "1Update");
		//Group 1 after update equal Group in DB?
		equal(group2Update, "2Update");
		
	}
	
	/**
	 * Insert.
	 *
	 * @param group the group
	 */
	private void insert(Group group){
		Inserts.group(group);
	}
	
	/**
	 * Select.
	 *
	 * @param id the id
	 * @return the group
	 */
	private Group select(short id){
		Group group = null;
		try {
			group = Selects.group(id);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + id);
		}
		return group;
	}
	
	/**
	 * Equal.
	 *
	 * @param group the group
	 * @param name the name
	 */
	private void equal(Group group, String name){
		Group groupTest = select(group.getGrId());
		assertTrue("Group " + name + " Name not equal", group.getName().equals(groupTest.getName()));
		assertTrue("Group " + name + " ID not equal", group.getGrId() == groupTest.getGrId());
	}
	
	/**
	 * Update.
	 *
	 * @param group the group
	 * @param groupUpdate the group update
	 */
	private void update(Group group, Group groupUpdate){
		try {
			Updates.group(group, groupUpdate.getName());
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + group.getGrId());
		}
	}
}
