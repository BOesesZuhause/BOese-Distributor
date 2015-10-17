
package de.bo.aid.boese.socket.test;

import static org.junit.Assert.*;

import java.net.URI;

import javax.websocket.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.socket.SocketEndpoint;

// TODO: Auto-generated Javadoc
/**
 * The Class ConectionTest.
 */
public class ConectionTest {
	
	/** The server. */
	private SocketEndpoint server;
	
    /**
     * Start server.
     *
     * @throws Exception the exception
     */
    @Before
    public void startServer() throws Exception {
        server = new SocketEndpoint();
        server.start();
    }
	

	/**
	 * Test connection.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testConnection() throws Exception {
		URI uri = URI.create("ws://localhost:8081/events/");
		Session s1 = SocketClient.connect(uri);
		assertNotNull(s1);
	}
	
	   /**
   	 * Shutdown.
   	 *
   	 * @throws Exception the exception
   	 */
   	@After
	    public void shutdown() throws Exception {
	        server.stop();
	    }

}
