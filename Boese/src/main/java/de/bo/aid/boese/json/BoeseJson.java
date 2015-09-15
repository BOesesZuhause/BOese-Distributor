
package de.bo.aid.boese.json;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.json.*;

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
	
	/** The seq nr. */
	protected int seqNr = -1;
	
	/** The ack nr. */
	protected int ackNr = -1;
	
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
		USERREQUESTALLDEVICES,
		USERSENDDEVICES,
		USERREQUESTDEVICECOMPONENTS,
		USERSENDDEVICECOMPONENT
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
	 * Getter for the sequence number.
	 *
	 * @return the sequence number
	 */
	public int getSeqenceNr() {
		return seqNr;
	}
	
	/**
	 * Getter for the acknowledge number.
	 *
	 * @return the acknowledge number
	 */
	public int getAcknowledgeId() {
		return ackNr;
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
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	protected BoeseJson(MessageType messageType, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		this.messageType = messageType;
		this.connectorId = connectorId;
		this.seqNr = seqNr;
		this.ackNr = ackNr;
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
		
		int headerConnectorID, headerSeqNr, headerAckNr;
		int headerStatus;
		long headerTimestamp;

		JsonObject header =  jo.getJsonObject("Header");
		if (header == null) {
			return null;
		}
		headerConnectorID = header.getInt("ConnectorId", -1);
		headerSeqNr = header.getInt("SequenceNr", -1);
		headerAckNr = header.getInt("AcknowledgeNr", -1);
		headerStatus = header.getInt("Status", -1);
		headerTimestamp = header.getInt("Timestamp", -1);
		
		switch(header.getInt("MessageType")) {
		case 0: //TODO TEST
			MultiMessage multi = new MultiMessage(headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
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
			bj = new RequestConnection(connectorNameRC, passwordRC, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp, userConnectorRC);
			break;
		case 2: // ConfirmConnection
			String passwordCC = jo.getString("Password");
			bj = new ConfirmConnection(passwordCC, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 3: // RequestAllDevices
			bj = new RequestAllDevices(headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 4: // SendDevices
			HashMap<String, Integer> devicesSD = new HashMap<>(); // name / id
			JsonArray devArSD = jo.getJsonArray("Devices");
			for (int i = 0; i < devArSD.size(); i++) {
				JsonObject device = devArSD.getJsonObject(i);
				devicesSD.put(device.getString("DeviceName"), device.getInt("DeviceId", -1));
			}
			bj = new SendDevices(devicesSD, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 5: // ConfirmDevices
			HashMap<String, Integer> devicesCD = new HashMap<>(); // name / id
			JsonArray devArCD = jo.getJsonArray("Devices");
			for (int i = 0; i < devArCD.size(); i++) {
				JsonObject device = devArCD.getJsonObject(i);
				devicesCD.put(device.getString("DeviceName"), device.getInt("DeviceId", -1));
			}
			bj = new ConfirmDevices(devicesCD, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 6: // RequestDeviceComponents
			int deviceIdRDC = jo.getInt("DeviceId", -1);
			bj = new RequestDeviceComponents(deviceIdRDC, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
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
			bj = new SendDeviceComponents(deviceIdSDC, componentsSDC, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 8: // ConfirmDeviceComponents
			int deviceIdCDC = jo.getInt("DeviceId", -1);
			HashMap<String, Integer> componentsCDC = new HashMap<>(); // name / id
			JsonArray compCDC = jo.getJsonArray("Components");
			for (int i = 0; i < compCDC.size(); i++) {
				JsonObject device = compCDC.getJsonObject(i);
				componentsCDC.put(device.getString("ComponentName"), device.getInt("DeviceComponentId", -1));
			}
			bj = new ConfirmDeviceComponents(deviceIdCDC, componentsCDC, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 9: // SendValue
			int deviceIdSV = jo.getInt("DeviceId", -1);
			int deviceComponentIdSV = jo.getInt("DeviceComponentId", -1);
			double valueSV = jo.getJsonNumber("Value").doubleValue();
			long timestampSV = jo.getJsonNumber("Timestamp").longValue();
			bj = new SendValue(deviceIdSV, deviceComponentIdSV, valueSV, timestampSV, headerConnectorID, 
					headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 10: // ConfirmValue
			int deviceIdCV = jo.getInt("DeviceId", -1);
			int deviceComponentIdCV = jo.getInt("DeviceComponentId", -1);
			bj = new ConfirmValue(deviceIdCV, deviceComponentIdCV, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 11:
			int deviceIdRV = jo.getInt("DeviceId", -1);
			int deviceComponentIdRV = jo.getInt("DeviceComponentId", -1);
			bj = new RequestValue(deviceIdRV, deviceComponentIdRV, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 12: // SendNotification
			int deviceIdSN = jo.getInt("DeviceId", -1);
			int deviceComponentIdSN = jo.getInt("DeviceComponentId", -1);
			int notificationType = jo.getInt("NotificationType", -1);
			long timestampSN = jo.getJsonNumber("Timestamp").longValue();
			String notificationStringSN = jo.getString("NotificationText", "");
			bj = new SendNotification(deviceIdSN, deviceComponentIdSN, notificationType, timestampSN, notificationStringSN, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 50: // UserRequestAllDevices
			bj = new RequestAllDevices(headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp, true);
			break;
		case 51: // UserSendDevices
			bj = new UserSendDevices(headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 52: // UserRequestDeviceComponents
			HashSet<Integer> devIdSetURDC = new HashSet<>();
			JsonArray devIdsURDC = jo.getJsonArray("Components");
			for (int i = 0; i < devIdsURDC.size(); i++) {
				JsonObject dekoId = devIdsURDC.getJsonObject(i);
				devIdSetURDC.add(dekoId.getInt("DeviceIds"));
			}
			bj = new UserRequestDeviceComponents(devIdSetURDC, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
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
			bj = new UserSendDeviceComponent(jo.getInt("DeviceId"), decoSetUSDC, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
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
	 * @param seqNr the seq nr
	 * @param ackNr the ack nr
	 * @param status the status
	 * @param timestamp the timestamp
	 * @return JsonObjectBuilder containing the Message Header
	 */
	private static JsonObjectBuilder addHeader(int messageType, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		JsonObjectBuilder header = Json.createObjectBuilder();
		header.add("MessageType", messageType);
		header.add("ConnectorId", connectorId);
		header.add("SequenceNr", seqNr);
		header.add("AcknowledgeNr", ackNr);
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
			job.add("Header", addHeader(0, multi.getConnectorId(), multi.getSeqenceNr(), multi.getAcknowledgeId(), multi.getStatus(), multi.getTimestamp()));
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
			job.add("Header", addHeader(1, rc.getConnectorId(), rc.getSeqenceNr(), rc.getAcknowledgeId(), rc.getStatus(), rc.getTimestamp()));
			job.add("ConnectorName", rc.getConnectorName());
			if (rc.getPassword() != null) {
				job.add("Password", rc.getPassword());
			}
			break;
		case CONFIRMCONNECTION:
			ConfirmConnection cc = (ConfirmConnection)message;
			job.add("Header", addHeader(2, cc.getConnectorId(), cc.getSeqenceNr(), cc.getAcknowledgeId(), cc.getStatus(), cc.getTimestamp()));
			job.add("Password", cc.getPassword());
			job.add("ConnectorId", cc.getConnectorId());
			break;
		case REQUESTALLDEVICES:
			RequestAllDevices rad = (RequestAllDevices)message;
			job.add("Header", addHeader(3, rad.getConnectorId(), rad.getSeqenceNr(), rad.getAcknowledgeId(), rad.getStatus(), rad.getTimestamp()));
			break;
		case SENDDEVICES:
			SendDevices sd = (SendDevices)message;
			job.add("Header", addHeader(4, sd.getConnectorId(), sd.getSeqenceNr(), sd.getAcknowledgeId(), sd.getStatus(), sd.getTimestamp()));
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
			job.add("Header", addHeader(5, cd.getConnectorId(), cd.getSeqenceNr(), cd.getAcknowledgeId(), cd.getStatus(), cd.getTimestamp()));
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
			job.add("Header", addHeader(6, rdc.getConnectorId(), rdc.getSeqenceNr(), rdc.getAcknowledgeId(), rdc.getStatus(), rdc.getTimestamp()));
			job.add("DeviceId", rdc.getDeviceId());
			break;
		case SENDDEVICECOMPONENTS:
			SendDeviceComponents sdc = (SendDeviceComponents)message;
			job.add("Header", addHeader(7, sdc.getConnectorId(), sdc.getSeqenceNr(), sdc.getAcknowledgeId(), sdc.getStatus(), sdc.getTimestamp()));
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
			job.add("Header", addHeader(8, cdc.getConnectorId(), cdc.getSeqenceNr(), cdc.getAcknowledgeId(), cdc.getStatus(), cdc.getTimestamp()));
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
			job.add("Header", addHeader(9, sv.getConnectorId(), sv.getSeqenceNr(), sv.getAcknowledgeId(), sv.getStatus(), sv.getTimestamp()));
			job.add("DeviceId", sv.getDeviceId());
			job.add("DeviceComponentId", sv.getDeviceComponentId());
			job.add("Value", sv.getValue());
			job.add("Timestamp", sv.getValueTimestamp());
			break;
		case CONFIRMVALUE:
			ConfirmValue cv = (ConfirmValue)message;
			job.add("Header", addHeader(10, cv.getConnectorId(), cv.getSeqenceNr(), cv.getAcknowledgeId(), cv.getStatus(), cv.getTimestamp()));
			job.add("DeviceId", cv.getDeviceId());
			job.add("DeviceComponentId", (cv.getDeviceComponentId()));
			break;
		case REQUESTVALUE:
			RequestValue rv = (RequestValue)message;
			job.add("Header", addHeader(11, rv.getConnectorId(), rv.getSeqenceNr(), rv.getAcknowledgeId(), rv.getStatus(), rv.getTimestamp()));
			job.add("DeviceId", rv.getDeviceId());
			job.add("DeviceComponentId", rv.getDeviceComponentId());
			break;
		case SENDNOTIFICATION:
			SendNotification sn = (SendNotification)message;
			job.add("Header", addHeader(12, sn.getConnectorId(), sn.getSeqenceNr(), sn.getAcknowledgeId(), sn.getStatus(), sn.getTimestamp()));
			job.add("DeviceId", sn.getDeviceId());
			job.add("DeviceComponentId", sn.getDeviceComponentId());
			job.add("NotificationType", sn.getNotificationType());
			job.add("Timestamp", sn.getNotificationTimestamp());
			job.add("NotificationText", sn.getNotificationText());
			break;
		case USERREQUESTALLDEVICES:
			RequestAllDevices urad = (RequestAllDevices)message;
			job.add("Header", addHeader(50, urad.getConnectorId(), urad.getSeqenceNr(), urad.getAcknowledgeId(), urad.getStatus(), urad.getTimestamp()));
			job.add("IsUserRequest", urad.isUserRequest());
			break;
		case USERSENDDEVICES:
			UserSendDevices usd = (UserSendDevices)message;
			job.add("Header", addHeader(51, usd.getConnectorId(), usd.getSeqenceNr(), usd.getAcknowledgeId(), usd.getStatus(), usd.getTimestamp()));
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
			job.add("Header", addHeader(52, urdc.getConnectorId(), urdc.getSeqenceNr(), urdc.getAcknowledgeId(), urdc.getStatus(), urdc.getTimestamp()));
			JsonArrayBuilder devicesURDC = Json.createArrayBuilder();
			for (Integer devIdURDC : urdc.getDeviceIds()) {
				devicesURDC.add(devIdURDC.intValue());
			}
			job.add("DeviceIds", devicesURDC);
			break;
		case USERSENDDEVICECOMPONENT:
			UserSendDeviceComponent usdc = (UserSendDeviceComponent)message;
			job.add("Header", addHeader(52, usdc.getConnectorId(), usdc.getSeqenceNr(), usdc.getAcknowledgeId(), usdc.getStatus(), usdc.getTimestamp()));
			job.add("DeviceId", usdc.getDeviceId());
			JsonArrayBuilder decosUSDC = Json.createArrayBuilder();
			JsonObjectBuilder decoUSDC;
			for (DeviceComponents deco : usdc.getComponentList()) {
				decoUSDC = Json.createObjectBuilder();
				decoUSDC.add("DeviceComponentId", deco.getDeviceComponentId());
				decoUSDC.add("Description", deco.getDescription());
				decoUSDC.add("ComponentName", deco.getComponentName());
				decoUSDC.add("Value", deco.getValue());
				decoUSDC.add("Timestamp", deco.getTimestamp());
				decoUSDC.add("Status", deco.getStatus());
				decoUSDC.add("Aktor", deco.isActor());
				decoUSDC.add("Unit", deco.getUnit());
				decosUSDC.add(decoUSDC);
			}
			job.add("Components", decosUSDC);
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
