package de.bo.aid.boese.exceptions;

public class DBObjectNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DBObjectNotFoundException(String m){
		super(m);
	}

}
