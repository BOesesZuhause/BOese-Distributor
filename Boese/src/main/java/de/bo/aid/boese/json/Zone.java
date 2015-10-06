package de.bo.aid.boese.json;

public class Zone {
	private int zoneId;
	private int superZoneId;
	private String zoneName;
	
	public Zone(int zoneId, int superZoneId, String name) {
		this.zoneId = zoneId;
		this.superZoneId = superZoneId;
		this.zoneName = name;
	}

	public int getZoneId() {
		return zoneId;
	}

	public int getSuperZoneId() {
		return superZoneId;
	}

	public String getZoneName() {
		return zoneName;
	}
	
}
