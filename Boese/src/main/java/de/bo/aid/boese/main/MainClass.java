package de.bo.aid.boese.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.socket.BoeseServer;
import de.bo.aid.boese.socket.SocketHandler;

public class MainClass {
	
	public static void handleMessage(String message, int connectorId) {
		BoeseJson bjMessage = BoeseJson.readMessage(new ByteArrayInputStream(message.getBytes()));
		switch (bjMessage.getType()) {
		case REQUESTCONNECTION:
			RequestConnection rc = (RequestConnection)bjMessage;
			int seqNr = rc.getSeqenceNr();
			if (rc.getPassword() == null && rc.getConnectorId() == -1) {
				//TODO User muss bestätigen tun
				String pw = "asfdkjsdglhsdf"; //TODO generate password
				
				int conId = Inserts.connector(rc.getConnectorName(), pw);
				SocketHandler.getInstance().setConnectorId(connectorId, conId);
				
				BoeseJson cc = new ConfirmConnection(pw, conId, seqNr+1, seqNr, 0, new Date().getTime());
				
				OutputStream os = new ByteArrayOutputStream();
				BoeseJson.parseMessage(cc, os);
				SocketHandler.getInstance().sendToConnector(conId, os.toString());
			} else {
				
			}
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
