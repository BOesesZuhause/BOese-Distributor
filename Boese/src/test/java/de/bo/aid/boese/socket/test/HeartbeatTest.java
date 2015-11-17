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

public class HeartbeatTest {
	
	//private Distributor distr ;
	private SocketServer server;
	private HeartbeatWorker worker;
	
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
	@Test
	public void test() throws Exception{
		URI uri = URI.create("ws://localhost:8081/events/");
		Session s1 = SocketClient.connect(uri);
		Thread.sleep(20000);
		assertFalse("Connection wasn't closed", s1.isOpen());
	}
	@After
	public void tearDown(){
		worker.pause();
		server.stop();
	}

}
