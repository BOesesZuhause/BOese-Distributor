package de.bo.aid.boese.main;

import java.io.ByteArrayInputStream;
import java.util.Date;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.socket.BoeseServer;

public class MainClass {
	
	public static void handleMessage(String message) {
		BoeseJson bjMessage = BoeseJson.readMessage(new ByteArrayInputStream(message.getBytes()));
		switch (bjMessage.getType()) {
		case REQUESTCONNECTION:
			RequestConnection rc = (RequestConnection)bjMessage;
			int seqNr = rc.getSeqenceNr();
			if (rc.getPassword() == null && rc.getConnectorId() == -1) {
				//TODO User muss bestätigen tun
				String pw = "asfdkjsdglhsdf"; //TODO generate password
				int conId = Inserts.connector(rc.getConnectorName(), pw);
				BoeseJson cc = new ConfirmConnection(pw, conId, seqNr+1, seqNr, 0, new Date().getTime());
				
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
