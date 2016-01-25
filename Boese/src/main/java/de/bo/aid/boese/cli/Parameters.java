/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 */

package de.bo.aid.boese.cli;

import com.beust.jcommander.Parameter;

// TODO: Auto-generated Javadoc
/**
 * The Class Parameters.
 */
public class Parameters {

    public static final int DEFAULT_PORT = 8081;
    public static final boolean DEFAULT_AUTOCONFIRM = false;
    public static final boolean DEFAULT_TLS = true;
    public static final String DEFAULT_KEYSTORE = "keystore";
    public static final String DEFAULT_KEYSTORE_PW = "GZAMNNA";
    public static final boolean DEFAULT_HEARTBEAT = true;
    public static final int DEFAULT_HEARTBEAT_INTERVAL = 60;
    public static final int DEFAULT_HEARTBEAT_THRESHOLD = 3;
    public static final String DEFAULT_DB_USER = "postgres";
    public static final String DEFAULT_DB_PASSWORD = "Di0bPWfw";
    public static final String DEFAULT_DB_NAME = "boese";
    public static final String DEFAULT_DB_HOST = "localhost";
    public static final int DEFAULT_DB_PORT = 5432;
    public static final String DEFAULT_PASSWORD = "BOese";

    /** Describes the path to the config file. */
    @Parameter(names = "-c", description = "The Path to the config-file.", required = false)
    private String config = "settings.properties";

    @Parameter(names = "-p", description = "The port on which the distributo listens.", required = false)
    private int port = DEFAULT_PORT;

    @Parameter(names = "-ac", description = "When enabled, all components are confirmed automaticaly.", required = false, arity=1)
    private boolean autoconfirm = DEFAULT_AUTOCONFIRM;

    @Parameter(names = "-tls", description = "When enabled, the connection is tls-encrypted.", required = false, arity=1)
    private boolean tls = DEFAULT_TLS;

    @Parameter(names = "-ks", description = "The path to the keystore-file with the tls-certificate.", required = false)
    private String keystore = DEFAULT_KEYSTORE;
    
    @Parameter(names = "-ks_pw",  description = "The password for the keystore-file.", required=false)
    private String keystore_password = DEFAULT_KEYSTORE_PW;
    
    @Parameter(names = "-hb",  description = "When enabled, heartbeat messages are sent.", required=false)
    private boolean heartbeat = DEFAULT_HEARTBEAT;
    
    @Parameter(names = "-hb_interval",  description = "The interval in seconds in which the heartbeat-messages are sent.", required=false)
    private int heartbeatInterval = DEFAULT_HEARTBEAT_INTERVAL;
    
    @Parameter(names = "-hb_threshold",  description = "The number of unanswered heartbeat-messages until a connector is disconnected.", required=false)
    private int heartbeatTreshold = DEFAULT_HEARTBEAT_THRESHOLD;
    
    @Parameter(names = "-db_user",  description = "The database-user for postgreSQL.", required=false)
    private String dbUser = DEFAULT_DB_USER;
    
    @Parameter(names = "-db_pw",  description = "The password for the database-user.", required=false)
    private String dbPassword = DEFAULT_DB_PASSWORD;
    
    @Parameter(names = "-db_name",  description = "The name of the used database.", required=false)
    private String dbName = DEFAULT_DB_NAME;
    
    @Parameter(names = "-db_host",  description = "The database-host.", required=false)
    private String dbHost = DEFAULT_DB_HOST;
    
    @Parameter(names = "-db_port",  description = "The port of the database.", required=false)
    private int dbPort = DEFAULT_DB_PORT;
    
    @Parameter(names = "-pw",  description = "The password for direct connect of user-connectors", required=false)
    private String password = DEFAULT_PASSWORD;
    
    @Parameter(names = "-h",  description = "Displays this message", help=true)
    private boolean help = false;

    
    
    /**
     * @return the help
     */
    public boolean isHelp() {
        return help;
    }

    /**
     * Gets the config.
     *
     * @return the config
     */
    public String getConfig() {
        return config;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return the autoconfirm
     */
    public boolean isAutoconfirm() {
        return autoconfirm;
    }

    /**
     * @return the tls
     */
    public boolean isTls() {
        return tls;
    }

    /**
     * @return the keystore
     */
    public String getKeystore() {
        return keystore;
    }

    /**
     * @return the keystore_password
     */
    public String getKeystore_password() {
        return keystore_password;
    }

    /**
     * @return the heartbeat
     */
    public boolean isHeartbeat() {
        return heartbeat;
    }

    /**
     * @return the heartbeatInterval
     */
    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    /**
     * @return the heartbeatTreshold
     */
    public int getHeartbeatTreshold() {
        return heartbeatTreshold;
    }

    /**
     * @return the dbUser
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * @return the dbPassword
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @return the dbHost
     */
    public String getDbHost() {
        return dbHost;
    }

    /**
     * @return the dbPort
     */
    public int getDbPort() {
        return dbPort;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }



}
