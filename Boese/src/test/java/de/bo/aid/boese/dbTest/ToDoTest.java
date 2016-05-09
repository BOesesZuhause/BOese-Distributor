/*
 * 
 */
package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.DB.util.HibernateUtil;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.exceptions.DBForeignKeyNotFoundException;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.model.Component;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.model.RepeatRule;
import de.bo.aid.boese.model.Rule;
import de.bo.aid.boese.model.ToDo;
import de.bo.aid.boese.model.Unit;
import de.bo.aid.boese.model.Zone;

// TODO: Auto-generated Javadoc
/**
 * The Class ToDoTest.
 */
public class ToDoTest {

	/** The todo1. */
	private ToDo todo1; 
	
	/** The todo2. */
	private ToDo todo2;
	
	/** The todo1 update. */
	private ToDo todo1Update;
	
	/** The todo2 update. */
	private ToDo todo2Update;

	/** The test rr1. */
	private RepeatRule testRr1;
	
	/** The test rr2. */
	private RepeatRule testRr2;
	
	/** The test rule. */
	private Rule testRule;
	
	/** The test deco. */
	private DeviceComponent testDeco;
	
	/** The test con. */
	private Connector testCon;
	
	/** The test zone. */
	private Zone testZone;
	
	/** The test dev. */
	private Device testDev;
	
	/** The test unit. */
	private Unit testUnit;
	
	/** The test comp. */
	private Component testComp;

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
		testCon = new Connector("test", "test", false);
		Inserts.connector(testCon);
		
		testZone = new Zone("1");
		Inserts.zone(testZone, testZone);
		
		testUnit = new Unit("test", "kg");
		Inserts.unit(testUnit);
		
		testDev = new Device("dev1", "12345");
		Inserts.device(testCon.getCoId(), testZone.getZoId(), testDev);

		testComp = new Component("Taster", true);
		Inserts.component(testUnit.getUnId(), testComp);
		
		testDeco = new DeviceComponent("test", -1000.0, 1000.0, false);
		Inserts.deviceComponent(testDev.getDeId(), testComp.getCoId(), testDeco);
		
		testRule = new Rule("", "", "");
		List<DeviceComponent> decolist = new ArrayList<>();
		decolist.add(testDeco);
		Inserts.rule(decolist, testRule, null);
		
		testRr1 = new RepeatRule("*;*;*;*;*;*", new BigDecimal(10.0), 1, testRule, testDeco);
		Inserts.repeatRule(testRr1, null);
		
		testRr2 = new RepeatRule("*;*;*;*;*;*", new BigDecimal(20.0), 1, testRule, testDeco);
		Inserts.repeatRule(testRr2, null);
		
		todo1 = new ToDo(new Date());
		todo2 = new ToDo(new Date());
		todo1Update = new ToDo(new Date());
		todo2Update = new ToDo(new Date());
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		//Insert ToDo 1
		insert(todo1, testRr1.getRrId());
		todo1Update.setToDoId(todo1.getToDoId());
		todo1Update.setRepeatRule(testRr1);
		//Insert ToDo 2
		insert(todo2, testRr2.getRrId());
		todo2Update.setToDoId(todo2.getToDoId());
		todo2Update.setRepeatRule(testRr2);

		//ToDo 1 equal ToDo in DB?
		equal(todo1, "1");
		//ToDo 2 equal ToDo in DB?
		equal(todo2, "2");
		
		//Update ToDo 1
		update(todo1, todo1Update);
		//Update ToDo 2
		update(todo2, todo2Update);
		
		//ToDo 1 after update equal ToDo in DB?
		equal(todo1Update, "1Update");
		//ToDo 1 after update equal ToDo in DB?
		equal(todo2Update, "2Update");
		
	}
	
	/**
	 * Insert.
	 *
	 * @param todo the todo
	 * @param rrId the rr id
	 */
	private void insert(ToDo todo, int rrId){
		try {
			Inserts.toDoWithoutChange(todo, rrId);
		} catch (DBForeignKeyNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	/**
	 * Select.
	 *
	 * @param id the id
	 * @return the to do
	 */
	private ToDo select(int id){
		ToDo todo = null;
		try {
			todo = Selects.toDo(id);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + id);
		}
		return todo;
	}
	
	/**
	 * Equal.
	 *
	 * @param todo the todo
	 * @param name the name
	 */
	private void equal(ToDo todo, String name){
		ToDo todoTest = select(todo.getToDoId());
//		assertTrue("ToDo " + name + " Name not equal", todo.getDate().equals(todoTest.getDate()));
		assertTrue("ToDo " + name + " RepeatRule not equal", todo.getRepeatRule().getRrId() == todoTest.getRepeatRule().getRrId());
		assertTrue("ToDo " + name + " ID not equal", todo.getToDoId() == todoTest.getToDoId());
	}
	
	/**
	 * Update.
	 *
	 * @param todo the todo
	 * @param todoUpdate the todo update
	 */
	private void update(ToDo todo, ToDo todoUpdate){
		try {
			Updates.toDo(todo, todoUpdate.getDate(), true, null);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + todo.getToDoId());
		}
	}
}
