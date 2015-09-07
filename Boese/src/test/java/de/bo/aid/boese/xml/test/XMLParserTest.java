
package de.bo.aid.boese.xml.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.xml.Action;
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.BoeseXML.XMLType;
import de.bo.aid.boese.xml.Component;
import de.bo.aid.boese.xml.Component.Comperator;
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
				+ "<VALUE>11.1</VALUE>"
				+ "<RESET_VALUE>10</RESET_VALUE>"
				+ "<START_TIME>12341234</START_TIME>"
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
		for (Component comp : ((Action)bXML).getActors()) {
			if (comp.getId() == 10) {
				assertTrue(comp.getComperator() == null);
				assertTrue(comp.getValue() == 11.1);
				assertTrue(comp.getResetValue() == 10);
				assertTrue(comp.getStartTime() == 12341234);
				assertTrue(comp.getDuration() == 15);
			}
		}
	}
	
	@Test
	public void conditionTest() {
//		System.out.println("Start condition Test 1");
		String rule = "<CONDITION>"
				+ "<COMPONENT>"
				+ "<ID>99</ID>"
				+ "<VALUE>11.1</VALUE>"
				+ "<RESET_VALUE>10</RESET_VALUE>"
				+ "<START_TIME>12341234</START_TIME>"
				+ "<DURATION>15</DURATION>"
				+ "<COMPERATOR>==</COMPERATOR>"
				+ "</COMPONENT>"
				+ "<COMPONENT>"
				+ "<ID>100</ID>"
				+ "<VALUE>21.1</VALUE>"
				+ "<RESET_VALUE>20</RESET_VALUE>"
				+ "<START_TIME>22341234</START_TIME>"
				+ "<DURATION>25</DURATION>"
				+ "<COMPERATOR>!=</COMPERATOR>"
				+ "</COMPONENT>"
				+ "</CONDITION>";
		
		InputStream is = new ByteArrayInputStream(rule.getBytes());
		BoeseXML bXML = BoeseXML.readXML(is);
		
		assertTrue(bXML.getClass() == Condition.class);
		assertTrue(bXML.getType() == XMLType.CONDITION);
		assertTrue(((Condition)bXML).containsComponent(99));
		assertTrue(((Condition)bXML).getRule().getComponents().size() == 2);
		for (Component comp : ((Condition)bXML).getRule().getComponents()) {
			if (comp.getId() == 99) {
				assertTrue(comp.getComperator() == Comperator.EQUAL);
				assertTrue(comp.getValue() == 11.1);
				assertTrue(comp.getResetValue() == -1);
				assertTrue(comp.getStartTime() == 12341234);
				assertTrue(comp.getDuration() == 15);
			} else if (comp.getId() == 100) {
				assertTrue(comp.getComperator() == Comperator.NOTEQUAL);
				assertTrue(comp.getValue() == 21.1);
				assertTrue(comp.getResetValue() == -1);
				assertTrue(comp.getStartTime() == 22341234);
				assertTrue(comp.getDuration() == 25);
			} else {
				assertTrue(false);
			}
		}
	}
	
	@Test
	public void conditionTestAnd() {
//		System.out.println("Start condition Test 2");
		
		String rule = "<CONDITION>"
				+ "<AND>"
				+ 	"<COMPONENT>"
				+ 		"<ID>99</ID>"
				+ 		"<VALUE>11.1</VALUE>"
				+ 		"<RESET_VALUE>10</RESET_VALUE>"
				+ 		"<START_TIME>12341234</START_TIME>"
				+ 		"<DURATION>15</DURATION>"
				+ 		"<COMPERATOR>==</COMPERATOR>"
				+ 	"</COMPONENT>"
				+ 	"<COMPONENT>"
				+ 		"<ID>100</ID>"
				+ 		"<VALUE>21.1</VALUE>"
				+ 		"<RESET_VALUE>20</RESET_VALUE>"
				+ 		"<START_TIME>22341234</START_TIME>"
				+ 		"<DURATION>25</DURATION>"
				+ 		"<COMPERATOR>!=</COMPERATOR>"
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
		
		for (Component comp : ((Condition)bXML).getRule().getGate().iterator().next().getComponents()) {
			if (comp.getId() == 99) {
				assertTrue(comp.getComperator() == Comperator.EQUAL);
				assertTrue(comp.getValue() == 11.1);
				assertTrue(comp.getResetValue() == -1);
				assertTrue(comp.getStartTime() == 12341234);
				assertTrue(comp.getDuration() == 15);
			} else if (comp.getId() == 100) {
				assertTrue(comp.getComperator() == Comperator.NOTEQUAL);
				assertTrue(comp.getValue() == 21.1);
				assertTrue(comp.getResetValue() == -1);
				assertTrue(comp.getStartTime() == 22341234);
				assertTrue(comp.getDuration() == 25);
			} else {
				assertTrue(false);
			}
		}
	}
}
