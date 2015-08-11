package de.bo.aid.boese.socket;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.websocket.Session;

import javassist.bytecode.ByteArray;


public class SocketHandler {
	
	private final HashMap<Integer, Session> sessions = new HashMap<>();
	private static SocketHandler instance = new SocketHandler();
	private static int currentId;
	
	private SocketHandler(){
		currentId = -1000;
	}
	
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
	
	public int addSession(Session session) {
		sessions.put(new Integer(--currentId), session);
		return currentId;
	}
	
	public void setConnectorId(int tmpId, int newId) {
		sessions.put(new Integer(newId), sessions.get(new Integer(tmpId)));
		sessions.remove(new Integer(tmpId));
	}
	
	public void removeSession(Session session){
		sessions.remove(session);
	}
	
	//TODO error handling
	public void sendToConnector(int connectorId, String message) {
        sendToSession(sessions.get(new Integer(connectorId)), message);
    }
	
	public void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	 public void sendToAllConnectedSessions(String message) {
	    for (Session session : sessions.values()) {
	        sendToSession(session, message);
	    }
	 }

	public static SocketHandler getInstance() {
		return instance;
	}

}
