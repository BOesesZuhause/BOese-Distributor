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

import de.bo.aid.boese.main.Distributor;



// TODO: Auto-generated Javadoc
/**
 * The Endpoint-class for Jetty. Here are all websocket-endpoints defined.
 * every connection has its own socketendpoint object. 
 */

//every connection creates a new SocketEndpoint Object. Therefore there is the SocketServer-Wrapper
@ClientEndpoint 
@ServerEndpoint("/events/")
public class SocketEndpoint
{
	
	/** The instance of the sessionhandler. It is used to manage the connected sessions */
	private SessionHandler handler = SessionHandler.getInstance();

	/** The Constant logger for log4j. */
	final  Logger logger = LogManager.getLogger(SocketEndpoint.class);
	
	/**
	 * THis method is called when a new session is opened. The connected session is added to the sessionhandler.
	 *
	 * @param session the connected session
	 */
	@OnOpen
	public void open(Session session) {	
		handler.addSession(session);
		logger.info("Socket connected: " + session);
	}

	/**
	 * Is called when a session is closed. THe session is then removed from the sessionhandler.
	 *
	 * @param session the closed session
	 */
	@OnClose
	public void close(Session session) {
		SessionData data = SessionHandler.getInstance().getDataBySession(session);		
		if(data == null){
			logger.info("Unknown Connector disconnected session: " + session);
		}else{
			Distributor.getInstance().removeTempsByConnector(data.getId());	
			logger.info("Connector with id: " + data.getId() + " disconnected");
		}
		handler.removeSession(session);
		
	}

	/**
	 * Is called if an error occured. Logs the error-message.
	 *
	 * @param error the error
	 */
	@OnError
	public void onError(Throwable error) {
		logger.error("Websocketerror:", error);
	}

	/**
	 * Is called when a message is received. The message is forwarded to the messagehandler of the socketserver.
	 *
	 * @param message the message
	 * @param session the session
	 */
	@OnMessage
	public void handleMessage(String message, Session session) {
		logger.info("Server received Message: " + message);
		SocketServer.getInstance().handleMessage(message, handler.getConnectorId(session));
	}
}