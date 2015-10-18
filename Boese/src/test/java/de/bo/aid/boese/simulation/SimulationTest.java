
package de.bo.aid.boese.simulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.db.Connection;
import de.bo.aid.boese.socket.SocketEndpoint;

// TODO: Auto-generated Javadoc
/**
 * The Class SimulationTest.
 */
public class SimulationTest {
	
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
        server.start(8081);
        Connection.getConnection();
    }
    

	

	/**
	 * Simulation.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void simulation() throws Exception {

		ConnectorSimulation sim = new ConnectorSimulation();
		UserSimulation userSim = new UserSimulation();
		sim.start();
		Thread.sleep(2000);		
		//userSim.confirmConnectors();
		Thread.sleep(2000);
		//userSim.confirmDevices();
		Thread.sleep(2000);
		//userSim.confirmDeviceComponents();
		Thread.sleep(5000);
		
		sim.sendValue(99.8);
		
		Thread.sleep(2000);
		
		
		sim.closeConnection();
		
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
