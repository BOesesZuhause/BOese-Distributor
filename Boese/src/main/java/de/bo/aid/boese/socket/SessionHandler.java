/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 */
package de.bo.aid.boese.socket;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.bo.aid.boese.main.Distributor;

/**
 * THe Sessionhandler manages all connected sessions and additional data.
 */
public class SessionHandler {
	
	/** The missed answer threshold. */
	private int missedAnswerThreshold = 5;
	
	/** The Constant logger for log4j. */
	final  Logger logger = LogManager.getLogger(SessionHandler.class);
	
	/** Saves the sessions as sessiondata-objects. We use a threadsafe list because the heartbeat-thread needs to check the sessions.
	 * The performance-drawback is minimal because the sessions are read more often than manipulated. */
	private final CopyOnWriteArrayList<SessionData> sessions = new CopyOnWriteArrayList<SessionData>();
	
	/** The singleton instance. */ 
	private static SessionHandler instance = new SessionHandler();
	
	/** The current id that is given to temp-connectors. */
	private static int currentId;
	
	/**
	 * Instantiates a new socket handler.
	 */
	private SessionHandler(){
		currentId = -1000;
	}
	
	/**
	 * Gets the connector id for a specific session.
	 *
	 * @param session the session
	 * @return the connector id
	 */
	public int getConnectorId(Session session) {
		int conId = -1;
		for(SessionData data : sessions){
			if(data.getSession().equals(session)){
				conId = data.getId();
			}
		}	
		return conId;
	}
	
	/**
	 * Rejects connection and closes it.
	 *
	 * @param connectorId the connector id of the session
	 */
	public void rejectConnection(int connectorId) {
			SessionData data = getDataByConnector(connectorId);
			if(data != null){
				try {
					data.getSession().close();
				} catch (IOException e) {
					logger.error("Could not close connection to connector with id: " + connectorId, e);
				}
				sessions.remove(data);
			}
	}
	
	/**
	 * Adds the session to the list and sets a tempid for it.
	 *
	 * @param session the session to be added
	 * @return the id given to the session
	 */
	public int addSession(Session session) {
		SessionData data = new SessionData();
		data.setSession(session);
		data.setId(--currentId);
		sessions.add(data);
		return currentId;
	}
	
	/**
	 * Replaces the tempId with a new one and adds the connectortype.
	 *
	 * @param tmpId the temp id
	 * @param newId the new id
	 * @param isUserConnector Boolean to determine if it is a userconnector
	 */
	public void setConnectorId(int tmpId, int newId, boolean isUserConnector) {
		SessionData data = getDataByConnector(tmpId);
		if(data != null){
			data.setId(newId);
			data.setUserConnector(isUserConnector);
		}
	}
	
	/**
	 * Removes the session from the list.
	 *
	 * @param session the session to be removed
	 */
	public void removeSession(Session session){
		SessionData data = getDataBySession(session);
		if(data != null){
			sessions.remove(data);	
		}
	}
	
	/**
	 * Sends a message to a specific connector.
	 *
	 * @param connectorId the connector id
	 * @param message the message to be sent
	 */
	public void sendToConnector(int connectorId, String message) {
		SessionData data = getDataByConnector(connectorId);
		if(data != null){
			sendToSession(data.getSession(), message);
		}
    }
	
	/**
	 * Sends a message to a specific session.
	 *
	 * @param session the session
	 * @param message the message to be sent
	 */
	public void sendToSession(Session session, String message) {
        try {
        	logger.info("Message sent: " + message);
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            sessions.remove(getDataBySession(session));
            logger.warn("Could not send the message to session: " + session, ex);
        }
    }
	
	 /**
 	 * Sends a message to all connected sessions.
 	 *
 	 * @param message the message to be send
 	 */
 	public void sendToAllConnectedSessions(String message) {
 		for(SessionData data : sessions){
 			sendToSession(data.getSession(), message);
 		}
	 }

	/**
	 * Gets the single instance of SocketHandler.
	 *
	 * @return single instance of SocketHandler
	 */
	public static SessionHandler getInstance() {
		return instance;
	}
	

	/**
	 * Sets the timestamp for the last heartbeat and resets the missed answers of a connector.
	 *
	 * @param connectorId the connector id
	 */
	public void handleHeartbeat(int connectorId) {
				SessionData data = getDataByConnector(connectorId);
				if(data != null){
					data.setLastHeartbeat(System.currentTimeMillis());
					data.setMissedAnswers(0);
				}
	}
	
	/**
	 * Gets the data by a specific session.
	 *
	 * @param session the session
	 * @return the data of the session. Returns null if the session is not found.
	 */
	public SessionData getDataBySession(Session session){
		for(SessionData data : sessions){
			if(data.getSession().equals(session)){
				return data;
			}
		}
		logger.error("Could not find session: " + session);
		return null;
	}
	
	
	/**
	 * Gets the data by a specific connectorid.
	 *
	 * @param connectorId the connector id
	 * @return the data of the session. Returns null if the session is not found.
	 */
	public SessionData getDataByConnector(int connectorId){
		for(SessionData data : sessions){
			if(data.getId() == connectorId){
				return data;
			}
		}
		logger.error("Could not find session for connector with id: " + connectorId);
		return null;
	}
	
	/**
	 * Gets the type of the connector.
	 *
	 * @param connectorId the connector id
	 * @return true if the connector is a user connector, or false if the connector is no user connector or does not exist
	 */
	public boolean getIsUserConnectorByConnector(int connectorId){
		for(SessionData data : sessions){
			if(data.getId() == connectorId){
				return data.isUserConnector();
			}
		}
		logger.error("Could not find session for connector with id: " + connectorId);
		return false;
	}

	/**
	 * checks the heartbeat-parameters for each session. IS called by the heartbeat-thread in a given interval.
	 * If a connector hasn't answered the heartbeat the missed answer count is increased. If the missed answers exceeds the
	 * missed answer threshold, the connector is disconnectd.
	 */
	public void checkHeartbeat(){
		for(SessionData data : sessions){
			long now = System.currentTimeMillis();
			if((now - data.getLastHeartbeat()) > HeartbeatWorker.getInterval()){
				if(data.getMissedAnswers() >= missedAnswerThreshold){
					try {
						data.getSession().close();
					} catch (IOException e) {
						logger.warn("Unable to close session: " + data.getSession(), e);
					}
					//TODO Die ConnectorId kann danach nicht mehr ermittelt werden für die close()-Nachricht
					//TODO boolean einführen ob noch Nachrichten gesendet werden sollen
					sessions.remove(data);
					Distributor.getInstance().removeTempsByConnector(data.getId());
					logger.warn("Connector with id: " + data.getId() + " exceeded Heartbeat-Threshold");
				}else{
					logger.warn("Connector with id: " + data.getId() + " does not respond");
					data.setMissedAnswers(data.getMissedAnswers() + 1);
				}
			}
		}
	}

	/**
	 * Gets the missed answer threshold.
	 *
	 * @return the missed answer threshold
	 */
	public int getMissedAnswerThreshold() {
		return missedAnswerThreshold;
	}

	/**
	 * Sets the missed answer threshold.
	 *
	 * @param missedAnswerThreshold the new missed answer threshold
	 */
	public void setMissedAnswerThreshold(int missedAnswerThreshold) {
		this.missedAnswerThreshold = missedAnswerThreshold;
	}

	/**
	 * Gets the list with the manages sessions.
	 *
	 * @return the sessions
	 */
	public CopyOnWriteArrayList<SessionData> getSessions() {
		return sessions;
	}

	/**
	 * Sets the state of a connector to user connector.
	 *
	 * @param coId the connectorid
	 */
	public void setUserConnector(int coId) {
		SessionData data = getDataByConnector(coId);
		if(data != null){
			data.setUserConnector(true);
		}
	}
	
	/**
	 * Checks if user connectors are connected.
	 *
	 * @return true, if at least one user connector is connected.
	 */
	public boolean hasUserConnectors(){
	       for(SessionData data : sessions){
	            if(data.isUserConnector()){
	                return true;
	            }
	        }
	       return false;
	}

	/**
	 * Sends a message to all user connectors.
	 *
	 * @param message the message to be sent
	 */
	public void sendToUserConnectors(String message){
		for(SessionData data : sessions){
			if(data.isUserConnector()){
				sendToSession(data.getSession(), message);
			}
		}
	}
}
