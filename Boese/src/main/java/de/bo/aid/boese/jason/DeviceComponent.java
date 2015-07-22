package de.bo.aid.boese.jason;

public class DeviceComponent {
	String name;
	String description;
	int unitId;
	
	public DeviceComponent(String name, String description, int unitId) {
		this.name = name;
		this.description = description;
		this.unitId = unitId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getUnitId() {
		return unitId;
	}
}
