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



package de.bo.aid.boese.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.bo.aid.boese.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ownMain.
 */
public class ownMain {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
//		System.out.println(Checks.deviceComponentID(1));
//		
//		List<Rule> rbdc = new ArrayList<Rule>();
//		rbdc = Selects.rulesByDeviceComponent(1);
//		for(Rule rule : rbdc){
//			System.out.println("hallo " + rule.getActions());
//		}

//		try{
//			Inserts.deviceGroup(0, (short) 1, (short) 1);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//			System.exit(0);
//		}
		
		String per = "<PERMISSION>"
				+ "<GROUP_ID>"
				+ "2"
				+ "</GROUP_ID>"
				+ "<GROUP_ID>"
				+ "3"
				+ "</GROUP_ID>"
				+ "</PERMISSION>";
		
		String act = "<ACTION>"
				+ "<ACTIVATE_RULE>"
				+ "<ACTIVATE_RULE_ID>"
				+ "1"
				+ "</ACTIVATE_RULE_ID>"
				+ "</ACTIVATE_RULE>"
				+ "<ACTOR>"
				+ "<ID>1</ID>"
				+ "<VALUE>11.1</VALUE>"
				+ "<RESET_VALUE>10</RESET_VALUE>"
				+ "<START_TIME>12341234</START_TIME>"
				+ "<DURATION>15</DURATION>"
				+ "</ACTOR>"
				+ "</ACTION>";
		
		String con = "<CONDITION>"
				+ "<COMPONENT>"
				+ "<ID>1</ID>"
				+ "<VALUE>11.1</VALUE>"
				+ "<RESET_VALUE>10</RESET_VALUE>"
				+ "<START_TIME>12341234</START_TIME>"
				+ "<DURATION>15</DURATION>"
				+ "<COMPERATOR>==</COMPERATOR>"
				+ "</COMPONENT>";
		
		List<Integer> l = new ArrayList<Integer>();
		l.add(new Integer(1));
		l.add(new Integer(2));
		
		try{
			Inserts.rule(l, per, con, act);
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("erledigt");
	}
}

/*
+ "<COMPONENT>"
+ "<ID>2</ID>"
+ "<VALUE>21.1</VALUE>"
+ "<RESET_VALUE>20</RESET_VALUE>"
+ "<START_TIME>22341234</START_TIME>"
+ "<DURATION>25</DURATION>"
+ "<COMPERATOR>!=</COMPERATOR>"
+ "</COMPONENT>"
+ "</CONDITION>"


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
*/

