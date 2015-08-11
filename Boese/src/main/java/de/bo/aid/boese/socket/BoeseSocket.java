package de.bo.aid.boese.socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import de.bo.aid.boese.main.MainClass;

@ClientEndpoint
@ServerEndpoint("/events/")
public class BoeseSocket {
	
	
	SocketHandler handler = SocketHandler.getInstance();
	@OnOpen
	public void open(Session session) {
		System.out.println("Socket Connected: " + session);
		handler.addSession(session);
	}

	@OnClose
	public void close(Session session) {
		handler.removeSession(session);
	}

	@OnError
	public void onError(Throwable error) {
		Logger.getLogger(BoeseSocket.class.getName()).log(Level.SEVERE, null, error);
	}

	@OnMessage
	public void handleMessage(String message, Session session) {
		//TODO observer pattern
		System.out.println("Server received Message: " + message);
		MainClass.handleMessage(message, handler.getConnectorId(session));
	}
}
