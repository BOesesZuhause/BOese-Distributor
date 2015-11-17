package de.bo.aid.boese.socket;

public class HeartbeatWorker extends Thread{
	
	private long intervall = 10000;	
	
	public long getIntervall() {
		return intervall;
	}

	public void setIntervall(long intervall) {
		this.intervall = intervall;
	}

	private boolean running = false;
	SessionHandler handler = SessionHandler.getInstance();
	

	@Override
	public void run() {
		running = true;
		while(running){
			try {
				Thread.sleep(intervall);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			handler.checkHeartbeat();
			handler.sendToAllConnectedSessions("HEARTBEAT");
		}				
	}
	
	public void pause(){
		running = false;
	}
	

}
