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

/**
 * The Thread for heartbeat-messages. While running it sends a heartbeat to all connected sessions
 * in a defined intervall.
 */
public class HeartbeatWorker extends Thread{
	
	/** The interval in which the hearbeat-messages are send. If not set the default is 60 seconds. */
	public static long interval = 60000;	
	
	/** The logger. */
	final  Logger logger = LogManager.getLogger(HeartbeatWorker.class);
	

   /** Boolean to determine if the thread is running or stopped. */
    private volatile boolean running = true;
    
    /** The session-handler instance which is used to send the heartbeat-messages. */
    SessionHandler handler = SessionHandler.getInstance();
	
	/**
	 * Gets the intervall.
	 *
	 * @return the intervall
	 */
	public static long getInterval() {
		return interval;
	}

	/**
	 * Sets the interval.
	 *
	 * @param interval the new interval
	 */
	public void setInterval(long interval) {
		HeartbeatWorker.interval = interval;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	/**
	 *  Sends the heartbeat to all confirmed connectors and sleeps for the given interval. 
	 *  Also calls the check-method of the handler in every cycle.
	 * 
	 */
	@Override
	public void run() {
		while(running){
		    logger.info("Starting Heartbeat cycle");
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
                Thread.sleep(interval / 10);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
			handler.checkHeartbeat();
			try {
				Thread.sleep((interval / 10) * 9);
			} catch (InterruptedException e) {
				logger.warn("Interrupted while sleeping", e);
			}
			
		}				
	}
	
	/**
	 * Stops the running thread.
	 */
	public void terminate(){
		running = false;
	}
	


}
