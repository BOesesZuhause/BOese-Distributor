/*
 * 
 */


package de.bo.aid.boese.simulation;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.db.Connection;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.main.Distributor;
import de.bo.aid.boese.main.DistributorProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class SimulationTest.
 */
public class SimulationTest {
	
	
	/** The user sim. */
	UserSimulation userSim;
	
    /**
     * Start server.
     *
     * @throws Exception the exception
     */
    @Before
    public void startServer() throws Exception {
    	Distributor distr = Distributor.getInstance();
    	DistributorProperties props = new DistributorProperties();
    	props.setHeartbeat(false);
    	props.setTLS(false);
    	props.setPort(8081);
    	props.setAutoConfirm(true);
    	distr.setProps(props);
    	
    	distr.startWebsocketServer();
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boeseTest", "localhost", 5432);
        Connection.getConnection();
        Inserts.defaults();
        userSim = new UserSimulation(distr);
    }
    

	

	/**
	 * Simulation.
	 */
	@Test
	public void simulation() {
		try{
		ConnectorSimulation sim = new ConnectorSimulation();
		sim.start();
		Thread.sleep(2000);		
		userSim.confirmConnectors();
		Thread.sleep(2000);
		userSim.confirmDevices();
		Thread.sleep(2000);
		userSim.confirmDeviceComponents();
		Thread.sleep(5000);
		
		sim.sendValue(99.8);
		
		Thread.sleep(2000);
		
		sim.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
			//fail(e.getMessage());
		}
		
	}
	
	   /**
   	 * Shutdown.
   	 *
   	 * @throws Exception the exception
   	 */
   	@After
	    public void shutdown() throws Exception {
	    }

}
