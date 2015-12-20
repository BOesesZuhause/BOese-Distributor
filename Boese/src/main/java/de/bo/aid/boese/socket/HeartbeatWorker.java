/*
 * 
 */
package de.bo.aid.boese.socket;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.HeartBeatMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class HeartbeatWorker.
 */
public class HeartbeatWorker extends Thread{
	
	/** The intervall. */
	public static long intervall = 60000;	
	
	/** The logger. */
	final  Logger logger = LogManager.getLogger(HeartbeatWorker.class);
	
	/**
	 * Gets the intervall.
	 *
	 * @return the intervall
	 */
	public static long getIntervall() {
		return intervall;
	}

	/**
	 * Sets the intervall.
	 *
	 * @param intervall the new intervall
	 */
	public void setIntervall(long intervall) {
		HeartbeatWorker.intervall = intervall;
	}

	/** The running. */
	private volatile boolean running = true;
	
	/** The handler. */
	SessionHandler handler = SessionHandler.getInstance();
	

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while(running){
			for (SessionData data : handler.getSessions()){
				int conId = handler.getConnectorId(data.getSession());
				if(conId != -1){
					BoeseJson hm = new HeartBeatMessage(conId, 0, System.currentTimeMillis());
					OutputStream os = new ByteArrayOutputStream();
					BoeseJson.parseMessage(hm, os);
					handler.sendToConnector(conId, os.toString());
				}				
			}
			try {
				Thread.sleep(intervall);
			} catch (InterruptedException e) {
				logger.warn("Interrupted while sleeping", e);
			}
			handler.checkHeartbeat();
		}				
	}
	
	/**
	 * Terminate.
	 */
	public void terminate(){
		running = false;
	}
	


}
