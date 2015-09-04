package de.bo.aid.boese.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

public class MainClass {
	//TODO handle Acknowledge abgleich
	
	//hashmaps for unconfirmed Objects with a temporary Id as key
	private static HashMap<Integer, String> tempConnectors = new HashMap<Integer, String>();
	private static HashMap<Integer, TempDevice> tempDevices = new HashMap<Integer, TempDevice>();
	private static HashMap<Integer, TempComponent> tempDeviceComponents = new HashMap<Integer, TempComponent>();
	
	//tempIds for unconfirmed Objects
	static int tempDeviceId = 1;
	static int tempCompId = 1;
	private static boolean autoConfirm = true; //Debugging
	int tempIdDeviceComponents = 1;
	
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
							component.getTimestamp(),connectorId);
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
	
	/**
	 * Method to handle Json messages and act depednding on the type and content
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
		default:
			break;
		}
	}

	public static void main(String[] args) {
//		ArrayList<Integer> decoIdL = new ArrayList<>();
//		decoIdL.add(29);
//		decoIdL.add(30);
//		String conditions = "<CONDITIONS><AND><COMPONENT><ID>16</ID><COMPERATOR>==</COMPERATOR><VALUE>1.0</VALUE><START_TIME>123123</START_TIME><DURATION>1</DURATION></COMPONENT></AND></CONDITIONS>";
//		String actions = "<ACTION><AKTOR><ID>21</ID><VALUE>1.0</VALUE><RESET_VALUE>0</RESET_VALUE><START_TIME>123123</START_TIME><DURATION>5</DURATION><REPEAT_AFTER_END>0</REPEAT_AFTER_END></AKTOR></ACTION>";
//		
//		Inserts.rule(decoIdL, "", conditions, actions);
		BoeseServer server = new BoeseServer();
		server.start();

	}
	
	public static HashMap<Integer, String> getTempConnectors(){
		return tempConnectors;
	}
	
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
	
	public static HashMap<Integer, TempDevice> getTempDevices() {
		return tempDevices;
	}

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
		
		int componentId = Inserts.component(name, unitId, true); 
		int deCoId = Inserts.deviceComponent(deviceId, componentId);
		HashMap<String, Integer> confirmComponents = new HashMap<String, Integer>();
		confirmComponents.put(name, deCoId);
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		inquiryList.add(new Inquiry(deCoId, temp.getValueTimestamp(), temp.getValue()));
		sendToDos(insertValues(inquiryList));
		
		sendConfirmComponent(deviceId, confirmComponents, 0, connectorId);
		
		System.out.println("User confirmed Component with name: " + name + " and Device with id: " + deviceId + "\n");	
		tempDevices.remove(temp);
	}
	
	public static void sendConfirmComponent(int deviceId, HashMap<String, Integer> components, int seqNr, int connectorId) {
		//Send ConfirmDeviceComponents
		BoeseJson cdc = new ConfirmDeviceComponents(deviceId, components, connectorId, seqNr+1, seqNr, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cdc, os);
		SocketHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	public static void sendValue(int deId, int deCoId, double value, long valueTimestamp, int connectorId, int seqNr) {
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson sv = new SendValue(deId, deCoId, value, valueTimestamp, connectorId, seqNr+1, seqNr, 0, new Date().getTime());
		os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sv, os);
		SocketHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	//Asynchronous handler
	public static void confirmConnectors(int tempId){
		try {
			confirmConnector(tempId);
			} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void confirmDevices(int tempDeviceId){
		try {
			confirmDevice(tempDeviceId, 0, null);
			} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void confirmDeviceComponents(int tempCompId){
		try {
			confirmDeviceComponent(tempCompId, 0, null);
			} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Helper
	public static HashMap<Integer, TempComponent> getTempComponents(){
		return tempDeviceComponents;
	}
	
	public static List<Component> insertValues(List<Inquiry> inquirys) {
		for (Inquiry inq : inquirys) {
			Inserts.value(inq.getDeviceComponentId(), new Date(inq.getTimestamp()), inq.getValue());
		}
		Controll controll = new Controll();
		List<Component> todos = controll.getToDos(inquirys);
		return todos;
	}
	
	public static void sendToDos(List<Component> todos) {
		for (Component component : todos) {
			int deCoId = component.getId();
			int deviceId = Selects.deviceComponent(deCoId).getDevice().getDeId();
			int idConnector = Selects.device(deviceId).getConnector().getCoId();
			sendValue(deviceId, deCoId, component.getValue(), new Date().getTime(), idConnector, -1);
		}
	}
}
