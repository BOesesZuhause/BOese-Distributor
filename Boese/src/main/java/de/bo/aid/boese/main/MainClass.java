package de.bo.aid.boese.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

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
import de.bo.aid.boese.json.SendDeviceComponents;
import de.bo.aid.boese.json.SendDevices;
import de.bo.aid.boese.json.SendValue;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.socket.BoeseServer;
import de.bo.aid.boese.socket.SocketHandler;

public class MainClass {
	//TODO handle Acknowledge abgleich
	
	
	private static void handleRequestConnections(RequestConnection rc, int connectorId) {
		int seqNr = rc.getSeqenceNr();
		if (rc.getPassword() == null && rc.getConnectorId() == -1) {
 
			//TODO User muss bestätigen tun

			SecureRandom sr = new SecureRandom();
			String pw = String.valueOf(sr.nextLong());

			int conId = Inserts.connector(rc.getConnectorName(), pw);
			SocketHandler.getInstance().setConnectorId(connectorId, conId);

			// Send ConfirmConnection
			BoeseJson cc = new ConfirmConnection(pw, conId, seqNr+1, seqNr, 0, new Date().getTime());
			OutputStream os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(cc, os);
			SocketHandler.getInstance().sendToConnector(conId, os.toString());

			// Send RequestAllDevices
			BoeseJson rad = new RequestAllDevices(conId, seqNr+2, seqNr+1, 0, new Date().getTime());
			os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(rad, os);
			SocketHandler.getInstance().sendToConnector(conId, os.toString());
		} else {
			String pw = rc.getPassword();
			int conId = rc.getConnectorId();
			String conName = rc.getConnectorName();

			Connector con = Selects.connector(conId);

			if (con.getName().compareTo(conName) == 0 && con.getPassword().compareTo(pw) == 0) {
				SocketHandler.getInstance().setConnectorId(connectorId, conId);
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
			//TODO find zone ID
			int zoneId = 0;
			if (devices.get(deviceName) == -1) { // device not in db
				//TODO get Serialnumber for Insert
				devices.put(deviceName, Inserts.device(connectorId, zoneId, deviceName, "serial"));
			} else {
				//TODO check if device is correct in db !!!erledigt!!!
			}
		}
		
		BoeseJson cd = new ConfirmDevices(devices, connectorId, seqNr+1, seqNr, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cd, os);
		SocketHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	private static void handleSendDeviceComponents(SendDeviceComponents sdc, int connectorId) {
		int seqNr = sdc.getSeqenceNr();
		if (connectorId != sdc.getConnectorId()) {
			SocketHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		int deviceId = sdc.getDeviceId();
		HashSet<DeviceComponents> components = sdc.getComponents();
		HashMap<String, Integer> confirmComponents = new HashMap<>();
		for (DeviceComponents component : components) {
			if (component.getDeviceComponentId() == -1) {
				// TODO User muss componentID zuweisen
				int componentId = 0;
				
				int deCoId = Inserts.deviceComponent(deviceId, componentId, component.getComponentName());
				confirmComponents.put(component.getComponentName(), deCoId);
				//TODO Inserts Value! !!!erledigt!!!
			} else {
				// TODO check if decoID is in db !!!erledigt!!!
				// TODO Inserts Value !!!erledigt!!!
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
		// TODO Insert Value
		
		BoeseJson cv = new ConfirmValue(deviceId, deviceComponentId, connectorId, seqNr+1, seqNr, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cv, os);
		SocketHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
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


}
