package de.bo.aid.boese.simulation;

import java.util.List;

import de.bo.aid.boese.main.MainClass;
import de.bo.aid.boese.model.Connector;
import javassist.NotFoundException;

public class UserSimulation {
	

	public void confirmConnectors(){
		List<Connector> connectors = MainClass.getTempConnectors();
		for(Connector c : connectors){
			try {
				MainClass.confirmConnector(c.getCoId());
				System.out.println("User confirmed Conector with name: " + c.getName() + "\n");
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
