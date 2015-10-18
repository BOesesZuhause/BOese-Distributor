
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
	

	/**
	 * Confirm connectors.
	 */
	public void confirmConnectors(){
		HashMap<Integer, String> connectors = Distributor.getTempConnectors();
		for(Integer key : connectors.keySet()){
			try {
				Distributor.confirmConnector(key, false);
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
		HashMap<Integer, TempDevice> devices = Distributor.getTempDevices();
		for(Integer key : devices.keySet()){
			try {
				Distributor.confirmDevice(key, 0, null);
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
		HashMap<Integer, TempComponent> devices = Distributor.getTempComponents();
		for(Integer key : devices.keySet()){
			try {
				Distributor.confirmDeviceComponent(key, 0, null);
				} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
