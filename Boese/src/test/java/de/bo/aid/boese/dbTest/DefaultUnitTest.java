package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.DB.util.HibernateUtil;
import de.bo.aid.boese.db.AllSelects;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.model.Unit;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultUnitTest.
 */
public class DefaultUnitTest {

	/** The namen. */
	List <String> namen;
	
	/** The volt. */
	final String volt = "Volt";
	
	/** The ampere. */
	final String ampere = "milliAmpere";
	
	/** The percent. */
	final String percent = "Percent";
	
	/** The bool. */
	final String bool = "OnOff";
	
	/** The temp. */
	final String temp = "Temperature";
	
	/** The dist. */
	final String dist = "Distance";
	
	/** The rgb. */
	final String rgb = "ColorRGB";
	
	/** The watt. */
	final String watt = "Watt";
	
	/** The time. */
	final String time = "Time";
	
	/** The vel. */
	final String vel = "Velocity";
	
	/** The weight. */
	final String weight = "Weight";
	
	/** The defaults. */
	Map<String, String> defaults = new HashMap<>(); 
	
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
		Inserts.defaultUnits();

		List <Unit> units = AllSelects.units();
		namen = new ArrayList<>();
		for(Unit unit : units){
			namen.add(unit.getName());
		}
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		if (!namen.contains(volt)){
			fail(volt + "not in DB");
		}
		if(!namen.contains(ampere)){
			fail(ampere + " not in DB");
		}
		if(!namen.contains(percent)){
			fail(percent + " not in DB");
		}
		if(!namen.contains(bool)){
			fail(bool + " not in DB");
		}
		if(!namen.contains(temp)){
			fail(temp + " not in DB");
		}
		if(!namen.contains(dist)){
			fail(dist + " not in DB");
		}
		if(!namen.contains(rgb)){
			fail(rgb + " not in DB");
		}
		if(!namen.contains(watt)){
			fail(watt + " not in DB");
		}
		if(!namen.contains(time)){
			fail(time + " not in DB");
		}
		if(!namen.contains(vel)){
			fail(vel + " not in DB");
		}
		if(!namen.contains(weight)){
			fail(weight + " not in DB");
		}
	}

}
