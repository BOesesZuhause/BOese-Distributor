package de.bo.aid.boese.simulation;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import de.bo.aid.boese.main.MainClass;
import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;
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
		HashMap<Integer, TempDevice> devices = MainClass.getTempDevices();
		for(Integer key : devices.keySet()){
			try {
				MainClass.confirmDevice(key, 0, null);
				} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void confirmDeviceComponents(){
		HashMap<Integer, TempComponent> devices = MainClass.getTempComponents();
		for(Integer key : devices.keySet()){
			try {
				MainClass.confirmDeviceComponent(key, 0, null);
				} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
