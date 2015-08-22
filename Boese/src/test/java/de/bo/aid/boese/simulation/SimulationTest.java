package de.bo.aid.boese.simulation;

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

		ConnectorSimulation sim = new ConnectorSimulation();
		UserSimulation userSim = new UserSimulation();
		sim.start();
		Thread.sleep(2000);
		
		userSim.confirmConnectors();
		
		Thread.sleep(5000);
		
		sim.sendValue(99.8);
		
		Thread.sleep(2000);
		
		
		sim.closeConnection();
		
	}
	
	   @After
	    public void shutdown() throws Exception {
	        server.stop();
	    }

}
