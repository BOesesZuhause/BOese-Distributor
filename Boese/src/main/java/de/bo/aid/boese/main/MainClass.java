
package de.bo.aid.boese.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

import org.hibernate.LazyInitializationException;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.ConfirmDeviceComponents;
import de.bo.aid.boese.json.ConfirmDevices;
import de.bo.aid.boese.json.ConfirmValue;
import de.bo.aid.boese.json.DeviceComponents;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.RequestDeviceComponents;
import de.bo.aid.boese.json.SendDeviceComponents;
import de.bo.aid.boese.json.SendDevices;
import de.bo.aid.boese.json.SendNotification;
import de.bo.aid.boese.json.SendValue;
import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.ruler.Controll;
import de.bo.aid.boese.ruler.Inquiry;
import de.bo.aid.boese.socket.BoeseServer;
import de.bo.aid.boese.socket.SocketHandler;
import de.bo.aid.boese.xml.Component;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class MainClass.
 */
public class MainClass {
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
	
	/**
	 * Handle request connections.
	 *
	 * @param rc the rc
	 * @param tempId the temp id
	 */
	private static void handleRequestConnections(RequestConnection rc, int tempId) {
		int seqNr = rc.getSeqenceNr();
		if (rc.getPassword() == null && rc.getConnectorId() == -1) {
 
			//Add requesting Connector to tempConnectors with tempId from SocketHandler
			tempConnectors.put(tempId, rc.getConnectorName());
			
			if(autoConfirm){
				//For Debugging
				confirmConnectors(tempId);
			}

		} else { //TODO test
			String pw = rc.getPassword();
			int conId = rc.getConnectorId();
			String conName = rc.getConnectorName();

			Connector con = Selects.connector(conId);

			if (con.getName().compareTo(conName) == 0 && con.getPassword().compareTo(pw) == 0) {
				SocketHandler.getInstance().setConnectorId(tempId, conId);
				BoeseJson cc = new ConfirmConnection(pw, conId, seqNr+1, seqNr, 0, new Date().getTime());
				
				OutputStream os = new ByteArrayOutputStream();
				BoeseJson.parseMessage(cc, os);
				SocketHandler.getInstance().sendToConnector(conId, os.toString());			
			} else {
				SocketHandler.getInstance().rejectConnection(conId);
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
		int seqNr = sd.getSeqenceNr();
		if (connectorId != sd.getConnectorId()) {
			SocketHandler.getInstance().rejectConnection(connectorId);
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
			sendConfirmDevices(confirmDevices, seqNr, connectorId);
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
		int seqNr = sdc.getSeqenceNr();
		if (connectorId != sdc.getConnectorId()) {
			SocketHandler.getInstance().rejectConnection(connectorId);
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
			sendConfirmComponent(deviceId, confirmComponents, seqNr, connectorId);
		}
	}
	
	/**
	 * Handle send value.
	 *
	 * @param sv the sv
	 * @param connectorId the connector id
	 */
	private static void handleSendValue(SendValue sv, int connectorId) {
		int seqNr = sv.getSeqenceNr();
		if (connectorId != sv.getConnectorId()) {
			SocketHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		int deviceId = sv.getDeviceId();
		int deviceComponentId = sv.getDeviceComponentId();
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		inquiryList.add(new Inquiry(deviceComponentId, sv.getTimestamp(), sv.getValue()));
		sendToDos(insertValues(inquiryList));
		
		BoeseJson cv = new ConfirmValue(deviceId, deviceComponentId, connectorId, seqNr+1, seqNr, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cv, os);
		SocketHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	private static void handleSendNotification(SendNotification sn, int connectorId) {
		int seqNr = sn.getSeqenceNr();
		if (connectorId != sn.getConnectorId()) {
			SocketHandler.getInstance().rejectConnection(connectorId);
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
			SocketHandler.getInstance().rejectConnection(connectorId);
		}
		switch (bjMessage.getType()) {
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
		BoeseServer server = new BoeseServer();
		server.start();

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
	 * @throws NotFoundException the not found exception
	 */
	public static void confirmConnector(int tempId) throws NotFoundException{
	
		String name= tempConnectors.get(tempId);
		
		if(name == null){
			throw new NotFoundException("Connector with tempId " + tempId + " not Found");
		}

		SecureRandom sr = new SecureRandom();
		String pw = String.valueOf(sr.nextLong());

		int conId = Inserts.connector(name, pw);
		SocketHandler.getInstance().setConnectorId(tempId, conId);

		// Send ConfirmConnection
		BoeseJson cc = new ConfirmConnection(pw, conId, 0, 0, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cc, os);
		SocketHandler.getInstance().sendToConnector(conId, os.toString());

		// Send RequestAllDevices
		BoeseJson rad = new RequestAllDevices(conId, 0, 0, 0, new Date().getTime());
		os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(rad, os);
		SocketHandler.getInstance().sendToConnector(conId, os.toString());
		
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
		
		sendConfirmDevices(devices, 0, connectorId);
		
		System.out.println("User confirmed Device with name: " + name + "\n");	
		tempDevices.remove(temp);
	}
	
	/**
	 * Send confirm devices.
	 *
	 * @param devices the devices
	 * @param seqNr the seq nr
	 * @param connectorId the connector id
	 */
	public static void sendConfirmDevices(HashMap<String, Integer> devices, int seqNr, int connectorId) {
		//send Confirm Devices
		BoeseJson cd = new ConfirmDevices(devices, connectorId, seqNr+1, seqNr, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cd, os);
		SocketHandler.getInstance().sendToConnector(connectorId, os.toString());

		//Send Request Device Components		
		for (Integer deviceId : devices.values()){
			BoeseJson rdc = new RequestDeviceComponents(deviceId, connectorId, 0, 0, 0, new Date().getTime());
			os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(rdc, os);
			SocketHandler.getInstance().sendToConnector(connectorId, os.toString());
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
		
		sendConfirmComponent(deviceId, confirmComponents, 0, connectorId);
		
		System.out.println("User confirmed Component with name: " + name + " and Device with id: " + deviceId + "\n");	
		tempDevices.remove(temp);
	}
	
	/**
	 * Send confirm component.
	 *
	 * @param deviceId the device id
	 * @param components the components
	 * @param seqNr the seq nr
	 * @param connectorId the connector id
	 */
	public static void sendConfirmComponent(int deviceId, HashMap<String, Integer> components, int seqNr, int connectorId) {
		//Send ConfirmDeviceComponents
		BoeseJson cdc = new ConfirmDeviceComponents(deviceId, components, connectorId, seqNr+1, seqNr, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cdc, os);
		SocketHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	/**
	 * Send value.
	 *
	 * @param deId the de id
	 * @param deCoId the de co id
	 * @param value the value
	 * @param valueTimestamp the value timestamp
	 * @param connectorId the connector id
	 * @param seqNr the seq nr
	 */
	public static void sendValue(int deId, int deCoId, double value, long valueTimestamp, int connectorId, int seqNr) {
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson sv = new SendValue(deId, deCoId, value, valueTimestamp, connectorId, seqNr+1, seqNr, 0, new Date().getTime());
		os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sv, os);
		SocketHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	/**
	 * Confirm connectors.
	 *
	 * @param tempId the temp id
	 */
	//Asynchronous handler
	public static void confirmConnectors(int tempId){
		try {
			confirmConnector(tempId);
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
		List<Component> todos = controll.getToDos(inquirys);
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
			sendValue(deviceId, deCoId, component.getValue(), new Date().getTime(), idConnector, -1);
		}
	}
}
