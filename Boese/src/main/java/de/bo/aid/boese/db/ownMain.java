package de.bo.aid.boese.db;

import java.util.ArrayList;
import java.util.List;

import de.bo.aid.boese.model.Zone;

public class ownMain {

	public static void main(String[] args){
		System.out.println(Checks.deviceComponentID(1));
		
		List<Zone> z = new ArrayList<Zone>();
		z = Selects.allZones();
		for(Zone zone : z){
			System.out.println(zone.getName());
		}
	}
}
