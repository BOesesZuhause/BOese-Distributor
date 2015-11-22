package de.bo.aid.boese.exceptions;

public class OnlyTwoObjectsForModuloException extends Exception{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new DB object not found exception.
	 *
	 * @param m the m
	 */
	public OnlyTwoObjectsForModuloException(String m){
		super(m);
	}

}
