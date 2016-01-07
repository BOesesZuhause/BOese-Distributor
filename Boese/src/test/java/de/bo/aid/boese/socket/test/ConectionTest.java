/*
 * 
 */
package de.bo.aid.boese.socket.test;

import static org.junit.Assert.*;

import java.net.URI;

import javax.websocket.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.socket.SocketServer;

// TODO: Auto-generated Javadoc
/**
 * The Class ConectionTest.
 */
public class ConectionTest {
	
	/** The server. */
	private SocketServer server;
	
	/** The s1. */
	private Session s1;
	
    /**
     * Start server.
     *
     * @throws Exception the exception
     */
    @Before
    public void startServer() throws Exception {
        server = SocketServer.getInstance();
        server.init(8081);
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
		s1 = SocketClient.connect(uri);
		assertNotNull(s1);
	}
	
	   /**
   	 * Shutdown.
   	 *
   	 * @throws Exception the exception
   	 */
   	@After
	    public void shutdown() throws Exception {
   			s1.close();
   			server.stop();
	    }

}
