package de.bo.aid.boese.simulation;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.Date;

import javax.websocket.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.db.Connection;
import de.bo.aid.boese.socket.BoeseServer;

public class SimulationTest {
	
	private BoeseServer server;
	
    @Before
    public void startServer() throws Exception {
        server = new BoeseServer();
        server.start();
        Connection.getConnection();
    }
    

	

	@Test
	public void simulation() throws Exception {

		Simulation sim = new Simulation();
		sim.start();
		
		assertTrue(true);
		
	}
	
	   @After
	    public void shutdown() throws Exception {
	        server.stop();
	    }

}
