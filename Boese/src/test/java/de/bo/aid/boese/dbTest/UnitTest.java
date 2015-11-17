package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.Unit;

public class UnitTest {

	private Unit unit1; 
	private Unit unit2;
	private Unit unit1Update;
	private Unit unit2Update;

	@Before
	public void setUp() throws Exception {
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boese", "localhost", "5432");
		unit1 = new Unit();
		unit1.setName("meter");
		unit1.setSymbol("m");;
		unit2 = new Unit();
		unit2.setName("gramm");
		unit2.setSymbol("g");
		unit1Update = new Unit();
		unit1Update.setName("Kilometer");
		unit1Update.setSymbol("km");
		unit2Update = new Unit();
		unit2Update.setName("kilogramm");
		unit2Update.setSymbol("kg");
	}

	@Test
	public void test() {
		
		//Insert Unit 1
		insert(unit1);
		unit1Update.setUnId(unit1.getUnId());
		//Insert Unit 2
		insert(unit2);
		unit2Update.setUnId(unit2.getUnId());

		//Unit 1 equal Unit in DB?
		equal(unit1, "1");
		//Unit 2 equal Unit in DB?
		equal(unit2, "2");
		
		//Update Unit 1
		update(unit1, unit1Update);
		//Update Unit 2
		update(unit2, unit2Update);
		
		//Unit 1 after update equal Unit in DB?
		equal(unit1Update, "1Update");
		//Unit 1 after update equal Unit in DB?
		equal(unit2Update, "2Update");
		
	}
	
	private void insert(Unit unit){
		Inserts.unit(unit);
	}
	
	private Unit select(int id){
		Unit unit = null;
		try {
			unit = Selects.unit(id);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + id);
		}
		return unit;
	}
	
	private void equal(Unit unit, String name){
		Unit unitTest = select(unit.getUnId());
		assertTrue("Unit " + name + " Name not equal", unit.getName().equals(unitTest.getName()));
		assertTrue("Unit " + name + " Symbol not equal", unit.getSymbol().equals(unitTest.getSymbol()));
		assertTrue("Unit " + name + " ID not equal", unit.getUnId() == unitTest.getUnId());
	}
	
	private void update(Unit unit, Unit unitUpdate){
		try {
			Updates.unit(unit, unitUpdate.getName(), unitUpdate.getSymbol());
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + unit.getUnId());
		}
	}
}
