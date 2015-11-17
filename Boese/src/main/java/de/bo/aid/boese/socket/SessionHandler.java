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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class SocketHandler.
 */
public class SessionHandler {
	
	/** The Constant logger for log4j. */
	final  Logger logger = LogManager.getLogger(SessionHandler.class);
	
	private final List<SessionData> sessions = new ArrayList<SessionData>();
	
	/** The instance. */ //TODO eventuell HashSet mit ID als key für mehr Performance
	private static SessionHandler instance = new SessionHandler();
	
	/** The current id. */
	private static int currentId;
	
	/**
	 * Instantiates a new socket handler.
	 */
	private SessionHandler(){
		currentId = -1000;
	}
	
	/**
	 * Gets the connector id.
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
	 * Reject connection.
	 *
	 * @param connectorId the connector id
	 */
	public void rejectConnection(int connectorId) {
		for(SessionData data : sessions){
			if(data.getId() == connectorId){
				try {
					data.getSession().close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sessions.remove(data);
			}
		}
	}
	
	/**
	 * Adds the session.
	 *
	 * @param session the session
	 * @return the int
	 */
	public int addSession(Session session) {
		SessionData data = new SessionData();
		data.setSession(session);
		data.setId(--currentId);
		sessions.add(data);
		return currentId;
	}
	
	/**
	 * Sets the connector id.
	 *
	 * @param tmpId the tmp id
	 * @param newId the new id
	 */
	public void setConnectorId(int tmpId, int newId) {
		for(SessionData data : sessions){
			if(data.getId()==tmpId){
				data.setId(newId);
			}
		}
	}
	
	/**
	 * Removes the session.
	 *
	 * @param session the session
	 */
	public void removeSession(Session session){
		sessions.remove(session);
	}
	
	/**
	 * Send to connector.
	 *
	 * @param connectorId the connector id
	 * @param message the message
	 */
	//TODO error handling
	public void sendToConnector(int connectorId, String message) {
		for(SessionData data : sessions){
			if(data.getId()==connectorId){
				sendToSession(data.getSession(), message);
			}
		}
    }
	
	/**
	 * Send to session.
	 *
	 * @param session the session
	 * @param message the message
	 */
	public void sendToSession(Session session, String message) {
        try {
        	logger.info("Message sent: " + message);
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            sessions.remove(session);
            logger.warn("Could not send the message to session: " + session);
        }
    }
	
	 /**
 	 * Send to all connected sessions.
 	 *
 	 * @param message the message
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

}
