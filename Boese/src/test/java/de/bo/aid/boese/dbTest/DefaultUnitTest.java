package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.db.AllSelects;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.Unit;

public class DefaultUnitTest {

	List <String> namen;
	
	final String volt = "Volt";
	final String ampere = "milliAmpere";
	final String percent = "Percent";
	final String bool = "OnOff";
	final String temp = "Temperature";
	final String dist = "Distance";
	final String rgb = "ColorRGB";
	final String watt = "Watt";
	final String time = "Time";
	final String vel = "Velocity";
	final String weight = "Weight";
	
	Map<String, String> defaults = new HashMap<>(); 
	
	@Before
	public void setUp() throws Exception {
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boeseTest", "localhost", "5432");
		HibernateUtil.setDBAuto("create");
		
		Inserts.defaults();
		Inserts.defaultUnits();

		List <Unit> units = AllSelects.units();
		namen = new ArrayList<>();
		for(Unit unit : units){
			namen.add(unit.getName());
		}
	}

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
