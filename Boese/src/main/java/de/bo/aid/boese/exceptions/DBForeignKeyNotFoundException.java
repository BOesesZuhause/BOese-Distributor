package de.bo.aid.boese.exceptions;

public class DBForeignKeyNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DBForeignKeyNotFoundException(String m){
		super(m);
	}

}
