/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 */
package de.bo.aid.boese.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import de.bo.aid.boese.cli.Parameters;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;
import de.bo.aid.boese.ruler.Controll;
import de.bo.aid.boese.ruler.Inquiry;
import de.bo.aid.boese.socket.SocketServer;
import de.bo.aid.boese.socket.SessionHandler;
import de.bo.aid.boese.xml.Component;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class MainClass.
 */
public class Distributor {
	//TODO handle Acknowledge abgleich
	
	/** The socket server. */
	private SocketServer socketServer;
	
	/** The temp connectors. */
	//hashmaps for unconfirmed Objects with a temporary Id as key
	private HashMap<Integer, String> tempConnectors = new HashMap<Integer, String>();
	
	/** The temp devices. */
	private HashMap<Integer, TempDevice> tempDevices = new HashMap<Integer, TempDevice>();
	
	/** The temp device components. */
	private HashMap<Integer, TempComponent> tempDeviceComponents = new HashMap<Integer, TempComponent>();
	
	/** The temp device id. */
	//tempIds for unconfirmed Objects
	private int tempDeviceId = 1;
	
	/** The temp comp id. */
	private int tempCompId = 1;
	
	/** The auto confirm. */
	private boolean autoConfirm;
	
	/** The temp id device components. */
	int tempIdDeviceComponents = 1;
	
	/** The protocol handler. */
	private ProtocolHandler protocolHandler;
	
	/** The websocket port. */
	private int websocketPort;
	
	/** The config file path. */
	private String configFilePath;
	
	/** The Constant logger for log4j. */
	final  Logger logger = LogManager.getLogger(Distributor.class);
	
	/**
	 * Start websocket server.
	 *
	 * @param port the port
	 */
	public void startWebsocketServer(int port){
		socketServer = SocketServer.getInstance();
		protocolHandler = new ProtocolHandler(this);
		socketServer.setMessageHandler(protocolHandler);
		
		if(port != 0){ //For the JUnit-test
			websocketPort = port;
		}
		socketServer.start(websocketPort);
		logger.info("Started websocket-server on prt: " + websocketPort);
	}
	
	/**
	 * Inits the database.
	 */
	private void initDatabase(){
		//TODO add default data
		
	}
	
	/**
	 * Check arguments.
	 *
	 * @param args the args
	 */
	private void checkArguments(String[] args){
		Parameters params = new Parameters();
		JCommander cmd = new JCommander(params);

		try {
			cmd.parse(args);

		} catch (ParameterException ex) {
			System.out.println(ex.getMessage());
			cmd.usage();
			System.exit(0);
		}
		
		configFilePath = params.getConfig();
		
		if (params.isGenConfig()) {
			createDefaultProperties();
			logger.info("created default properties-file at: " + configFilePath);
			System.exit(0);
		}
		
	}
	
	
	/**
	 * Load the properties-file.
	 */
	private void loadProperties() {

		Properties props = new Properties();
		FileInputStream file = null;

		// load the file handle
		try {
			file = new FileInputStream(configFilePath);
		} catch (FileNotFoundException e) {
			logger.error("config File not found at: " + configFilePath);
			e.printStackTrace();
			System.exit(0);
		}

		// load all the properties from the file
		try {
			props.load(file);
		} catch (IOException e) {
			logger.error("IO-Exception while loading config-file");
			e.printStackTrace();
			System.exit(0);
		}

		// close the file handle
		try {
			file.close();
		} catch (IOException e) {
			logger.error("IO-Exception while closing config-file");
			e.printStackTrace();
			System.exit(0);
		}

		// retrieve the properties
		websocketPort = Integer.parseInt(props.getProperty("WebsocketPort"));
		autoConfirm = Boolean.parseBoolean(props.getProperty("autoConfirm"));
	}

	/**
	 * Creates the default properties-file.
	 */
	private void createDefaultProperties() {
		Properties prop = new Properties();
		OutputStream output = null;

		prop.setProperty("WebsocketPort", "8081");
		prop.setProperty("autoConfirm", "false");
		try {
			output = new FileOutputStream(configFilePath);
		} catch (FileNotFoundException e) {
			logger.error("Could not open file: " + configFilePath);
			e.printStackTrace();
			System.exit(0);
		}

		try {
			prop.store(output, null);
		} catch (IOException e) {
			logger.error("IO-Exception while saving default properties-file");
			e.printStackTrace();
			System.exit(0);
		}
	}
	

	
	

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Distributor distr = new Distributor();
		distr.checkArguments(args);
		distr.loadProperties();
		distr.initDatabase();
		distr.startWebsocketServer(0);
	}
	
	/**
	 * Gets the temp connectors.
	 *
	 * @return the temp connectors
	 */
	public HashMap<Integer, String> getTempConnectors(){
		return tempConnectors;
	}
	
	/**
	 * Confirm connector.
	 *
	 * @param tempId the temp id
	 * @param isUserConnector if it is an user connector
	 * @throws NotFoundException the not found exception
	 */
	public void confirmConnector(int tempId, boolean isUserConnector) throws NotFoundException{
	
		//check if connector exists
		String name= tempConnectors.get(tempId);		
		if(name == null){
			throw new NotFoundException("Connector with tempId " + tempId + " not Found");
		}

		//generate password
		SecureRandom sr = new SecureRandom();
		String pw = String.valueOf(sr.nextLong());
		
		//generate id and connect it with the session 
		int conId = Inserts.connector(name, pw);
		SessionHandler.getInstance().setConnectorId(tempId, conId);
		protocolHandler.sendConfirmConnection(pw, conId);

		if (!isUserConnector) {			
			protocolHandler.sendRequestAllDevices(conId);
		}
		
		logger.info("User confirmed Connector with name: " + name + "\n");
		tempConnectors.remove(tempId);
	}
	
	/**
	 * Confirm device.
	 *
	 * @param tempId the temp id
	 * @param zoneId the zone id
	 * @param name the name
	 * @throws NotFoundException the not found exception
	 */
	public void confirmDevice(int tempId, int zoneId, String name) throws NotFoundException{

		//check if device exists
		TempDevice temp = tempDevices.get(tempId);		
		if(temp == null){
			throw new NotFoundException("Device with tempId " + tempId + " not found");
		}
		//use temp-name, if no name is submitted
		if(name == null){
			name = temp.getName();
		}
		
		//insert device in db ans send confirmDevice-message
		int connectorId = temp.getConnectorID();		
		HashMap<String, Integer> devices = new HashMap<String, Integer>();
		devices.put(name, Inserts.device(connectorId, zoneId, name, "serial"));	
		protocolHandler.sendConfirmDevices(devices, connectorId);
		
		logger.info("User confirmed Device with name: " + name + "\n");	
		tempDevices.remove(temp);
	}
	
	
	/**
	 * Gets the temp devices.
	 *
	 * @return the temp devices
	 */
	public HashMap<Integer, TempDevice> getTempDevices() {
		return tempDevices;
	}

	/**
	 * Gets the temp device components.
	 *
	 * @return the temp device components
	 */
	public HashMap<Integer, TempComponent> getTempDeviceComponents() {
		return tempDeviceComponents;
	}

	/**
	 * Sets the temp device components.
	 *
	 * @param tempDeviceComponents the temp device components
	 */
	public void setTempDeviceComponents(HashMap<Integer, TempComponent> tempDeviceComponents) {
		this.tempDeviceComponents = tempDeviceComponents;
	}

	/**
	 * Sets the temp connectors.
	 *
	 * @param tempConnectors the temp connectors
	 */
	public void setTempConnectors(HashMap<Integer, String> tempConnectors) {
		this.tempConnectors = tempConnectors;
	}

	/**
	 * Sets the temp devices.
	 *
	 * @param tempDevices the temp devices
	 */
	public void setTempDevices(HashMap<Integer, TempDevice> tempDevices) {
		this.tempDevices = tempDevices;
	}

	/**
	 * Confirm device component.
	 *
	 * @param tempId the temp id
	 * @param unitId the unit id
	 * @param name the name
	 * @throws NotFoundException the not found exception
	 */
	public void confirmDeviceComponent(int tempId, int unitId, String name) throws NotFoundException{
		
		//check if Component exists
		TempComponent temp = tempDeviceComponents.get(tempId);
		if(temp == null){
			throw new NotFoundException("Component with tempId " + tempId + " not found");
		}
		//use temp-name if no name was submitted
		if (name == null){
			name = temp.getName();
		}
		
		//insert deviceComponent in db and send confirm message and check for todos
		int deviceId = temp.getDeviceId();
		int connectorId = temp.getConnectorId();		
		int componentId = Inserts.component(name, unitId, !temp.isActor()); 
		int deCoId = Inserts.deviceComponent(deviceId, componentId, temp.getDescription());
		HashMap<String, Integer> confirmComponents = new HashMap<String, Integer>();
		confirmComponents.put(name, deCoId);
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		inquiryList.add(new Inquiry(deCoId, temp.getValueTimestamp(), temp.getValue()));
		protocolHandler.sendToDos(inquiryList);	
		protocolHandler.sendConfirmComponent(deviceId, confirmComponents, connectorId);
		
		logger.info("User confirmed Component with name: " + name + " and Device with id: " + deviceId + "\n");	
		tempDevices.remove(temp);
	}
	

	/**
	 * Gets the temp components.
	 *
	 * @return the temp components
	 */
	// Helper
	public HashMap<Integer, TempComponent> getTempComponents(){
		return tempDeviceComponents;
	}
	
	/**
	 * Insert values.
	 *
	 * @param inquirys the inquirys
	 * @return the list
	 */
	public List<Component> insertValues(List<Inquiry> inquirys) {
		for (Inquiry inq : inquirys) {
			Inserts.value(inq.getDeviceComponentId(), new Date(inq.getTimestamp()), inq.getValue());
		}
		Controll controll = new Controll();
		List<Component> todos;
		try {
			todos = controll.getToDos(inquirys);
		} catch (Exception e) {
			logger.error("Bad XML: " + e.getMessage());
			todos = null;
		}
		return todos;
	}
	
	/**
	 * Adds the temp connector.
	 *
	 * @param name the name
	 * @param tempId the temp id
	 */
	public void addTempConnector(String name, int tempId){
		tempConnectors.put(tempId, name);
		if(autoConfirm){
			try {
				confirmConnector(tempId, false);
			} catch (NotFoundException e) {
				logger.error("Could not confirm connector with id: " + tempId + " (not found)");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds the temp devie.
	 *
	 * @param temp the temp
	 */
	public void addTempDevie(TempDevice temp){
		tempDevices.put(tempDeviceId, temp);
		if(autoConfirm){
			try {
				confirmDevice(tempDeviceId, 0, null);
			} catch (NotFoundException e) {
				logger.error("Could not confirm device with id: " + tempDeviceId + " (not found)");
				e.printStackTrace();
			}
		}
		tempDeviceId++;
	}
	
	/**
	 * Adds the temp component.
	 *
	 * @param temp the temp
	 */
	public void addTempComponent(TempComponent temp){
		tempDeviceComponents.put(tempCompId, temp);
		if(autoConfirm){
			try {
				confirmDeviceComponent(tempCompId, 0, null);
			} catch (NotFoundException e) {
				logger.error("Could not confirm comonent with id: " + tempCompId + " (not found)");
				e.printStackTrace();
			}
		}
		tempCompId++;
	}
	

}
