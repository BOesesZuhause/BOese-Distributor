package de.bo.aid.boese.socket;

public interface MessageHandler {
	
	public void handleMessage(String message, int connectorId);

}
