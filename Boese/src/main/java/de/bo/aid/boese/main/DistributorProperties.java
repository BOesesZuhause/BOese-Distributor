package de.bo.aid.boese.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
public class DistributorProperties extends Properties{
	
	private final String DB_URL = "DB_URL";
	private final String USER = "DB_USER";
	private final String PORT = "WebsocketPort";
	private final String CONFIRM = "autoConfirm";
	private final String PASSWORD = "DB_PASSWORD";
	private final String DATABASE = "DB_NAME";
	
	
	final  Logger logger = LogManager.getLogger(DistributorProperties.class);

	
	public void load(String path){
		FileInputStream file = null;
		try {
			file = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			logger.error("config File not found at: " + path);
			e.printStackTrace();
			System.exit(0);
		}

		// load all the properties from the file
		try {
			this.load(file);
		} catch (IOException e) {
			logger.error("IO-Exception while loading config-file");
			e.printStackTrace();
			System.exit(0);
		}

		// close the file handle
		try {
			file.close();
		} catch (IOException e) {
			logger.error("IO-Exception while closing config-file");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
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
	
	public void setDefaults(){
		this.setPort(8081);
		this.setAutoConfirm(false);
		this.setDbUser("postgres");
		this.setDbPassword("DI0bPWfw");
		this.setDbName("boese");
		this.setDbURL("jdbc:postgresql://localhost:5432/boese");
	}
	
	public String getDbURL() {
		return this.getProperty(DB_URL);
	}
	public void setDbURL(String dbURL) {
		this.setProperty(DB_URL, dbURL);
	}
	public String getDbName() {
		return this.getProperty(DATABASE);
	}
	public void setDbName(String dbName) {
		this.setProperty(DATABASE, dbName);
	}
	public String getDbUser() {
		return this.getProperty(USER);
	}
	public void setDbUser(String dbUser) {
		this.setProperty(USER, dbUser);
	}
	public String getDbPassword() {
		return this.getProperty(PASSWORD);
	}
	public void setDbPassword(String dbPassword) {
		this.setProperty(PASSWORD, dbPassword);
	}
	public int getPort() {
		int port;
		try{
		port = Integer.parseInt(this.getProperty(PORT));
		}catch (NumberFormatException e){
			logger.error("Unable to load Port from properties");
			logger.info("Trying to use default port 8081");
			port = 8081;
		}
		return port;
	}
	public void setPort(int port) {
		this.setProperty(PORT, port + "");
	}
	public boolean isAutoConfirm() {
		return Boolean.parseBoolean(this.getProperty(CONFIRM));
	}
	public void setAutoConfirm(boolean autoConfirm) {
		this.setProperty(CONFIRM, autoConfirm + "");
	}
}
