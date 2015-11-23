package de.bo.aid.boese.json;

public class UnitJSON {
	private int unitId;
	private String unitName;
	private String unitSymbol;
	
	public UnitJSON(int unitId, String unitName, String unitSymbol) {
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitSymbol = unitSymbol;
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
