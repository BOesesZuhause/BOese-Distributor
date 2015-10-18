

package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class UserTempComponent.
 */
public class UserTempComponent {
	
	/** The temp component id. */
	private int tempComponentId;
	
	/** The unit id. */
	private int unitId;
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new user temp component.
	 *
	 * @param tempComponentId the temp component id
	 * @param unitId the unit id
	 * @param name the name
	 */
	public UserTempComponent(int tempComponentId, int unitId, String name) {
		this.tempComponentId = tempComponentId;
		this.unitId = unitId;
		this.name = name;
	}

	/**
	 * Gets the temp component id.
	 *
	 * @return the temp component id
	 */
	public int getTempComponentId() {
		return tempComponentId;
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
}
