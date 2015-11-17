package de.bo.aid.boese.socket;

import javax.websocket.Session;

public class SessionData {
	
	private int id;
	private int missedAnswers;
	private long lastHeartbeat;
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
	public long getLastHeartbeat() {
		return lastHeartbeat;
	}
	public void setLastHeartbeat(long lastHeartbeat) {
		this.lastHeartbeat = lastHeartbeat;
	}
	public int getMissedAnswers() {
		return missedAnswers;
	}
	public void setMissedAnswers(int missedAnswers){
		this.missedAnswers = missedAnswers;
	}
	
	
	

}
