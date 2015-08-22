package de.bo.aid.boese.simulation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
import de.bo.aid.boese.simulation.SocketClientStandalone.MessageHandler;

/**
 * This class simulates a connector.
 * 
 * @author Sebastian
 *
 */
public class ConnectorSimulation implements MessageHandler {
	
	SocketClientStandalone client;
	boolean connectionClosed = false;
	
	
	
	//For time measurements
	private long sendTime;

	// Connector details
	private int conId = -1;
	private String password = null;
	private final String NAME = "KonnektorSim";
	private List<Device> devices = new ArrayList<Device>();
	private Device heizung;
	private Device steckerleiste;
	private Device tuersensor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.bo.aid.boese.socket.test.SocketClientStandalone.MessageHandler#
	 * handleMessage(java.lang.String)
	 */
	public void handleMessage(String message) {
		System.out.println("Client received Message: " + message);

		BoeseJson bjMessage = BoeseJson.readMessage(new ByteArrayInputStream(message.getBytes()));

		if (bjMessage == null) {
			return; // TODO Fehlermeldungen
		}

		switch (bjMessage.getType()) {

		case CONFIRMCONNECTION:
			handleConfirmconnection((ConfirmConnection) bjMessage);
			break;

		case REQUESTALLDEVICES:
			handleRequestAllDevices((RequestAllDevices) bjMessage);
			break;

		case CONFIRMDEVICES:
			handleConfirmDevices((ConfirmDevices) bjMessage);
			break;

		case REQUESTDEVICECOMPONENTS:
			handleRequestDeviceComponents((RequestDeviceComponents) bjMessage);
			break;

		case CONFIRMDEVICECOMPONENTS:
			handleConfirmDeviceComponents((ConfirmDeviceComponents) bjMessage);
			break;

		case CONFIRMVALUE:
			handleConfirmValue((ConfirmValue) bjMessage);
			break;
		default:
			break;

		}
	}

	private void handleConfirmValue(ConfirmValue bjMessage) {
		System.out.println("Server Confirmed Value "   + "Duration: " + (new Date().getTime() - sendTime) + "ms\n");
		//TODO What to do with confirm-message?
	}

	private void handleConfirmDeviceComponents(ConfirmDeviceComponents bjMessage) {
		System.out.println("Server confirmed DeviceComponents "  + "Duration: " + (new Date().getTime() - sendTime) + "ms\n");
		
		//find Device
		int deviceId = bjMessage.getDeviceId();	
		Device dev = null;
		for(Device d : devices){
			if(d.getId()== deviceId){
				dev = d;
			}
		}
		if(dev == null){
			//TODO add Error handling
		}
		
		//Confirmed Components
		HashMap<String, Integer> compMap = bjMessage.getComponents();
		
		//actual Components
		HashSet<DeviceComponents> actualCompMap = dev.getComponents();	
		
		
		for (String componentName : compMap.keySet()) {	
			for(DeviceComponents devComp : actualCompMap){
				if(devComp.getComponentName().equals(componentName)){
					devComp.setDeviceComponentId(compMap.get(componentName));
				}
			}
		}
		
	}

	private void handleRequestDeviceComponents(RequestDeviceComponents bjMessage) {
		System.out.println("Server requests DeviceComponents for Device with id: " + bjMessage.getDeviceId());
		Device requestedDevice = null;
		for(Device dev : devices){
			if(dev.getId() == bjMessage.getDeviceId()){
				requestedDevice = dev;
			}
		}
		
		int seqNr = bjMessage.getSeqenceNr();
		
		sendTime = new Date().getTime();
		SendDeviceComponents sendDevComp = new SendDeviceComponents(requestedDevice.getId(), requestedDevice.getComponents(), conId, seqNr+1, seqNr, 0, sendTime);
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendDevComp, os);
		client.sendMessage(os.toString());
		}

	private void handleConfirmDevices(ConfirmDevices bjMessage) {
		System.out.println("Server confirmed Devices "  + "Duration: " + (new Date().getTime() - sendTime) + "ms\n");
	
		HashMap<String, Integer> devMap = bjMessage.getDevices();
		for (String deviceName : devMap.keySet()) {	
			for(Device dev : devices){
				if(dev.getName().equals(deviceName)){
					dev.setId(devMap.get(deviceName));
				}
			}
		}
	}

	private void handleRequestAllDevices(RequestAllDevices bjMessage) {
		System.out.println("Server requests Devices");
		
		int seqNr = bjMessage.getSeqenceNr();

		
		HashMap<String, Integer> devHash = new HashMap<>();
		for(Device dev : devices){
			devHash.put(dev.getName(), dev.getId());
		}
		
		sendTime = new Date().getTime();
		SendDevices sendDevs = new SendDevices(devHash, conId, seqNr + 1, seqNr, 0, sendTime);
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendDevs, os);
		client.sendMessage(os.toString());
	}

	private void handleConfirmconnection(ConfirmConnection bjMessage) {
		this.password = bjMessage.getPassword();
		this.conId = bjMessage.getConnectorId();
		System.out.println("Client was confirmed by Server Duration:" + (new Date().getTime() - sendTime) + "ms\n");
	}
	
	private void initializeData(){
		// Initialize Attributes
		heizung = new Device(-1, "Heizung");
		DeviceComponents c1 = new DeviceComponents(-1, "Temperatursensor", 24, new Date().getTime());
		DeviceComponents c2 = new DeviceComponents(-1, "Temperaturregler", -1, new Date().getTime());
		heizung.addComponent(c1);
		heizung.addComponent(c2);
		
		steckerleiste = new Device(-1, "steckerleiste");
		DeviceComponents c3 = new DeviceComponents(-1, "Fernseher", 50, new Date().getTime());
		DeviceComponents c4 = new DeviceComponents(-1,"Lampe",  40, new Date().getTime());
		DeviceComponents c5 = new DeviceComponents(-1, "Kuehlschrank", 200, new Date().getTime());
		steckerleiste.addComponent(c3);
		steckerleiste.addComponent(c4);
		steckerleiste.addComponent(c5);
		
		tuersensor = new Device(-1, "Tuersensor");
		DeviceComponents c6 = new DeviceComponents(-1,"Tuersensor",  0, new Date().getTime());
		tuersensor.addComponent(c6);
		
		devices.add(heizung);
		devices.add(steckerleiste);
		devices.add(tuersensor);
	}

	/**
	 * Starts the Connector Simulation
	 * 
	 */
	public void start() {
		initializeData();

		// Initialize client
		URI uri = URI.create("ws://localhost:8081/events/");
		client = new SocketClientStandalone();
		client.addMessageHandler(this);
		client.connect(uri);

		// Request connection
		sendTime = new Date().getTime();
		RequestConnection reqCon = new RequestConnection(NAME, password, conId, 0, 0, 0, sendTime);
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(reqCon, os);
		client.sendMessage(os.toString());

// wait for answer
//while(!connectionClosed){
//	try {
//		Thread.sleep(1);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}

	}
	
	public void sendValue(double value){
		int devId = heizung.getId();
		int devCompId = 0;
		
		for(DeviceComponents devComp : heizung.getComponents()){
			if(devComp.getComponentName().equals("Temperatursensor")){
				devCompId = devComp.getDeviceComponentId();
			}
		}
		
		if(devCompId == 0){
			//TODO add Error handling
		}
		
		sendTime = new Date().getTime();
		SendValue sendval = new SendValue(devId, devCompId, value, sendTime, conId, 0, 0, 0, sendTime);
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendval, os);
		client.sendMessage(os.toString());
			}

	@Override
	public void closeConnection() {
		connectionClosed = true;
		
	}
}
