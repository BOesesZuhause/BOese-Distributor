/*
 * 
 */
package de.bo.aid.boese.xml.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.xml.Action;
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.BoeseXML.XMLType;
import de.bo.aid.boese.xml.CalculationList.CalculationTypes;
import de.bo.aid.boese.xml.ComponentXML;
import de.bo.aid.boese.xml.ComponentXML.Comperator;
import de.bo.aid.boese.xml.Condition;
import de.bo.aid.boese.xml.GateList.GateType;
import de.bo.aid.boese.xml.Permission;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLParserTest.
 */
public class XMLParserTest {
	
	/**
	 * Permissioin test before.
	 */
	@Before
	public void permissioinTestBefore() {
		
	}
	
	/**
	 * Permissioin test after.
	 */
	@After
	public void permissioinTestAfter() {
		
	}
	
	/**
	 * Permissioin test.
	 */
	@Test
	public void permissioinTest() {
		
		String rule = "<PERMISSION>"
				+ "<GROUP_ID>"
				+ "2"
				+ "</GROUP_ID>"
				+ "<GROUP_ID>"
				+ "3"
				+ "</GROUP_ID>"
				+ "</PERMISSION>";
		
		InputStream is = new ByteArrayInputStream(rule.getBytes());
		BoeseXML bXML = BoeseXML.readXML(is);
		
		assertTrue(bXML.getClass() == Permission.class);
		assertTrue(bXML.getType() == XMLType.PERMISSION);
		assertTrue(((Permission)bXML).getGroupId().size() == 2);
		for (Integer i : ((Permission)bXML).getGroupId()) {
			assertTrue((i.intValue() == 2) || (i.intValue() == 3));
		}
	}
	
	/**
	 * Action test.
	 */
	@Test
	public void actionTest() {
		
		String rule = "<ACTION>"
				+ "<ACTIVATE_RULE>"
				+ "<ACTIVATE_RULE_ID>"
				+ "2"
				+ "</ACTIVATE_RULE_ID>"
				+ "<ACTIVATE_RULE_ID>"
				+ "3"
				+ "</ACTIVATE_RULE_ID>"
				+ "</ACTIVATE_RULE>"
				+ "<DEACTIVATE_RULE>"
				+ "<DEACTIVATE_RULE_ID>"
				+ "3"
				+ "</DEACTIVATE_RULE_ID>"
				+ "<DEACTIVATE_RULE_ID>"
				+ "4"
				+ "</DEACTIVATE_RULE_ID>"
				+ "<DEACTIVATE_RULE_ID>"
				+ "5"
				+ "</DEACTIVATE_RULE_ID>"
				+ "</DEACTIVATE_RULE>"
				+ "<ACTOR>"
				+ "<ID>10</ID>"
				+ "<VALUE><CONSTANT>11.1</CONSTANT></VALUE>"
				+ "<RESET_VALUE>10</RESET_VALUE>"
				+ "<START_TIME>*;*;*;*;*;*</START_TIME>"
				+ "<DURATION>15</DURATION>"
				+ "</ACTOR>"
				+ "</ACTION>";
		
		InputStream is = new ByteArrayInputStream(rule.getBytes());
		BoeseXML bXML = BoeseXML.readXML(is);
		
		assertTrue(bXML.getClass() == Action.class);
		assertTrue(bXML.getType() == XMLType.ACTION);
		assertTrue(((Action)bXML).getActivateRules().size() == 2);
		for (Integer i : ((Action)bXML).getActivateRules()) {
			assertTrue((i.intValue() == 2) || (i.intValue() == 3));
		}
		assertTrue(((Action)bXML).getDeactivateRules().size() == 3);
		for (Integer i : ((Action)bXML).getDeactivateRules()) {
			assertTrue((i.intValue() == 3) || (i.intValue() == 4) || (i.intValue() == 5));
		}
		assertTrue(((Action)bXML).getActors().size() == 1);
		for (ComponentXML comp : ((Action)bXML).getActors()) {
			if (comp.getId() == 10) {
				assertTrue(comp.getComperator() == null);
				assertTrue(comp.getCalculation().getConstants().iterator().next() == 11.1);
				assertTrue(comp.getResetValue() == 10);
				assertTrue(comp.getStartTime().getDate().getTime() / 60000 == ((new Date().getTime() / 60000) +1));
				assertTrue(comp.getDuration() == 15);
			}
		}
	}
	
	/**
	 * Condition test.
	 */
	@Test
	public void conditionTest() {
//		System.out.println("Start condition Test 1");
		String rule = "<CONDITION>"
				+ "<COMPONENT>"
				+ "<ID>99</ID>"
				+ "<VALUE><CONSTANT>11.1</CONSTANT></VALUE>"
				+ "<RESET_VALUE>10</RESET_VALUE>"
				+ "<START_TIME>*;*;*;*;*;*</START_TIME>"
				+ "<DURATION>15</DURATION>"
				+ "<COMPARATOR>==</COMPARATOR>"
				+ "</COMPONENT>"
				+ "<COMPONENT>"
				+ "<ID>100</ID>"
				+ "<VALUE><CONSTANT>21.1</CONSTANT></VALUE>"
				+ "<RESET_VALUE>20</RESET_VALUE>"
				+ "<START_TIME>*;*;*;*;*;*</START_TIME>"
				+ "<DURATION>25</DURATION>"
				+ "<COMPARATOR>!=</COMPARATOR>"
				+ "</COMPONENT>"
				+ "</CONDITION>";
		
		InputStream is = new ByteArrayInputStream(rule.getBytes());
		BoeseXML bXML = BoeseXML.readXML(is);
		
		assertTrue(bXML.getClass() == Condition.class);
		assertTrue(bXML.getType() == XMLType.CONDITION);
		assertTrue(((Condition)bXML).containsComponent(99));
		assertTrue(((Condition)bXML).getRule().getComponents().size() == 2);
		for (ComponentXML comp : ((Condition)bXML).getRule().getComponents()) {
			if (comp.getId() == 99) {
				assertTrue(comp.getComperator() == Comperator.EQUAL);
				assertTrue(comp.getCalculation().getConstants().iterator().next() == 11.1);
				assertTrue(comp.getResetValue() == -1);
				assertTrue(comp.getStartTime().getDate().getTime() / 60000 == ((new Date().getTime() / 60000) +1));
				assertTrue(comp.getDuration() == 15);
			} else if (comp.getId() == 100) {
				assertTrue(comp.getComperator() == Comperator.NOTEQUAL);
				assertTrue(comp.getCalculation().getConstants().iterator().next() == 21.1);
				assertTrue(comp.getResetValue() == -1);
				assertTrue(comp.getStartTime().getDate().getTime() / 60000 == ((new Date().getTime() / 60000) +1));
				assertTrue(comp.getDuration() == 25);
			} else {
				assertTrue(false);
			}
		}
	}
	
	/**
	 * Condition test and.
	 */
	@Test
	public void conditionTestAnd() {
//		System.out.println("Start condition Test 2");
		
		String rule = "<CONDITION>"
				+ "<AND>"
				+ 	"<COMPONENT>"
				+ 		"<ID>99</ID>"
				+ 		"<VALUE><CONSTANT>11.1</CONSTANT></VALUE>"
				+ 		"<RESET_VALUE>10</RESET_VALUE>"
				+ "<START_TIME>*;*;*;*;*;*</START_TIME>"
				+ 		"<DURATION>15</DURATION>"
				+ 		"<COMPARATOR>==</COMPARATOR>"
				+ 	"</COMPONENT>"
				+ 	"<COMPONENT>"
				+ 		"<ID>100</ID>"
				+ 		"<VALUE><CONSTANT>21.1</CONSTANT></VALUE>"
				+ 		"<RESET_VALUE>20</RESET_VALUE>"
				+ "<START_TIME>*;*;*;*;*;*</START_TIME>"
				+ 		"<DURATION>25</DURATION>"
				+ 		"<COMPARATOR>!=</COMPARATOR>"
				+ 	"</COMPONENT>"
				+ "</AND>"
				+ "</CONDITION>";
		
		InputStream is = new ByteArrayInputStream(rule.getBytes());
		BoeseXML bXML = BoeseXML.readXML(is);
		
		assertTrue(bXML.getClass() == Condition.class);
		assertTrue(bXML.getType() == XMLType.CONDITION);
		assertTrue(((Condition)bXML).containsComponent(99));
		assertTrue(((Condition)bXML).getRule().getGate().size() == 1);
		assertTrue(((Condition)bXML).getRule().getGate().iterator().next().getComponents().size() == 2);
		assertTrue(((Condition)bXML).getRule().getGate().iterator().next().getGateType() == GateType.AND_GATE);
		
		for (ComponentXML comp : ((Condition)bXML).getRule().getGate().iterator().next().getComponents()) {
			if (comp.getId() == 99) {
				assertTrue(comp.getComperator() == Comperator.EQUAL);
				assertTrue(comp.getCalculation().getConstants().iterator().next() == 11.1);
				assertTrue(comp.getResetValue() == -1);
				assertTrue(comp.getStartTime().getDate().getTime() / 60000 == ((new Date().getTime() / 60000) +1));
				assertTrue(comp.getDuration() == 15);
			} else if (comp.getId() == 100) {
				assertTrue(comp.getComperator() == Comperator.NOTEQUAL);
				assertTrue(comp.getCalculation().getConstants().iterator().next() == 21.1);
				assertTrue(comp.getResetValue() == -1);
				assertTrue(comp.getStartTime().getDate().getTime() / 60000 == ((new Date().getTime() / 60000) +1));
				assertTrue(comp.getDuration() == 25);
			} else {
				assertTrue(false);
			}
		}
	}
	
	/**
	 * Condition test add.
	 */
	@Test
	public void conditionTestAdd() {
		String rule = "<CONDITION>"
				+ "<AND>"
				+ 	"<COMPONENT>"
				+ 		"<ID>99</ID>"
				+ 		"<VALUE>"
				+			"<ADD>"
				+ 				"<CONSTANT>11.1</CONSTANT>"
				+				"<VARIABLE>2</VARIABLE>"
				+			"</ADD>"
				+		"</VALUE>"
				+ 		"<RESET_VALUE>10</RESET_VALUE>"
				+ "<START_TIME>*;*;*;*;*;*</START_TIME>"
				+ 		"<DURATION>15</DURATION>"
				+ 		"<COMPARATOR>==</COMPARATOR>"
				+ 	"</COMPONENT>"
				+ "</AND>"
				+ "</CONDITION>";

		InputStream is = new ByteArrayInputStream(rule.getBytes());
		BoeseXML bXML = BoeseXML.readXML(is);
		assertTrue(bXML.getClass() == Condition.class);
		assertTrue(bXML.getType() == XMLType.CONDITION);
		assertTrue(((Condition)bXML).containsComponent(99));
		assertTrue(((Condition)bXML).getRule().getGate().size() == 1);
		assertTrue(((Condition)bXML).getRule().getGate().iterator().next().getComponents().size() == 1);
		assertTrue(((Condition)bXML).getRule().getGate().iterator().next().getGateType() == GateType.AND_GATE);
		
		for (ComponentXML comp : ((Condition)bXML).getRule().getGate().iterator().next().getComponents()) {
			assertTrue(comp.getComperator() == Comperator.EQUAL);
			assertTrue(comp.getCalculation().getCalculations().iterator().next().getCalculationType() == CalculationTypes.ADD);
			assertTrue(comp.getCalculation().getCalculations().iterator().next().getConstants().iterator().next() == 11.1);
			assertTrue(comp.getCalculation().getCalculations().iterator().next().getVariables().iterator().next() == 2.0);
			assertTrue(comp.getResetValue() == -1);
			assertTrue(comp.getStartTime().getDate().getTime() / 60000 == ((new Date().getTime() / 60000) +1));
			assertTrue(comp.getDuration() == 15);
		}
	}
}
