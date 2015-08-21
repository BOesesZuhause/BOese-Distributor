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
import java.util.concurrent.CopyOnWriteArrayList;

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
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.socket.BoeseServer;
import de.bo.aid.boese.socket.SocketHandler;
import javassist.NotFoundException;

public class MainClass {
	//TODO handle Acknowledge abgleich
	
	//Lists for unconfirmed Objects
	private static List<Connector> tempConnectors = new CopyOnWriteArrayList<Connector>();
	private static List<Device> tempDevices = new CopyOnWriteArrayList<Device>();
	private static List<DeviceComponents> tempDeviceComponents = new CopyOnWriteArrayList<DeviceComponents>();
	
	private static void handleRequestConnections(RequestConnection rc, int tempId) {
		int seqNr = rc.getSeqenceNr();
		if (rc.getPassword() == null && rc.getConnectorId() == -1) {
 
			//Add requesting Connector to tempConnectors
			Connector con = new Connector();
			con.setCoId(tempId);
			con.setName(rc.getConnectorName());
			tempConnectors.add(con);

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
		for (String deviceName : devices.keySet()) {
			//TODO find zone ID (is set by user)
			int zoneId = 0;
			//Device is inserted by user
			if (devices.get(deviceName) == -1) { // device not in db
				//TODO get Serialnumber for Insert
				devices.put(deviceName, Inserts.device(connectorId, zoneId, deviceName, "serial"));
			} else {
				//TODO check if device is correct in db !!!erledigt!!!
			}
		}
		
		//send Confirm Devices
		BoeseJson cd = new ConfirmDevices(devices, connectorId, seqNr+1, seqNr, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cd, os);
		SocketHandler.getInstance().sendToConnector(connectorId, os.toString());
		
		
		//Send Request Device Components		
		for (Integer deviceId : devices.values()){
			BoeseJson rdc = new RequestDeviceComponents(deviceId, connectorId, seqNr+1, seqNr, 0, new Date().getTime());
			os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(rdc, os);
			SocketHandler.getInstance().sendToConnector(connectorId, os.toString());
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
		for (DeviceComponents component : components) {
			if (component.getDeviceComponentId() == -1) { // Component has no DeCoId
				// TODO User muss componentID zuweisen
				int componentId = Inserts.component(component.getComponentName(), 0, true); // TODO!!!!
				int deCoId = Inserts.deviceComponent(deviceId, componentId, component.getComponentName());
				confirmComponents.put(component.getComponentName(), deCoId);
				//Inserts.value(deCoId, new Date(component.getTimestamp()), component.getValue()); //TODO
			} else { // Component has DeCoId
				Device device = Selects.device(deviceId);
				if (device == null) { // Device does not exist
					// TODO was tun wir, wenn der Konnector eine Device ID nennt, die nicht in der DB ist?
				} else {
					@SuppressWarnings("unchecked")
					Iterator<DeviceComponent> itDc = device.getDeviceComponents().iterator();
					while (itDc.hasNext()) { // search for devicecomponent in db
						DeviceComponent dc = itDc.next();
						if (dc.getDeCoId() == component.getDeviceComponentId()) { // deviceComponent gefunden
							confirmComponents.put(component.getComponentName(), dc.getDeCoId());
							Inserts.value(dc.getDeCoId(), new Date(component.getTimestamp()), component.getValue());
							break;
						} // TODO was passiert wenn DeCo nicht beim device dabei ist?
					}
				}
			}
		}
		
		BoeseJson cdc = new ConfirmDeviceComponents(deviceId, confirmComponents, connectorId, seqNr+1, seqNr, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cdc, os);
		SocketHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	private static void handleSendValue(SendValue sv, int connectorId) {
		int seqNr = sv.getSeqenceNr();
		if (connectorId != sv.getConnectorId()) {
			SocketHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		int deviceId = sv.getDeviceId();
		int deviceComponentId = sv.getDeviceComponentId();
		Inserts.value(deviceComponentId, new Date(sv.getValueTimestamp()), sv.getValue());
		
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
		BoeseServer server = new BoeseServer();
		server.start();

	}
	
	public static List<Connector> getTempConnectors(){
		return tempConnectors;
	}
	
	public static void confirmConnector(int tempId) throws NotFoundException{
		
		Connector con = null;
		for(Connector c : tempConnectors){
			if(c.getCoId() == tempId){
				con = c;
			}
		}
		
		if(con == null){
			throw new NotFoundException("Connector with tempId " + tempId + " not Found");
		}
		tempConnectors.remove(con);
	
		SecureRandom sr = new SecureRandom();
		String pw = String.valueOf(sr.nextLong());

		int conId = Inserts.connector(con.getName(), pw);
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
	}
	
	public static void confirmDevice(){
		//TODO complete
	}
	
	public static void confirmDeviceComponent(){
		//TODO complete
	}


}
