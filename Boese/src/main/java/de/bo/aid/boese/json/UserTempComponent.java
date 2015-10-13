package de.bo.aid.boese.json;

public class UserTempComponent {
	private int tempComponentId;
	private int unitId;
	private String name;
	
	public UserTempComponent(int tempComponentId, int unitId, String name) {
		this.tempComponentId = tempComponentId;
		this.unitId = unitId;
		this.name = name;
	}

	public int getTempComponentId() {
		return tempComponentId;
	}

	public int getUnitId() {
		return unitId;
	}

	public String getName() {
		return name;
	}
	
	
}
