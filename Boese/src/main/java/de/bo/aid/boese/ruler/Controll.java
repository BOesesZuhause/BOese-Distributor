package de.bo.aid.boese.ruler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.model.Rule;
import de.bo.aid.boese.xml.BoeseXML;

public class Controll {
	
	Checker check;
	Interpretor interpretor;
	
	public Controll(){
		check = new Checker();
		interpretor = new Interpretor();
	}

	public HashMap<Integer, Double> getActions(List<Inquiry> inquirys){
		HashMap<Integer, Double> toDo = new HashMap<Integer, Double>();
		
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
					List<Integer> conDeCoId = interpretor.getAllDeCoIdsCondition(conBXML);
					HashMap<Integer, Double> deCoValue = new HashMap<>();
					for(int i : conDeCoId){
						if(i == id){
							deCoValue.put(id, inquiry.getValue());
						}
						else{
							deCoValue.put(i, Selects.currentValue(i));
						}
					}
					
				}
			}
		}
		
		return toDo;
	}
	
}
