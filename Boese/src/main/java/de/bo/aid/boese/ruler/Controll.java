
package de.bo.aid.boese.ruler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.model.Rule;
import de.bo.aid.boese.xml.Action;
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.Component;
import de.bo.aid.boese.xml.Condition;

// TODO: Auto-generated Javadoc
/**
 * The Class Controll.
 */
public class Controll {
	
	/** The check. */
	Checker check;
	
	/** The interpretor. */
	Interpretor interpretor;
	
	/**
	 * Instantiates a new controll.
	 */
	public Controll(){
		check = new Checker();
		interpretor = new Interpretor();
	}

	/**
	 * Gets the to dos.
	 *
	 * @param inquirys the inquirys
	 * @return the to dos
	 */
	public List<Component> getToDos(List<Inquiry> inquirys){
		List<Component> toDo = new ArrayList<Component>();
		
		for(Inquiry inquiry : inquirys){
			int id = inquiry.getDeviceComponentId();
			List<Rule> rules = Selects.rulesByDeviceComponent(id);
			for(Rule rule : rules){
				if(check.deCoInCondition(id, rule.getConditions())){
					InputStream is = new ByteArrayInputStream(rule.getConditions().getBytes());
					System.out.println("Rule: " + rule.getRuId());
					BoeseXML conBXML = BoeseXML.readXML(is);
					is = new ByteArrayInputStream(rule.getActions().getBytes());
					BoeseXML actBXML = BoeseXML.readXML(is);
//					is = new ByteArrayInputStream(rule.getPermissions().getBytes());
//					BoeseXML perBXML = BoeseXML.readXML(is);
					HashMap<Integer, Double> deCoValue = new HashMap<>();
					for(int i : ((Condition)conBXML).getComponentIds()){
						if(i == id){
							deCoValue.put(id, inquiry.getValue());
						}
						else{
							deCoValue.put(i, Selects.currentValue(i));
						}
					}
					Boolean con = check.condition(((Condition)conBXML).getRule(), deCoValue);
					if(con != null && con){
						toDo.addAll(toDo.size(), (check.action((Action)actBXML)));
					}
				}
			}
		}
		
		return toDo;
	}
	
}
