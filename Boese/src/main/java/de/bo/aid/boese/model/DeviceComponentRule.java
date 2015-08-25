package de.bo.aid.boese.model;

public class DeviceComponentRule {

	private DeviceComponentRuleId id;
	private DeviceComponent devicecomponent;
	private Rule rule;

	public DeviceComponentRule() {
	}

	public DeviceComponentRule(DeviceComponentRuleId id, DeviceComponent devicecomponent, Rule rule) {
		this.id = id;
		this.devicecomponent = devicecomponent;
		this.rule = rule;
	}

	public DeviceComponentRuleId getId() {
		return this.id;
	}

	public void setId(DeviceComponentRuleId id) {
		this.id = id;
	}

	public DeviceComponent getDevicecomponent() {
		return this.devicecomponent;
	}

	public void setDevicecomponent(DeviceComponent devicecomponent) {
		this.devicecomponent = devicecomponent;
	}

	public Rule getRule() {
		return this.rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

}
