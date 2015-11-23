package de.bo.aid.boese.json;

public class UnitJSON {
	private int unitId;
	private String unitName;
	private String unitSymbol;
	private int tempUnitId;
	
	public UnitJSON(int unitId, String unitName, String unitSymbol) {
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitSymbol = unitSymbol;
		this.tempUnitId = -1;
	}
	
	public UnitJSON(int unitId, int tempUnitId, String unitName, String unitSymbol) {
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitSymbol = unitSymbol;
		this.tempUnitId = tempUnitId;
	}
	
	public int getTempUnitId() {
		return tempUnitId;
	}

	public int getUnitId() {
		return unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public String getUnitSymbol() {
		return unitSymbol;
	}
}
