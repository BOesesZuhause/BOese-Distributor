/*
 * 
 */
package de.bo.aid.boese.socket.test;

import static org.junit.Assert.assertFalse;

import java.net.URI;

import javax.websocket.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.socket.HeartbeatWorker;
import de.bo.aid.boese.socket.SessionHandler;
import de.bo.aid.boese.socket.SocketServer;

// TODO: Auto-generated Javadoc
/**
 * The Class HeartbeatTest.
 */
public class HeartbeatTest {
	
	/** The server. */
	//private Distributor distr ;
	private SocketServer server;
	
	/** The worker. */
	private HeartbeatWorker worker;
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp(){
//		distr = new Distributor();
//		distr.startWebsocketServer(8081);
//		distr.startHeartbeat();
		
        server = SocketServer.getInstance();
        server.start(8081);
        worker = new HeartbeatWorker();
        worker.setIntervall(5000);
        SessionHandler.getInstance().setMissedAnswerThreshold(3);
        worker.start();
	}
	
	/**
	 * Test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void test() throws Exception{
		URI uri = URI.create("ws://localhost:8081/events/");
		Session s1 = SocketClient.connect(uri);
		Thread.sleep(20000);
		assertFalse("Connection wasn't closed", s1.isOpen());
	}
	
	/**
	 * Tear down.
	 */
	@After
	public void tearDown(){
		worker.terminate();
		try {
			worker.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.stop();
	}

}
