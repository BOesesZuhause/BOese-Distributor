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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LazyInitializationException;

import de.bo.aid.boese.db.AllSelects;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.ConfirmDeviceComponents;
import de.bo.aid.boese.json.ConfirmDevices;
import de.bo.aid.boese.json.ConfirmValue;
import de.bo.aid.boese.json.DeviceComponents;
import de.bo.aid.boese.json.MultiMessage;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.RequestDeviceComponents;
import de.bo.aid.boese.json.Rule;
import de.bo.aid.boese.json.SendDeviceComponents;
import de.bo.aid.boese.json.SendDevices;
import de.bo.aid.boese.json.SendStatus;
import de.bo.aid.boese.json.SendValue;
import de.bo.aid.boese.json.UserConfirmRules;
import de.bo.aid.boese.json.UserConfirmTemps;
import de.bo.aid.boese.json.UserCreateRules;
import de.bo.aid.boese.json.UserDevice;
import de.bo.aid.boese.json.UserRequestConnectors;
import de.bo.aid.boese.json.UserRequestDeviceComponents;
import de.bo.aid.boese.json.UserRequestGeneral;
import de.bo.aid.boese.json.UserSendConnectors;
import de.bo.aid.boese.json.UserSendDeviceComponent;
import de.bo.aid.boese.json.UserSendDevices;
import de.bo.aid.boese.json.UserSendRules;
import de.bo.aid.boese.json.UserSendTemps;
import de.bo.aid.boese.json.UserSendZones;
import de.bo.aid.boese.json.UserTempComponent;
import de.bo.aid.boese.json.Zone;
import de.bo.aid.boese.json.BoeseJson.MessageType;
import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.ruler.Inquiry;
import de.bo.aid.boese.ruler.Interpretor;
import de.bo.aid.boese.socket.MessageHandler;
import de.bo.aid.boese.socket.SessionHandler;
import de.bo.aid.boese.xml.BoeseXML;
import de.bo.aid.boese.xml.Component;
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

	/**
	 * Instantiates a new protocol handler.
	 *
	 * @param distributor
	 *            the distributor
	 */
	public ProtocolHandler(Distributor distributor) {
		this.distributor = distributor;
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
			logger.warn("empty message");
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
		case USERREQUESTALLDEVICES:
			handleUserRequestAllDevices((RequestAllDevices) bjMessage, connectorId);
			break;
		case USERREQUESTDEVICECOMPONENTS:
			handleUserRequestDeviceComponents((UserRequestDeviceComponents) bjMessage, connectorId);
			break;
		case USERREQUESTCONNECTORS:
			handleUserRequestConnectors(bjMessage, connectorId);
			break;
		case USERREQUESTALLCONNECTORS:
			handleUserRequestConnectors(bjMessage, connectorId);
			break;
		case USERREQUESTALLZONES:
			handleUserRequestAllZones((UserRequestGeneral) bjMessage, connectorId);
			break;
		case USERREQUESTALLRULES:
			handleUserRequestAllRules((UserRequestGeneral) bjMessage, connectorId);
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
		default:
			break;
		}
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

		if (rc.getPassword() == null && rc.getConnectorId() == -1) {

			// Add requesting Connector to tempConnectors with tempId from
			// SocketHandler
			distributor.addTempConnector(rc.getConnectorName(), tempId);

		} else {
			String pw = rc.getPassword();
			int conId = rc.getConnectorId();
			String conName = rc.getConnectorName();

			Connector con = Selects.connector(conId);

			if (con.getName().compareTo(conName) == 0 && con.getPassword().compareTo(pw) == 0) {
				SessionHandler.getInstance().setConnectorId(tempId, conId);
				sendConfirmConnection(pw, conId);
				sendRequestAllDevices(conId);
			} else {
				SessionHandler.getInstance().rejectConnection(conId);
			}
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
		if (connectorId != sd.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}

		HashMap<String, Integer> devices = sd.getDevices();
		HashMap<String, Integer> confirmDevices = new HashMap<>();

		for (String deviceName : devices.keySet()) {
			if (devices.get(deviceName) == -1) { // device not in db

				TempDevice temp = new TempDevice(connectorId, deviceName);
				distributor.addTempDevie(temp);
			} else { // device already in db
				Device dev = Selects.device(devices.get(deviceName));
				if (dev.getAlias() == deviceName) { // device is correct in DB
					confirmDevices.put(deviceName, dev.getDeId());
				} else { // device id does not fit to device name
					// TODO error handling
				}
			}
		}
		if (!confirmDevices.isEmpty()) {
			sendConfirmDevices(confirmDevices, connectorId);
		}
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
		// TODO Regelparsing mit component values
		if (connectorId != sdc.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		int deviceId = sdc.getDeviceId();

		HashSet<DeviceComponents> components = sdc.getComponents();
		HashMap<String, Integer> confirmComponents = new HashMap<>();
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		for (DeviceComponents component : components) {
			if (component.getDeviceComponentId() == -1) { // Component has no
															// DeCoId
				TempComponent temp = new TempComponent(deviceId, component.getComponentName(), component.getValue(),
						component.getTimestamp(), connectorId, component.getDescription(), component.getUnit(),
						component.isActor());
				distributor.addTempComponent(temp);
			} else { // Component has DeCoId
				Device device = Selects.device(deviceId);
				if (device == null) { // Device does not exist
					// TODO was tun wir, wenn der Konnector eine Device ID
					// nennt, die nicht in der DB ist?
				} else {
					Iterator<DeviceComponent> itDc = null;
					try {
						itDc = device.getDeviceComponents().iterator();
					} catch (LazyInitializationException lix) {
							//TODO
					}
					if (itDc != null) {
						while (itDc.hasNext()) { // search for devicecomponent
													// in db
							DeviceComponent dc = itDc.next();
							if (dc.getDeCoId() == component.getDeviceComponentId()) { // found
																						// deviceComponent
								confirmComponents.put(component.getComponentName(), dc.getDeCoId());
								inquiryList.add(
										new Inquiry(dc.getDeCoId(), component.getTimestamp(), component.getValue()));
								// Inserts.value(dc.getDeCoId(), new
								// Date(component.getTimestamp()),
								// component.getValue());
								break;
							} else {
								// TODO was passiert wenn DeCo nicht beim device
								// dabei ist?
							}
						}
					}
				}
			}
		}
		if (!confirmComponents.isEmpty()) {
			sendToDos(inquiryList);
			sendConfirmComponent(deviceId, confirmComponents, connectorId);
		}
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
		if (connectorId != sv.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		int deviceId = sv.getDeviceId();
		int deviceComponentId = sv.getDeviceComponentId();
		ArrayList<Inquiry> inquiryList = new ArrayList<>();
		inquiryList.add(new Inquiry(deviceComponentId, sv.getTimestamp(), sv.getValue()));
		sendToDos(inquiryList);
		sendConfirmValue(deviceId, deviceComponentId, connectorId);
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
		if (connectorId != urad.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		List<Device> devList = AllSelects.Devices();
		HashSet<UserDevice> deviceList = new HashSet<>();
		for (Device dev : devList) {
			deviceList.add(new UserDevice(dev.getAlias(), dev.getDeId(), dev.getZone().getZoId(),
					dev.getConnector().getCoId()));
		}
		sendUserSendDevices(deviceList, connectorId);
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
		if (connectorId != urdc.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashSet<Integer> deIDList = urdc.getDeviceIds();
		for (Integer devId : deIDList) {
			Set<DeviceComponent> decoSet = Selects.device(devId.intValue()).getDeviceComponents();
			HashSet<DeviceComponents> decos = new HashSet<>();
			for (DeviceComponent deco : decoSet) {
				decos.add(new DeviceComponents(deco.getDeCoId(), deco.getComponent().getName(),
						deco.getCurrentValue().doubleValue(), deco.getComponent().getUnit().getName(),
						deco.getDescription(), deco.getComponent().isSensor(), deco.getStatus()));
			}
			sendUserSendDeviceComponent(devId, decos, connectorId);
		}
	}

	/**
	 * Handle user request connectors.
	 *
	 * @param urc
	 *            the urc
	 * @param connectorId
	 *            the connector id
	 */
	private void handleUserRequestConnectors(BoeseJson urc, int connectorId) {
		if (connectorId != urc.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashMap<Integer, String> connectors = new HashMap<>();
		if (urc.getType() == MessageType.USERREQUESTCONNECTORS) {
			HashSet<Integer> conIdList = ((UserRequestConnectors) urc).getConnectorIds();
			for (Integer conId : conIdList) {
				Connector con = Selects.connector(conId.intValue());
				connectors.put(conId, con.getName());
			}
		} else if (urc.getType() == MessageType.USERREQUESTALLCONNECTORS) {
			List<Connector> connectorList = AllSelects.Connector();
			for (de.bo.aid.boese.model.Connector connector : connectorList) {
				connectors.put(connector.getCoId(), connector.getName());
			}
		}
		sendUserSendConnectors(connectors, connectorId);
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
		if (connectorId != urg.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashSet<Zone> zones = new HashSet<>();
		List<de.bo.aid.boese.model.Zone> zoneList = AllSelects.Zones();
		for (de.bo.aid.boese.model.Zone zone : zoneList) {
			zones.add(new Zone(zone.getZoId(), zone.getZone().getZoId(), zone.getName()));
		}
		sendUserSendZone(zones, connectorId);
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
		if (connectorId != urg.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashSet<Rule> rules = new HashSet<>();
		List<de.bo.aid.boese.model.Rule> ruleList = AllSelects.Rules();
		for (de.bo.aid.boese.model.Rule rule : ruleList) {
			rules.add(new Rule(rule.getRuId(), rule.getActive().booleanValue(), rule.getInsertDate().getTime(),
					rule.getModifyDate().getTime(), rule.getPermissions(), rule.getConditions(), rule.getActions()));
		}
		sendUserSendRules(rules, connectorId);
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
				distributor.confirmConnector(con, false);
			} catch (NotFoundException nfe) {
				// TODO
			}
		}
		for (Entry<Integer, Integer> entry : uct.getTempDevices().entrySet()) {
			try {
				distributor.confirmDevice(entry.getKey(), entry.getValue(), null);
			} catch (NotFoundException nfe) {
				// TODO
			}
		}
		for (UserTempComponent comp : uct.getTempDeviceComponents()) {
			try {
				distributor.confirmDeviceComponent(comp.getTempComponentId(), comp.getUnitId(), comp.getName());
			} catch (NotFoundException nfe) {
				// TODO
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
		if (connectorId != ucr.getConnectorId()) {
			SessionHandler.getInstance().rejectConnection(connectorId);
			return;
		}
		HashMap<Integer, Integer> tempRules = new HashMap<>();
		List<Integer> ruleDeCoIds = new ArrayList<>();
		Interpretor interpretor = new Interpretor();
		int ruleId;
		for (Rule rule : ucr.getRules()) {
			ruleDeCoIds = interpretor.getAllDeCoIdsCondition(
					BoeseXML.readXML(new ByteArrayInputStream(rule.getConditions().getBytes())));
			ruleId = Inserts.rule(ruleDeCoIds, rule.getPermissions(), rule.getPermissions(), rule.getActions());
			tempRules.put(rule.getTempRuleId(), ruleId);
		}
		sendConfirmRules(tempRules, connectorId);
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
		Updates.deviceComponentStatus(ss.getStatusCode(), ss.getDeviceComponentId());

		// TODO Shouldn't this be ConfirmStatus?
		BoeseJson cs = new SendStatus(ss.getDeviceComponentId(), ss.getStatusCode(), ss.getStatusTimestamp(), false,
				connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(cs, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
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
		BoeseJson cdc = new ConfirmDeviceComponents(deviceId, components, connectorId, 0, new Date().getTime());
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
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson sv = new SendValue(deId, deCoId, value, valueTimestamp, connectorId, 0, new Date().getTime());
		os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(sv, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
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
		BoeseJson cd = new ConfirmDevices(devices, connectorId, 0, new Date().getTime());
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
		BoeseJson rdc = new RequestDeviceComponents(deviceId, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(rdc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

	/**
	 * Send to dos.
	 *
	 * @param inquirys
	 *            the inquirys
	 */
	public void sendToDos(List<Inquiry> inquirys) {
		List<Component> todos = distributor.insertValues(inquirys);
		for (Component component : todos) {
			int deCoId = component.getId();
			int deviceId = Selects.deviceComponent(deCoId).getDevice().getDeId();
			int idConnector = Selects.device(deviceId).getConnector().getCoId();
			sendValue(deviceId, deCoId, component.getValue(), new Date().getTime(), idConnector);
		}
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
		BoeseJson rad = new RequestAllDevices(conId, 0, new Date().getTime());
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
		BoeseJson cv = new ConfirmValue(deviceId, deviceComponentId, connectorId, 0, new Date().getTime());
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
		BoeseJson ucor = new UserConfirmRules(tempRules, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(ucor, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

	/**
	 * Send user send temps.
	 *
	 * @param connectorId the connector id
	 */
	public void sendUserSendTemps(int connectorId) {
		BoeseJson ust = new UserSendTemps(distributor.getTempConnectors(), distributor.getTempDevices(),
				distributor.getTempComponents(), connectorId, 0, new Date().getTime());
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
	public void sendUserSendRules(HashSet<Rule> rules, int connectorId) {

		BoeseJson usc = new UserSendRules(rules, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usc, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

	/**
	 * Send user send zone.
	 *
	 * @param zones the zones
	 * @param connectorId the connector id
	 */
	public void sendUserSendZone(HashSet<Zone> zones, int connectorId) {
		BoeseJson usc = new UserSendZones(zones, connectorId, 0, new Date().getTime());
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
		BoeseJson usc = new UserSendConnectors(connectors, connectorId, 0, new Date().getTime());
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
		BoeseJson usdc = new UserSendDeviceComponent(devId.intValue(), decos, connectorId, 0, new Date().getTime());
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
		BoeseJson usd = new UserSendDevices(deviceList, connectorId, 0, new Date().getTime());
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(usd, os);
		SessionHandler.getInstance().sendToConnector(connectorId, os.toString());
	}

}
