package de.bo.aid.boese.model;

import java.util.Date;

public class ToDo {
	
	/** The ToDoid. */
	private int toDoId;
	
	/** The time when it will be executed */
	private Date date;
	
	/** The DeviceComponent. */
	private DeviceComponent deviceComponent;
	
	/** The Rule. */
	private Rule rule;
	
	/** Is it active */
	private boolean active;
	
	/** The Repeat Rule */
	private RepeatRule repeatRule;

	public ToDo() {
	}

	public ToDo(int toDoId, Date date, boolean active) {
		this.toDoId = toDoId;
		this.date = date;
		this.active = active;
	}

	public ToDo(int toDoId, Date date, DeviceComponent deviceComponent, Rule rule, boolean active,
			RepeatRule repeatRule) {
		this.toDoId = toDoId;
		this.date = date;
		this.deviceComponent = deviceComponent;
		this.rule = rule;
		this.active = active;
		this.repeatRule = repeatRule;
	}

	public int getToDoId() {
		return toDoId;
	}

	public void setToDoId(int toDoId) {
		this.toDoId = toDoId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DeviceComponent getDeviceComponent() {
		return deviceComponent;
	}

	public void setDeviceComponent(DeviceComponent deviceComponent) {
		this.deviceComponent = deviceComponent;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public RepeatRule getRepeatRule() {
		return repeatRule;
	}

	public void setRepeatRule(RepeatRule repeatRule) {
		this.repeatRule = repeatRule;
	}

}
