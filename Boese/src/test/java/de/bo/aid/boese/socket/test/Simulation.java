package de.bo.aid.boese.socket.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.ConfirmDevices;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.SendDevices;
import de.bo.aid.boese.socket.test.SocketClientStandalone.MessageHandler;

/**
 * This class simulates a connector.
 * 
 * @author Sebastian
 *
 */
public class Simulation implements MessageHandler {
	
	SocketClientStandalone client;

	// Connector details
	private int conId = -1;
	private String password = null;
	private final String NAME = "KonnektorSim";
	private HashMap<String, Integer> devices = new HashMap<>(); //TODO save device-Ids

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
			return; // TODO
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

			break;

		case CONFIRMDEVICECOMPONENTS:

			break;

		case CONFIRMVALUE:

			break;

		}
	}

	private void handleConfirmDevices(ConfirmDevices bjMessage) {
		// TODO Auto-generated method stub
		
	}

	private void handleRequestAllDevices(RequestAllDevices bjMessage) {
		System.out.println("Server requests Devices");
		
		int seqNr = bjMessage.getSeqenceNr();

		SendDevices sendDevs = new SendDevices(devices, conId, seqNr + 1, seqNr, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sendDevs, os);
		client.sendMessage(os.toString());
	}

	private void handleConfirmconnection(ConfirmConnection bjMessage) {
		this.password = bjMessage.getPassword();
		this.conId = bjMessage.getConnectorId();
		System.out.println("Client swas confirmed by Server");
	}

	/**
	 * Starts the Connector Simulation
	 * 
	 */
	public void start() {
		// Initialize Attributes
		devices.put("Heizung", -1);
		devices.put("Schalter", -1);
		devices.put("Tuersensor", -1);

		// Initialize client
		URI uri = URI.create("ws://localhost:8081/events/");
		client = new SocketClientStandalone();
		client.addMessageHandler(this);
		client.connect(uri);

		// Request connection
		RequestConnection reqCon = new RequestConnection(NAME, password, conId, 0, 0, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(reqCon, os);
		client.sendMessage(os.toString());

		// wait for answer
		try {
			Thread.sleep(5000); //TODO connection should be open until rejected
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
