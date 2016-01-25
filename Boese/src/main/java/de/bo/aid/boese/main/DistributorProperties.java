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

import de.bo.aid.boese.cli.Parameters;


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
	
	private final String KEYSTORE = "keystore_path";
	
	private final String KEYSTOR_PW = "keystore_password";
	
	/** The heartbeat. */
	private final String HEARTBEAT = "heartbeat_enabled";
	
	/** The hb intervall. */
	private final String HB_INTERVALL = "heartbeat_intervall";
	
	/** The hb threshold. */
	private final String HB_THRESHOLD = "heartbeat_threshold";
	
	/** The confirm. */
	private final String CONFIRM = "auto_confirm";
	
	/** The password. */
	private final String DB_PASSWORD = "database_password";
	
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
	 * @throws FileNotFoundException 
	 */
	public void load(String path) throws FileNotFoundException{
        FileInputStream file = null;
        
        file = new FileInputStream(path);
    

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
//    if(!validate()){
//        System.exit(0);
//    }
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
		this.setKeyStore("keystore");
		this.setKeystorePassword("GZAMNNA");
		this.setHeartbeat(true);
		this.setHeartBetIntervall(60);
		this.setHeartBeatThreshold(3);
		this.setDbUser("postgres");
		this.setDbPassword("Di0bPWfw");
		this.setDbName("boese");
		this.setDbHost("localhost");
		this.setDbPort(5432);
		this.setDefaultPassword("Boese");
	}
	
    public void setDefaultsIfNotExist(String path){
        boolean change = false;
        
        if(this.getHeartbeat() == null){
            this.setHeartbeat(Parameters.DEFAULT_HEARTBEAT);
            logger.warn(HEARTBEAT + " was not set. Using default value");
            change = true;
        }
        if(this.getTLS() == null){
            this.setTLS(Parameters.DEFAULT_TLS);
            logger.warn(TLS + " was not set. Using default value");
            change = true;
        }
        if(this.getDbHost() == null){
            this.setDbHost(Parameters.DEFAULT_DB_HOST);
            logger.warn(DB_HOST + " was not set. Using default value");
            change = true;
        }
        if(this.getDbName() == null){
            this.setDbName(Parameters.DEFAULT_DB_NAME); 
            logger.warn(DATABASE + " was not set. Using default value");
            change = true;
        }
        if(this.getDbPassword() == null){
            this.setDbPassword(Parameters.DEFAULT_DB_PASSWORD);
            logger.warn( DB_PASSWORD + " was not set. Using default value");
            change = true;
        }
        if(this.getDbPort() == null){
            this.setDbPort(Parameters.DEFAULT_DB_PORT);
            logger.warn(DB_PORT + " was not set. Using default value");
            change = true;
        }
        if(this.getDbUser() == null){
            this.setDbUser(Parameters.DEFAULT_DB_USER);
            logger.warn(USER + " was not set. Using default value");
            change = true;
        }
        
        if(this.getDefaultPassword() == null){
            this.setDefaultPassword(Parameters.DEFAULT_PASSWORD);
            logger.warn(DEFAULT_PASSWORD + " was not set. Using default value");
            change = true;
        }
        if(this.getHeartbeatIntervall() == null){
            this.setHeartBetIntervall(Parameters.DEFAULT_HEARTBEAT_INTERVAL);
            logger.warn(HB_INTERVALL + " was not set. Using default value");
            change = true;
        }
        if(this.getHeartBeatThreshold() == null){
            this.setHeartBeatThreshold(Parameters.DEFAULT_HEARTBEAT_THRESHOLD);
            logger.warn(HB_THRESHOLD + " was not set. Using default value");
            change = true;
        }
        if(this.getKeyStore() == null){
            this.setKeyStore(Parameters.DEFAULT_KEYSTORE);
            logger.warn(KEYSTORE + " was not set. Using default value");
            change = true;
        }
        if(this.getKeystorePassword() == null){
            this.setKeystorePassword(Parameters.DEFAULT_KEYSTORE_PW);
            logger.warn(KEYSTOR_PW + " was not set. Using default value");
            change = true;
        }
        if(this.getPort() == null){
            this.setPort(Parameters.DEFAULT_PORT);
            logger.warn(WS_PORT + " was not set. Using default value");
            change = true;
        }
        
        if(!validate()){
            System.exit(0);
        }
        
        if(change){
            this.save(path);
        }
    }
    
    public void addParams(Parameters params){
        boolean change = false;
       
        if(params.isHeartbeat() != Parameters.DEFAULT_HEARTBEAT){
            this.setHeartbeat(params.isHeartbeat());
            change = true;
        }
        if(params.isTls() != Parameters.DEFAULT_TLS){
            this.setTLS(params.isTls());
            change = true;
        }
        if(!params.getDbHost().equals(Parameters.DEFAULT_DB_HOST)){
            this.setDbHost(params.getDbHost());
            change = true;
        }
        if(!params.getDbName().equals(Parameters.DEFAULT_DB_NAME)){
            this.setDbName(params.getDbName());
            change = true;
        }
        if(!params.getDbPassword().equals(Parameters.DEFAULT_DB_PASSWORD)){
            this.setDbPassword(params.getDbPassword());
            change = true;
        }
        if(params.getDbPort() != Parameters.DEFAULT_DB_PORT){
            this.setDbPort(params.getDbPort());
            change = true;
        }
        if(!params.getDbUser().equals(Parameters.DEFAULT_DB_USER)){
            this.setDbUser(params.getDbUser());
            change = true;
        }
        if(!params.getPassword().equals(Parameters.DEFAULT_PASSWORD)){
            this.setDefaultPassword(params.getPassword());
            change = true;
        }
        if(params.getHeartbeatInterval() != Parameters.DEFAULT_HEARTBEAT_INTERVAL){
            this.setHeartBetIntervall(params.getHeartbeatInterval());
            change = true;
        }
        if(params.getHeartbeatTreshold() != Parameters.DEFAULT_HEARTBEAT_THRESHOLD){
            this.setHeartBeatThreshold(params.getHeartbeatTreshold());
            change = true;
        }
        if(!params.getKeystore().equals(Parameters.DEFAULT_KEYSTORE)){
            this.setKeyStore(params.getKeystore());
            change = true;
        }
        if(!params.getKeystore_password().equals(Parameters.DEFAULT_KEYSTORE_PW)){
            this.setKeystorePassword(params.getKeystore_password());
            change = true;
        }
        if(params.getPort() != Parameters.DEFAULT_PORT){
            this.setPort(params.getPort());
            change = true;
        }

        if(!validate()){
            System.exit(0);
        }
        
        if(change){
            this.save(params.getConfig());
            logger.info("Saved new values from parameters to settings-file");
        }
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
	public Boolean getTLS(){
	    if(this.getProperty(TLS) == null){
	        return null;
	    }else{
	        return Boolean.parseBoolean(this.getProperty(TLS)); 
	    }
	}
	
	/**
	 * Sets the tls.
	 *
	 * @param tls the new tls
	 */
	public void setTLS(boolean tls){
		this.setProperty(TLS, tls + "");
	}
	
	public String getKeystorePassword(){
	    return this.getProperty(KEYSTOR_PW);
	}
	
	public void setKeystorePassword(String password){
	    this.setProperty(KEYSTOR_PW, password);
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
	public Boolean getHeartbeat(){
	    if(this.getProperty(HEARTBEAT) == null){
	        return null;
	    }else{
	        return Boolean.parseBoolean(this.getProperty(HEARTBEAT));   
	    }
	}
	
	public String getKeyStore(){
	    return this.getProperty(KEYSTORE);
	}
	
	public void setKeyStore(String store){
	    this.setProperty(KEYSTORE, store);
	}
	
	
	/**
	 * Gets the heartbeat intervall.
	 *
	 * @return the heartbeat intervall
	 */
	public Integer getHeartbeatIntervall(){
	        if(this.getProperty(HB_INTERVALL) == null){
	            return null;
	        }else{
	            int intervall;
	            try{
	            intervall = Integer.parseInt(this.getProperty(HB_INTERVALL));
	            }catch (NumberFormatException e){
	                logger.error("Unable to load HB_Interval from properties", e);
	                return null;
	            }
	            return intervall; 
	        }
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
   	public Integer getHeartBeatThreshold(){
   	    if(this.getProperty(HB_THRESHOLD) == null){
   	        return null;
   	    }else{
   	     int threshold;
         try{
             threshold = Integer.parseInt(this.getProperty(HB_THRESHOLD));
         }catch (NumberFormatException e){
             logger.error("Unable to load HB_threshold from properties", e);
             return null;
         }
         return threshold;  
   	    }
          
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
		return this.getProperty(DB_PASSWORD);
	}
	
	/**
	 * Sets the db password.
	 *
	 * @param dbPassword the new db password
	 */
	public void setDbPassword(String dbPassword) {
		this.setProperty(DB_PASSWORD, dbPassword);
	}
	
	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	@NotNull
	public Integer getPort() {
	    if(this.getProperty(WS_PORT) == null){
	        return null;
	    }else{
	        int port;
	        try{
	        port = Integer.parseInt(this.getProperty(WS_PORT));
	        }catch (NumberFormatException e){
	            logger.error("Unable to load Port from properties", e);
	            return null;
	        }
	        return port;  
	    }

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
	public Boolean isAutoConfirm() {
	    if(this.getProperty(CONFIRM) == null){
	        return null;
	    }else{
	        return Boolean.parseBoolean(this.getProperty(CONFIRM));   
	    }
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
	public void setDbPort(int port){
		this.setProperty(DB_PORT, port + "");
	}

	/**
	 * Gets the db port.
	 *
	 * @return the db port
	 */
	@NotNull
	public Integer getDbPort() {
	    if(this.getProperty(DB_PORT) == null){
	        return null;
	    }else{
	        int port;
	         try{
	             port = Integer.parseInt(this.getProperty(DB_PORT));
	         }catch (NumberFormatException e){
	             logger.error("Unable to load databas_port from properties", e);
	             return null;
	         }
	         return port;  
	    }

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
