package de.bo.aid.boese.socket;
import java.io.IOException;


import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.websocket.Session;


public class SocketHandler {
	
	private final Set<Session> sessions = new HashSet<Session>();
	private static SocketHandler instance = new SocketHandler();
	
	private SocketHandler(){
	}
	
	
	public void addSession(Session session){
		sessions.add(session);
	}
	
	public void removeSession(Session session){
		sessions.remove(session);
	}
	
	
	private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	 private void sendToAllConnectedSessions(JsonObject message) {
	        for (Session session : sessions) {
	            sendToSession(session, message);
	        }
	    }

	public static SocketHandler getInstance() {
		return instance;
	}

}
