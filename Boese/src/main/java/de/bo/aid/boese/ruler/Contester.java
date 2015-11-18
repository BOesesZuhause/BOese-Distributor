/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 */



package de.bo.aid.boese.ruler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.ComponentXML;
import de.bo.aid.boese.xml.Condition;
import de.bo.aid.boese.xml.GateList;

// TODO: Auto-generated Javadoc
/**
 * The Class Contester.
 */
public class Contester {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		String rule = "<CONDITION>"
					+ "<OR>"
					+ 	"<AND>"
					+ 		"<COMPONENT>"
					+ 			"<ID>99</ID>"
					+ 			"<VALUE>11.1</VALUE>"
					+ 			"<RESET_VALUE>10</RESET_VALUE>"
					//+ 			"<START_TIME>12341234</START_TIME>"
					+ 			"<DURATION>15</DURATION>"
					+ 			"<COMPERATOR>==</COMPERATOR>"
					+ 		"</COMPONENT>"
					+ 		"<COMPONENT>"
					+ 			"<ID>100</ID>"
					+ 			"<VALUE>21.1</VALUE>"
					+ 			"<RESET_VALUE>20</RESET_VALUE>"
				//	+ 			"<START_TIME>22341234</START_TIME>"
					+ 			"<DURATION>25</DURATION>"
					+ 			"<COMPERATOR>!=</COMPERATOR>"
					+ 		"</COMPONENT>"
					+ 	"</AND>"
					+ 	"<AND>"
					+ 		"<COMPONENT>"
					+ 			"<ID>99</ID>"
					+ 			"<VALUE>11.1</VALUE>"
					+ 			"<RESET_VALUE>10</RESET_VALUE>"
					//+ 			"<START_TIME>12341234</START_TIME>"
					+ 			"<DURATION>15</DURATION>"
					+ 			"<COMPERATOR>!=</COMPERATOR>"
					+ 		"</COMPONENT>"
					+ 		"<COMPONENT>"
					+ 			"<ID>100</ID>"
					+ 			"<VALUE>21.1</VALUE>"
					+ 			"<RESET_VALUE>20</RESET_VALUE>"
					//+ 			"<START_TIME>22341234</START_TIME>"
					+ 			"<DURATION>25</DURATION>"
					+ 			"<COMPERATOR>==</COMPERATOR>"
					+ 		"</COMPONENT>"
					+ 	"</AND>"
					+ "</OR>"
					+ "</CONDITION>";
		
		InputStream is = new ByteArrayInputStream(rule.getBytes());
		BoeseXML bXML = BoeseXML.readXML(is);
		
		for(int i : ((Condition)bXML).getComponentIds()){
			System.out.println(i);
		}
		
		Checker c = new Checker();
		
		HashMap<Integer, Double> hm = new HashMap<Integer, Double>();
		hm.put(99, 11.1);
		hm.put(100, 11.1);
		
		System.out.println(rekTest(((Condition)bXML).getRule()));
		System.out.println();
		System.out.println(rekuTest(((Condition)bXML).getRule()));
		System.out.println();
		System.out.println(c.condition(((Condition)bXML).getRule(), hm));
		System.out.println();
		hm = new HashMap<Integer, Double>();
		hm.put(99, 21.1);
		hm.put(100, 21.1);
		System.out.println(c.condition(((Condition)bXML).getRule(), hm));
		System.out.println();
		hm = new HashMap<Integer, Double>();
		hm.put(99, 11.1);
		hm.put(100, 21.1);
		System.out.println(c.condition(((Condition)bXML).getRule(), hm));
		
		testAll();
		
	}
	
	/**
	 * Rek test.
	 *
	 * @param test the test
	 * @return the string
	 */
	private static String rekTest(GateList test){
		String s = "";
		Set<GateList> sg = test.getGate();
		for(GateList gl : sg){
			s += " (";
			
			Set<GateList> sg2 = gl.getGate();
			for(GateList gl2 : sg2){
				s += " (";
				Set<ComponentXML> sc = gl2.getComponents();
				for(ComponentXML c : sc){
					s += rekuTest(c) + " + ";
				}
				s += ") " + gl.getGateType();
			}
			
			s += ") ";
		}
		return s;
	}
	
	/**
	 * Reku test.
	 *
	 * @param test the test
	 * @return the string
	 */
	private static String rekuTest(GateList test){
		String s = "";
		Set<GateList> sg = test.getGate();
		for(GateList gl : sg){
			s += " (";
			s += rekuTest(gl);	
			for(ComponentXML c : gl.getComponents()){
				s += rekuTest(c) + " + ";
			}
			s += ") " + gl.getGateType();				
		}
		return s;

	}
	
	/**
	 * Reku test.
	 *
	 * @param comp the comp
	 * @return the string
	 */
	private static String rekuTest(ComponentXML comp){
		if(comp == null){
			return "";
		}
		else{
			return "ID" + comp.getId() + " " + comp.getComperator() + " " + comp.getValue();
		}
	}
	
	/**
	 * Test all.
	 */
	private static void testAll(){
		Control c = new Control();
		List<Inquiry> inquirys = new ArrayList<Inquiry>();
		inquirys.add(new Inquiry(1, new Date().getTime(), 11.1));
		try {
			for (ComponentXML comp : c.getToDos(inquirys)){
				System.out.println(comp.getId() + "\t" + comp.getValue() + "\t" + comp.getResetValue() + "\t" + comp.getStartTime() + "\t" + comp.getDuration() + "\t");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
