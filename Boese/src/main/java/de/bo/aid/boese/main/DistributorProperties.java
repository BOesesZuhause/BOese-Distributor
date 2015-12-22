/*
 * 
 */
package de.bo.aid.boese.main;

import java.util.Set;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.ConstraintViolation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class DistributorProperties.
 */
@SuppressWarnings("serial")
public class DistributorProperties extends Properties{
	
	/** The db host. */
	private final String DB_HOST = "DB_HOST";
	
	/** The user. */
	private final String USER = "DB_USER";
	
	/** The ws port. */
	private final String WS_PORT = "Websocket_Port";
	
	/** The confirm. */
	private final String CONFIRM = "autoConfirm";
	
	/** The password. */
	private final String PASSWORD = "DB_PASSWORD";
	
	/** The database. */
	private final String DATABASE = "DB_NAME";
	
	/** The db port. */
	private final String DB_PORT = "DB_PORT";
	
	/** The default password. */
	private final String DEFAULT_PASSWORD = "DEFAULT_CONNECTOR_PASSWORD";
	
	
	/** The logger. */
	final  Logger logger = LogManager.getLogger(DistributorProperties.class);

	/**
	 * Validate.
	 *
	 * @return true, if successful
	 */
	private boolean validate(){
	      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	      Validator validator = factory.getValidator();	
	      Set<ConstraintViolation<DistributorProperties>> constraintViolations =validator.validate(this);
	      for(ConstraintViolation<DistributorProperties> violation : constraintViolations ){
	    	  logger.error(violation.getPropertyPath() + " " + violation.getMessage());
	      }
	      return constraintViolations.isEmpty();
	}
	
	/**
	 * Load.
	 *
	 * @param path the path
	 */
	//TODO validate properties
	public void load(String path){
		FileInputStream file = null;
		try {
			file = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			logger.error("config File not found at: " + path, e);
			System.exit(0);
		}

		// load all the properties from the file
		try {
			this.load(file);
		} catch (IOException e) {
			logger.error("IO-Exception while loading config-file", e);
			System.exit(0);
		}

		// close the file handle
		try {
			file.close();
		} catch (IOException e) {
			logger.error("IO-Exception while closing config-file", e);
			System.exit(0);
		}
		if(!validate()){
			System.exit(0);
		}
	}
	
	/**
	 * Save.
	 *
	 * @param path the path
	 */
	public void save(String path){
		OutputStream output = null;
		try {
			output = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			logger.error("Could not open file: " + path);
			e.printStackTrace();
			System.exit(0);
		}

		try {
			this.store(output, null);
		} catch (IOException e) {
			logger.error("IO-Exception while saving default properties-file");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Sets the defaults.
	 */
	public void setDefaults(){
		this.setPort(8081);
		this.setAutoConfirm(false);
		this.setDbUser("postgres");
		this.setDbPassword("Di0bPWfw");
		this.setDbName("boese");
		this.setDbHost("localhost");
		this.setDbPort("5432");
		this.setDefaultPassword("Boese");
	}
	
	/**
	 * Gets the db host.
	 *
	 * @return the db host
	 */
	@NotNull
	public String getDbHost() {
		return this.getProperty(DB_HOST);
	}
	
	/**
	 * Sets the db host.
	 *
	 * @param dbURL the new db host
	 */
	public void setDbHost(String dbURL) {
		this.setProperty(DB_HOST, dbURL);
	}
	
	/**
	 * Gets the db name.
	 *
	 * @return the db name
	 */
	@NotNull
	public String getDbName() {
		return this.getProperty(DATABASE);
	}
	
	/**
	 * Sets the db name.
	 *
	 * @param dbName the new db name
	 */
	public void setDbName(String dbName) {
		this.setProperty(DATABASE, dbName);
	}
	
	/**
	 * Gets the db user.
	 *
	 * @return the db user
	 */
	@NotNull
	public String getDbUser() {
		return this.getProperty(USER);
	}
	
	/**
	 * Sets the db user.
	 *
	 * @param dbUser the new db user
	 */
	public void setDbUser(String dbUser) {
		this.setProperty(USER, dbUser);
	}
	
	/**
	 * Gets the db password.
	 *
	 * @return the db password
	 */
	@NotNull
	public String getDbPassword() {
		return this.getProperty(PASSWORD);
	}
	
	/**
	 * Sets the db password.
	 *
	 * @param dbPassword the new db password
	 */
	public void setDbPassword(String dbPassword) {
		this.setProperty(PASSWORD, dbPassword);
	}
	
	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	@NotNull
	public int getPort() {
		int port;
		try{
		port = Integer.parseInt(this.getProperty(WS_PORT));
		}catch (NumberFormatException e){
			logger.error("Unable to load Port from properties", e);
			logger.info("Trying to use default port 8081");
			port = 8081;
		}
		return port;
	}
	
	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(int port) {
		this.setProperty(WS_PORT, port + "");
	}
	
	/**
	 * Checks if is auto confirm.
	 *
	 * @return true, if is auto confirm
	 */
	@NotNull
	public boolean isAutoConfirm() {
		return Boolean.parseBoolean(this.getProperty(CONFIRM));
	}
	
	/**
	 * Sets the auto confirm.
	 *
	 * @param autoConfirm the new auto confirm
	 */
	public void setAutoConfirm(boolean autoConfirm) {
		this.setProperty(CONFIRM, autoConfirm + "");
	}
	
	/**
	 * Sets the db port.
	 *
	 * @param port the new db port
	 */
	public void setDbPort(String port){
		this.setProperty(DB_PORT, port);
	}

	/**
	 * Gets the db port.
	 *
	 * @return the db port
	 */
	@NotNull
	public String getDbPort() {
		return this.getProperty(DB_PORT);
	}
	
	/**
	 * Sets the default password.
	 *
	 * @param pw the new default password
	 */
	public void setDefaultPassword(String pw){
	    this.setProperty(DEFAULT_PASSWORD, pw);
	}
	
	/**
	 * Gets the default password.
	 *
	 * @return the default password
	 */
	@NotNull
	public String getDefaultPassword(){
	    return this.getProperty(DEFAULT_PASSWORD);
	}
}
