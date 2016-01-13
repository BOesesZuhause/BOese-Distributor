/*
 * 				     )                  
 *		      (   ( /(                  
 *		    ( )\  )\())    (        (   
 *		    )((_)((_)\    ))\ (    ))\  
 *		   ((_)_   ((_)  /((_))\  /((_) 
 *			| _ ) / _ \ (_)) ((_)(_))   
 *			| _ \| (_) |/ -_)(_-</ -_)  
 *			|___/ \___/ \___|/__/\___|  
 *                            
 *
 *
 *
 *            			;            
 * 		      +        ;;;        + 
 * 			  +       ;;;;;       + 
 * 			  +      ;;;;;;;      + 
 * 			  ++    ;;;;;;;;;    ++ 
 * 			  +++++;;;;;;;;;;;+++++  
 * 			   ++++;;;;;;;;;;;++++  
 * 				++;;;;;;;;;;;;;++    
 * 			     ;;;;;;;;;;;;;;;     
 * 			    ;;;;;;;;;;;;;;;;;     
 * 				:::::::::::::::::    
 * 				:::::::::::::::::      
 *				:::::::::::::::::    
 *    			::::::@@@@@::::::    
 * 				:::::@:::::@:::::    
 * 				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *				:::::::::::::::::  
 */



package de.bo.aid.boese.socket;

import java.io.FileNotFoundException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;


// TODO: Auto-generated Javadoc
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
	 * Inits the websocketserver for unsecure connection with a given port.
	 *
	 * @param port the port
	 */
	public void init(int port){
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
	        }
	        catch (Exception e)
	        {
	            logger.error("Error while starting the websocket-server", e);
	        }        
	}
	
	/**
	 * Inits the websocketserver for TLS with a given port.
	 *
	 * @param port the port
	 */
	public void initTLS(int port){
		server = new Server(); 
		 // Setup SSL
        URL keystore = null;
		try {
			keystore = findResource("keystore");
		} catch (FileNotFoundException e) {
			logger.error("Could not find keystore in classpath");
			e.printStackTrace();
		}

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(keystore.toExternalForm());
        sslContextFactory.setKeyStorePassword("GZAMNNA");
        sslContextFactory.setKeyManagerPassword("GZAMNNA");
        sslContextFactory.addExcludeProtocols("SSLv3"); // a good thing to do
        sslContextFactory.addExcludeCipherSuites(".*_GCM_.*"); // geez these ciphers are slow

        // Setup HTTPS Configuration
        HttpConfiguration httpsConf = new HttpConfiguration();
        httpsConf.setSecurePort(port);
        httpsConf.setSecureScheme("https");
        httpsConf.addCustomizer(new SecureRequestCustomizer());

        ServerConnector htttpsConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory,"http/1.1"),
                new HttpConnectionFactory(httpsConf));
        htttpsConnector.setPort(port);

        server.addConnector(htttpsConnector);

        // Establish base handler list
        HandlerList baseHandlers = new HandlerList();
        server.setHandler(baseHandlers);

        // Add Servlet Context
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        baseHandlers.addHandler(context);

        // Add WebSocket
        ServerContainer jsrContainer = null;
		try {
			jsrContainer = WebSocketServerContainerInitializer.configureContext(context);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			jsrContainer.addEndpoint(SocketEndpoint.class);
		} catch (DeploymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Add default handler (for errors and whatnot) - always last
        baseHandlers.addHandler(new DefaultHandler());

        // Lets see how the server is setup after it is started
         server.setDumpAfterStart(true);
	}

	/**
	 * Starts the websocket-server.
	 */
	public void start()
    {
       
            try {
				server.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.debug(server.dump());

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
	
    /**
     * Find resource.
     *
     * @param path the path
     * @return the url
     * @throws FileNotFoundException the file not found exception
     */
    private URL findResource(String path) throws FileNotFoundException
    {
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url == null)
        {
            throw new FileNotFoundException("Resource Not Found: " + path);
        }
        return url;
    }

}
