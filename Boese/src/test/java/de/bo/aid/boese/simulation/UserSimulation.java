/*
 * 
 */



package de.bo.aid.boese.simulation;

import java.util.HashMap;


import de.bo.aid.boese.main.Distributor;
import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSimulation.
 */
public class UserSimulation {
	
	/** The distr. */
	Distributor distr;
	

	/**
	 * Instantiates a new user simulation.
	 *
	 * @param distr the distr
	 */
	public UserSimulation(Distributor distr) {
	this.distr = distr;
	}

	/**
	 * Confirm connectors.
	 */
	public void confirmConnectors(){
		HashMap<Integer, String> connectors = distr.getTempConnectors();
		for(Integer key : connectors.keySet()){
			try {
				distr.confirmConnector(key, false);
				//Hier ist der Connector schon entfernt (connectors zeigt auf die Map in der MainClass)
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Confirm devices.
	 */
	public void confirmDevices(){
		HashMap<Integer, TempDevice> devices = distr.getTempDevices();
		for(Integer key : devices.keySet()){ //TODO ConncurentModificationException
			try {
				distr.confirmDevice(key, 0, null);
				} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Confirm device components.
	 */
	public void confirmDeviceComponents(){
		HashMap<Integer, TempComponent> devices = distr.getTempComponents();
		for(Integer key : devices.keySet()){
			try {
				distr.confirmDeviceComponent(key, 0, null);
				} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
