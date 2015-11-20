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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import de.bo.aid.boese.cli.Parameters;
import de.bo.aid.boese.db.Connection;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;
import de.bo.aid.boese.model.Component;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.ruler.Control;
import de.bo.aid.boese.ruler.Inquiry;
import de.bo.aid.boese.ruler.ToDoChecker;
import de.bo.aid.boese.socket.SocketServer;
import de.bo.aid.boese.socket.HeartbeatWorker;
import de.bo.aid.boese.socket.SessionHandler;
import de.bo.aid.boese.xml.ComponentXML;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class MainClass.
 */
public class Distributor {
	
/** The logo. */
private final String logo = 
 "            (                           \n"
+"           ( )\\         (        (     \n"
+"           )((_)  (    ))\\ (    ))\\   \n"
+"          ((_)_   )\\  /((_))\\  /((_)  \n"
+"           | _ ) ((_)(_)) ((_)(_))      \n"
+"           | _ \\/ _ \\/ -_)(_-</ -_)   \n"
+"           |___/\\___/\\___|/__/\\___|  \n"
+"                                        \n"
+"                      ;                 \n"
+"            +        ;;;         +      \n"
+"            +       ;;;;;        +      \n"
+"            +      ;;;;;;;       +      \n"
+"            ++    ;;;;;;;;;     ++      \n"
+"            +++++;;;;;;;;;;;+++++       \n"
+"             ++++;;;;;;;;;;;+++++       \n"
+"              ++;;;;;;;;;;;;;++         \n"
+"               ;;;;;;;;;;;;;;;          \n"
+"              ;;;;;;;;;;;;;;;;;         \n"
+"              :::::::::::::::::         \n"
+"              :::::::::::::::::         \n"
+"              :::::::::::::::::         \n"
+"              ::::::@@@@@::::::         \n"
+"              :::::@:::::@:::::         \n"
+"              ::::@:::::::@::::         \n"
+"              :::::::::::::::::         \n"
+"              :::::::::::::::::         \n";
	
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
	
	/** The tdc. */
	private ToDoChecker tdc;
	
	/** The props. */
	DistributorProperties props;
	
	/**
	 * Start websocket server.
	 *
	 * @param port the port
	 */
	public void startWebsocketServer(int port){
		socketServer = SocketServer.getInstance();
		protocolHandler = new ProtocolHandler(this);
		socketServer.setMessageHandler(protocolHandler);
		
		if(port == 0){ //For the JUnit-test
			socketServer.start(websocketPort);
		}else{
			socketServer.start(port);
		}

	}
	
	/**
	 * Start Todo Checker.
	 */
	public void startToDoChecker(){
		tdc = new ToDoChecker();
		tdc.start();
	}
	
	/**
	 * Inits the database.
	 */
	private void initDatabase(){
		HibernateUtil.setDBUser(props.getDbUser());
		HibernateUtil.setDBPassword(props.getDbPassword());
		HibernateUtil.setDBURL(props.getDbName(), props.getDbHost(), props.getDbPort());
		
		Inserts.defaults();		
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
	
	//TODO validate properties
	/**
	 * Load the properties-file.
	 */
	private void loadProperties() {
		props= new DistributorProperties();
		props.load(configFilePath);
		
		websocketPort = props.getPort();
		autoConfirm = props.isAutoConfirm();
	}

	/**
	 * Creates the default properties-file.
	 */
	//TODO test
	private void createDefaultProperties() {
		DistributorProperties props = new DistributorProperties();
		props.setDefaults();
		props.save(configFilePath);
	}
	
	/**
	 * Prints the logo.
	 */
	private void printLogo(){
		System.out.println(logo);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Distributor distr = new Distributor();
		distr.printLogo();
		distr.checkArguments(args);
		distr.loadProperties();
		distr.initDatabase();
		distr.startWebsocketServer(0);
		//distr.startHeartbeat();
	}
	
	public void startHeartbeat() {
		HeartbeatWorker worker = new HeartbeatWorker();
		worker.start();
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
	
		String name= tempConnectors.get(tempId);
		
		if(name == null){
			throw new NotFoundException("Connector with tempId " + tempId + " not Found");
		}

		SecureRandom sr = new SecureRandom();
		String pw = String.valueOf(sr.nextLong());

		Connector con = new Connector(name, pw);
		Inserts.connector(con);
		SessionHandler.getInstance().setConnectorId(tempId, con.getCoId());

		// Send ConfirmConnection
		BoeseJson cc = new ConfirmConnection(pw, con.getCoId(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cc, os);
		SessionHandler.getInstance().sendToConnector(con.getCoId(), os.toString());

		if (!isUserConnector) {			
			// Send RequestAllDevices
			BoeseJson rad = new RequestAllDevices(con.getCoId(), 0, new Date().getTime());
			os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(rad, os);
			SessionHandler.getInstance().sendToConnector(con.getCoId(), os.toString());
		}
		
		System.out.println("User confirmed Connector with name: " + name + "\n");
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

		TempDevice temp = tempDevices.get(tempId);
		
		if(temp == null){
			throw new NotFoundException("Device with tempId " + tempId + " not found");
		}
		if(name == null){
			name = temp.getName();
		}
		
		int connectorId = temp.getConnectorID();
		
		HashMap<String, Integer> devices = new HashMap<String, Integer>();
		try {
			Device dev = new Device(name, "serial");
			Inserts.device(connectorId, zoneId, dev);
			devices.put(name, dev.getDeId());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		protocolHandler.sendConfirmDevices(devices, connectorId);
		
		System.out.println("User confirmed Device with name: " + name + "\n");	
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
		TempComponent temp = tempDeviceComponents.get(tempId);
		if(temp == null){
			throw new NotFoundException("Component with tempId " + tempId + " not found");
		}
		if (name == null){
			name = temp.getName();
		}
		int deviceId = temp.getDeviceId();
		int connectorId = temp.getConnectorId();
 
		int deCoId = 0;
		// TODO Was ist wenn Inserts.component() funktioniert aber Inserts.deviceComponent nicht
		// TODO jedesmal wird eine Komponente erstellt
		try{
			Component comp = new Component(name, temp.isActor());
			Inserts.component(unitId, comp); 
			DeviceComponent deco = new DeviceComponent(temp.getDescription());
			Inserts.deviceComponent(deviceId, comp.getCoId(), deco);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			//User hat DeviceComponent bestätigt, aber es gab ein Fehler
			return;
		}
		HashMap<String, Integer> confirmComponents = new HashMap<String, Integer>();
		confirmComponents.put(name, deCoId);
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		inquiryList.add(new Inquiry(deCoId, temp.getValueTimestamp(), temp.getValue()));
		protocolHandler.sendToDos(inquiryList);
		
		protocolHandler.sendConfirmComponent(deviceId, confirmComponents, connectorId);
		
		System.out.println("User confirmed Component with name: " + name + " and Device with id: " + deviceId + "\n");	
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
	public List<ComponentXML> insertValues(List<Inquiry> inquirys) {
		for (Inquiry inq : inquirys) {
			try{
				Inserts.value(inq.getDeviceComponentId(), new Date(inq.getTimestamp()), inq.getValue());
			}
			catch(Exception e){
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		Control controll = new Control();
		List<ComponentXML> todos;
		try {
			todos = controll.getToDos(inquirys);
		} catch (Exception e) {
			System.err.println("Bad XML: " + e.getMessage());
			// TODO Exception Handling
			e.printStackTrace();
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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		tempCompId++;
	}
	
	/**
	 * Gets the tdc.
	 *
	 * @return the tdc
	 */
	public ToDoChecker getTdc() {
		return tdc;
	}

	/**
	 * Change in rule.
	 *
	 * @param ruleId the rule id
	 */
	public static void changeInRule(int ruleId){
		List<DeviceComponent> decos = Selects.deviceComponentsByRule(ruleId);
		List<Inquiry> in = new ArrayList<Inquiry>();
		for(DeviceComponent deco : decos){
			in.add(new Inquiry(deco.getDeCoId(), new Date().getTime(), deco.getCurrentValue().doubleValue()));
		}
		Control controll = new Control();
		try {
			// TODO sendToDos
//			.sendToDos(controll.getToDos(in));
		} catch (Exception e) {
			System.err.println("Bad XML: " + e.getMessage());
			// TODO Exception Handling
		}
	}

}
