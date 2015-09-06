
package de.bo.aid.boese.model;
// Generated 30.07.2015 11:14:41 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * HistoryLogDeviceComponent generated by hbm2java.
 */
public class HistoryLogDeviceComponent implements java.io.Serializable {

	/** The hi lo co id. */
	private int hiLoCoId;
	
	/** The timestamp. */
	private Date timestamp;
	
	/** The device component. */
	private DeviceComponent deviceComponent;
	
	/** The value. */
	private BigDecimal value;

	/**
	 * Instantiates a new history log device component.
	 */
	public HistoryLogDeviceComponent() {
	}

	/**
	 * Instantiates a new history log device component.
	 *
	 * @param hiLoCoId the hi lo co id
	 * @param deviceComponent the device component
	 * @param value the value
	 */
	public HistoryLogDeviceComponent(int hiLoCoId, DeviceComponent deviceComponent, BigDecimal value) {
		this.hiLoCoId = hiLoCoId;
		this.deviceComponent = deviceComponent;
		this.value = value;
	}

	/**
	 * Gets the hi lo co id.
	 *
	 * @return the hi lo co id
	 */
	public int getHiLoCoId() {
		return this.hiLoCoId;
	}

	/**
	 * Sets the hi lo co id.
	 *
	 * @param hiLoCoId the new hi lo co id
	 */
	public void setHiLoCoId(int hiLoCoId) {
		this.hiLoCoId = hiLoCoId;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the device component.
	 *
	 * @return the device component
	 */
	public DeviceComponent getDeviceComponent() {
		return this.deviceComponent;
	}

	/**
	 * Sets the device component.
	 *
	 * @param deviceComponent the new device component
	 */
	public void setDeviceComponent(DeviceComponent deviceComponent) {
		this.deviceComponent = deviceComponent;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public BigDecimal getValue() {
		return this.value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
