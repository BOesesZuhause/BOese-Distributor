/*
 * 
 */
package de.bo.aid.boese.socket;

import javax.websocket.Session;

// TODO: Auto-generated Javadoc
/**
 * The Class SessionData.
 */
public class SessionData {
	
	/** The id. */
	private int id;
	
	/**  The type of the connector is cached here to quickly determine if it is a user connector. */
	private boolean isUserConnector = false;
	
	/** The missed answers. */
	private int missedAnswers;
	
	/** The last heartbeat. */
	private long lastHeartbeat;
	
	/** The session. */
	private Session session;
	
	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}
	
	/**
	 * Sets the session.
	 *
	 * @param session the new session
	 */
	public void setSession(Session session) {
		this.session = session;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	/**
	 * Checks if is user connector.
	 *
	 * @return true, if is user connector
	 */
	public boolean isUserConnector() {
		return isUserConnector;
	}

	/**
	 * Sets the user connector.
	 *
	 * @param isUserConnector the new user connector
	 */
	public void setUserConnector(boolean isUserConnector) {
		this.isUserConnector = isUserConnector;
	}

	/**
	 * Gets the last heartbeat.
	 *
	 * @return the last heartbeat
	 */
	public long getLastHeartbeat() {
		return lastHeartbeat;
	}
	
	/**
	 * Sets the last heartbeat.
	 *
	 * @param lastHeartbeat the new last heartbeat
	 */
	public void setLastHeartbeat(long lastHeartbeat) {
		this.lastHeartbeat = lastHeartbeat;
	}
	
	/**
	 * Gets the missed answers.
	 *
	 * @return the missed answers
	 */
	public int getMissedAnswers() {
		return missedAnswers;
	}
	
	/**
	 * Sets the missed answers.
	 *
	 * @param missedAnswers the new missed answers
	 */
	public void setMissedAnswers(int missedAnswers){
		this.missedAnswers = missedAnswers;
	}
	
	
	

}
