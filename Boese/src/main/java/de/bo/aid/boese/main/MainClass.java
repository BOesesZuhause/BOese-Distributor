package de.bo.aid.boese.main;

import de.bo.aid.boese.socket.BoeseServer;

public class MainClass {

	public static void main(String[] args) {
		BoeseServer server = new BoeseServer();
		server.start();

	}

}
