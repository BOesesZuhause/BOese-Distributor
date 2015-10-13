
package de.bo.aid.boese.json;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.json.*;

import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempDevice;

// TODO: Auto-generated Javadoc
/**
 * This class is the base for all Boese Json Messages.
 * It also provides methods to parse Json to Boese Message and vies versa.
 */
public class BoeseJson {
	
	/** The message type. */
	protected MessageType messageType = null;
	
	/** The connector id. */
	protected int connectorId = -1;
	
	/** The status. */
	protected int status = -1;
	
	/** The timestamp. */
	protected long timestamp = -1;
	
	/**
	 * Enumeration with all available message types.
	 */
	public enum MessageType {
		MULTI,
		/** The requestconnection. */
		REQUESTCONNECTION, 
		/** The confirmconnection. */
		CONFIRMCONNECTION, 
		/** The requestalldevices. */
		REQUESTALLDEVICES, 
		/** The senddevices. */
		SENDDEVICES, 
		/** The confirmdevices. */
		CONFIRMDEVICES, 
		/** The requestdevicecomponents. */
		REQUESTDEVICECOMPONENTS,
		/** The senddevicecomponents. */
		SENDDEVICECOMPONENTS, 
		/** The confirmdevicecomponents. */
		CONFIRMDEVICECOMPONENTS, 
		/** The sendvalue. */
		SENDVALUE, 
		/** The confirmvalue. */
		CONFIRMVALUE, 
		/** The requestvalue. */
		REQUESTVALUE,
		SENDNOTIFICATION,
		SENDSTATUS,
		CONFIRMSTATUS,
		USERREQUESTALLDEVICES,
		USERSENDDEVICES,
		USERREQUESTDEVICECOMPONENTS,
		USERSENDDEVICECOMPONENT,
		USERREQUESTCONNECTORS,
		USERREQUESTALLCONNECTORS,
		USERSENDCONNETORS,
		USERREQUESTALLZONES,
		USERSENDZONES,
		USERREQUESTALLRULES,
		USERSENDRULES,
		USERREQUESTTEMPS,
		USERSENDTEMPS,
		USERCONFIRMTEMPS
	}

	/**
	 * Getter for the message type.
	 *
	 * @return the message type
	 */
	public MessageType getType() {
		return messageType;
	}
	
	/**
	 * Getter for the connector ID.
	 *
	 * @return the connector ID
	 */
	public int getConnectorId() {
		return connectorId;
	}
	
	/**
	 * Getter for the status flag.
	 *
	 * @return the status flag
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Getter for the timestamp of the message.
	 *
	 * @return the timestamp of the message
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Protected constructor for child classes only.
	 *
	 * @param messageType the message type
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	protected BoeseJson(MessageType messageType, int connectorId, int status, long timestamp) {
		this.messageType = messageType;
		this.connectorId = connectorId;
		this.status = status;
		this.timestamp = timestamp;
	}
	
	/**
	 * Reads an input stream and parses it to a BoeseJson Message type.
	 * With getType() method you can check which message is returned.
	 * @param is InputStream with Json message
	 * @return an BoeseJson object
	 */
	public static BoeseJson readMessage(InputStream is) {
		JsonReader jr = Json.createReader(is);
		JsonObject jo = null;
		try {
			jo = jr.readObject();
		} catch(Exception e) {
			jr.close();
			return null;
		}
		jr.close();
		BoeseJson bj = null;
		
		int headerConnectorID;
		int headerStatus;
		long headerTimestamp;

		JsonObject header =  jo.getJsonObject("Header");
		if (header == null) {
			return null;
		}
		headerConnectorID = header.getInt("ConnectorId", -1);
		headerStatus = header.getInt("Status", -1);
		headerTimestamp = header.getInt("Timestamp", -1);
		
		switch(header.getInt("MessageType")) {
		case 0: //TODO TEST
			MultiMessage multi = new MultiMessage(headerConnectorID, headerStatus, headerTimestamp);
			JsonArray messages = jo.getJsonArray("Messages");
			for(JsonValue message : messages){
				InputStream in = new ByteArrayInputStream(message.toString().getBytes());	
				BoeseJson bs = readMessage(in);
				multi.addMessage(bs);
			}
			bj = multi;
			break;
		case 1: // RequestConnection
			String connectorNameRC = jo.getString("ConnectorName");
			String passwordRC = null;
			boolean userConnectorRC = false;
			if (jo.containsKey("Password")) {
				passwordRC = jo.getString("Password");
			}
			if (jo.containsKey("IsUserConnector")) {
				userConnectorRC = jo.getBoolean("IsUserConnector");
			}
			bj = new RequestConnection(connectorNameRC, passwordRC, headerConnectorID, headerStatus, headerTimestamp, userConnectorRC);
			break;
		case 2: // ConfirmConnection
			String passwordCC = jo.getString("Password");
			bj = new ConfirmConnection(passwordCC, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 3: // RequestAllDevices
			bj = new RequestAllDevices(headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 4: // SendDevices
			HashMap<String, Integer> devicesSD = new HashMap<>(); // name / id
			JsonArray devArSD = jo.getJsonArray("Devices");
			for (int i = 0; i < devArSD.size(); i++) {
				JsonObject device = devArSD.getJsonObject(i);
				devicesSD.put(device.getString("DeviceName"), device.getInt("DeviceId", -1));
			}
			bj = new SendDevices(devicesSD, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 5: // ConfirmDevices
			HashMap<String, Integer> devicesCD = new HashMap<>(); // name / id
			JsonArray devArCD = jo.getJsonArray("Devices");
			for (int i = 0; i < devArCD.size(); i++) {
				JsonObject device = devArCD.getJsonObject(i);
				devicesCD.put(device.getString("DeviceName"), device.getInt("DeviceId", -1));
			}
			bj = new ConfirmDevices(devicesCD, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 6: // RequestDeviceComponents
			int deviceIdRDC = jo.getInt("DeviceId", -1);
			bj = new RequestDeviceComponents(deviceIdRDC, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 7: // SendDeviceComponents
			int deviceIdSDC = jo.getInt("DeviceId", -1);
			HashSet<DeviceComponents> componentsSDC= new HashSet<>();
			JsonArray sendArSDC = jo.getJsonArray("Components");
			for (int i = 0; i < sendArSDC.size(); i++) {
				JsonObject components = sendArSDC.getJsonObject(i);
				String unit = null; //TODO wenn unit != null soll in db angelegt werden
				if (components.getJsonString("Unit") != null) {
					unit = components.getString("Unit");
				}
				String description = null;
				if (components.getJsonString("Description") != null) {
					description = components.getString("Description");
				}
				componentsSDC.add(
						new DeviceComponents(components.getInt("DeviceComponentId", -1), 
								components.getString("ComponentName"), 
								components.getJsonNumber("Value").doubleValue(), 
								components.getJsonNumber("Timestamp").longValue(),
								unit,
								description,
								components.getBoolean("Actor", false)));
			}
			bj = new SendDeviceComponents(deviceIdSDC, componentsSDC, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 8: // ConfirmDeviceComponents
			int deviceIdCDC = jo.getInt("DeviceId", -1);
			HashMap<String, Integer> componentsCDC = new HashMap<>(); // name / id
			JsonArray compCDC = jo.getJsonArray("Components");
			for (int i = 0; i < compCDC.size(); i++) {
				JsonObject device = compCDC.getJsonObject(i);
				componentsCDC.put(device.getString("ComponentName"), device.getInt("DeviceComponentId", -1));
			}
			bj = new ConfirmDeviceComponents(deviceIdCDC, componentsCDC, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 9: // SendValue
			int deviceIdSV = jo.getInt("DeviceId", -1);
			int deviceComponentIdSV = jo.getInt("DeviceComponentId", -1);
			double valueSV = jo.getJsonNumber("Value").doubleValue();
			long timestampSV = jo.getJsonNumber("Timestamp").longValue();
			bj = new SendValue(deviceIdSV, deviceComponentIdSV, valueSV, timestampSV, headerConnectorID, 
					headerStatus, headerTimestamp);
			break;
		case 10: // ConfirmValue
			int deviceIdCV = jo.getInt("DeviceId", -1);
			int deviceComponentIdCV = jo.getInt("DeviceComponentId", -1);
			bj = new ConfirmValue(deviceIdCV, deviceComponentIdCV, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 11:
			int deviceIdRV = jo.getInt("DeviceId", -1);
			int deviceComponentIdRV = jo.getInt("DeviceComponentId", -1);
			bj = new RequestValue(deviceIdRV, deviceComponentIdRV, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 12: // SendNotification
			int deviceIdSN = jo.getInt("DeviceId", -1);
			int deviceComponentIdSN = jo.getInt("DeviceComponentId", -1);
			int notificationType = jo.getInt("NotificationType", -1);
			long timestampSN = jo.getJsonNumber("Timestamp").longValue();
			String notificationStringSN = jo.getString("NotificationText", "");
			bj = new SendNotification(deviceIdSN, deviceComponentIdSN, notificationType, timestampSN, notificationStringSN, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 13: // SendStatus
			int deviceComponentIdSS = jo.getInt("DeviceComponentId", -1);
			int statusCodeSS = jo.getInt("StatusCode", -1);
			long timestampSS = jo.getJsonNumber("Timestamp").longValue();
			bj = new SendStatus(deviceComponentIdSS, statusCodeSS, timestampSS, true, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 14: // ConfirmStatus
			int deviceComponentIdCS = jo.getInt("DeviceComponentId", -1);
			int statusCodeCS = jo.getInt("StatusCode", -1);
			long timestampCS = jo.getJsonNumber("Timestamp").longValue();
			bj = new SendStatus(deviceComponentIdCS, statusCodeCS, timestampCS, false, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 50: // UserRequestAllDevices
			bj = new RequestAllDevices(headerConnectorID, headerStatus, headerTimestamp, true);
			break;
		case 51: // UserSendDevices
			bj = new UserSendDevices(headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 52: // UserRequestDeviceComponents
			HashSet<Integer> devIdSetURDC = new HashSet<>();
			JsonArray devIdsURDC = jo.getJsonArray("DeviceIds");
			for (JsonValue value : devIdsURDC) {
				devIdSetURDC.add(Integer.parseInt(value.toString()));
			}
			bj = new UserRequestDeviceComponents(devIdSetURDC, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 53: // UserSendDeviceComponents
			HashSet<DeviceComponents> decoSetUSDC = new HashSet<>();
			JsonArray decosUSDC = jo.getJsonArray("Components");
			for (int i = 0; i < decosUSDC.size(); i++) {
				JsonObject decoUSDC = decosUSDC.getJsonObject(i);
				decoSetUSDC.add(new DeviceComponents(decoUSDC.getInt("DeviceComponentId"), 
													decoUSDC.getString("ComponentName"), 
													decoUSDC.getJsonNumber("Value").doubleValue(), 
													decoUSDC.getJsonNumber("Timestamp").longValue(), 
													decoUSDC.getString("Unit"), 
													decoUSDC.getString("Description"), 
													decoUSDC.getBoolean("Actor", false)));
			}
			bj = new UserSendDeviceComponent(jo.getInt("DeviceId"), decoSetUSDC, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 54: // UserRequestConnectors
			HashSet<Integer> conIdSetURCO = new HashSet<>();
			JsonArray conIdsURCO = jo.getJsonArray("ConnectorIds");
			for (JsonValue value : conIdsURCO) {
				conIdSetURCO.add(Integer.parseInt(value.toString()));
			}
			bj = new UserRequestConnectors(conIdSetURCO, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 55: // UserRequestAllConnectors
			bj = new UserRequestGeneral(MessageType.USERREQUESTALLCONNECTORS, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 56: // UserSendConnectors
			HashMap<Integer, String> connectorMapUSC = new HashMap<Integer, String>();
			JsonArray connectorsUSC = jo.getJsonArray("Connectors");
			for (int i = 0; i < connectorsUSC.size(); i++) {
				JsonObject conUSC = connectorsUSC.getJsonObject(i);
				connectorMapUSC.put(conUSC.getInt("ConnectorId"), conUSC.getString("ConnectorName"));
			}
			bj = new UserSendConnectors(connectorMapUSC, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 57: // UserRequestAllZones
			bj = new UserRequestGeneral(MessageType.USERREQUESTALLZONES, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 58: // UserSendZones
			HashSet<Zone> zoneSetUSZ = new HashSet<Zone>();
			JsonArray zonesUSZ = jo.getJsonArray("Zones");
			for (int i = 0; i < zonesUSZ.size(); i++) {
				JsonObject zoneUSZ = zonesUSZ.getJsonObject(i);
				zoneSetUSZ.add(new Zone(
							zoneUSZ.getInt("ZoneId"),
							zoneUSZ.getInt("SuperZoneId"),
							zoneUSZ.getString("ZoneName", "")));
			}
			bj = new UserSendZones(zoneSetUSZ, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 59: // UserRequestAllRules
			bj = new UserRequestGeneral(MessageType.USERREQUESTALLRULES, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 60: // UserSendRules
			HashSet<Rule> ruleSetURAR = new HashSet<>();
			JsonArray rulesURAR = jo.getJsonArray("Rules");
			for (int i = 0; i < rulesURAR.size(); i++) {
				JsonObject ruleURAR = rulesURAR.getJsonObject(i);
				ruleSetURAR.add(new Rule(ruleURAR.getInt("RuleId"), 
								ruleURAR.getBoolean("Active"), 
								ruleURAR.getJsonNumber("InsertDate").longValue(), 
								ruleURAR.getJsonNumber("ModifyDate").longValue(), 
								ruleURAR.getString("Permissions"), 
								ruleURAR.getString("Conditions"), 
								ruleURAR.getString("Actions")));
			}
			bj = new UserSendRules(ruleSetURAR, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 80: // UserRequestTemps
			bj = new UserRequestGeneral(MessageType.USERREQUESTTEMPS, headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 81: // UserSendTemps
			HashMap<Integer, String> tempConnectorsUST = new HashMap<>();
			HashMap<Integer, TempDevice> tempDevicesUST = new HashMap<>();
			HashMap<Integer, TempComponent> tempDeviceComponentsUST = new HashMap<>();
			JsonArray tempConsUST = jo.getJsonArray("TempConnectors");
			for (int i = 0; i < tempConsUST.size(); i++) {
				JsonObject con = tempConsUST.getJsonObject(i);
				tempConnectorsUST.put(con.getInt("ConnectorTmpId"), con.getString("ConnectorName"));
			}
			JsonArray tempDevsUST = jo.getJsonArray("TempDevices");
			for (int i = 0; i < tempDevsUST.size(); i++) {
				JsonObject dev = tempDevsUST.getJsonObject(i);
				tempDevicesUST.put(dev.getInt("ConnectorTmpId"), new TempDevice(dev.getInt("ConnectorId"), dev.getString("DeviceName")));
			}
			JsonArray tempDeCosUST = jo.getJsonArray("TempDevicesComponents");
			for (int i = 0; i < tempDeCosUST.size(); i++) {
				JsonObject deCo = tempDeCosUST.getJsonObject(i);
				tempDeviceComponentsUST.put(deCo.getInt("ComponentTmpId"), new TempComponent(
								deCo.getInt("ComponentTmpId"), 
								deCo.getString("Name"), 
								-1, 
								-1, 
								deCo.getInt("ConnectorId"), 
								deCo.getString("Description"), 
								deCo.getString("Unit"), 
								deCo.getBoolean("Actor")));
			}
			bj = new UserSendTemps(tempConnectorsUST, tempDevicesUST, tempDeviceComponentsUST, 
					headerConnectorID, headerStatus, headerTimestamp);
			break;
		case 82: // UserConfirmTemps
			HashSet<Integer> tempConnectorsSetUCT = new HashSet<Integer>();
			HashMap<Integer, Integer> tempDevicesSetUCT = new HashMap<>();
			HashSet<UserTempComponent> tempDeviceComponentsUCT = new HashSet<>();
			JsonArray tempConnectorsUCT = jo.getJsonArray("TempConnectors");
			for (JsonValue value : tempConnectorsUCT) {
				tempConnectorsSetUCT.add(Integer.parseInt(value.toString()));
			}
			JsonArray tempDevicesUCT = jo.getJsonArray("TempDevices");
			for (int i = 0; i < tempDevicesUCT.size(); i++) {
				JsonObject dev = tempDevicesUCT.getJsonObject(i);
				tempDevicesSetUCT.put(dev.getInt("DeviceTmpId"), dev.getInt("ZoneId"));
			}
			JsonArray tempComponentsUST = jo.getJsonArray("TempDevices");
			for (int i = 0; i < tempComponentsUST.size(); i++) {
				JsonObject dev = tempComponentsUST.getJsonObject(i);
				tempDeviceComponentsUCT.add(new UserTempComponent(dev.getInt("ComponentTmpId"), 
						dev.getInt("UnitId"), dev.getString("Name")));
			}
			bj = new UserConfirmTemps(tempConnectorsSetUCT, tempDevicesSetUCT, tempDeviceComponentsUCT, 
					headerConnectorID, headerStatus, headerTimestamp);
			break;
		default:
			break;
		}
		return bj;
	}
	
	/**
	 * Private method to create a JsonObject with the Json header.
	 *
	 * @param messageType the message type
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 * @return JsonObjectBuilder containing the Message Header
	 */
	private static JsonObjectBuilder addHeader(int messageType, int connectorId, int status, long timestamp) {
		JsonObjectBuilder header = Json.createObjectBuilder();
		header.add("MessageType", messageType);
		header.add("ConnectorId", connectorId);
		header.add("Status", status);
		header.add("Timestamp", timestamp);
		return header;
	}
	
	private static JsonObjectBuilder generateBuilder(BoeseJson message) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		switch (message.getType()) {
		case MULTI:
			//TODO TEST
			MultiMessage multi = (MultiMessage)message;
			job.add("Header", addHeader(0, multi.getConnectorId(), multi.getStatus(), multi.getTimestamp()));
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			JsonObjectBuilder objectBuilder;
			for(BoeseJson singleMessage : multi.getMessages()){
				objectBuilder = generateBuilder(singleMessage);
				arrayBuilder.add(objectBuilder);
			}
			job.add("Messages", arrayBuilder);
			break;
		case REQUESTCONNECTION:
			RequestConnection rc = (RequestConnection)message;
			job.add("Header", addHeader(1, rc.getConnectorId(), rc.getStatus(), rc.getTimestamp()));
			job.add("ConnectorName", rc.getConnectorName());
			if (rc.getPassword() != null) {
				job.add("Password", rc.getPassword());
			}
			break;
		case CONFIRMCONNECTION:
			ConfirmConnection cc = (ConfirmConnection)message;
			job.add("Header", addHeader(2, cc.getConnectorId(), cc.getStatus(), cc.getTimestamp()));
			job.add("Password", cc.getPassword());
			job.add("ConnectorId", cc.getConnectorId());
			break;
		case REQUESTALLDEVICES:
			RequestAllDevices rad = (RequestAllDevices)message;
			job.add("Header", addHeader(3, rad.getConnectorId(), rad.getStatus(), rad.getTimestamp()));
			break;
		case SENDDEVICES:
			SendDevices sd = (SendDevices)message;
			job.add("Header", addHeader(4, sd.getConnectorId(), sd.getStatus(), sd.getTimestamp()));
			JsonArrayBuilder devicesSDAr = Json.createArrayBuilder();
			JsonObjectBuilder deviceSD;
			for (Entry<String, Integer> entry : sd.getDevices().entrySet()) {
				deviceSD = Json.createObjectBuilder();
				deviceSD.add("DeviceName", entry.getKey());
				deviceSD.add("DeviceId", entry.getValue());
				devicesSDAr.add(deviceSD);
			}
			job.add("Devices", devicesSDAr);
			break;
		case CONFIRMDEVICES:
			ConfirmDevices cd = (ConfirmDevices)message;
			job.add("Header", addHeader(5, cd.getConnectorId(), cd.getStatus(), cd.getTimestamp()));
			JsonArrayBuilder devicesCDAr = Json.createArrayBuilder();
			JsonObjectBuilder deviceCD;
			for (Entry<String, Integer> entry : cd.getDevices().entrySet()) {
				deviceCD = Json.createObjectBuilder();
				deviceCD.add("DeviceName", entry.getKey());
				deviceCD.add("DeviceId", entry.getValue());
				devicesCDAr.add(deviceCD);
			}
			job.add("Devices", devicesCDAr);
			break;
		case REQUESTDEVICECOMPONENTS:
			RequestDeviceComponents rdc = (RequestDeviceComponents)message;
			job.add("Header", addHeader(6, rdc.getConnectorId(), rdc.getStatus(), rdc.getTimestamp()));
			job.add("DeviceId", rdc.getDeviceId());
			break;
		case SENDDEVICECOMPONENTS:
			SendDeviceComponents sdc = (SendDeviceComponents)message;
			job.add("Header", addHeader(7, sdc.getConnectorId(), sdc.getStatus(), sdc.getTimestamp()));
			job.add("DeviceId", sdc.getDeviceId());
			JsonArrayBuilder deviceComponentsSDCAr = Json.createArrayBuilder();
			JsonObjectBuilder deviceComponentSDC;
			for (DeviceComponents deviceComponent : sdc.getComponents()) {
				deviceComponentSDC = Json.createObjectBuilder();
				deviceComponentSDC.add("DeviceComponentId", deviceComponent.getDeviceComponentId());
				deviceComponentSDC.add("ComponentName", deviceComponent.getComponentName());
				deviceComponentSDC.add("Value", deviceComponent.getValue());
				deviceComponentSDC.add("Timestamp", deviceComponent.getTimestamp());
				if (deviceComponent.getUnit() != null) {
					deviceComponentSDC.add("Unit", deviceComponent.getUnit());
				}
				if (deviceComponent.getDescription() != null) {					
					deviceComponentSDC.add("Description", deviceComponent.getDescription());
				}
				deviceComponentSDC.add("Actor", deviceComponent.isActor());
				deviceComponentsSDCAr.add(deviceComponentSDC);
			}
			job.add("Components", deviceComponentsSDCAr);
			break;
		case CONFIRMDEVICECOMPONENTS:
			ConfirmDeviceComponents cdc = (ConfirmDeviceComponents)message;
			job.add("Header", addHeader(8, cdc.getConnectorId(), cdc.getStatus(), cdc.getTimestamp()));
			job.add("DeviceId", cdc.getDeviceId());
			JsonArrayBuilder componentsCDCA = Json.createArrayBuilder();
			JsonObjectBuilder componentCDC;
			for (Entry<String, Integer> entry : cdc.getComponents().entrySet()) {
				componentCDC = Json.createObjectBuilder();
				componentCDC.add("DeviceComponentId", entry.getValue());
				componentCDC.add("ComponentName", entry.getKey());
				componentsCDCA.add(componentCDC);
			}
			job.add("Components", componentsCDCA);
			break;
		case SENDVALUE:
			SendValue sv = (SendValue)message;
			job.add("Header", addHeader(9, sv.getConnectorId(), sv.getStatus(), sv.getTimestamp()));
			job.add("DeviceId", sv.getDeviceId());
			job.add("DeviceComponentId", sv.getDeviceComponentId());
			job.add("Value", sv.getValue());
			job.add("Timestamp", sv.getValueTimestamp());
			break;
		case CONFIRMVALUE:
			ConfirmValue cv = (ConfirmValue)message;
			job.add("Header", addHeader(10, cv.getConnectorId(), cv.getStatus(), cv.getTimestamp()));
			job.add("DeviceId", cv.getDeviceId());
			job.add("DeviceComponentId", (cv.getDeviceComponentId()));
			break;
		case REQUESTVALUE:
			RequestValue rv = (RequestValue)message;
			job.add("Header", addHeader(11, rv.getConnectorId(), rv.getStatus(), rv.getTimestamp()));
			job.add("DeviceId", rv.getDeviceId());
			job.add("DeviceComponentId", rv.getDeviceComponentId());
			break;
		case SENDNOTIFICATION:
			SendNotification sn = (SendNotification)message;
			job.add("Header", addHeader(12, sn.getConnectorId(), sn.getStatus(), sn.getTimestamp()));
			job.add("DeviceId", sn.getDeviceId());
			job.add("DeviceComponentId", sn.getDeviceComponentId());
			job.add("NotificationType", sn.getNotificationType());
			job.add("Timestamp", sn.getNotificationTimestamp());
			job.add("NotificationText", sn.getNotificationText());
			break;
		case SENDSTATUS:
			SendStatus ss = (SendStatus)message;
			job.add("Header", addHeader(13, ss.getConnectorId(), ss.getStatus(), ss.getTimestamp()));
			job.add("DeviceComponentId", ss.getDeviceComponentId());
			job.add("Timestamp", ss.getStatusTimestamp());
			break;
		case CONFIRMSTATUS:
			SendStatus cs = (SendStatus)message;
			job.add("Header", addHeader(14, cs.getConnectorId(), cs.getStatus(), cs.getTimestamp()));
			job.add("DeviceComponentId", cs.getDeviceComponentId());
			job.add("Timestamp", cs.getStatusTimestamp());
			break;
		case USERREQUESTALLDEVICES:
			RequestAllDevices urad = (RequestAllDevices)message;
			job.add("Header", addHeader(50, urad.getConnectorId(), urad.getStatus(), urad.getTimestamp()));
			job.add("IsUserRequest", urad.isUserRequest());
			break;
		case USERSENDDEVICES:
			UserSendDevices usd = (UserSendDevices)message;
			job.add("Header", addHeader(51, usd.getConnectorId(), usd.getStatus(), usd.getTimestamp()));
			JsonArrayBuilder devicesUSD = Json.createArrayBuilder();
			JsonObjectBuilder deviceUSD;
			for (UserDevice dev : usd.getDevices()) {
				deviceUSD = Json.createObjectBuilder();
				deviceUSD.add("DeviceName", dev.getDeviceName());
				deviceUSD.add("DeviceId", dev.getDeviceId());
				deviceUSD.add("ZoneId", dev.getZoneId());
				deviceUSD.add("ConnectorId", dev.getConnectorId());
				devicesUSD.add(deviceUSD);
			}
			job.add("Devices", devicesUSD);
			break;
		case USERREQUESTDEVICECOMPONENTS:
			UserRequestDeviceComponents urdc = (UserRequestDeviceComponents)message;
			job.add("Header", addHeader(52, urdc.getConnectorId(), urdc.getStatus(), urdc.getTimestamp()));
			JsonArrayBuilder devicesURDC = Json.createArrayBuilder();
			for (Integer devIdURDC : urdc.getDeviceIds()) {
				devicesURDC.add(devIdURDC.intValue());
			}
			job.add("DeviceIds", devicesURDC);
			break;
		case USERSENDDEVICECOMPONENT:
			UserSendDeviceComponent usdc = (UserSendDeviceComponent)message;
			job.add("Header", addHeader(53, usdc.getConnectorId(), usdc.getStatus(), usdc.getTimestamp()));
			job.add("DeviceId", usdc.getDeviceId());
			JsonArrayBuilder decosUSDC = Json.createArrayBuilder();
			JsonObjectBuilder decoUSDC;			
			for (DeviceComponents deco : usdc.getComponentList()) {
				String description = deco.getDescription() == null ? "" : deco.getDescription();
				String componentName = deco.getComponentName() == null ? "" : deco.getComponentName();
				String unit = deco.getUnit() == null ? "" : deco.getUnit();
				decoUSDC = Json.createObjectBuilder();
				decoUSDC.add("DeviceComponentId", deco.getDeviceComponentId());
				decoUSDC.add("Description", description);
				decoUSDC.add("ComponentName", componentName);
				decoUSDC.add("Value", deco.getValue());
				decoUSDC.add("Timestamp", deco.getTimestamp());
				decoUSDC.add("Status", deco.getStatus());
				decoUSDC.add("Aktor", deco.isActor());
				decoUSDC.add("Unit", unit);
				decosUSDC.add(decoUSDC);
			}
			job.add("Components", decosUSDC);
			break;
		case USERREQUESTCONNECTORS:
			UserRequestConnectors urc = (UserRequestConnectors)message;
			job.add("Header", addHeader(54, urc.getConnectorId(), urc.getStatus(), urc.getTimestamp()));
			JsonArrayBuilder connectorsURC = Json.createArrayBuilder();
			for (Integer conIdURC : urc.getConnectorIds()) {
				connectorsURC.add(conIdURC.intValue());
			}
			job.add("ConnectorIds", connectorsURC);
			break;
		case USERSENDCONNETORS:
			UserSendConnectors usc = (UserSendConnectors)message;
			job.add("Header", addHeader(56, usc.getConnectorId(), usc.getStatus(), usc.getTimestamp()));
			JsonArrayBuilder consUSC = Json.createArrayBuilder();
			JsonObjectBuilder conUSC;
			for (Entry<Integer, String> entry : usc.getConnectors().entrySet()) {
				conUSC = Json.createObjectBuilder();
				conUSC.add("ConnectorId", entry.getKey());
				conUSC.add("ConnectorName", entry.getValue());
				consUSC.add(conUSC);
			}
			job.add("Connectors", consUSC);
			break;
		case USERREQUESTALLCONNECTORS:
			UserRequestGeneral urac = (UserRequestGeneral)message;
			job.add("Header", addHeader(55, urac.getConnectorId(), urac.getStatus(), urac.getTimestamp()));
			break;
		case USERREQUESTALLZONES:
			UserRequestGeneral uraz = (UserRequestGeneral)message;
			job.add("Header", addHeader(57, uraz.getConnectorId(), uraz.getStatus(), uraz.getTimestamp()));
			break;
		case USERSENDZONES:
			UserSendZones usz = (UserSendZones)message;
			job.add("Header", addHeader(58, usz.getConnectorId(), usz.getStatus(), usz.getTimestamp()));
			JsonArrayBuilder zonesUSZ = Json.createArrayBuilder();
			JsonObjectBuilder zoneUSZ;
			for (Zone zone : usz.getZones()) {
				zoneUSZ = Json.createObjectBuilder();
				zoneUSZ.add("ZoneId", zone.getZoneId());
				zoneUSZ.add("SuperZoneId", zone.getSuperZoneId());
				zoneUSZ.add("ZoneName", zone.getZoneName());
				zonesUSZ.add(zoneUSZ);
			}
			job.add("Zones", zonesUSZ);
			break;
		case USERREQUESTALLRULES:
			UserRequestGeneral urar = (UserRequestGeneral)message;
			job.add("Header", addHeader(59, urar.getConnectorId(), urar.getStatus(), urar.getTimestamp()));
			break;
		case USERSENDRULES:
			UserSendRules usr = (UserSendRules)message;
			job.add("Header", addHeader(60, usr.getConnectorId(), usr.getStatus(), usr.getTimestamp()));
			JsonArrayBuilder rulesUSR = Json.createArrayBuilder();
			JsonObjectBuilder ruleUSR;
			for (Rule rule : usr.getRules()) {
				ruleUSR = Json.createObjectBuilder();
				ruleUSR.add("RuleId", rule.getRuleId());
				ruleUSR.add("Active", rule.isActive());
				ruleUSR.add("InsertDate", rule.getInsertDate());
				ruleUSR.add("ModifyDate", rule.getModifyDate());
				ruleUSR.add("Permissions", rule.getPermissions());
				ruleUSR.add("Conditions", rule.getConditions());
				ruleUSR.add("Actions", rule.getActions());
				rulesUSR.add(ruleUSR);
			}
			job.add("Rules", rulesUSR);
			break;
		case USERREQUESTTEMPS:
			UserRequestGeneral urt = (UserRequestGeneral)message;
			job.add("Header", addHeader(80, urt.getConnectorId(), urt.getStatus(), urt.getTimestamp()));
			break;
		case USERSENDTEMPS:
			UserSendTemps ust = (UserSendTemps)message;
			job.add("Header", addHeader(81, ust.getConnectorId(), ust.getStatus(), ust.getTimestamp()));
			JsonArrayBuilder tempConnectorsUST = Json.createArrayBuilder();
			JsonObjectBuilder tempConnectorUST;
			for (Entry<Integer, String> entry : ust.getTempConnectors().entrySet()) {
				tempConnectorUST = Json.createObjectBuilder();
				tempConnectorUST.add("ConnectorTmpId", entry.getKey());
				tempConnectorUST.add("ConnectorName", entry.getValue());
				tempConnectorsUST.add(tempConnectorUST);
			}
			job.add("TmpConnectors", tempConnectorsUST);
			JsonArrayBuilder tempDevicesUST = Json.createArrayBuilder();
			JsonObjectBuilder tempDeviceUST;
			for (Entry<Integer, TempDevice> entry : ust.getTempDevices().entrySet()) {
				tempDeviceUST = Json.createObjectBuilder();
				tempDeviceUST.add("DeviceTmpId", entry.getKey());
				tempDeviceUST.add("DeviceName", entry.getValue().getName());
				tempDeviceUST.add("ConnectorId", entry.getValue().getConnectorID());
				tempDevicesUST.add(tempDeviceUST);
			}
			job.add("TmpDevices", tempDevicesUST);
			JsonArrayBuilder tempDeviceComponentsUST = Json.createArrayBuilder();
			JsonObjectBuilder tempDeviceComponentUST;
			for (Entry<Integer, TempComponent> entry : ust.getTempDeviceComponents().entrySet()) {
				tempDeviceComponentUST = Json.createObjectBuilder();
				tempDeviceComponentUST.add("ComponentTmpId", entry.getKey());
				tempDeviceComponentUST.add("DeviceId", entry.getValue().getDeviceId());
				tempDeviceComponentUST.add("ConnectorId", entry.getValue().getConnectorId());
				tempDeviceComponentUST.add("Name", entry.getValue().getName());
				tempDeviceComponentUST.add("Description", entry.getValue().getDescription());
				tempDeviceComponentUST.add("Aktor", entry.getValue().isActor());
				tempDeviceComponentUST.add("Unit", entry.getValue().getValue());
				tempDeviceComponentsUST.add(tempDeviceComponentUST);
			}
			job.add("TmpDeviceComponents", tempDeviceComponentsUST);
			break;
		case USERCONFIRMTEMPS:
			UserConfirmTemps uct = (UserConfirmTemps)message;
			job.add("Header", addHeader(82, uct.getConnectorId(), uct.getStatus(), uct.getTimestamp()));
			JsonArrayBuilder tempConnectorsURC = Json.createArrayBuilder();
			for (Integer conIdUCT : uct.getTempConnectors()) {
				tempConnectorsURC.add(conIdUCT.intValue());
			}
			job.add("TmpConnectors", tempConnectorsURC);
			JsonArrayBuilder tempDevicesURC = Json.createArrayBuilder();
			JsonObjectBuilder tempDeviceURT;
			for (Entry<Integer, Integer> entry : uct.getTempDevices().entrySet()) {
				tempDeviceURT = Json.createObjectBuilder();
				tempDeviceURT.add("DeviceTmpId", entry.getKey());
				tempDeviceURT.add("ZoneId", entry.getValue());
				tempDevicesURC.add(tempDeviceURT);
			}
			job.add("TmpDevices", tempDevicesURC);
			JsonArrayBuilder tempDeviceComponentsUCT = Json.createArrayBuilder();
			JsonObjectBuilder tempDeviceComponentUCT;
			for (UserTempComponent utc : uct.getTempDeviceComponents()) {
				tempDeviceComponentUCT = Json.createObjectBuilder();
				tempDeviceComponentUCT.add("ComponentTmpId", utc.getTempComponentId());
				tempDeviceComponentUCT.add("UnitID", utc.getUnitId());
				tempDeviceComponentUCT.add("Name", utc.getName());
				tempDeviceComponentsUCT.add(tempDeviceComponentUCT);
			}
			job.add("TmpDeviceComponents", tempDeviceComponentsUCT);
			break;
		default:
			break;
		}
		return job;
	}
	
	/**
	 * Writes a BoeseJson Message to a given output stream.
	 *
	 * @param message the BoeseJson Message
	 * @param os the OutputStream to write in. If null a new ByteArrayOutputStream is generated.
	 * @return true, if writing was successful
	 */
	public static boolean parseMessage(BoeseJson message, OutputStream os) {
		boolean output = true;
		JsonObjectBuilder job = generateBuilder(message);
		if (os == null) {
			os = new ByteArrayOutputStream();
		}
		JsonWriter writer = Json.createWriter(os);
		writer.writeObject(job.build());
		writer.close();
		return output;
	}
}
