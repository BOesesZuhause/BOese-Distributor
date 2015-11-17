package de.bo.aid.boese.socket;

public class HeartbeatWorker implements Runnable{
	
	private boolean running = false;
	SessionHandler handler = SessionHandler.getInstance();
	

	@Override
	public void run() {
		running = true;
		while(running){
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			handler.sendToAllConnectedSessions("HEARTBEAT");
		}				
	}
	
	public void stop(){
		running = false;
	}
	

}
