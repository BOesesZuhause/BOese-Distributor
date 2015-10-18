

package de.bo.aid.boese.model;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class ToDo.
 */
public class ToDo {
	
	/** The ToDoid. */
	private int toDoId;
	
	/**  The time when it will be executed. */
	private Date date;
	
	/** The DeviceComponent. */
	private DeviceComponent deviceComponent;
	
	/** The Rule. */
	private Rule rule;
	
	/**  Is it active. */
	private boolean active;
	
	/**  The Repeat Rule. */
	private RepeatRule repeatRule;

	/**
	 * Instantiates a new to do.
	 */
	public ToDo() {
	}

	/**
	 * Instantiates a new to do.
	 *
	 * @param toDoId the to do id
	 * @param date the date
	 * @param active the active
	 */
	public ToDo(int toDoId, Date date, boolean active) {
		this.toDoId = toDoId;
		this.date = date;
		this.active = active;
	}

	/**
	 * Instantiates a new to do.
	 *
	 * @param toDoId the to do id
	 * @param date the date
	 * @param deviceComponent the device component
	 * @param rule the rule
	 * @param active the active
	 * @param repeatRule the repeat rule
	 */
	public ToDo(int toDoId, Date date, DeviceComponent deviceComponent, Rule rule, boolean active,
			RepeatRule repeatRule) {
		this.toDoId = toDoId;
		this.date = date;
		this.deviceComponent = deviceComponent;
		this.rule = rule;
		this.active = active;
		this.repeatRule = repeatRule;
	}

	/**
	 * Gets the to do id.
	 *
	 * @return the to do id
	 */
	public int getToDoId() {
		return toDoId;
	}

	/**
	 * Sets the to do id.
	 *
	 * @param toDoId the new to do id
	 */
	public void setToDoId(int toDoId) {
		this.toDoId = toDoId;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the device component.
	 *
	 * @return the device component
	 */
	public DeviceComponent getDeviceComponent() {
		return deviceComponent;
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
	 * Gets the rule.
	 *
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * Sets the rule.
	 *
	 * @param rule the new rule
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the repeat rule.
	 *
	 * @return the repeat rule
	 */
	public RepeatRule getRepeatRule() {
		return repeatRule;
	}

	/**
	 * Sets the repeat rule.
	 *
	 * @param repeatRule the new repeat rule
	 */
	public void setRepeatRule(RepeatRule repeatRule) {
		this.repeatRule = repeatRule;
	}

}
