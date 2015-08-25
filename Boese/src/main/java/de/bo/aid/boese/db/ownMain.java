package de.bo.aid.boese.db;

import java.util.ArrayList;
import java.util.List;

import de.bo.aid.boese.model.*;

public class ownMain {

	public static void main(String[] args){
//		System.out.println(Checks.deviceComponentID(1));
//		
		List<Rule> rbdc = new ArrayList<Rule>();
		rbdc = Selects.rulesByDeviceComponent(1);
		for(Rule rule : rbdc){
			System.out.println("hallo " + rule.getActions());
		}
	}
}
