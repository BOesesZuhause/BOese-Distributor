
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

// TODO: Auto-generated Javadoc
/**
 * The Class BoeseSocket.
 */
@ClientEndpoint
@ServerEndpoint("/events/")
public class BoeseSocket {
	
	
	/** The handler. */
	SocketHandler handler = SocketHandler.getInstance();
	
	/**
	 * Open.
	 *
	 * @param session the session
	 */
	@OnOpen
	public void open(Session session) {
		System.out.println("Socket Connected: " + session);
		handler.addSession(session);
	}

	/**
	 * Close.
	 *
	 * @param session the session
	 */
	@OnClose
	public void close(Session session) {
		handler.removeSession(session);
	}

	/**
	 * On error.
	 *
	 * @param error the error
	 */
	@OnError
	public void onError(Throwable error) {
		Logger.getLogger(BoeseSocket.class.getName()).log(Level.SEVERE, null, error);
	}

	/**
	 * Handle message.
	 *
	 * @param message the message
	 * @param session the session
	 */
	@OnMessage
	public void handleMessage(String message, Session session) {
		//TODO observer pattern
		System.out.println("Server received Message: " + message);
		MainClass.handleMessage(message, handler.getConnectorId(session));
	}
}
