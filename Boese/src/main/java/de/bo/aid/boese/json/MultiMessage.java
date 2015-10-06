package de.bo.aid.boese.json;

import java.util.HashSet;

public class MultiMessage extends BoeseJson {
	private HashSet<BoeseJson> boeses;
	
	public MultiMessage(HashSet<BoeseJson> boese, int connectorId, int status, long timestamp) {
		super(MessageType.MULTI, connectorId, status, timestamp);
		this.boeses = boese;
	}
	
	public MultiMessage(int connectorId, int status, long timestamp) {
		super(MessageType.MULTI, connectorId, status, timestamp);
		this.boeses = new HashSet<>();
	}
	
	public void addMessage(BoeseJson message ){
		boeses.add(message);
	}
	
	public HashSet<BoeseJson> getMessages() {
		return boeses;
	}


}
