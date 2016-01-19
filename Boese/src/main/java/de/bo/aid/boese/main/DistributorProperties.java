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
	private final String DB_HOST = "database_host";
	
	/** The user. */
	private final String USER = "database_user";
	
	/** The ws port. */
	private final String WS_PORT = "websocket_port";
	
	/** the tls. */
	private final String TLS = "tls_enabled";
	
	/** The heartbeat. */
	private final String HEARTBEAT = "heartbeat_enabled";
	
	/** The hb intervall. */
	private final String HB_INTERVALL = "heartbeat_intervall";
	
	/** The hb threshold. */
	private final String HB_THRESHOLD = "heartbeat_threshold";
	
	/** The confirm. */
	private final String CONFIRM = "auto_confirm";
	
	/** The password. */
	private final String PASSWORD = "database_password";
	
	/** The database. */
	private final String DATABASE = "database_name";
	
	/** The db port. */
	private final String DB_PORT = "database_port";
	
	/** The default password. */
	private final String DEFAULT_PASSWORD = "default_connector_password";

	
	
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
	public void load(String path){
		FileInputStream file = null;
		try {
			file = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			logger.error("config File not found at: " + path, e);
			logger.info("Generating default properties file");
			this.setDefaults();
			this.save(path);
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
		this.setTLS(true);
		this.setHeartbeat(true);
		this.setHeartBetIntervall(60);
		this.setHeartBeatThreshold(3);
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
	 * Gets the tls.
	 *
	 * @return the tls
	 */
	public boolean getTLS(){
		return Boolean.parseBoolean(this.getProperty(TLS));
	}
	
	/**
	 * Sets the tls.
	 *
	 * @param tls the new tls
	 */
	public void setTLS(boolean tls){
		this.setProperty(TLS, tls + "");
	}
	
	/**
	 * Sets the heartbeat.
	 *
	 * @param heartbeat the new heartbeat
	 */
	public void setHeartbeat(boolean heartbeat){
	    this.setProperty(HEARTBEAT, heartbeat + "");
	}
	
	/**
	 * Gets the heartbeat.
	 *
	 * @return the heartbeat
	 */
	public boolean getHeartbeat(){
	    return Boolean.parseBoolean(this.getProperty(HEARTBEAT));
	}
	
	
	
	/**
	 * Gets the heartbeat intervall.
	 *
	 * @return the heartbeat intervall
	 */
	public int getHeartbeatIntervall(){
	       int intervall;
	        try{
	        intervall = Integer.parseInt(this.getProperty(HB_INTERVALL));
	        }catch (NumberFormatException e){
	            logger.error("Unable to load HB_Interval from properties", e);
	            logger.info("Using default intervall 60");
	            intervall = 60;
	        }
	        return intervall;
	}
	
    /**
     * Sets the heart bet intervall.
     *
     * @param intervall the new heart bet intervall
     */
    public void setHeartBetIntervall(int intervall){
        this.setProperty(HB_INTERVALL, intervall + "");
    }
	
	/**
	 * Sets the heart beat threshold.
	 *
	 * @param threshold the new heart beat threshold
	 */
	public void setHeartBeatThreshold(int threshold){
	    this.setProperty(HB_THRESHOLD, threshold + "");
	}
	
	   /**
   	 * Gets the heart beat threshold.
   	 *
   	 * @return the heart beat threshold
   	 */
   	public int getHeartBeatThreshold(){
           int threshold;
            try{
            threshold = Integer.parseInt(this.getProperty(HB_THRESHOLD));
            }catch (NumberFormatException e){
                logger.error("Unable to load HB_threshold from properties", e);
                logger.info("Using default threshold 3");
                threshold = 3;
            }
            return threshold;
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
