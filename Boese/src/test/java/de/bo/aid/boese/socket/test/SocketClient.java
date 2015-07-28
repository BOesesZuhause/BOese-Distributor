package de.bo.aid.boese.socket.test;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.eclipse.jetty.util.component.LifeCycle;

import de.bo.aid.boese.socket.BoeseSocket;
@ClientEndpoint
public class SocketClient
{
	
	public static Session connect(URI uri) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        try {
            // Attempt Connect
            return container.connectToServer(SocketClient.class, uri);
        } finally {
            // Force lifecycle stop when done with container.
            // This is to free up threads and resources that the
            // JSR-356 container allocates. But unfortunately
            // the JSR-356 spec does not handle lifecycles (yet)
            if (container instanceof LifeCycle) {
                ((LifeCycle) container).stop();
            }
        }
    }
	
}