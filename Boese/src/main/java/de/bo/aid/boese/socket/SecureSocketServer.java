package de.bo.aid.boese.socket;

import java.io.FileNotFoundException;
import java.net.URL;

import javax.websocket.server.ServerContainer;

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

public class SecureSocketServer
{
	
	/** The instance. */
	private static SecureSocketServer instance = new SecureSocketServer();
	
	/**
	 * Instantiates a new socket server.
	 */
	private SecureSocketServer(){};


	/**
	 * Gets the message handler.
	 *
	 * @return the message handler
	 */
	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	/**
	 * Sets the message handler.
	 *
	 * @param messageHandler the new message handler
	 */
	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	/** The server. */
	private Server server;
	
	/** The message handler. */
	private MessageHandler messageHandler;
   
    private URL findResource(String path) throws FileNotFoundException
    {
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url == null)
        {
            throw new FileNotFoundException("Resource Not Found: " + path);
        }
        return url;
    }

    public void start(int port) throws Exception
    {
        Server server = new Server();
        int httpsPort = port;

        // Setup SSL
        URL keystore = findResource("keystore");

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(keystore.toExternalForm());
        sslContextFactory.setKeyStorePassword("GZAMNNA");
        sslContextFactory.setKeyManagerPassword("GZAMNNA");
        sslContextFactory.addExcludeProtocols("SSLv3"); // a good thing to do
        sslContextFactory.addExcludeCipherSuites(".*_GCM_.*"); // geez these ciphers are slow

        // Setup HTTPS Configuration
        HttpConfiguration httpsConf = new HttpConfiguration();
        httpsConf.setSecurePort(httpsPort);
        httpsConf.setSecureScheme("https");
        httpsConf.addCustomizer(new SecureRequestCustomizer());

        ServerConnector htttpsConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory,"http/1.1"),
                new HttpConnectionFactory(httpsConf));
        htttpsConnector.setPort(httpsPort);

        server.addConnector(htttpsConnector);

        // Establish base handler list
        HandlerList baseHandlers = new HandlerList();
        server.setHandler(baseHandlers);

        // Add Servlet Context
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        baseHandlers.addHandler(context);

        // Add WebSocket
        ServerContainer jsrContainer = WebSocketServerContainerInitializer.configureContext(context);
        jsrContainer.addEndpoint(SocketEndpoint.class);

        // Add default handler (for errors and whatnot) - always last
        baseHandlers.addHandler(new DefaultHandler());

        // Lets see how the server is setup after it is started
         server.setDumpAfterStart(true);

        try
        {
            // Start the server thread
            server.start();
            // Wait for the server thread to end
           // server.join();
        }
        catch (Throwable t)
        {
            t.printStackTrace(System.err);
        }
    }
    
	/**
	 * Stop.
	 */
	public void stop(){
		try {
			server.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the single instance of SocketServer.
	 *
	 * @return single instance of SocketServer
	 */
	public static SecureSocketServer getInstance() {
		return instance ;
	}


	/**
	 * Handle message.
	 *
	 * @param message the message
	 * @param connectorId the connector id
	 */
	public void handleMessage(String message, int connectorId) {
		messageHandler.handleMessage(message, connectorId);		
	}
}