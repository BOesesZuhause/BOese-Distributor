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
import java.util.List;

import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.model.Rule;
import de.bo.aid.boese.xml.Action;
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.ComponentXML;
import de.bo.aid.boese.xml.Condition;

// TODO: Auto-generated Javadoc
/**
 * The Class Controll.
 */
public class Control {
	
	/** The check. */
	Checker check;
	
	/** The interpretor. */
	Interpretor interpretor;
	
	/**
	 * Instantiates a new controll.
	 */
	public Control(){
		check = new Checker();
		interpretor = new Interpretor();
	}

	/**
	 * Gets the to dos.
	 *
	 * @param inquirys the inquirys
	 * @return the to dos
	 * @throws Exception the exception
	 */
	public List<ComponentXML> getToDos(List<Inquiry> inquirys) throws Exception{
		List<ComponentXML> toDo = new ArrayList<ComponentXML>();
		
		for(Inquiry inquiry : inquirys){
			int id = inquiry.getDeviceComponentId();
			List<Rule> rules = Selects.rulesByDeviceComponent(id);
			for(Rule rule : rules){
				if(check.deCoInCondition(id, rule.getConditions())){
					InputStream is = new ByteArrayInputStream(rule.getConditions().getBytes());
					BoeseXML conBXML = BoeseXML.readXML(is);
					is = new ByteArrayInputStream(rule.getActions().getBytes());
					BoeseXML actBXML = BoeseXML.readXML(is);
//					is = new ByteArrayInputStream(rule.getPermissions().getBytes());
//					BoeseXML perBXML = BoeseXML.readXML(is);
//					HashMap<Integer, Double> deCoValue = new HashMap<>();
//					for(int i : ((Condition)conBXML).getComponentIds()){
//						if(i == id){
//							deCoValue.put(id, inquiry.getValue());
//						}
//						else{
//							deCoValue.put(i, Selects.currentValue(i));
//						}
//					}
					Boolean con = check.condition(((Condition)conBXML).getRule());
					if(con != null && con){
						try {
							toDo.addAll(toDo.size(), (check.action((Action)actBXML)));
							
						} catch (Exception e) {
							System.err.println("Bad XML: " + e.getMessage());
							e.printStackTrace();
							throw new Exception(e.getMessage());
						}
					}
				}
			}
		}
		
		return toDo;
	}
	
}
