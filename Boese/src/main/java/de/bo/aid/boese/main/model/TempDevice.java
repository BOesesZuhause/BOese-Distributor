package de.bo.aid.boese.main.model;

public class TempDevice {
	private String name;
	private int connectorID;
	
	public TempDevice(int connectorId, String name) {
		this.name = name;
		this.connectorID = connectorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getConnectorID() {
		return connectorID;
	}

	public void setConnectorID(int connectorID) {
		this.connectorID = connectorID;
	}

}
