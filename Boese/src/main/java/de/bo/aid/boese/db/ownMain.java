package de.bo.aid.boese.db;

import java.util.ArrayList;
import java.util.List;

import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.Zone;

public class ownMain {

	public static void main(String[] args){
//		System.out.println(Checks.deviceComponentID(1));
//		
		List<Device> cdl = new ArrayList<Device>();
		cdl = Selects.connectorDeviceList(10);
		for(Device device : cdl){
			System.out.println("hallo " + device.getAlias());
		}
	}
}
