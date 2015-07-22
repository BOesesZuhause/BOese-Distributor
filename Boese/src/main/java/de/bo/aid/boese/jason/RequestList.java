package de.bo.aid.boese.jason;

public class RequestList extends BoeseJson {
	private int connectorID;
	private int zoneID;
	private int serviceID;
	
	public RequestList(int connector, int zone, int service) {
		this.connectorID = connector;
		this.zoneID = zone;
		this.serviceID = service;
		this.messageType = MessageType.REQUESTLIST;
	}
	
	public int getConnectorId() {
		return connectorID;
	}
	
	public int getZoneId() {
		return zoneID;
	}
	
	public int getServiceId() {
		return serviceID;
	}
}
