package de.bo.aid.boese.simulation;

import static org.junit.Assert.*;

import java.util.HashMap;

import javax.validation.constraints.AssertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.main.Distributor;
import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;

// TODO: Auto-generated Javadoc
/**
 * The Class UITempsTest.
 */
public class UITempsTest {
	
	/** The distr. */
	Distributor distr;
	
	/**
	 * Sets the temp connectors.
	 */
	private void setTempConnectors() {
		HashMap<Integer, String> tempConnectors = new HashMap<>();
		tempConnectors.put(1000, "Erster Connector");
		tempConnectors.put(666, "Böser Connector");
		tempConnectors.put(1234, "Standard Connector");
		distr.setTempConnectors(tempConnectors);
	}
	
	/**
	 * Sets the temp devices.
	 */
	private void setTempDevices() {
		HashMap<Integer, TempDevice> tempDevices = new HashMap<>();
		tempDevices.put(234, new TempDevice(1, "Cooles Gerät"));
		tempDevices.put(345, new TempDevice(1, "tolles Gerät"));
		tempDevices.put(456, new TempDevice(1, "Irgendein Gerät"));
		tempDevices.put(3344, new TempDevice(1, "und noch ein Gerät"));
		distr.setTempDevices(tempDevices);
	}
	
	/**
	 * Sets the temp de cos.
	 */
	private void setTempDeCos() {
		HashMap<Integer, TempComponent> tempDeviceComponents = new HashMap<>();
		tempDeviceComponents.put(1122, new TempComponent(1, "outdoorTemperature", 12.5, 112233, 1000, "Misst Temperatur", "degree", false));
		tempDeviceComponents.put(1233, new TempComponent(2, "taster1", 7.23, 9988776, 666, "Taste Rechts außen", "%", true));
		distr.setTempDeviceComponents(tempDeviceComponents);
	}
	
	/**
	 * Start server.
	 */
	@Before
	public void startServer() {
		String args[] = {"-config", "settings.properties"};
		distr = new Distributor();
		distr.printLogo();
		distr.checkArguments(args);
		distr.loadProperties();
		distr.initDatabase();
		distr.startWebsocketServer(0);
		setTempConnectors();
		setTempDevices();
		setTempDeCos();
	}
	
	/**
	 * Test.
	 */
	@Test
	public void test() {
//		while(true) {;}
		assertTrue(true);
	}
	
	/**
	 * After.
	 */
	@After
	public void after() {
		distr = null;
	}

}
