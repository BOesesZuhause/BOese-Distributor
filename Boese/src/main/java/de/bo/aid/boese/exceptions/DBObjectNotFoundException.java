package de.bo.aid.boese.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class DBObjectNotFoundException.
 */
public class DBObjectNotFoundException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new DB object not found exception.
	 *
	 * @param m the m
	 */
	public DBObjectNotFoundException(String m){
		super(m);
	}

}
