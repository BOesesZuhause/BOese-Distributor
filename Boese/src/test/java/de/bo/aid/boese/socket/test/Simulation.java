package de.bo.aid.boese.socket.test;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.Date;

import javax.websocket.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.socket.BoeseServer;

public class Simulation {
	
//	private BoeseServer server;
//	
//    @Before
//    public void startServer() throws Exception {
//        server = new BoeseServer();
//        server.start();
//    }
//	
//
//	@Test
//	public void simulation() throws Exception {
//		URI uri = URI.create("ws://localhost:8081/events/");
//		SocketClientStandalone client = new SocketClientStandalone(uri);
//		
//		String message = "{"
//				+ "\"Header\":{"
//				+ "\"MessageType\":1,"
//				+ "\"ConnectorId\":-1,"
//				+ "\"SequenceNr\":0,"
//				+ "\"AcknowledgeNr\":0,"
//				+ "\"Status\":0,"
//				+ "\"Timestamp\":" + new Date().getTime()
//				+ "},"
//				+ "\"ConnectorName\":\"Konnektor1\""
//				+ "}";
//		
//		client.sendMessage(message);
//		
//		//wait for answer
//		Thread.sleep(5000);
//		
//		message = "{"
//				+ "\"Header\":{"
//				+ "\"MessageType\":1,"
//				+ "\"ConnectorId\":-1,"
//				+ "\"SequenceNr\":0,"
//				+ "\"AcknowledgeNr\":0,"
//				+ "\"Status\":0,"
//				+ "\"Timestamp\":" + new Date().getTime()
//				+ "},"
//				+ "\"ConnectorName\":\"Konnektor1\""
//				+ "}";
//		
//		client.sendMessage(message);
//		
//		//Wait for answer
//		Thread.sleep(1000);
//		
//		assertTrue(true);
//		
//	}
//	
//	   @After
//	    public void shutdown() throws Exception {
//	        server.stop();
//	    }

}
