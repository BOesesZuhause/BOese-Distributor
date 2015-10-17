
package de.bo.aid.boese.socket;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import de.bo.aid.boese.main.MainClass;


// TODO: Auto-generated Javadoc
/**
 * The Class BoeseServer.
 */

@ClientEndpoint
@ServerEndpoint("/events/")
public class SocketEndpoint
{
	
	/** The server. */
	private Server server;

	/**
	 * Start.
	 */
	public void start()
    {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8081);
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
            //server.dump(System.err);
            //server.join();
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
	
	/** The handler. */
	SessionHandler handler = SessionHandler.getInstance();
	
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
		//TODO observer pattern
		System.out.println("Server received Message: " + message);
		MainClass.handleMessage(message, handler.getConnectorId(session));
	}
}