package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class UnitJSON.
 */
public class UnitJSON {
	
	/** The unit id. */
	private int unitId;
	
	/** The unit name. */
	private String unitName;
	
	/** The unit symbol. */
	private String unitSymbol;
	
	/** The temp unit id. */
	private int tempUnitId;
	
	/**
	 * Instantiates a new unit json.
	 *
	 * @param unitId the unit id
	 * @param unitName the unit name
	 * @param unitSymbol the unit symbol
	 */
	public UnitJSON(int unitId, String unitName, String unitSymbol) {
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitSymbol = unitSymbol;
		this.tempUnitId = -1;
	}
	
	/**
	 * Instantiates a new unit json.
	 *
	 * @param unitId the unit id
	 * @param tempUnitId the temp unit id
	 * @param unitName the unit name
	 * @param unitSymbol the unit symbol
	 */
	public UnitJSON(int unitId, int tempUnitId, String unitName, String unitSymbol) {
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitSymbol = unitSymbol;
		this.tempUnitId = tempUnitId;
	}
	
	/**
	 * Gets the temp unit id.
	 *
	 * @return the temp unit id
	 */
	public int getTempUnitId() {
		return tempUnitId;
	}

	/**
	 * Gets the unit id.
	 *
	 * @return the unit id
	 */
	public int getUnitId() {
		return unitId;
	}

	/**
	 * Gets the unit name.
	 *
	 * @return the unit name
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * Gets the unit symbol.
	 *
	 * @return the unit symbol
	 */
	public String getUnitSymbol() {
		return unitSymbol;
	}
}
