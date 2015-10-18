


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


// TODO: Auto-generated Javadoc
/**
 * The Class BoeseServer.
 */

@ClientEndpoint
@ServerEndpoint("/events/")
public class SocketEndpoint
{
	
//	private static SocketEndpoint instance = new SocketEndpoint();
//	
//	private SocketEndpoint(){};
//	
//	public static SocketEndpoint getInstance(){
//		return instance;
//	}
	

	
	/** The handler. */
	private SessionHandler handler = SessionHandler.getInstance();

	
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
		Logger.getLogger(SocketEndpoint.class.getName()).log(Level.SEVERE, null, error);
	}

	/**
	 * Handle message.
	 *
	 * @param message the message
	 * @param session the session
	 */
	@OnMessage
	public void handleMessage(String message, Session session) {
		System.out.println("Server received Message: " + message);
		SocketServer.getInstance().handleMessage(message, handler.getConnectorId(session));
	}
}