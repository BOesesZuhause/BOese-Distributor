package de.bo.aid.boese.rulerTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.Component;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.ruler.Checker;
import de.bo.aid.boese.xml.CalculationList;
import de.bo.aid.boese.xml.CalculationList.CalculationTypes;

public class CalculationTest {
	
	CalculationList cl1;
	CalculationList cl2;
	CalculationList first;

	double const1;
	double const2;
	double value1;
	double value2;
	double value3;
	double firstvalue;
	
	Checker check;
	
	DeviceComponent deco1;
	DeviceComponent deco2;
	DeviceComponent deco3;
	
	double erg;
	double calc;

	@Before
	public void setUp() throws Exception {
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boese", "localhost", "5432");
		
		Inserts.defaults();
		
		const1 = 10.0;
		const2 = 20.0;
		value1 = 10.0;
		value2 = 20.0;
		value3 = 40.0;
		firstvalue = 1000.0;
		
		Connector con  = new Connector("test", "123");
		Device dev = new Device("test", "123");
		Component comp = new Component("Sensor", false);
		deco1 = new DeviceComponent("1");
		deco2 = new DeviceComponent("2");
		deco3 = new DeviceComponent("3");
		
		Inserts.connector(con);
		Inserts.component(0, comp);
		Inserts.device(con.getCoId(), 0, dev);
		Inserts.deviceComponent(dev.getDeId(), comp.getCoId(), deco1);
		Inserts.deviceComponent(dev.getDeId(), comp.getCoId(), deco2);
		Inserts.deviceComponent(dev.getDeId(), comp.getCoId(), deco3);

		Inserts.value(deco1.getDeCoId(), new Date(), value1);
		Inserts.value(deco2.getDeCoId(), new Date(), value2);
		Inserts.value(deco3.getDeCoId(), new Date(), value3);
		deco1.setCurrentValue(new BigDecimal(value1));
		deco2.setCurrentValue(new BigDecimal(value2));
		deco3.setCurrentValue(new BigDecimal(value3));
		
		cl1 = new CalculationList();
		cl2 = new CalculationList();
		first = new CalculationList();
		first.addConstant(firstvalue);
		cl2.setFirst(first);
		cl2.addConstant(const1);
		cl2.addConstant(const2);
		cl2.addValiable(deco1.getDeCoId());
		cl2.addValiable(deco2.getDeCoId());
		
		cl1.addValiable(deco3.getDeCoId());
		cl1.setFirst(cl2);
		
		check = new Checker();
	}

	@Test
	public void addTest() {
		cl1.setCalculationType(CalculationTypes.ADD);
		cl2.setCalculationType(CalculationTypes.ADD);
		
		try {
			calc = check.calculate(cl1);
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		erg = value1 + value2 + value3 + const1 + const2 + firstvalue;
		
		assertTrue("Addition not correct (" + erg + " == " + calc + ")", erg == calc);
	}
	
	@Test
	public void subTest() {
		cl1.setCalculationType(CalculationTypes.SUB);
		cl2.setCalculationType(CalculationTypes.SUB);
		
		try {
			calc = check.calculate(cl1);
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		erg = (((firstvalue) - value1 - value2 - const1 - const2) - value3);
		
		assertTrue("Subtraktion not correct (" + erg + " == " + calc + ")", erg == calc);
	}
	
	@Test
	public void mulTest() {
		cl1.setCalculationType(CalculationTypes.MUL);
		cl2.setCalculationType(CalculationTypes.MUL);
		
		try {
			calc = check.calculate(cl1);
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		erg = value1 * value2 * value3 * const1 * const2 * firstvalue;
		
		assertTrue("Multiplikation not correct (" + erg + " == " + calc + ")", erg == calc);
	}
	
	@Test
	public void divTest() {
		cl1.setCalculationType(CalculationTypes.DIV);
		cl2.setCalculationType(CalculationTypes.DIV);
		
		try {
			calc = check.calculate(cl1);
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		erg = (((firstvalue) / value1 / value2 / const1 / const2) / value3);
		
		assertTrue("Multiplikation not correct (" + erg + " == " + calc + ")", erg == calc);
	}

	@Test
	public void modTest() {
		cl1.setCalculationType(CalculationTypes.MOD);
		cl2.setCalculationType(CalculationTypes.ADD);
		
		try {
			calc = check.calculate(cl1);
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		erg = (firstvalue + value1 + value2  + const1 + const2) % value3;
		
		assertTrue("Modulo not correct (" + erg + " == " + calc + ")", erg == calc);
	}
	


	@Test
	public void absTest() {
		first.setCalculationType(CalculationTypes.ABS);
		
		try {
			calc = check.calculate(first);
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		erg = firstvalue;
		
		assertTrue("AbsolutValue not correct (" + erg + " == " + calc + ")", erg == calc);
		
		first = new CalculationList();
		first.addConstant(firstvalue * -1);
		first.setCalculationType(CalculationTypes.ABS);
		
		try {
			calc = check.calculate(first);
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		erg = firstvalue;
		
		assertTrue("AbsolutValue not correct (" + erg + " == " + calc + ")", erg == calc);
	}

}
