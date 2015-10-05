
package de.bo.aid.boese.model;
// Generated 30.07.2015 11:14:41 by Hibernate Tools 4.3.1

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * DeviceComponenteReplace generated by hbm2java.
 */
public class DeviceComponenteReplace implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** The id. */
	private DeviceComponenteReplaceId id;
	
	/** The device component by de co id. */
	private DeviceComponent deviceComponentByDeCoId;
	
	/** The device component by de co idreplaced. */
	private DeviceComponent deviceComponentByDeCoIdreplaced;
	
	/** The timestap. */
	private Date timestap;

	/**
	 * Instantiates a new device componente replace.
	 */
	public DeviceComponenteReplace() {
	}

	/**
	 * Instantiates a new device componente replace.
	 *
	 * @param id the id
	 * @param deviceComponentByDeCoId the device component by de co id
	 * @param deviceComponentByDeCoIdreplaced the device component by de co idreplaced
	 * @param timestap the timestap
	 */
	public DeviceComponenteReplace(DeviceComponenteReplaceId id, DeviceComponent deviceComponentByDeCoId,
			DeviceComponent deviceComponentByDeCoIdreplaced, Date timestap) {
		this.id = id;
		this.deviceComponentByDeCoId = deviceComponentByDeCoId;
		this.deviceComponentByDeCoIdreplaced = deviceComponentByDeCoIdreplaced;
		this.timestap = timestap;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public DeviceComponenteReplaceId getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(DeviceComponenteReplaceId id) {
		this.id = id;
	}

	/**
	 * Gets the device component by de co id.
	 *
	 * @return the device component by de co id
	 */
	public DeviceComponent getDeviceComponentByDeCoId() {
		return this.deviceComponentByDeCoId;
	}

	/**
	 * Sets the device component by de co id.
	 *
	 * @param deviceComponentByDeCoId the new device component by de co id
	 */
	public void setDeviceComponentByDeCoId(DeviceComponent deviceComponentByDeCoId) {
		this.deviceComponentByDeCoId = deviceComponentByDeCoId;
	}

	/**
	 * Gets the device component by de co idreplaced.
	 *
	 * @return the device component by de co idreplaced
	 */
	public DeviceComponent getDeviceComponentByDeCoIdreplaced() {
		return this.deviceComponentByDeCoIdreplaced;
	}

	/**
	 * Sets the device component by de co idreplaced.
	 *
	 * @param deviceComponentByDeCoIdreplaced the new device component by de co idreplaced
	 */
	public void setDeviceComponentByDeCoIdreplaced(DeviceComponent deviceComponentByDeCoIdreplaced) {
		this.deviceComponentByDeCoIdreplaced = deviceComponentByDeCoIdreplaced;
	}

	/**
	 * Gets the timestap.
	 *
	 * @return the timestap
	 */
	public Date getTimestap() {
		return this.timestap;
	}

	/**
	 * Sets the timestap.
	 *
	 * @param timestap the new timestap
	 */
	public void setTimestap(Date timestap) {
		this.timestap = timestap;
	}

}
