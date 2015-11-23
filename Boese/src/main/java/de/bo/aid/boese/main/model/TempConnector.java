package de.bo.aid.boese.main.model;

public class TempConnector {
	private String name;
	private boolean userConnector;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isUserConnector() {
		return userConnector;
	}
	public void setUserConnector(boolean userConnector) {
		this.userConnector = userConnector;
	}
	public TempConnector(){
		
	}
	
	public TempConnector(String name, boolean userConnector) {
		super();
		this.name = name;
		this.userConnector = userConnector;
	}
	
	

}
