package de.bo.aid.boese.socket;

import javax.websocket.Session;

public class SessionData {
	
	private int id;
	private int lastHeartbeat;
	private Session session;
	
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLastHeartbeat() {
		return lastHeartbeat;
	}
	public void setLastHeartbeat(int lastHeartbeat) {
		this.lastHeartbeat = lastHeartbeat;
	}
	
	
	

}
