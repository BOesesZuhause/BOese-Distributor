package de.bo.aid.boese.socket.test;

import static org.junit.Assert.*;

import java.net.URI;

import javax.websocket.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.socket.BoeseServer;

public class ConectionTest {
	
	private BoeseServer server;
	
    @Before
    public void startServer() throws Exception {
        server = new BoeseServer();
        server.start();
    }
	

	@Test
	public void testConnection() throws Exception {
		URI uri = URI.create("ws://localhost:8080/events/");
		Session s1 = SocketClient.connect(uri);
		assertNotNull(s1);
	}
	
	   @After
	    public void shutdown() throws Exception {
	        server.stop();
	    }

}
