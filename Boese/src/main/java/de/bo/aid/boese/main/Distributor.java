
package de.bo.aid.boese.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LazyInitializationException;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import de.bo.aid.boese.cli.Parameters;
import de.bo.aid.boese.db.AllSelects;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.BoeseJson.MessageType;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.ConfirmDeviceComponents;
import de.bo.aid.boese.json.ConfirmDevices;
import de.bo.aid.boese.json.ConfirmValue;
import de.bo.aid.boese.json.DeviceComponents;
import de.bo.aid.boese.json.MultiMessage;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.RequestDeviceComponents;
import de.bo.aid.boese.json.Rule;
import de.bo.aid.boese.json.SendDeviceComponents;
import de.bo.aid.boese.json.SendDevices;
import de.bo.aid.boese.json.SendNotification;
import de.bo.aid.boese.json.SendStatus;
import de.bo.aid.boese.json.SendValue;
import de.bo.aid.boese.json.UserConfirmRules;
import de.bo.aid.boese.json.UserConfirmTemps;
import de.bo.aid.boese.json.UserCreateRules;
import de.bo.aid.boese.json.UserDevice;
import de.bo.aid.boese.json.UserRequestConnectors;
import de.bo.aid.boese.json.UserRequestDeviceComponents;
import de.bo.aid.boese.json.UserRequestGeneral;
import de.bo.aid.boese.json.UserSendConnectors;
import de.bo.aid.boese.json.UserSendDeviceComponent;
import de.bo.aid.boese.json.UserSendDevices;
import de.bo.aid.boese.json.UserSendRules;
import de.bo.aid.boese.json.UserSendTemps;
import de.bo.aid.boese.json.UserSendZones;
import de.bo.aid.boese.json.UserTempComponent;
import de.bo.aid.boese.json.Zone;
import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.ruler.Controll;
import de.bo.aid.boese.ruler.Inquiry;
import de.bo.aid.boese.ruler.Interpretor;
import de.bo.aid.boese.socket.SocketEndpoint;
import de.bo.aid.boese.socket.MessageHandler;
import de.bo.aid.boese.socket.SessionHandler;
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.Component;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class MainClass.
 */
public class Distributor {
	//TODO handle Acknowledge abgleich
	
	/** The temp connectors. */
	//hashmaps for unconfirmed Objects with a temporary Id as key
	private static HashMap<Integer, String> tempConnectors = new HashMap<Integer, String>();
	
	/** The temp devices. */
	private static HashMap<Integer, TempDevice> tempDevices = new HashMap<Integer, TempDevice>();
	
	/** The temp device components. */
	private static HashMap<Integer, TempComponent> tempDeviceComponents = new HashMap<Integer, TempComponent>();
	
	/** The temp device id. */
	//tempIds for unconfirmed Objects
	static int tempDeviceId = 1;
	
	/** The temp comp id. */
	static int tempCompId = 1;
	
	/** The auto confirm. */
	private static boolean autoConfirm = true; //Debugging
	
	/** The temp id device components. */
	int tempIdDeviceComponents = 1;
	
	private ProtocolHandler protocolHandler;
	private int websocketPort;
	private String configFilePath;
	
	/** The Constant logger for log4j. */
	final  Logger logger = LogManager.getLogger(Distributor.class);
	
	
	private void startWebsocketServer(){
		SocketEndpoint endpoint = new SocketEndpoint();
		//endpoint.setPort(websocketPort);
		protocolHandler = new ProtocolHandler();
		endpoint.setMessageHandler(protocolHandler);
		endpoint.start(websocketPort);
	}
	
	private void initDatabase(){
		
	}
	
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
	}

	/**
	 * Creates the default properties-file.
	 */
	private void createDefaultProperties() {
		Properties prop = new Properties();
		OutputStream output = null;

		prop.setProperty("WebsocketPort", "8081");
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
	 * Handle request connections.
	 *
	 * @param rc the rc
	 * @param tempId the temp id
	 */
	private static void handleRequestConnections(RequestConnection rc, int tempId) {
		 
		if (rc.getPassword() == null && rc.getConnectorId() == -1) {
 
			//Add requesting Connector to tempConnectors with tempId from SocketHandler
			tempConnectors.put(tempId, rc.getConnectorName());
			
			if(autoConfirm){
				//For Debugging
				confirmConnectors(tempId, rc.isUserConnector());
			}

		} else { //TODO test
			String pw = rc.getPassword();
			int conId = rc.getConnectorId();
			String conName = rc.getConnectorName();

			Connector con = Selects.connector(conId);

			if (con.getName().compareTo(conName) == 0 && con.getPassword().compareTo(pw) == 0) {
				SessionHandler.getInstance().setConnectorId(tempId, conId);
				BoeseJson cc = new ConfirmConnection(pw, conId, 0, new Date().getTime());
				
				OutputStream os = new ByteArrayOutputStream();
				BoeseJson.parseMessage(cc, os);
				SessionHandler.getInstance().sendToConnector(conId, os.toString());			
			} else {
				SessionHandler.getInstance().rejectConnection(conId);
			}
		}
	}
	
	/**
	 * Handle send devices.
	 *
	 * @param sd the sd
	 * @param connectorId the connector id
	 */
	private static void handleSendDevices(SendDevices sd, int connectorId) {
		if (connectorId != sd.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}

		HashMap<String, Integer> devices = sd.getDevices();
		HashMap<String, Integer> confirmDevices = new HashMap<>();
		
		for (String deviceName : devices.keySet()) {
			if (devices.get(deviceName) == -1) { // device not in db
				
				TempDevice temp = new TempDevice(connectorId, deviceName);
				tempDevices.put(tempDeviceId, temp);
				//For Debugging
				if(autoConfirm){
					confirmDevices(tempDeviceId);
				}
				tempDeviceId++;
			} else { //device already in db
				Device dev = Selects.device(devices.get(deviceName));
				if (dev.getAlias() == deviceName) { // device is correct in DB
					confirmDevices.put(deviceName, dev.getDeId());
				} else { // device id does not fit to device name
					// TODO error handling
				}
			}
		}
		if (!confirmDevices.isEmpty()) {
			sendConfirmDevices(confirmDevices, connectorId);
		}
	}
	
	/**
	 * Handle send device components.
	 *
	 * @param sdc the sdc
	 * @param connectorId the connector id
	 */
	private static void handleSendDeviceComponents(SendDeviceComponents sdc, int connectorId) {
		// TODO Regelparsing mit component values
		if (connectorId != sdc.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		int deviceId = sdc.getDeviceId();
		
		HashSet<DeviceComponents> components = sdc.getComponents(); 
		HashMap<String, Integer> confirmComponents = new HashMap<>();
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		
		for (DeviceComponents component : components) {
			if (component.getDeviceComponentId() == -1) { // Component has no DeCoId
				
				TempComponent temp = new TempComponent(deviceId, component.getComponentName(), component.getValue(), 
							component.getTimestamp(),connectorId, component.getDescription(), component.getUnit(), component.isActor());
				tempDeviceComponents.put(tempCompId, temp);
				//For Debugging	
				if(autoConfirm){
					confirmDeviceComponents(tempCompId);
				}
				tempCompId++;
			} else { // Component has DeCoId
				Device device = Selects.device(deviceId);
				if (device == null) { // Device does not exist
					// TODO was tun wir, wenn der Konnector eine Device ID nennt, die nicht in der DB ist?
				} else {
					Iterator<DeviceComponent> itDc = null;
					try {
						itDc = device.getDeviceComponents().iterator();
					} catch (LazyInitializationException lix){
						
					}
					if (itDc != null) {
						while (itDc.hasNext()) { // search for devicecomponent in db
							DeviceComponent dc = itDc.next();
							if (dc.getDeCoId() == component.getDeviceComponentId()) { // found deviceComponent
								confirmComponents.put(component.getComponentName(), dc.getDeCoId());
								inquiryList.add(new Inquiry(dc.getDeCoId(), component.getTimestamp(), component.getValue()));
//								Inserts.value(dc.getDeCoId(), new Date(component.getTimestamp()), component.getValue());
								break;
							} else {
								// TODO was passiert wenn DeCo nicht beim device dabei ist?
							}
						}						
					}
				}
			}
		}

		if(!confirmComponents.isEmpty()){
			sendToDos(insertValues(inquiryList));
			sendConfirmComponent(deviceId, confirmComponents, connectorId);
		}
	}
	
	/**
	 * Handle send value.
	 *
	 * @param sv the sv
	 * @param connectorId the connector id
	 */
	private static void handleSendValue(SendValue sv, int connectorId) {
		if (connectorId != sv.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		int deviceId = sv.getDeviceId();
		int deviceComponentId = sv.getDeviceComponentId();
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		inquiryList.add(new Inquiry(deviceComponentId, sv.getTimestamp(), sv.getValue()));
		sendToDos(insertValues(inquiryList));
		
		BoeseJson cv = new ConfirmValue(deviceId, deviceComponentId, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cv, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	private static void handleSendNotification(SendNotification sn, int connectorId) {
		if (connectorId != sn.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(new Date().getTime());
		sb.append(",");
		sb.append(sn.getConnectorId());
		sb.append(",");
		sb.append(sn.getDeviceId());
		sb.append(",");
		sb.append(sn.getDeviceComponentId());
		sb.append(",");
		sb.append(sn.getNotificationType());
		sb.append(",");
		sb.append(sn.getNotificationTimestamp());
		sb.append(",");
		sb.append(sn.getNotificationText());
		sb.append("\n");
		try {
		    Files.write(Paths.get("logfile.csv"), sb.toString().getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
			System.out.println("Error while writing the log file");
		}	
	}
	
	private static void handleUserRequestAllDevices(RequestAllDevices urad, int connectorId) {
		if (connectorId != urad.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		List<Device> devList = AllSelects.Devices();
		HashSet<UserDevice> deviceList = new HashSet<>();
		for (Device dev : devList) {
			deviceList.add(new UserDevice(dev.getAlias(), dev.getDeId(), dev.getZone().getZoId(), dev.getConnector().getCoId()));
		}
		BoeseJson usd = new UserSendDevices(deviceList, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usd, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	private static void handleUserRequestDeviceComponents(UserRequestDeviceComponents urdc, int connectorId) {
		if (connectorId != urdc.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashSet<Integer> deIDList = urdc.getDeviceIds();
		for (Integer devId : deIDList) {
			Set<DeviceComponent> decoSet = Selects.device(devId.intValue()).getDeviceComponents();
			HashSet<DeviceComponents> decos = new HashSet<>();
			for (DeviceComponent deco : decoSet) {
				decos.add(new DeviceComponents(deco.getDeCoId(), 
											deco.getComponent().getName(), 
											deco.getCurrentValue().doubleValue(), 
											deco.getComponent().getUnit().getName(), 
											deco.getDescription(), 
											deco.getComponent().isSensor(),
											deco.getStatus()));
			}
			BoeseJson usdc = new UserSendDeviceComponent(devId.intValue(), decos, connectorId, 0, new Date().getTime());
			OutputStream os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(usdc, os);
			SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
		}
	}
	
	private static void handleUserRequestConnectors(BoeseJson urc, int connectorId) {
		if (connectorId != urc.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashMap<Integer, String> connectors = new HashMap<>();
		if (urc.getType() == MessageType.USERREQUESTCONNECTORS) {
			HashSet<Integer> conIdList = ((UserRequestConnectors)urc).getConnectorIds();
			for (Integer conId : conIdList) {
				Connector con = Selects.connector(conId.intValue());
				connectors.put(conId, con.getName());			
			}
		} else if (urc.getType() == MessageType.USERREQUESTALLCONNECTORS){
			List<Connector> connectorList = AllSelects.Connector();
			for (de.bo.aid.boese.model.Connector connector : connectorList) {
				connectors.put(connector.getCoId(), connector.getName());
			}
		}
		BoeseJson usc = new UserSendConnectors(connectors, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	private static void handleUserRequestAllZones(UserRequestGeneral urg, int connectorId) {
		if (connectorId != urg.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashSet<Zone> zones = new HashSet<>();
		List<de.bo.aid.boese.model.Zone> zoneList = AllSelects.Zones();
		for (de.bo.aid.boese.model.Zone zone : zoneList) {
			zones.add(new Zone(zone.getZoId(), zone.getZone().getZoId(), zone.getName()));
		}
		
		BoeseJson usc = new UserSendZones(zones, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	
	private static void handleUserRequestAllRules(UserRequestGeneral urg, int connectorId) {
		if (connectorId != urg.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashSet<Rule> rules = new HashSet<>();
		List<de.bo.aid.boese.model.Rule> ruleList = AllSelects.Rules();
		for (de.bo.aid.boese.model.Rule rule : ruleList) {
			rules.add(new Rule(rule.getRuId(), rule.getActive().booleanValue(), rule.getInsertDate().getTime(), rule.getModifyDate().getTime(), 
					rule.getPermissions(), rule.getConditions(), rule.getActions()));
		}
		
		BoeseJson usc = new UserSendRules(rules, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	
	private static void handleUserRequestTemps(UserRequestGeneral urt, int connectorId) {
		if (connectorId != urt.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		BoeseJson ust = new UserSendTemps(tempConnectors, tempDevices, tempDeviceComponents, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(ust, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	
	private static void handleUserConfirmTemps(UserConfirmTemps uct, int connectorId) {
		if (connectorId != uct.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		for (Integer con : uct.getTempConnectors()) {
			try {
				confirmConnector(con, false);
			} catch(NotFoundException nfe) {
				// TODO
			}
		}
		for (Entry<Integer, Integer> entry : uct.getTempDevices().entrySet()) {
			try {
				confirmDevice(entry.getKey(), entry.getValue(), null);
			} catch(NotFoundException nfe) {
				// TODO
			}
		}
		for (UserTempComponent comp : uct.getTempDeviceComponents()){
			try {
				confirmDeviceComponent(comp.getTempComponentId(), comp.getUnitId(), comp.getName());
			} catch(NotFoundException nfe) {
				// TODO
			}
		}
	}
	
	private static void handleUserCreateRules(UserCreateRules ucr, int connectorId) {
		if (connectorId != ucr.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashMap<Integer, Integer> tempRules = new HashMap<>();
		List<Integer> ruleDeCoIds = new ArrayList<>();
		Interpretor interpretor = new Interpretor();
		int ruleId;
		for (Rule rule : ucr.getRules()) {
			ruleDeCoIds = interpretor.getAllDeCoIdsCondition(BoeseXML.readXML(new ByteArrayInputStream(rule.getConditions().getBytes())));
			ruleId = Inserts.rule(ruleDeCoIds, rule.getPermissions(), rule.getPermissions(), rule.getActions());
			tempRules.put(rule.getTempRuleId(), ruleId);
		}
		BoeseJson ucor = new UserConfirmRules(tempRules, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(ucor, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	private static void handleMultiMessages(MultiMessage mm, int connectorId) {
		if (connectorId != mm.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		//TODO evt. schlechte Performance weil hin und her geparst wird
		for(BoeseJson message : mm.getMessages()){ 
			OutputStream os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(message, os);
			handleMessage(os.toString(), connectorId);
		}
	}
	
	private static void handleSendStatus(SendStatus ss, int connectorId) {
		if (connectorId != ss.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		Updates.deviceComponentStatus(ss.getStatusCode(), ss.getDeviceComponentId());
		BoeseJson cs = new SendStatus(ss.getDeviceComponentId(), ss.getStatusCode(), ss.getStatusTimestamp(), false, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cs, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	
	/**
	 * Method to handle Json messages and act depednding on the type and content.
	 *
	 * @param message A string containing the Json message
	 * @param connectorId The Id of the connector.
	 */
	//TODO rename connectorId (intern Id in SocketHandler class)
	public static void handleMessage(String message, int connectorId) { 
		BoeseJson bjMessage = BoeseJson.readMessage(new ByteArrayInputStream(message.getBytes()));
		if (bjMessage == null) {
			System.out.println("No message");
			SessionHandler.getInstance().rejectConnection(connectorId);
		}
		//System.out.println(bjMessage.getType());
		switch (bjMessage.getType()) {
		case MULTI:
			handleMultiMessages((MultiMessage)bjMessage, connectorId);
			break;
		case REQUESTCONNECTION:
			handleRequestConnections((RequestConnection)bjMessage, connectorId);
			break;
		case SENDDEVICES:
			handleSendDevices((SendDevices)bjMessage, connectorId);
			break;
		case SENDDEVICECOMPONENTS:
			handleSendDeviceComponents((SendDeviceComponents)bjMessage, connectorId);
			break;
		case SENDVALUE:
			handleSendValue((SendValue)bjMessage, connectorId);
			break;
		case SENDNOTIFICATION:
			handleSendNotification((SendNotification)bjMessage, connectorId);
			break;
		case SENDSTATUS:
			handleSendStatus((SendStatus)bjMessage, connectorId);
			break;
		case USERREQUESTALLDEVICES:
			handleUserRequestAllDevices((RequestAllDevices)bjMessage, connectorId);
			break;
		case USERREQUESTDEVICECOMPONENTS:
			handleUserRequestDeviceComponents((UserRequestDeviceComponents)bjMessage, connectorId);
			break;
		case USERREQUESTCONNECTORS:
			handleUserRequestConnectors(bjMessage, connectorId);
			break;
		case USERREQUESTALLCONNECTORS:
			handleUserRequestConnectors(bjMessage, connectorId);
			break;
		case USERREQUESTALLZONES:
			handleUserRequestAllZones((UserRequestGeneral)bjMessage, connectorId);
			break;
		case USERREQUESTALLRULES:
			handleUserRequestAllRules((UserRequestGeneral)bjMessage, connectorId);
			break;
		case USERREQUESTTEMPS:
			handleUserRequestTemps((UserRequestGeneral)bjMessage, connectorId);
			break;
		case USERCONFIRMTEMPS:
			handleUserConfirmTemps((UserConfirmTemps)bjMessage, connectorId);
			break;
		case USERCREATERULES:
			handleUserCreateRules((UserCreateRules)bjMessage, connectorId);
			break;
		default:
			break;
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
//		ArrayList<Integer> decoIdL = new ArrayList<>();
//		decoIdL.add(29);
//		decoIdL.add(30);
//		String conditions = "<CONDITION><AND><COMPONENT><ID>16</ID><COMPERATOR>==</COMPERATOR><VALUE>1.0</VALUE><START_TIME>123123</START_TIME><DURATION>1</DURATION></COMPONENT></AND></CONDITION>";
//		String actions = "<ACTION><ACTOR><ID>21</ID><VALUE>1.0</VALUE><RESET_VALUE>0</RESET_VALUE><START_TIME>123123</START_TIME><DURATION>5</DURATION><REPEAT_AFTER_END>0</REPEAT_AFTER_END></ACTOR></ACTION>";
//		
//		Inserts.rule(decoIdL, "", conditions, actions);
		Distributor distr = new Distributor();
		distr.checkArguments(args);
		distr.loadProperties();
		distr.initDatabase();
		distr.startWebsocketServer();
//		SocketEndpoint server = new SocketEndpoint();
//		server.start(8081);

	}
	
	/**
	 * Gets the temp connectors.
	 *
	 * @return the temp connectors
	 */
	public static HashMap<Integer, String> getTempConnectors(){
		return tempConnectors;
	}
	
	/**
	 * Confirm connector.
	 *
	 * @param tempId the temp id
	 * @param isUserConnector if it is an user connector
	 * @throws NotFoundException the not found exception
	 */
	public static void confirmConnector(int tempId, boolean isUserConnector) throws NotFoundException{
	
		String name= tempConnectors.get(tempId);
		
		if(name == null){
			throw new NotFoundException("Connector with tempId " + tempId + " not Found");
		}

		SecureRandom sr = new SecureRandom();
		String pw = String.valueOf(sr.nextLong());

		int conId = Inserts.connector(name, pw);
		SessionHandler.getInstance().setConnectorId(tempId, conId);

		// Send ConfirmConnection
		BoeseJson cc = new ConfirmConnection(pw, conId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cc, os);
		SessionHandler.getInstance().sendToConnector(conId, os.toString());

		if (!isUserConnector) {			
			// Send RequestAllDevices
			BoeseJson rad = new RequestAllDevices(conId, 0, new Date().getTime());
			os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(rad, os);
			SessionHandler.getInstance().sendToConnector(conId, os.toString());
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
	public static void confirmDevice(int tempId, int zoneId, String name) throws NotFoundException{

		TempDevice temp = tempDevices.get(tempId);
		
		if(temp == null){
			throw new NotFoundException("Device with tempId " + tempId + " not found");
		}
		if(name == null){
			name = temp.getName();
		}
		
		int connectorId = temp.getConnectorID();
		
		HashMap<String, Integer> devices = new HashMap<String, Integer>();
		devices.put(name, Inserts.device(connectorId, zoneId, name, "serial"));
		
		sendConfirmDevices(devices, connectorId);
		
		System.out.println("User confirmed Device with name: " + name + "\n");	
		tempDevices.remove(temp);
	}
	
	/**
	 * Send confirm devices.
	 *
	 * @param devices the devices
	 * @param connectorId the connector id
	 */
	public static void sendConfirmDevices(HashMap<String, Integer> devices, int connectorId) {
		//send Confirm Devices
		BoeseJson cd = new ConfirmDevices(devices, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cd, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());

		//Send Request Device Components		
		for (Integer deviceId : devices.values()){
			BoeseJson rdc = new RequestDeviceComponents(deviceId, connectorId, 0, new Date().getTime());
			os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(rdc, os);
			SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
		}
	}
	
	/**
	 * Gets the temp devices.
	 *
	 * @return the temp devices
	 */
	public static HashMap<Integer, TempDevice> getTempDevices() {
		return tempDevices;
	}

	/**
	 * Confirm device component.
	 *
	 * @param tempId the temp id
	 * @param unitId the unit id
	 * @param name the name
	 * @throws NotFoundException the not found exception
	 */
	public static void confirmDeviceComponent(int tempId, int unitId, String name) throws NotFoundException{
		TempComponent temp = tempDeviceComponents.get(tempId);
		if(temp == null){
			throw new NotFoundException("Component with tempId " + tempId + " not found");
		}
		if (name == null){
			name = temp.getName();
		}
		int deviceId = temp.getDeviceId();
		int connectorId = temp.getConnectorId();
		
		int componentId = Inserts.component(name, unitId, !temp.isActor()); 
		int deCoId = Inserts.deviceComponent(deviceId, componentId, temp.getDescription());
		HashMap<String, Integer> confirmComponents = new HashMap<String, Integer>();
		confirmComponents.put(name, deCoId);
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		inquiryList.add(new Inquiry(deCoId, temp.getValueTimestamp(), temp.getValue()));
		sendToDos(insertValues(inquiryList));
		
		sendConfirmComponent(deviceId, confirmComponents, connectorId);
		
		System.out.println("User confirmed Component with name: " + name + " and Device with id: " + deviceId + "\n");	
		tempDevices.remove(temp);
	}
	
	/**
	 * Send confirm component.
	 *
	 * @param deviceId the device id
	 * @param components the components
	 * @param connectorId the connector id
	 */
	public static void sendConfirmComponent(int deviceId, HashMap<String, Integer> components, int connectorId) {
		//Send ConfirmDeviceComponents
		BoeseJson cdc = new ConfirmDeviceComponents(deviceId, components, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cdc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	/**
	 * Send value.
	 *
	 * @param deId the de id
	 * @param deCoId the de co id
	 * @param value the value
	 * @param valueTimestamp the value timestamp
	 * @param connectorId the connector id
	 */
	public static void sendValue(int deId, int deCoId, double value, long valueTimestamp, int connectorId ) {
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson sv = new SendValue(deId, deCoId, value, valueTimestamp, connectorId, 0, new Date().getTime());
		os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sv, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	/**
	 * Confirm connectors.
	 *
	 * @param tempId the temp id
	 * @param isUserConnector if it is an user connector
	 */
	//Asynchronous handler
	public static void confirmConnectors(int tempId, boolean isUserConnector){
		try {
			confirmConnector(tempId, isUserConnector);
			} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Confirm devices.
	 *
	 * @param tempDeviceId the temp device id
	 */
	public static void confirmDevices(int tempDeviceId){
		try {
			confirmDevice(tempDeviceId, 0, null);
			} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Confirm device components.
	 *
	 * @param tempCompId the temp comp id
	 */
	public static void confirmDeviceComponents(int tempCompId){
		try {
			confirmDeviceComponent(tempCompId, 0, null);
			} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the temp components.
	 *
	 * @return the temp components
	 */
	// Helper
	public static HashMap<Integer, TempComponent> getTempComponents(){
		return tempDeviceComponents;
	}
	
	/**
	 * Insert values.
	 *
	 * @param inquirys the inquirys
	 * @return the list
	 */
	public static List<Component> insertValues(List<Inquiry> inquirys) {
		for (Inquiry inq : inquirys) {
			Inserts.value(inq.getDeviceComponentId(), new Date(inq.getTimestamp()), inq.getValue());
		}
		Controll controll = new Controll();
		List<Component> todos;
		try {
			todos = controll.getToDos(inquirys);
		} catch (Exception e) {
			System.err.println("Bad XML: " + e.getMessage());
			// TODO Exception Handling
			todos = null;
		}
		return todos;
	}
	
	/**
	 * Send to dos.
	 *
	 * @param todos the todos
	 */
	public static void sendToDos(List<Component> todos) {
		for (Component component : todos) {
			int deCoId = component.getId();
			int deviceId = Selects.deviceComponent(deCoId).getDevice().getDeId();
			int idConnector = Selects.device(deviceId).getConnector().getCoId();
			sendValue(deviceId, deCoId, component.getValue(), new Date().getTime(), idConnector);
		}
	}
}
