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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LazyInitializationException;

import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.constants.NotificationType;
import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.exceptions.DBForeignKeyNotFoundException;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.ConfirmDeviceComponents;
import de.bo.aid.boese.json.ConfirmDevices;
import de.bo.aid.boese.json.ConfirmValue;
import de.bo.aid.boese.json.DeviceComponents;
import de.bo.aid.boese.json.HeartBeatMessage;
import de.bo.aid.boese.json.MultiMessage;
import de.bo.aid.boese.json.RepeatRuleJSON;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.RequestDeviceComponents;
import de.bo.aid.boese.json.RuleJSON;
import de.bo.aid.boese.json.SendDeviceComponents;
import de.bo.aid.boese.json.SendDevices;
import de.bo.aid.boese.json.SendNotification;
import de.bo.aid.boese.json.SendStatus;
import de.bo.aid.boese.json.SendValue;
import de.bo.aid.boese.json.UnitJSON;
import de.bo.aid.boese.json.UserConfirmRepeatRules;
import de.bo.aid.boese.json.UserConfirmRules;
import de.bo.aid.boese.json.UserConfirmTemps;
import de.bo.aid.boese.json.UserConfirmUnits;
import de.bo.aid.boese.json.UserConfirmZones;
import de.bo.aid.boese.json.UserCreateRepeatRules;
import de.bo.aid.boese.json.UserCreateRules;
import de.bo.aid.boese.json.UserCreateUnits;
import de.bo.aid.boese.json.UserCreateZones;
import de.bo.aid.boese.json.UserDevice;
import de.bo.aid.boese.json.UserRequestConnectors;
import de.bo.aid.boese.json.UserRequestDeviceComponents;
import de.bo.aid.boese.json.UserRequestGeneral;
import de.bo.aid.boese.json.UserSendConnectors;
import de.bo.aid.boese.json.UserSendDeviceComponent;
import de.bo.aid.boese.json.UserSendDevices;
import de.bo.aid.boese.json.UserSendRepeatRules;
import de.bo.aid.boese.json.UserSendRules;
import de.bo.aid.boese.json.UserSendTemps;
import de.bo.aid.boese.json.UserSendUnits;
import de.bo.aid.boese.json.UserSendZones;
import de.bo.aid.boese.json.UserTempComponent;
import de.bo.aid.boese.json.ZoneJSON;
import de.bo.aid.boese.json.BoeseJson.MessageType;
import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;
import de.bo.aid.boese.modelJPA.Component;
import de.bo.aid.boese.modelJPA.Connector;
import de.bo.aid.boese.modelJPA.Device;
import de.bo.aid.boese.modelJPA.DeviceComponent;
import de.bo.aid.boese.modelJPA.RepeatRule;
import de.bo.aid.boese.modelJPA.Rule;
import de.bo.aid.boese.modelJPA.Unit;
import de.bo.aid.boese.modelJPA.Zone;
import de.bo.aid.boese.ruler.Inquiry;
import de.bo.aid.boese.ruler.Interpreter;
import de.bo.aid.boese.socket.MessageHandler;
import de.bo.aid.boese.socket.SessionHandler;
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.ComponentXML;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class ProtocolHandler.
 */
public class ProtocolHandler implements MessageHandler {

	/** The distributor. */
	private Distributor distributor;

	/** The Constant logger for log4j. */
	final Logger logger = LogManager.getLogger(ProtocolHandler.class);
	
	private DAOHandler daoHandler;

	/**
	 * Instantiates a new protocol handler.
	 *
	 * @param distributor
	 *            the distributor
	 */
	public ProtocolHandler(Distributor distributor) {
		this.distributor = distributor;
		this.daoHandler = DAOHandler.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.bo.aid.boese.socket.MessageHandler#handleMessage(java.lang.String,
	 * int)
	 */
	@Override
	/**
	 * Method to handle Json messages and act depednding on the type and
	 * content.
	 *
	 * @param message
	 *            A string containing the Json message
	 * @param connectorId
	 *            The Id of the connector.
	 */
	// TODO rename connectorId (intern Id in SocketHandler class)
	public void handleMessage(String message, int connectorId) {
		BoeseJson bjMessage = BoeseJson.readMessage(new ByteArrayInputStream(message.getBytes()));
		if (bjMessage == null) {
			logger.warn("received empty message");
			SessionHandler.getInstance().rejectConnection(connectorId);
		}
		// System.out.println(bjMessage.getType());
		switch (bjMessage.getType()) {
		case MULTI:
			handleMultiMessages((MultiMessage) bjMessage, connectorId);
			break;
		case REQUESTCONNECTION:
			handleRequestConnection((RequestConnection) bjMessage, connectorId);
			break;
		case SENDDEVICES:
			handleSendDevices((SendDevices) bjMessage, connectorId);
			break;
		case SENDDEVICECOMPONENTS:
			handleSendDeviceComponents((SendDeviceComponents) bjMessage, connectorId);
			break;
		case SENDVALUE:
			handleSendValue((SendValue) bjMessage, connectorId);
			break;
		case SENDSTATUS:
			handleSendStatus((SendStatus) bjMessage, connectorId);
			break;
		case SENDNOTIFICATION:
		    handleSendNotification((SendNotification) bjMessage, connectorId);
		    break;
		case USERREQUESTALLDEVICES:
			handleUserRequestAllDevices((RequestAllDevices) bjMessage, connectorId);
			break;
		case USERREQUESTDEVICECOMPONENTS:
			handleUserRequestDeviceComponents((UserRequestDeviceComponents) bjMessage, connectorId);
			break;
		case USERREQUESTCONNECTORS:
			handleUserRequestConnectors((UserRequestConnectors)bjMessage, connectorId);
			break;
		case USERREQUESTALLCONNECTORS:
			handleUserRequestConnectors((UserRequestConnectors)bjMessage, connectorId);
			break;
		case USERREQUESTALLZONES:
			handleUserRequestAllZones((UserRequestGeneral) bjMessage, connectorId);
			break;
		case USERREQUESTALLRULES:
			handleUserRequestAllRules((UserRequestGeneral) bjMessage, connectorId);
			break;
		case USERREQUESTALLREPEATRULES:
			handleUserRequestAllRepeatRules((UserRequestGeneral) bjMessage, connectorId);
			break;
		case USERREQUESTTEMPS:
			handleUserRequestTemps((UserRequestGeneral) bjMessage, connectorId);
			break;
		case USERCONFIRMTEMPS:
			handleUserConfirmTemps((UserConfirmTemps) bjMessage, connectorId);
			break;
		case USERCREATERULES:
			handleUserCreateRules((UserCreateRules) bjMessage, connectorId);
			break;
		case HEARTBEATMESSAGE:
			handleHeartBeat((HeartBeatMessage) bjMessage, connectorId);
			break;
		case USERCREATEZONES:
			handleUserCreateZones((UserCreateZones) bjMessage, connectorId);
			break;
		case USERREQUESTALLUNITS:
			handleUserRequestAllUnits((UserRequestGeneral) bjMessage, connectorId);
			break;
		case USERCREATEUNITS:
			handleUserCreateUnits((UserCreateUnits) bjMessage, connectorId);
			break;
		case USERCREATEREPEATRULES:
			handleUserCreateRepeatRules((UserCreateRepeatRules) bjMessage, connectorId);
		default:
		    logger.warn("Unknown message type");
			break;
		}
	}

	/**
	 * Handle send notification.
	 *
	 * @param bjMessage the bj message
	 * @param connectorId the connector id
	 */
	private void handleSendNotification(SendNotification bjMessage, int connectorId) {	    
	    OutputStream os = new ByteArrayOutputStream();
        BoeseJson.parseMessage(bjMessage, os);
        SessionHandler.getInstance().sendToUserConnectors(os.toString());       
    }

    /**
	 * Handle heart beat.
	 *
	 * @param bjMessage the bj message
	 * @param connectorId the connector id
	 */
	private void handleHeartBeat(HeartBeatMessage bjMessage, int connectorId) {
		SessionHandler.getInstance().handleHeartbeat(connectorId);		
	}

	/**
	 * Handle request connections.
	 *
	 * @param rc
	 *            the rc
	 * @param tempId
	 *            the temp id
	 */
	private void handleRequestConnection(RequestConnection rc, int tempId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

	    //Gui-connector without id but with password
        if(rc.isUserConnector() && rc.getPassword() != null && rc.getConnectorId() == -1){
            String pw = rc.getPassword();
            String conName = rc.getConnectorName();
            
            if(distributor.checkDefaultPassword(pw)){
                distributor.addTempConnector(conName, tempId, true);
                try {
                    distributor.confirmConnector(tempId);
                } catch (NotFoundException e) {
                    logger.error("Error while adding gui-connector with default-password", e);
                }
                return;          
            }else{
                logger.info("Gui-Connector submitted wrong default password");
                SessionHandler.getInstance().rejectConnection(tempId);
                return;
            }
        }  
	    
		if (rc.getPassword() == null && rc.getConnectorId() == -1) { //connector without id
			
			// Add requesting Connector to tempConnectors with tempId from SocketHandler
			if(rc.isUserConnector()){
				distributor.addTempConnector(rc.getConnectorName(), tempId, true);
			}else{
				distributor.addTempConnector(rc.getConnectorName(), tempId, false);
			}
		} else { //connector with id
    			String pw = rc.getPassword();
    			int conId = rc.getConnectorId();
    			String conName = rc.getConnectorName();
    
    			Connector con = daoHandler.getConnectorDAO().get(em, conId);
    			if(con != null){
    				if (con.getName().compareTo(conName) == 0 && con.getPassword().compareTo(pw) == 0) {
    					SessionHandler.getInstance().setConnectorId(tempId, conId, rc.isUserConnector());
    					sendConfirmConnection(pw, conId);
    					sendRequestAllDevices(conId);
    				} else {
    					SessionHandler.getInstance().rejectConnection(tempId);
    				}
    			}
    			else{
    				em.getTransaction().rollback();
    				SessionHandler.getInstance().rejectConnection(tempId);
    				logger.error("Connector with ID " + conId + " not found in DB");
    			}
    			em.getTransaction().commit();
    			em.close();
		}
	}

	/**
	 * Handle send devices.
	 *
	 * @param sd
	 *            the sd
	 * @param connectorId
	 *            the connector id
	 */
	private void handleSendDevices(SendDevices sd, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != sd.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}

		HashMap<String, Integer> devices = sd.getDevices();
		HashMap<String, Integer> confirmDevices = new HashMap<>();
		Map<Integer, Device> devicesDB = daoHandler.getDeviceDAO().getAllAsMap(em);
		for (String deviceName : devices.keySet()) {
			if (devices.get(deviceName) == -1) { // device not in db
				TempDevice temp = new TempDevice(connectorId, deviceName);
				distributor.addTempDevie(temp);
			} else { // device already in db
				Device dev = devicesDB.get(devices.get(deviceName));
				if(dev != null){
					confirmDevices.put(deviceName, dev.getDeId());
				}
				else{
					logger.error("Device with ID " + devices.get(deviceName) + " not found in DB");
					logger.warn("Received device with unknown id. DeviceName: " + deviceName);
					TempDevice temp = new TempDevice(connectorId, deviceName);
					distributor.addTempDevie(temp);
					em.getTransaction().rollback();
				}
			}
		}
		if (!confirmDevices.isEmpty()) {
			sendConfirmDevices(confirmDevices, connectorId);
		}
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Handle send device components.
	 *
	 * @param sdc
	 *            the sdc
	 * @param connectorId
	 *            the connector id
	 */
	private void handleSendDeviceComponents(SendDeviceComponents sdc, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != sdc.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		int deviceId = sdc.getDeviceId();
		HashSet<DeviceComponents> components = sdc.getComponents();
		HashMap<String, Integer> confirmComponents = new HashMap<>();
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		
		Device device = daoHandler.getDeviceDAO().get(em, deviceId);
		if(device == null){
			logger.error("Device with ID " + deviceId + " not found in DB");
			return;
		}
		for (DeviceComponents component : components) { //iterate over sendComponents from connector
			if (component.getDeviceComponentId() == -1) { //component has no id
				TempComponent temp = new TempComponent(deviceId, component.getComponentName(), component.getValue(),
						component.getTimestamp(), connectorId, component.getDescription(), component.getUnit()==null? "undefined" : component.getUnit() ,
						component.isActor());
				distributor.addTempComponent(temp);
			} else { // Component has id
					Iterator<DeviceComponent> itDc = null;
					try {
						itDc = device.getDeviceComponents().iterator();
					} catch (LazyInitializationException lix) {
					    logger.error(lix);
					}
					boolean found = false;
					DeviceComponent dc = null;
					if (itDc != null) {

						while (itDc.hasNext()) { //iterate over deviceComponents from device(db)
							dc = itDc.next();
							if (dc.getDeCoId() == component.getDeviceComponentId()) { // found deviceComponent in db
								found = true;
								break;
						}
						}
							//component is already in db
						if(found){
							confirmComponents.put(component.getComponentName(), dc.getDeCoId());
							inquiryList.add(
									new Inquiry(dc.getDeCoId(), component.getTimestamp(), component.getValue()));
							// Inserts.value(dc.getDeCoId(), new
							// Date(component.getTimestamp()),
							// component.getValue());
						}else{ //component is not in db
							logger.warn("Received component with unknown id. ComponentName: " + component.getComponentName());
							TempComponent temp = new TempComponent(deviceId, component.getComponentName(), component.getValue(),
									component.getTimestamp(), connectorId, component.getDescription(), component.getUnit(),
									component.isActor());
							distributor.addTempComponent(temp);
						}
					}
			}
		}
		if (!confirmComponents.isEmpty()) {
			sendToDos(getToDos(inquiryList));
			sendConfirmComponent(deviceId, confirmComponents, connectorId);
		}
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Handle send value.
	 *
	 * @param sv
	 *            the sv
	 * @param connectorId
	 *            the connector id
	 */
	private void handleSendValue(SendValue sv, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != sv.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		int deviceId = sv.getDeviceId();
		int deviceComponentId = sv.getDeviceComponentId();
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sv, os);
		if (SessionHandler.getInstance().getIsUserConnectorByConnector(connectorId)) {
			// SendValue came from a user connector
			// if deco is actor, send SendValue to the actor
			Connector deviceComponet_Connector = daoHandler.getDeviceComponentDAO().getBelongingConnector(em, deviceComponentId);
			if(deviceComponet_Connector != null) {
				SessionHandler.getInstance().sendToConnector(deviceComponet_Connector.getCoId(), os.toString());
			} 
			else{
				// The deco, device, or connector does not exist -> db inconsistent?
				logger.error("DeviceComponent with ID " + deviceComponentId + " not found in DB");
				sendNotificationToAllUserConnectors("Unable to switch component with id: " + deviceComponentId +
				        "The component, device or connector is unknown.", NotificationType.ERROR, System.currentTimeMillis());
				em.getTransaction().rollback();
			}
			
		}
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		inquiryList.add(new Inquiry(deviceComponentId, sv.getValueTimestamp(), sv.getValue()));
		sendToDos(getToDos(inquiryList));
		sendConfirmValue(deviceId, deviceComponentId, connectorId);
		
		
		//sendValue to all userConnectors
		SessionHandler.getInstance().sendToUserConnectors(os.toString());
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Handle user request all devices.
	 *
	 * @param urad
	 *            the urad
	 * @param connectorId
	 *            the connector id
	 */
	private void handleUserRequestAllDevices(RequestAllDevices urad, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != urad.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		Set<Device> devList = daoHandler.getDeviceDAO().getAll(em);
		HashSet<UserDevice> deviceList = new HashSet<>();
		for (Device dev : devList) {
			//TODO n+1 Problem
			deviceList.add(new UserDevice(dev.getAlias(), dev.getDeId(), dev.getZone().getZoId(),
					dev.getConnector().getCoId()));
		}
		if(deviceList.isEmpty()){
		    sendNotificationToAllUserConnectors("The distributor has no known devices", NotificationType.WARNING, System.currentTimeMillis());
		}
		sendUserSendDevices(deviceList, connectorId);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Handle user request device components.
	 *
	 * @param urdc
	 *            the urdc
	 * @param connectorId
	 *            the connector id
	 */
	private void handleUserRequestDeviceComponents(UserRequestDeviceComponents urdc, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != urdc.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		//TODO
		for (int deviceId : urdc.getDeviceIds()) {
			Device device = daoHandler.getDeviceDAO().getWithDeviceComponents(em, deviceId);
			if(device != null){
				Set<DeviceComponent> decoSet = device.getDeviceComponents();
				
	            HashSet<DeviceComponents> decos = new HashSet<>();
	            for (DeviceComponent deco : decoSet) {
	                decos.add(new DeviceComponents(deco.getDeCoId(),
	                        deco.getComponent().getName(),
	                        deco.getCurrentValue().doubleValue(),
	                        System.currentTimeMillis(),
	                        deco.getComponent().getUnit().getName(),
	                        deco.getDescription(),
	                        deco.getComponent().isActor(),
	                        deco.getStatus()));
	            }
	            
	            sendUserSendDeviceComponent(deviceId, decos, connectorId);
			} 
			else{ 
				logger.error("Device with ID " + deviceId + " not found in DB");
				sendNotificationToAllUserConnectors("No deviceComponent found for device with id: " + deviceId + " .",
				        NotificationType.ERROR, System.currentTimeMillis());
			}

		}
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Handle user request connectors.
	 *
	 * @param urc
	 *            the urc
	 * @param connectorId
	 *            the connector id
	 */
	private void handleUserRequestConnectors(UserRequestConnectors urc, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != urc.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashMap<Integer, String> connectors = new HashMap<>();
		Map<Integer, Connector> connectorsDB= daoHandler.getConnectorDAO().getAllAsMap(em);
		if (urc.getType() == MessageType.USERREQUESTCONNECTORS) {
			HashSet<Integer> conIdList = urc.getConnectorIds();
			for (Integer conId : conIdList) {
				Connector con = connectorsDB.get(conId);
				if(con == null){ 
					logger.error("Connector with ID " + conId + " not found in DB");
					sendNotificationToAllUserConnectors("requested connecter with id: " + conId.intValue() + " is unknown.",
					        NotificationType.ERROR, System.currentTimeMillis());
				}
				connectors.put(conId, con.getName());
			}
		} else if (urc.getType() == MessageType.USERREQUESTALLCONNECTORS) {
			for (Connector connector : connectorsDB.values()) {
				connectors.put(connector.getCoId(), connector.getName());
			}
		}
		sendUserSendConnectors(connectors, connectorId);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Handle user request all zones.
	 *
	 * @param urg
	 *            the urg
	 * @param connectorId
	 *            the connector id
	 */
	private void handleUserRequestAllZones(UserRequestGeneral urg, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != urg.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashSet<ZoneJSON> zones = new HashSet<>();
		Set<Zone> zoneList = daoHandler.getZoneDAO().getAll(em);
		for (Zone zone : zoneList) {
			zones.add(new ZoneJSON(zone.getZoId(), zone.getZone().getZoId(), zone.getName()));
		}
		if(zones.isEmpty()){
		    sendNotificationToAllUserConnectors("The distributor has no known zones", NotificationType.WARNING, System.currentTimeMillis());
		}
		sendUserSendZone(zones, connectorId);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Handle user request all rules.
	 *
	 * @param urg
	 *            the urg
	 * @param connectorId
	 *            the connector id
	 */
	private void handleUserRequestAllRules(UserRequestGeneral urg, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != urg.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashSet<RuleJSON> rules = new HashSet<>();
		Set<Rule> ruleList = daoHandler.getRuleDAO().getAll(em);
		for (Rule rule : ruleList) {
			rules.add(new RuleJSON(rule.getRuId(), rule.getActive().booleanValue(), rule.getInsertDate().getTime(),
					rule.getModifyDate().getTime(), rule.getPermissions(), rule.getConditions(), rule.getActions()));
		}
		if(rules.isEmpty()){
		    sendNotificationToAllUserConnectors("The distributor has no known rules", NotificationType.WARNING, System.currentTimeMillis());
		}
		sendUserSendRules(rules, connectorId);
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * Handle user request all repeat rules.
	 *
	 * @param urg the urg
	 * @param connectorId the connector id
	 */
	private void handleUserRequestAllRepeatRules(UserRequestGeneral urg, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != urg.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashSet<RepeatRuleJSON> rules = new HashSet<>();
		Set<RepeatRule> ruleList = daoHandler.getRepeatRuleDAO().getAll(em);
		for (RepeatRule rule : ruleList) {
			rules.add(new RepeatRuleJSON(rule.getRrId(), rule.getRepeatsAfterEnd(), rule.getValue().doubleValue(), 
					rule.getRule().getRuId(), rule.getDeviceComponent().getDeCoId(), rule.getRepeat()));
		}
		if(rules.isEmpty()){
		    sendNotificationToAllUserConnectors("The distributor has no known repeatRules", NotificationType.WARNING, System.currentTimeMillis());
		}
		sendUserSendRepeatRules(rules, connectorId);
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * Handle user request all units.
	 *
	 * @param urg the urg
	 * @param connectorId the connector id
	 */
	private void handleUserRequestAllUnits(UserRequestGeneral urg, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != urg.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashSet<UnitJSON> units = new HashSet<>();
		Set<Unit> unitList = daoHandler.getUnitDAO().getAll(em);
		for (Unit unit : unitList) {
			units.add(new UnitJSON(unit.getUnId(), unit.getName(), unit.getSymbol()));
		}
		sendNotificationToAllUserConnectors("The distributor has no known units", NotificationType.WARNING, System.currentTimeMillis());
		sendUserSendUnits(units, connectorId);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Handle user request temps.
	 *
	 * @param urt
	 *            the urt
	 * @param connectorId
	 *            the connector id
	 */
	private void handleUserRequestTemps(UserRequestGeneral urt, int connectorId) {
		if (connectorId != urt.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		sendUserSendTemps(connectorId);
	}

	/**
	 * Handle user confirm temps.
	 *
	 * @param uct
	 *            the uct
	 * @param connectorId
	 *            the connector id
	 */
	private void handleUserConfirmTemps(UserConfirmTemps uct, int connectorId) {
		if (connectorId != uct.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		for (Integer con : uct.getTempConnectors()) {
			try {
				distributor.confirmConnector(con);
			} catch (NotFoundException nfe) {
				logger.error(nfe.getMessage(), nfe);
				sendNotificationToAllUserConnectors("The connecter with tempId: " + con.intValue() + " could not be found", 
				        NotificationType.ERROR, 
				        System.currentTimeMillis());
			}
		}
		for (Entry<Integer, Integer> entry : uct.getTempDevices().entrySet()) {
			try {
			    //TODO device-name soll mitgesndet werden
				distributor.confirmDevice(entry.getKey(), entry.getValue(), null);
			} catch (NotFoundException nfe) {
			    logger.error(nfe.getMessage(), nfe);
	             sendNotificationToAllUserConnectors("The device with tempId: " + entry.getKey() + " could not be found", 
	                        NotificationType.ERROR, 
	                        System.currentTimeMillis());
			}
		}
		for (UserTempComponent comp : uct.getTempDeviceComponents()) {
			try {
				distributor.confirmDeviceComponent(comp.getTempComponentId(), comp.getUnitId(), comp.getName());
			} catch (NotFoundException nfe) {
				logger.error(nfe.getMessage(), nfe);
                sendNotificationToAllUserConnectors("The deviceComponent with tempId: " + comp.getTempComponentId() + " could not be found", 
                        NotificationType.ERROR, 
                        System.currentTimeMillis());
			}
		}
	}

	/**
	 * Handle user create rules.
	 *
	 * @param ucr
	 *            the ucr
	 * @param connectorId
	 *            the connector id
	 */
	private void handleUserCreateRules(UserCreateRules ucr, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != ucr.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashMap<Integer, Integer> tempRules = new HashMap<>();
		List<DeviceComponent> ruleDeCos = new ArrayList<>();
		Interpreter interpretor = new Interpreter();
		for (RuleJSON rule : ucr.getRules()) {
			Rule r = null;
			if (BoeseXML.readXML(new ByteArrayInputStream(rule.getConditions().getBytes())) == null ||
					BoeseXML.readXML(new ByteArrayInputStream(rule.getPermissions().getBytes())) == null ||
					BoeseXML.readXML(new ByteArrayInputStream(rule.getActions().getBytes())) == null) {
				logger.warn("Invalid XML in new Rule");
				sendNotificationToAllUserConnectors("Syntax error in rule with id: " + rule.getRuleId(),
				        NotificationType.ERROR, System.currentTimeMillis());
			} else {
				ruleDeCos = interpretor.getAllDeCosCondition(
						BoeseXML.readXML(new ByteArrayInputStream(rule.getConditions().getBytes())));
				try {
					r = new Rule(rule.getPermissions(), rule.getConditions(), rule.getActions());
					r.setActive(rule.isActive()); // TODO constructor mit active erstellen
					Inserts.rule(ruleDeCos, r, distributor.getTdc());
				} catch (DBForeignKeyNotFoundException e) {
					sendNotificationToAllUserConnectors("Error while saving rule with id: " + rule.getRuleId() +
					        ". The referenced devicecomponents are unknown.", 
					        NotificationType.ERROR, System.currentTimeMillis());
				    logger.error(e.getMessage(), e);
				}
				tempRules.put(rule.getTempRuleId(), r.getRuId());
			}
		}
		sendConfirmRules(tempRules, connectorId);
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * Handle user create repeat rules.
	 *
	 * @param ucrr the ucrr
	 * @param connectorId the connector id
	 */
	private void handleUserCreateRepeatRules(UserCreateRepeatRules ucrr, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != ucrr.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashMap<Integer, Integer> tempRules = new HashMap<>();
		for (RepeatRuleJSON rule : ucrr.getRules()) {
			//TODO überlegen ob es sein muss die Rule und die DeviceComponete erst zu holen
			Rule ru = daoHandler.getRuleDAO().get(em, rule.getRuleId());
			DeviceComponent deco = daoHandler.getDeviceComponentDAO().get(em, rule.getDecoId());
			if(ru != null && deco != null){
				RepeatRule r = new RepeatRule(rule.getCron(), BigDecimal.valueOf(rule.getValue()), rule.getRepeatsAfterEnd(), ru, deco);
				daoHandler.getRepeatRuleDAO().create(em, r);
				tempRules.put(rule.getTempId(), r.getRrId());
			}
			else {
			    sendNotificationToAllUserConnectors("Error while saving repeaterule with id: " + rule.getRuleId() +
                        ". The referenced devicecomponents are unknown.", 
                        NotificationType.ERROR, System.currentTimeMillis());
				logger.error("Rule with ID " + rule.getRuleId() + " not found in DB or\n"
							+ "DeviceComponent with ID " + rule.getDecoId() + " not found in DB");
			}
		}
		sendConfirmRepeatRules(tempRules, connectorId);
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * Handle user create zones.
	 *
	 * @param ucz the ucz
	 * @param connectorId the connector id
	 */
	private void handleUserCreateZones(UserCreateZones ucz, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != ucz.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashMap<Integer, Integer> tempZones = new HashMap<>();
		Map<Integer, Zone> zonesDB = daoHandler.getZoneDAO().getAllAsMap(em);
		for (ZoneJSON zone : ucz.getZones()) {
			Zone z = null;
			z = new Zone(zone.getZoneName());
			Zone superZone = zonesDB.get(zone.getSuperZoneId());
			if(superZone == null) {
			    sendNotificationToAllUserConnectors("Error while saving zone with id: " + zone.getZoneId() +
                        ". The referenced superzone is unknown.", 
                        NotificationType.ERROR, System.currentTimeMillis());
				logger.error("Zone as SuperZone with ID " + zone.getSuperZoneId() + " not found in DB");
			}
			z.setZone(superZone);
			daoHandler.getZoneDAO().create(em, z);
			tempZones.put(zone.getTempZoneId(), z.getZoId());
			
		}
		if (!tempZones.isEmpty()) {
			sendConfirmZones(tempZones, connectorId);
		}
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * Handle user create units.
	 *
	 * @param ucu the ucu
	 * @param connectorId the connector id
	 */
	private void handleUserCreateUnits(UserCreateUnits ucu, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if (connectorId != ucu.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashMap<Integer, Integer> tempUnits = new HashMap<>();
		for (UnitJSON unit : ucu.getUnits()) {
			Unit u = null;
			u = new Unit(unit.getUnitName(), unit.getUnitSymbol());
			daoHandler.getUnitDAO().create(em, u);
			tempUnits.put(unit.getTempUnitId(), u.getUnId());			
		}
		if (!tempUnits.isEmpty()) {
			sendConfirmUnits(tempUnits, connectorId);
		}
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * Handle multi messages.
	 *
	 * @param mm
	 *            the mm
	 * @param connectorId
	 *            the connector id
	 */
	private void handleMultiMessages(MultiMessage mm, int connectorId) {
		if (connectorId != mm.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		// TODO evt. schlechte Performance weil hin und her geparst wird
		for (BoeseJson message : mm.getMessages()) {
			OutputStream os = new ByteArrayOutputStream();
			BoeseJson.parseMessage(message, os);
			handleMessage(os.toString(), connectorId);
		}
	}

	/**
	 * Handle send status.
	 *
	 * @param ss
	 *            the ss
	 * @param connectorId
	 *            the connector id
	 */
	private void handleSendStatus(SendStatus ss, int connectorId) {
		if (connectorId != ss.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		
//		try {
//			Updates.deviceComponentStatus(ss.getStatusCode(), ss.getDeviceComponentId());
//		} catch (DBObjectNotFoundException e) {
//			logger.error(e.getMessage(), e);
//		}
//
//		// TODO Shouldn't this be ConfirmStatus? Confirm zurücksenden
////		BoeseJson cs = new SendStatus(ss.getDeviceComponentId(), ss.getStatusCode(), ss.getStatusTimestamp(), false,
////				connectorId, 0, new Date().getTime());
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(ss, os);
//		//SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
//		//send status to all userConnectors
//		SessionHandler.getInstance().sendToUserConnectors(os.toString());
	}

	/**
	 * Send confirm component.
	 *
	 * @param deviceId
	 *            the device id
	 * @param components
	 *            the components
	 * @param connectorId
	 *            the connector id
	 */
	public void sendConfirmComponent(int deviceId, HashMap<String, Integer> components, int connectorId) {
		BoeseJson cdc = new ConfirmDeviceComponents(deviceId, components, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cdc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

	/**
	 * Send value.
	 *
	 * @param deId
	 *            the de id
	 * @param deCoId
	 *            the de co id
	 * @param value
	 *            the value
	 * @param valueTimestamp
	 *            the value timestamp
	 * @param connectorId
	 *            the connector id
	 */
	public void sendValue(int deId, int deCoId, double value, long valueTimestamp, int connectorId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		//TODO Sinn überlegen
		DeviceComponent deco = daoHandler.getDeviceComponentDAO().get(em, deCoId);
		if(deco != null){
			Component comp = deco.getComponent();
		}
		else{
			logger.error("DeviceComponent with ID " + deCoId + " not found in DB");
		}
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson sv = new SendValue(deId, deCoId, value, valueTimestamp, distributor.getConnectorID(), 0, new Date().getTime());
		os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sv, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Send confirm devices.
	 *
	 * @param devices
	 *            the devices
	 * @param connectorId
	 *            the connector id
	 */
	public void sendConfirmDevices(HashMap<String, Integer> devices, int connectorId) {
		// send Confirm Devices
		BoeseJson cd = new ConfirmDevices(devices, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cd, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());

		// Send Request Device Components
		for (Integer deviceId : devices.values()) {
			sendRequestDeviceComponents(deviceId, connectorId);
		}

	}

	/**
	 * Send request device components.
	 *
	 * @param deviceId the device id
	 * @param connectorId the connector id
	 */
	public void sendRequestDeviceComponents(int deviceId, int connectorId) {
		BoeseJson rdc = new RequestDeviceComponents(deviceId, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(rdc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	/**
	 * Get ToDos.
	 * 
	 * @param inquirys a Inquiry List
	 * @return the List
	 */
	public List<ComponentXML> getToDos(List<Inquiry> inquirys) {
		return distributor.insertValues(inquirys);
	}

	/**
	 * Send to dos.
	 *
	 * @param todos a ComponentXML Liste
	 */
	public void sendToDos(List<ComponentXML> todos) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		for (ComponentXML component : todos) {
			int deCoId = component.getId();
			//TODO 3 Selects in For-Schleife?
			DeviceComponent deco = daoHandler.getDeviceComponentDAO().get(em, deCoId);
			if(deco != null){
				Device device = deco.getDevice();
				Connector connector = device.getConnector();
				sendValue(device.getDeId(), deCoId, component.getValue(), new Date().getTime(), connector.getCoId());
			}
			else{
				logger.error("DeviceComponent with ID " + deCoId + " not found in DB");
			}
		}
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Send confirm connection.
	 *
	 * @param pw the pw
	 * @param conId the con id
	 */
	public void sendConfirmConnection(String pw, int conId) {
		BoeseJson cc = new ConfirmConnection(pw, conId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cc, os);
		SessionHandler.getInstance().sendToConnector(conId, os.toString());
	}

	/**
	 * Send request all devices.
	 *
	 * @param conId the con id
	 */
	public void sendRequestAllDevices(int conId) {
		BoeseJson rad = new RequestAllDevices(distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(rad, os);
		SessionHandler.getInstance().sendToConnector(conId, os.toString());
	}

	/**
	 * Send confirm value.
	 *
	 * @param deviceId the device id
	 * @param deviceComponentId the device component id
	 * @param connectorId the connector id
	 */
	public void sendConfirmValue(int deviceId, int deviceComponentId, int connectorId) {
		BoeseJson cv = new ConfirmValue(deviceId, deviceComponentId, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cv, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

	/**
	 * Send confirm rules.
	 *
	 * @param tempRules the temp rules
	 * @param connectorId the connector id
	 */
	public void sendConfirmRules(HashMap<Integer, Integer> tempRules, int connectorId) {
		BoeseJson ucor = new UserConfirmRules(tempRules, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(ucor, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	/**
	 * Send confirm repeat rules.
	 *
	 * @param tempRules the temp rules
	 * @param connectorId the connector id
	 */
	public void sendConfirmRepeatRules(HashMap<Integer, Integer> tempRules, int connectorId) {
		BoeseJson ucorr = new UserConfirmRepeatRules(tempRules, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(ucorr, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	/**
	 * Send confirm zones.
	 *
	 * @param tempZones the temp zones
	 * @param connectorId the connector id
	 */
	public void sendConfirmZones(HashMap<Integer, Integer> tempZones, int connectorId) {
		BoeseJson ucoz = new UserConfirmZones(tempZones, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(ucoz, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	/**
	 * Send confirm units.
	 *
	 * @param tempUnits the temp units
	 * @param connectorId the connector id
	 */
	public void sendConfirmUnits(HashMap<Integer, Integer> tempUnits, int connectorId) {
		BoeseJson ucou = new UserConfirmUnits(tempUnits, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(ucou, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

	/**
	 * Send user send temps.
	 *
	 * @param connectorId the connector id
	 */
	public void sendUserSendTemps(int connectorId) {
		BoeseJson ust = new UserSendTemps(distributor.getTempConnectors(), distributor.getTempDevices(),
		distributor.getTempComponents(), distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(ust, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

	/**
	 * Send user send rules.
	 *
	 * @param rules the rules
	 * @param connectorId the connector id
	 */
	public void sendUserSendRules(HashSet<RuleJSON> rules, int connectorId) {

		BoeseJson usc = new UserSendRules(rules, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	/**
	 * Send user send repeat rules.
	 *
	 * @param rules the rules
	 * @param connectorId the connector id
	 */
	public void sendUserSendRepeatRules(HashSet<RepeatRuleJSON> rules, int connectorId) {

		BoeseJson usc = new UserSendRepeatRules(rules, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	/**
	 * Send user send units.
	 *
	 * @param units the units
	 * @param connectorId the connector id
	 */
	public void sendUserSendUnits(HashSet<UnitJSON> units, int connectorId) {

		BoeseJson usu = new UserSendUnits(units, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usu, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

	/**
	 * Send user send zone.
	 *
	 * @param zones the zones
	 * @param connectorId the connector id
	 */
	public void sendUserSendZone(HashSet<ZoneJSON> zones, int connectorId) {
		BoeseJson usc = new UserSendZones(zones, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

	/**
	 * Send user send connectors.
	 *
	 * @param connectors the connectors
	 * @param connectorId the connector id
	 */
	public void sendUserSendConnectors(HashMap<Integer, String> connectors, int connectorId) {
		BoeseJson usc = new UserSendConnectors(connectors, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

	/**
	 * Send user send device component.
	 *
	 * @param devId the dev id
	 * @param decos the decos
	 * @param connectorId the connector id
	 */
	public void sendUserSendDeviceComponent(Integer devId, HashSet<DeviceComponents> decos, int connectorId) {
		BoeseJson usdc = new UserSendDeviceComponent(devId.intValue(), decos, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usdc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

	/**
	 * Send user send devices.
	 *
	 * @param deviceList the device list
	 * @param connectorId the connector id
	 */
	public void sendUserSendDevices(HashSet<UserDevice> deviceList, int connectorId) {
		BoeseJson usd = new UserSendDevices(deviceList, distributor.getConnectorID(), 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usd, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}
	
	/**
	 * Send notification to all user connectors.
	 *
	 * @param message the message
	 * @param type the type
	 * @param timestamp the timestamp
	 */
	public void sendNotificationToAllUserConnectors(String message, int type, long timestamp){
	    BoeseJson sn = new SendNotification(type, timestamp, message, distributor.getConnectorID(), 0, System.currentTimeMillis());
	    OutputStream os = new ByteArrayOutputStream();
	    BoeseJson.parseMessage(sn, os);
	    SessionHandler.getInstance().sendToUserConnectors(os.toString());
	}

}
