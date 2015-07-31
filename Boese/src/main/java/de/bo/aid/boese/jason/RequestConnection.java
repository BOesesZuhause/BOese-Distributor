package de.bo.aid.boese.jason;

public class RequestConnection extends BoeseJson {
	private int status;
	private String name;
	private String password;
	
	public RequestConnection(int status, String name, String password) {
		this.status = status;
		this.name = name;
		this.password = password;
		this.messageType = MessageType.REQUESTCONNECTION;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
}
