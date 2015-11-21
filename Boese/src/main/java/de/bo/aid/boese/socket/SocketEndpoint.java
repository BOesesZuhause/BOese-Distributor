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

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



// TODO: Auto-generated Javadoc
/**
 * The Class BoeseServer.
 */

//every connection creates a new SocketEndpoint Object. Therefore there is the SocketServer-Wrapper
@ClientEndpoint 
@ServerEndpoint("/events/")
public class SocketEndpoint
{
	
	/** The handler. */
	private SessionHandler handler = SessionHandler.getInstance();

	/** The Constant logger for log4j. */
	final  Logger logger = LogManager.getLogger(SocketEndpoint.class);
	
	/**
	 * Open.
	 *
	 * @param session the session
	 */
	@OnOpen
	public void open(Session session) {	
		handler.addSession(session);
		logger.info("Socket connected: " + session);
	}

	/**
	 * Close.
	 *
	 * @param session the session
	 */
	@OnClose
	public void close(Session session) {
		int conId = handler.getConnectorId(session);
		handler.removeSession(session);
		if(conId == -1){
			logger.info("Unknown Socket disconnected: " + session);
		}else{
			logger.info("Connector with id: " + conId + "disconnected");
		}
	}

	/**
	 * On error.
	 *
	 * @param error the error
	 */
	@OnError
	public void onError(Throwable error) {
		logger.error("Websocketerror:");
		error.printStackTrace();
	}

	/**
	 * Handle message.
	 *
	 * @param message the message
	 * @param session the session
	 */
	@OnMessage
	public void handleMessage(String message, Session session) {
		logger.info("Server received Message: " + message);
		if(message.equals("HEARTBEAT")){
			handler.handleHeartbeat(session);
		}else{
			SocketServer.getInstance().handleMessage(message, handler.getConnectorId(session));
		}
	}
}