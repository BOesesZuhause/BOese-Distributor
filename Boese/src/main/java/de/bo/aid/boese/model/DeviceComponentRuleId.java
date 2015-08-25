package de.bo.aid.boese.model;

public class DeviceComponentRuleId implements java.io.Serializable {

	private int deCoId;
	private int ruId;

	public DeviceComponentRuleId() {
	}

	public DeviceComponentRuleId(int deCoId, int ruId) {
		this.deCoId = deCoId;
		this.ruId = ruId;
	}

	public int getDeCoId() {
		return this.deCoId;
	}

	public void setDeCoId(int deCoId) {
		this.deCoId = deCoId;
	}

	public int getRuId() {
		return this.ruId;
	}

	public void setRuId(int ruId) {
		this.ruId = ruId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DeviceComponentRuleId))
			return false;
		DeviceComponentRuleId castOther = (DeviceComponentRuleId) other;

		return (this.getDeCoId() == castOther.getDeCoId()) && (this.getRuId() == castOther.getRuId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getDeCoId();
		result = 37 * result + this.getRuId();
		return result;
	}
}
