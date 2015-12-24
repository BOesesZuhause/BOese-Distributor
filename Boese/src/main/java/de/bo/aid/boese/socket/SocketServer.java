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

import javax.websocket.server.ServerContainer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

/**
 * This class wraps the jetty-websocket-server and embedds it.
 */
public class SocketServer {
    
    /** The Constant logger for log4j. */
    final  Logger logger = LogManager.getLogger(SocketServer.class);
	
	/** The singleton instance of the class. */
	private static SocketServer instance = new SocketServer();

    /** The jetty-server-object. */
    private Server server;
    
    /** The registered messagehandler. */
    private MessageHandler messageHandler;
	
	/**
	 * Instantiates a new socket server.
	 */
	private SocketServer(){
	    
	};


	/**
	 * Gets the message handler.
	 *
	 * @return the message handler
	 */
	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	/**
	 * Registers a new messagehandler.
	 *
	 * @param messageHandler the new message handler
	 */
	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	/**
	 * Starts the websocket-server with the give port.
	 *
	 * @param port the port to listen on
	 */
	public void start(int port)
    {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        try
        {
            // Initialize javax.websocket layer
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);

            // Add WebSocket endpoint to javax.websocket layer
            wscontainer.addEndpoint(SocketEndpoint.class);

            server.start();
            logger.debug(server.dump());
        }
        catch (Exception e)
        {
            logger.error("Error while starting the websocket-server", e);
        }
    }
	
	/**
	 * Stops the running websocket-server.
	 */
	public void stop(){
		try {
			server.stop();
		} catch (Exception e) {
		    logger.error("Error while stopping the websocket-server", e);
		}
	}

	/**
	 * Gets the single instance of SocketServer.
	 *
	 * @return single instance of SocketServer
	 */
	public static SocketServer getInstance() {
		return instance ;
	}


	/**
	 * Forwards received messages to the messagehandler.
	 *
	 * @param message the received message
	 * @param connectorId the connector id
	 */
	public void handleMessage(String message, int connectorId) {
		messageHandler.handleMessage(message, connectorId);		
	}

}
