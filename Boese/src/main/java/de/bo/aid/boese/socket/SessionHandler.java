


package de.bo.aid.boese.socket;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;


// TODO: Auto-generated Javadoc
/**
 * The Class SocketHandler.
 */
public class SessionHandler {
	
	/** The sessions. */
	private final HashMap<Integer, Session> sessions = new HashMap<>();
	
	/** The instance. */
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
		Iterator<Integer> it = sessions.keySet().iterator();
		while (it.hasNext()) {
			Integer cId = it.next();
			if (sessions.get(cId).equals(session)) {
				conId = cId.intValue();
				break;
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
		Session session = sessions.get(new  Integer(connectorId));
		try {
			session.close();
		} catch (IOException e) {
			// TODO!!!!
			e.printStackTrace();
		}
		sessions.remove(new  Integer(connectorId));
	}
	
	/**
	 * Adds the session.
	 *
	 * @param session the session
	 * @return the int
	 */
	public int addSession(Session session) {
		sessions.put(new Integer(--currentId), session);
		return currentId;
	}
	
	/**
	 * Sets the connector id.
	 *
	 * @param tmpId the tmp id
	 * @param newId the new id
	 */
	public void setConnectorId(int tmpId, int newId) {
		sessions.put(new Integer(newId), sessions.get(new Integer(tmpId)));
		sessions.remove(new Integer(tmpId));
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
        sendToSession(sessions.get(new Integer(connectorId)), message);
    }
	
	/**
	 * Send to session.
	 *
	 * @param session the session
	 * @param message the message
	 */
	public void sendToSession(Session session, String message) {
        try {
        	System.out.println("Message sent: " + message);
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(SessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	 /**
 	 * Send to all connected sessions.
 	 *
 	 * @param message the message
 	 */
 	public void sendToAllConnectedSessions(String message) {
	    for (Session session : sessions.values()) {
	        sendToSession(session, message);
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
