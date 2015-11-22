/*
 * 
 */
package de.bo.aid.boese.socket;

// TODO: Auto-generated Javadoc
/**
 * The Class HeartbeatWorker.
 */
public class HeartbeatWorker extends Thread{
	
	/** The intervall. */
	private long intervall = 10000;	
	
	/**
	 * Gets the intervall.
	 *
	 * @return the intervall
	 */
	public long getIntervall() {
		return intervall;
	}

	/**
	 * Sets the intervall.
	 *
	 * @param intervall the new intervall
	 */
	public void setIntervall(long intervall) {
		this.intervall = intervall;
	}

	/** The running. */
	private boolean running = false;
	
	/** The handler. */
	SessionHandler handler = SessionHandler.getInstance();
	

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while(running){
			handler.sendToAllConnectedSessions("HEARTBEAT");
			try {
				Thread.sleep(intervall);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			handler.checkHeartbeat();
		}				
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
	


}
