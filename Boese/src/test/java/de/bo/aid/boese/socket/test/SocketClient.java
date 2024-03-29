/*
 * 
 */
package de.bo.aid.boese.socket.test;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

// TODO: Auto-generated Javadoc

/**
 * The Class SocketClient.
 */
@ClientEndpoint
public class SocketClient
{
	
	/**
	 * On message.
	 *
	 * @param message the message
	 */
	@OnMessage
	 public void onMessage(String message) {
		System.out.println("Client received Message: " + message);
	}
	 
	/**
	 * Connect.
	 *
	 * @param uri the uri
	 * @return the session
	 * @throws Exception the exception
	 */
	public static Session connect(URI uri) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        
        

        //try {
            // Attempt Connect
            return container.connectToServer(SocketClient.class, uri);
        //} 
//        finally {
//            // Force lifecycle stop when done with container.
//            // This is to free up threads and resources that the
//            // JSR-356 container allocates. But unfortunately
//            // the JSR-356 spec does not handle lifecycles (yet)
//            if (container instanceof LifeCycle) {
//                ((LifeCycle) container).stop();
//            }
//        }
    }
	
}