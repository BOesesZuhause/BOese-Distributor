package de.bo.aid.boese.json;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.json.*;

/**
 * This class is the base for all Boese Json Messages.
 * It also provides methods to parse Json to Boese Message and vies versa.
 */
public class BoeseJson {
	protected MessageType messageType = null;
	protected int connectorId = -1;
	protected int seqNr = -1;
	protected int ackNr = -1;
	protected int status = -1;
	protected long timestamp = -1;
	
	/**
	 * Enumeration with all available message types
	 */
	public enum MessageType {
		REQUESTCONNECTION, CONFIRMCONNECTION, REQUESTALLDEVICES, SENDDEVICES, CONFIRMDEVICES, REQUESTDEVICECOMPONENTS,
		SENDDEVICECOMPONENTS, CONFIRMDEVICECOMPONENTS, SENDVALUE, CONFIRMVALUE
	}

	/**
	 * Getter for the message type
	 * @return the message type
	 */
	public MessageType getType() {
		return messageType;
	}
	
	/**
	 * Getter for the connector ID
	 * @return the connector ID 
	 */
	public int getConnectorId() {
		return connectorId;
	}
	
	/**
	 * Getter for the sequence number
	 * @return the sequence number
	 */
	public int getSeqenceNr() {
		return seqNr;
	}
	
	/**
	 * Getter for the acknowledge number
	 * @return the acknowledge number
	 */
	public int getAcknowledgeId() {
		return ackNr;
	}
	
	public int getStatus() {
		return status;
	}
	
	/**
	 * Getter for the status flag
	 * @return the status flag
	 */
	public long getTimestamp() {
		return status;
	}
	
	/**
	 * Protected constructor for child classes only
	 * @param messageType
	 * @param connectorId
	 * @param seqNr
	 * @param ackNr
	 * @param status
	 * @param timestamp
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
		JsonObject jo = jr.readObject();
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
		case 1: // RequestConnection
			String connectorNameRC = jo.getString("ConnectorName");
			bj = new RequestConnection(connectorNameRC, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 2: // ConfirmConnection
			String passwordCC = jo.getString("Password");
			bj = new ConfirmConnection(passwordCC, headerConnectorID, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
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
				componentsSDC.add(new DeviceComponents(components.getInt("DeviceComponentId", -1), components.getString("ComponentName"), 
						components.getJsonNumber("Value").doubleValue(), components.getJsonNumber("Timestamp").longValue()));
			}
			bj = new SendDeviceComponents(deviceIdSDC, componentsSDC, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 8: // ConfirmDeviceComponents
			int deviceIdCDC = jo.getInt("DeviceId", -1);
			HashMap<String, Integer> componentsCDC = new HashMap<>(); // name / id
			JsonArray compCDC = jo.getJsonArray("Components");
			for (int i = 0; i < compCDC.size(); i++) {
				JsonObject device = compCDC.getJsonObject(i);
				componentsCDC.put(device.getString("ComponentName"), device.getInt("ComponentId", -1));
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
		default:
			break;
		}
		return bj;
	}
	
	/**
	 * Private method to create a JsonObject with the Json header
	 * @param messageType
	 * @param connectorId
	 * @param seqNr
	 * @param ackNr
	 * @param status
	 * @param timestamp
	 * @return JsonObjectBuilder containing the Message Header
	 */
	private static JsonObjectBuilder addHeader(int messageType, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		JsonObjectBuilder header = Json.createObjectBuilder();
		header.add("MessageType", messageType);
		header.add("ConnectorId", connectorId);
		header.add("SequenceNr", seqNr);
		header.add("AcknowledgeNr", connectorId);
		header.add("Status", ackNr);
		header.add("Timestamp", timestamp);
		return header;
	}
	
	/**
	 * Writes a BoeseJson Message to a given output stream
	 * @param message the BoeseJson Message
	 * @param os the OutputStream to write in
	 * @return true, if writing was successful
	 */
	public static boolean parseMessage(BoeseJson message, OutputStream os) {
		boolean output = true;
		JsonObjectBuilder job = Json.createObjectBuilder();
		switch (message.getType()) {
		case REQUESTCONNECTION:
			RequestConnection rc = (RequestConnection)message;
			job.add("Header", addHeader(3, rc.getConnectorId(), rc.getSeqenceNr(), rc.getAcknowledgeId(), rc.getStatus(), rc.getTimestamp()));
			job.add("ConnectorName", rc.getConnectorName());
			break;
		case CONFIRMCONNECTION:
			ConfirmConnection cc = (ConfirmConnection)message;
			job.add("Header", addHeader(2, cc.getConnectorId(), cc.getSeqenceNr(), cc.getAcknowledgeId(), cc.getStatus(), cc.getTimestamp()));
			job.add("Password", cc.getPassword());
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
			job.add("Components", devicesSDAr);
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
			job.add("Components", devicesCDAr);
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
				deviceComponentsSDCAr.add(deviceComponentSDC);
			}
			job.add("Components", deviceComponentsSDCAr);
			break;
		case CONFIRMDEVICECOMPONENTS:
			ConfirmDeviceComponents cdc = (ConfirmDeviceComponents)message;
			job.add("Header", addHeader(8, cdc.getConnectorId(), cdc.getSeqenceNr(), cdc.getAcknowledgeId(), cdc.getStatus(), cdc.getTimestamp()));
			job.add("DeviceId", cdc.getDeviceId());
			JsonArrayBuilder componentsCDCAr = Json.createArrayBuilder();
			JsonObjectBuilder componentCDC;
			for (Entry<String, Integer> entry : cdc.getComponents().entrySet()) {
				componentCDC = Json.createObjectBuilder();
				componentCDC.add("ComponentName", entry.getKey());
				componentCDC.add("ComponentId", entry.getValue());
				componentsCDCAr.add(componentCDC);
			}
			job.add("Components", componentsCDCAr);
			break;
		case SENDVALUE:
			SendValue sv = (SendValue)message;
			job.add("Header", addHeader(9, sv.getConnectorId(), sv.getSeqenceNr(), sv.getAcknowledgeId(), sv.getStatus(), sv.getTimestamp()));
			job.add("DeviceId", sv.getDeviceId());
			job.add("DeviceComponentId", sv.getDeviceComponentId());
			job.add("Value", sv.getValue());
			job.add("Timestamp", sv.getTimestamp());
			break;
		case CONFIRMVALUE:
			ConfirmValue cv = (ConfirmValue)message;
			job.add("Header", addHeader(10, cv.getConnectorId(), cv.getSeqenceNr(), cv.getAcknowledgeId(), cv.getStatus(), cv.getTimestamp()));
			job.add("DeviceId", ((ConfirmValue)message).getDeviceId());
			job.add("DeviceComponentId", ((SendValue)message).getDeviceComponentId());
			break;
		default:
			break;
		}
		JsonWriter writer = Json.createWriter(os);
		writer.writeObject(job.build());
		writer.close();
		return output;
	}
}
