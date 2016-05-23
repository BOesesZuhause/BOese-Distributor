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
package de.bo.aid.boese.main;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import de.bo.aid.boese.DB.util.DBDefaults;
import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.cli.Parameters;
import de.bo.aid.boese.constants.NotificationType;
import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.dao.ServiceDAO;
import de.bo.aid.boese.dao.UnitDAO;
import de.bo.aid.boese.dao.ZoneDAO;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.UserSendTemps;
import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempConnector;
import de.bo.aid.boese.main.model.TempDevice;
import de.bo.aid.boese.modelJPA.Component;
import de.bo.aid.boese.modelJPA.Connector;
import de.bo.aid.boese.modelJPA.Device;
import de.bo.aid.boese.modelJPA.DeviceComponent;
import de.bo.aid.boese.modelJPA.Service;
import de.bo.aid.boese.modelJPA.Unit;
import de.bo.aid.boese.modelJPA.Zone;
import de.bo.aid.boese.ruler.Inquiry;
import de.bo.aid.boese.ruler.Interpreter;
import de.bo.aid.boese.ruler.ToDoChecker;
import de.bo.aid.boese.socket.HeartbeatWorker;
import de.bo.aid.boese.socket.SessionHandler;
import de.bo.aid.boese.socket.SocketServer;
import de.bo.aid.boese.xml.ComponentXML;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class MainClass.
 */
public class Distributor {
	
/** The logo. */
private final String logo =
 "                   )                    \n"
+"            (   ( /(                    \n"
+"          ( )\\  )\\())    (        (   \n"
+"          )((_)((_)\\    ))\\ (    ))\\ \n"
+"         ((_)_   ((_)  /((_))\\  /((_)  \n"
+"          | _ ) / _ \\ (_)) ((_)(_))    \n"
+"          | _ \\| (_) |/ -_)(_-</ -_)   \n"
+"          |___/ \\___/ \\___|/__/\\___| \n"
+"                                        \n"
+"                      ;                 \n"
+"            +        ;;;        +       \n"
+"            +       ;;;;;       +       \n"
+"            +      ;;;;;;;      +       \n"
+"            ++    ;;;;;;;;;    ++       \n"
+"            +++++;;;;;;;;;;;+++++       \n"
+"             ++++;;;;;;;;;;;++++        \n"
+"              ++;;;;;;;;;;;;;++         \n"
+"               ;;;;;;;;;;;;;;;          \n"
+"              ;;;;;;;;;;;;;;;;;         \n"
+"              :::::::::::::::::         \n"
+"              :::::::::::::::::         \n"
+"              :::::::::::::::::         \n"
+"              ::::::@@@@@::::::         \n"
+"              :::::@:::::@:::::         \n"
+"              ::::@:::::::@::::         \n"
+"              :::::::::::::::::         \n"
+"              :::::::::::::::::         \n";
	
	/** The socket server. */
	private SocketServer socketServer;
	
	/** The instance. */
	private static Distributor instance;
	
	private DAOHandler daoHandler;
	
	/** The temp connectors. */
	//hashmaps for unconfirmed Objects with a temporary Id as key
	private HashMap<Integer, TempConnector> tempConnectors = new HashMap<Integer, TempConnector>();
	
	/** The temp devices. */
	private HashMap<Integer, TempDevice> tempDevices = new HashMap<Integer, TempDevice>();
	
	/** The temp device components. */
	private HashMap<Integer, TempComponent> tempDeviceComponents = new HashMap<Integer, TempComponent>();
	
	/** The connector id. */
	private final int connectorID = 0;
	
	/** The temp device id. */
	//tempIds for unconfirmed Objects
	private int tempDeviceId = 1;
	
	/** The temp comp id. */
	private int tempCompId = 1;
	
	
	/** The temp id device components. */
	int tempIdDeviceComponents = 1;
	
	/** The protocol handler. */
	private ProtocolHandler protocolHandler;
	
	
	/** The Constant logger for log4j. */
	private static final  Logger logger = LogManager.getLogger(Distributor.class);
	
	/** The tdc. */
	private ToDoChecker tdc;
	
	/** The props. */
	DistributorProperties props;
	
	Parameters params;

	Zone global;
    
    /**
     * Instantiates a new distributor.
     */
    private Distributor(){}
    
    /**
     * Gets the single instance of Distributor.
     *
     * @return single instance of Distributor
     */
    public static Distributor getInstance(){
    	if(instance == null){
    		instance = new Distributor();
    	}
    	return instance;
    }
    
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        Distributor distr = Distributor.getInstance();
        distr.printLogo();
        distr.checkArguments(args);
        logger.info("Loading properties");
        distr.loadProperties();
        logger.info("Properties loades successfully");
        
        logger.info("Beginning database initialization");
        distr.initDatabase();
        logger.info("Database initialized successfully");
        logger.info("Starting websocketserver");
        distr.startWebsocketServer();
        logger.info("websocketserver started successfully");
        distr.startHeartbeat();
        logger.info("starting todochecker-thread");
        distr.startToDoChecker();
        logger.info("todochecker-thread started successfully");
    }
	
	/**
	 * Start websocket server.
	 *
	 */
	public void startWebsocketServer(){
	    if(props.getHeartbeat()){
	        SessionHandler.getInstance().setAsync(true);
	    }
		socketServer = SocketServer.getInstance();
		protocolHandler = new ProtocolHandler(this);
		socketServer.setMessageHandler(protocolHandler);
		
			if(props.getTLS()){
				logger.info("Initalizing websocketserver with TLS");
				socketServer.initTLS(props.getPort(), props.getKeyStore(), props.getKeystorePassword());
			}else{
				logger.info("Initalizing websocketserver without TLS");
				socketServer.init(props.getPort());
		}
		socketServer.start();
	}
	
	/**
	 * Start Todo Checker.
	 */
	public void startToDoChecker(){
		tdc = new ToDoChecker(protocolHandler);
		tdc.start();
	}
	
	/**
	 * Inits the database.
	 */
	public void initDatabase(){
		JPAUtil.setDBUser(props.getDbUser());
		JPAUtil.setDBPassword(props.getDbPassword());
		JPAUtil.setDBURL(props.getDbName(), props.getDbHost(), props.getDbPort());
		JPAUtil.init();
		daoHandler = DAOHandler.getInstance();
		DBDefaults.defaults();
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		global = daoHandler.getZoneDAO().get(em, 1);
		em.getTransaction().commit();
		em.close();
		
	}
	
	/**
	 * Check arguments.
	 *
	 * @param args the args
	 */
	public void checkArguments(String[] args){
		params = new Parameters();
		JCommander cmd = new JCommander(params);

		try {
			cmd.parse(args);

		} catch (ParameterException ex) {
			System.out.println(ex.getMessage());
			cmd.usage();
			System.exit(0);
		}
		
	      if(params.isHelp()){
	            cmd.usage();
	            System.exit(0);
	        }
		
		
	}
	
	//TODO validate properties
	/**
	 * Load the properties-file.
	 */
	public void loadProperties() {
	    props = new DistributorProperties();

        try {
            //Load settings-file
            props.load(params.getConfig());
        } catch (FileNotFoundException e) {
            logger.warn("Could not find settings-file at: " + params.getConfig());
            logger.info("Generating new settings-file at: " + params.getConfig());
        }
        
        props.setDefaultsIfNotExist(params.getConfig()); //set default value if a value is not set
        props.addParams(params); //overwrite values with cli-values
		
	}
	
	/**
	 * Prints the logo.
	 */
	public void printLogo(){
		System.out.println(logo);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			logger.error("Failed to sleep", e);
		}
	}

	
	/**
	 * Start heartbeat.
	 */
	public void startHeartbeat() {
	    if(props.getHeartbeat()){
	        logger.info("starting heartbeat-thread");
	        HeartbeatWorker worker = new HeartbeatWorker();
	        HeartbeatWorker.interval = props.getHeartbeatIntervall() * 1000;
	        SessionHandler.getInstance().setMissedAnswerThreshold(props.getHeartBeatThreshold());
	        worker.start(); 
	        logger.info("heartbeat-thread started successfully");
	    }
	}

	/**
	 * Gets the temp connectors.
	 *
	 * @return the temp connectors
	 */
	public HashMap<Integer, TempConnector> getTempConnectors(){
		return tempConnectors;
	}
	
	/**
	 * Removes the temps by connector.
	 *
	 * @param tempId the temp id
	 */
	public void removeTempsByConnector(int tempId){
	
	//remove connector
	tempConnectors.remove(tempId);

	//remove devices
	Iterator<Map.Entry<Integer, TempDevice>> devIterator = tempDevices.entrySet().iterator();
	while(devIterator.hasNext()){
	   Entry<Integer, TempDevice> entry = devIterator.next();
	   if(entry.getValue().getConnectorID()== tempId){
		   devIterator.remove();
	   }
	}
	
	//remove componentd
	Iterator<Map.Entry<Integer, TempComponent>> compIterator = tempDeviceComponents.entrySet().iterator();
	while(devIterator.hasNext()){
	   Entry<Integer, TempComponent> entry = compIterator.next();
	   if(entry.getValue().getConnectorId()== tempId){
		   compIterator.remove();
	   }
	}
	
	//send updated data to gui-connectors
	BoeseJson ust = new UserSendTemps(getTempConnectors(), getTempDevices(),
	getTempComponents(), getConnectorID(), 0, new Date().getTime());
	OutputStream os = new ByteArrayOutputStream();
	BoeseJson.parseMessage(ust, os);
	SessionHandler.getInstance().sendToUserConnectors(os.toString());
	

	}
	
	/**
	 * Confirm connector.
	 *
	 * @param tempId the temp id
	 * @throws NotFoundException the not found exception
	 */
	public void confirmConnector(int tempId) throws NotFoundException{
	
		String name = tempConnectors.get(tempId).getName();
		if(name == null){
			throw new NotFoundException("Connector with tempId " + tempId + " not Found");
		}
		boolean isUserConnector = tempConnectors.get(tempId).isUserConnector();

		SecureRandom sr = new SecureRandom();
		String pw = String.valueOf(sr.nextLong());

		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Connector con = daoHandler.getConnectorDAO().create(em, name, pw, isUserConnector);
		em.getTransaction().commit();
		em.close();
		SessionHandler.getInstance().setConnectorId(tempId, con.getCoId(), con.isUserConnector());

		// Send ConfirmConnection
		BoeseJson cc = new ConfirmConnection(pw, con.getCoId(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cc, os);
		SessionHandler.getInstance().sendToConnector(con.getCoId(), os.toString());

		if (!isUserConnector) {			
			// Send RequestAllDevices
			BoeseJson rad = new RequestAllDevices(con.getCoId(), 0, new Date().getTime());
			os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(rad, os);
			SessionHandler.getInstance().sendToConnector(con.getCoId(), os.toString());
		}
//		else{
//			//save isUserConnector in sessionData for caching 
//			SessionHandler.getInstance().setUserConnector(con.getCoId());
//		}
		
		logger.info("User confirmed Connector with name: " + name);
		tempConnectors.remove(tempId);
	}
	
	/**
	 * Confirm device.
	 *
	 * @param tempId the temp id
	 * @param zoneId the zone id
	 * @param name the name
	 * @throws NotFoundException the not found exception
	 */
	public void confirmDevice(int tempId, int zoneId, String name) throws NotFoundException{

		TempDevice temp = tempDevices.get(tempId);
		
		if(temp == null){
			throw new NotFoundException("Device with tempId " + tempId + " not found");
		}
		if(name == null){
			name = temp.getName();
		}
		
		int connectorId = temp.getConnectorID();
		
		HashMap<String, Integer> devices = new HashMap<String, Integer>();
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Connector connector = daoHandler.getConnectorDAO().get(em, temp.getConnectorID());
		Zone zone = daoHandler.getZoneDAO().get(em, zoneId);
		Device dev = daoHandler.getDeviceDAO().create(em, name, "serial", connector, zone);
		em.getTransaction().commit();
		em.close();
		devices.put(temp.getName(), dev.getDeId());	
		protocolHandler.sendConfirmDevices(devices, connectorId);		
		logger.info("User confirmed Device with name: " + name );
		tempDevices.remove(tempId);
	}
	
	
	/**
	 * Gets the temp devices.
	 *
	 * @return the temp devices
	 */
	public HashMap<Integer, TempDevice> getTempDevices() {
		return tempDevices;
	}

	/**
	 * Gets the temp device components.
	 *
	 * @return the temp device components
	 */
	public HashMap<Integer, TempComponent> getTempDeviceComponents() {
		return tempDeviceComponents;
	}

	/**
	 * Sets the temp device components.
	 *
	 * @param tempDeviceComponents the temp device components
	 */
	public void setTempDeviceComponents(HashMap<Integer, TempComponent> tempDeviceComponents) {
		this.tempDeviceComponents = tempDeviceComponents;
	}

	/**
	 * Sets the temp connectors.
	 *
	 * @param tempConnectors the temp connectors
	 */
	public void setTempConnectors(HashMap<Integer, TempConnector> tempConnectors) {
		this.tempConnectors = tempConnectors;
	}
	
	/**
	 * Sets the props.
	 *
	 * @param props the new props
	 */
	public void setProps(DistributorProperties props){
		this.props = props;
	}

	/**
	 * Sets the temp devices.
	 *
	 * @param tempDevices the temp devices
	 */
	public void setTempDevices(HashMap<Integer, TempDevice> tempDevices) {
		this.tempDevices = tempDevices;
	}

	/**
	 * Confirm device component.
	 *
	 * @param tempId the temp id
	 * @param unitId the unit id
	 * @param name the name
	 * @throws NotFoundException the not found exception
	 */
	public void confirmDeviceComponent(int tempId, int unitId, String name) throws NotFoundException{
		TempComponent temp = tempDeviceComponents.get(tempId);
		if(temp == null){
			throw new NotFoundException("Component with tempId " + tempId + " not found");
		}
		if (name == null){
			name = temp.getName();
		}
		int deviceId = temp.getDeviceId();
		int connectorId = temp.getConnectorId();
 
		int deCoId = 0;
		// TODO Was ist wenn Inserts.component() funktioniert aber Inserts.deviceComponent nicht (=>Transactions)
		// TODO jedesmal wird eine Komponente erstellt
		
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		//TODO müssen Device und Unit wirklich geholt werden?
		Unit unit = daoHandler.getUnitDAO().get(em, unitId);
		Component comp = daoHandler.getComponentDAO().create(em, name, temp.isActor(), unit);
		Device device = daoHandler.getDeviceDAO().get(em, deviceId);
		DeviceComponent deco = daoHandler.getDeviceComponentDAO().create(em, temp.getDescription(), -1000.0, 1000.0, 0.0, true, comp, device);
		em.getTransaction().commit();
		em.close();
		
		
		deCoId = deco.getDeCoId();
		HashMap<String, Integer> confirmComponents = new HashMap<String, Integer>();
		confirmComponents.put(temp.getName(), deCoId);
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		inquiryList.add(new Inquiry(deCoId, temp.getValueTimestamp(), temp.getValue()));
		protocolHandler.sendToDos(protocolHandler.getToDos(inquiryList));
		
		protocolHandler.sendConfirmComponent(deviceId, confirmComponents, connectorId);
		
		logger.info("User confirmed Component with name: " + name + " and Device with id: " + deviceId);	
		tempDeviceComponents.remove(tempId);
	}
	

	/**
	 * Gets the temp components.
	 *
	 * @return the temp components
	 */
	// Helper
	public HashMap<Integer, TempComponent> getTempComponents(){
		return tempDeviceComponents;
	}
	
	/**
	 * Insert values.
	 *
	 * @param inquirys the inquirys
	 * @return the list
	 */
	public List<ComponentXML> insertValues(List<Inquiry> inquirys) {
		for (Inquiry inq : inquirys) {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			DeviceComponent deco = daoHandler.getDeviceComponentDAO().get(em, inq.getDeviceComponentId());
			//ist die DeviceComponet auf loggen gestellt und die Differenz zwischen neuen und alten groß genug?
			if(deco.isLoggen() && Math.abs(deco.getCurrentValue().doubleValue() - inq.getValue()) > deco.getLogDiffernce().doubleValue()){
				daoHandler.getLogDeviceComponentDAO().create(em, deco, new Date(inq.getTimestamp()), deco.getCurrentValue());
			}
			deco.setCurrentValue(BigDecimal.valueOf(inq.getValue()));
			em.getTransaction().commit();
			em.close();
		}
		Interpreter interpreter = new Interpreter();
		List<ComponentXML> todos;
		try {
			todos = interpreter.getToDos(inquirys);
		} catch (Exception e) {
			System.err.println("Bad XML: " + e.getMessage());
			// TODO Exception Handling
			e.printStackTrace();
			todos = null;
		}
		return todos;
	}
	
	/**
	 * Adds the temp connector.
	 *
	 * @param name the name
	 * @param tempId the temp id
	 * @param userConnector the user connector
	 */
	public void addTempConnector(String name, int tempId, boolean userConnector){
		TempConnector tempCon = new TempConnector(name, userConnector);
		tempConnectors.put(tempId, tempCon);
		protocolHandler.sendNotificationToAllUserConnectors("New Connector connected", NotificationType.INFO, System.currentTimeMillis());
		if(props.isAutoConfirm()){
			try {
				confirmConnector(tempId);
			} catch (NotFoundException e) {
				logger.warn("Connector with tempId: " + tempId + " not found for auto-confirmation", e);
			}
		}
	}
	
	/**
	 * Adds the temp devie.
	 *
	 * @param temp the temp
	 */
	public void addTempDevie(TempDevice temp){
		tempDevices.put(tempDeviceId, temp);
		if(props.isAutoConfirm()){
			try {
				confirmDevice(tempDeviceId, 1, null);
			} catch (NotFoundException e) {
			    logger.warn("Device with tempId: " + tempDeviceId + " not found for auto-confirmation", e);
			}
		}
		tempDeviceId++;
	}
	
	/**
	 * Adds the temp component.
	 *
	 * @param temp the temp
	 */
	public void addTempComponent(TempComponent temp){
		tempDeviceComponents.put(tempCompId, temp);
		if(props.isAutoConfirm()){
			try {
				confirmDeviceComponent(tempCompId, 1, null);
			} catch (NotFoundException e) {
			    logger.error("Couldnt confirm Component with name: " + temp.getName() + " (not found)", e);
			}
		}
		tempCompId++;
	}
	
	
	
	/**
	 * Gets the connector id.
	 *
	 * @return the connector id
	 */
	public int getConnectorID() {
        return connectorID;
    }

    /**
	 * Gets the tdc.
	 *
	 * @return the tdc
	 */
	public ToDoChecker getTdc() {
		return tdc;
	}

    /**
     * Check default password.
     *
     * @param pw the pw
     * @return true, if successful
     */
    public boolean checkDefaultPassword(String pw) {
        return pw.equals(props.getDefaultPassword());
    }
}
