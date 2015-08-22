package de.bo.aid.boese.simulation;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import de.bo.aid.boese.main.MainClass;
import de.bo.aid.boese.model.Connector;
import javassist.NotFoundException;

public class UserSimulation {
	

	public void confirmConnectors(){
		HashMap<Integer, String> connectors = MainClass.getTempConnectors();
		for(Integer key : connectors.keySet()){
			try {
				MainClass.confirmConnector(key);
				//Hier ist der Connector schon entfernt (connectors zeigt auf die Map in der MainClass)
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void confirmDevices(){
		
	}
	
	public void confirmDeviceComponents(){
		
	}

}
