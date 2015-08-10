package de.bo.aid.boese.json;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.json.*;

public class BoeseJson {
	protected MessageType messageType = null;
	protected int connectorId = -1;
	protected int seqNr = -1;
	protected int ackNr = -1;
	protected int status = -1;
	protected long timestamp = -1;
	
	public enum MessageType {
		REQUESTCONNECTION, CONFIRMCONNECTION, REQUESTALLDEVICES, SENDDEVICES, CONFIRMDEVICES, REQUESTDEVICECOMPONENTS,
		SENDDEVICECOMPONENTS, CONFIRMDEVICECOMPONENTS, SENDVALUE, CONFIRMVALUE, 
	}

	public MessageType getType() {
		return messageType;
	}
	
	public int getConnectorId() {
		return connectorId;
	}
	
	public int getSeqenceNr() {
		return seqNr;
	}
	
	public int getAcknolageId() {
		return ackNr;
	}
	
	public int getStatus() {
		return status;
	}
	
	public long getTimestamp() {
		return status;
	}
	
	protected BoeseJson(MessageType messageType, int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		this.messageType = messageType;
		this.connectorId = connectorId;
		this.seqNr = seqNr;
		this.ackNr = ackNr;
		this.status = status;
		this.timestamp = timestamp;
	}
	
	public static BoeseJson readMessage(InputStream is) {
		JsonReader jr = Json.createReader(is);
		JsonObject jo = jr.readObject();
		jr.close();
		
		if(jo.getJsonNumber("MessageType") == null) {
			return null;
		} else {}
		BoeseJson bj = null;
		
		int headerConnectorID, headerSeqNr, headerAckNr;
		int headerStatus;
		long headerTimestamp;

		JsonObject header =  jo.getJsonObject("Header");
		headerConnectorID = header.getInt("ConnectorId", -1);
		headerSeqNr = header.getInt("SequenceNr", -1);
		headerAckNr = header.getInt("AcknolageNr", -1);
		headerStatus = header.getInt("Status", -1);
		headerTimestamp = header.getInt("Status", -1);
		
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
			bj = new SendDeviceComponents(componentsSDC, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
			break;
		case 8: // ConfirmDeviceComponents
			int deviceIdCDC = jo.getInt("DeviceId", -1);
			HashMap<String, Integer> componentsCDC = new HashMap<>(); // name / id
			JsonArray compCDC = jo.getJsonArray("Components");
			for (int i = 0; i < compCDC.size(); i++) {
				JsonObject device = compCDC.getJsonObject(i);
				componentsCDC.put(device.getString("ComponentName"), device.getInt("ComponentId", -1));
			}
			bj = new ConfirmDeviceComponents(componentsCDC, headerConnectorID, headerSeqNr, headerAckNr, headerStatus, headerTimestamp);
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
		default:
			break;
		}
		return bj;
	}
	
	public static boolean parseMessage(BoeseJson message, OutputStream os) {
		boolean output = false;
		JsonObjectBuilder job = Json.createObjectBuilder();
//		switch (message.getType()) {
//		case SENDVALUE:
//			job.add("MessageType", 1);
//			JsonArrayBuilder values = Json.createArrayBuilder();
//			SendValue sv = (SendValue)message;
//			job.add("DeviceComponentId", sv.getDeviceComponentId());
//			Iterator<Long> itSV = sv.getTimestamps().iterator();
//			while (itSV.hasNext()) {
//				Long timestamp = itSV.next();
//				double value = sv.getValue(timestamp);
//				JsonObjectBuilder valueSV = Json.createObjectBuilder();
//				valueSV.add("Timestamp", timestamp.longValue());
//				valueSV.add("Value", value);
//				values.add(valueSV);
//			}
//			job.add("Values", values);
//			break;
//		case REQUESTVALUE:
//			job.add("MessageType", 2);
//			job.add("DeviceComponentId", ((RequestValue)message).getDeviceComponentId());
//			job.add("TsBegin", ((RequestValue)message).getBegin());
//			job.add("TsEnd", ((RequestValue)message).getEnd());
//			break;
//		case ADDDEVICE:
//			job.add("MessageType", 3);
//			JsonArrayBuilder components = Json.createArrayBuilder();
//			AddDevice ad = (AddDevice)message;
//			job.add("Alias", ad.getAlias());
//			Iterator<DeviceComponent> itDC = ad.getComponents().iterator();
//			while (itDC.hasNext()) {
//				DeviceComponent dc = itDC.next();
//				JsonObjectBuilder componentsAD = Json.createObjectBuilder();
//				componentsAD.add("Name", dc.getName());
//				componentsAD.add("Description", dc.getDescription());
//				componentsAD.add("UnitId", dc.getUnitId());
//				components.add(components);
//			}
//			job.add("Components", components);
//			break;
//		case REMOVEDEVICE:
//			job.add("MessageType", 4);
//			job.add("IdDevice", ((RemoveDevice)message).getDeviceId());
//			break;
//		case SENDDEVICE:
//			job.add("MessageType", 5);
//			JsonArrayBuilder devicecsSD = Json.createArrayBuilder();
//			SendDevice sd = (SendDevice)message;
//			Iterator<Integer> itSD = sd.getComponentIDs().iterator();
//			while (itSD.hasNext()) {
//				int deviceIdSD = itSD.next().intValue();
//				String componentNameSD = sd.getComponentName(deviceIdSD);
//				JsonObjectBuilder componentSD = Json.createObjectBuilder();
//				componentSD.add("ComponentId", deviceIdSD);
//				componentSD.add("ComponentName", componentNameSD);
//				devicecsSD.add(componentSD);
//			}
//			job.add("Devices", devicecsSD);
//			break;
//		case SENDDEVICECOMPONENT:
//			job.add("MessageType", 6);
//			JsonArrayBuilder componentsSDC = Json.createArrayBuilder();
//			SendDeviceComponent sdc = (SendDeviceComponent)message;
//			Iterator<Integer> itSDC = sdc.getComponentIds().iterator();
//			while (itSDC.hasNext()) {
//				int componentIdSDC = itSDC.next().intValue();
//				String componentNameSDC = sdc.getComponentName(componentIdSDC);
//				JsonObjectBuilder componentSDC = Json.createObjectBuilder();
//				componentSDC.add("ComponentId", componentIdSDC);
//				componentSDC.add("ComponentName", componentNameSDC);
//				componentsSDC.add(componentSDC);
//			}
//			job.add("DeviceComponents", componentsSDC);
//			break;
//		case REQUESTDEVICE:
//			job.add("MessageType", 7);
//			job.add("ConnectorId", ((RequestDevice)message).getConnectorId());
//			job.add("ZoneId", ((RequestDevice)message).getZoneId());
//			job.add("ServiceId", ((RequestDevice)message).getServiceId());
//			break;
//		case SENDLIST:
//			job.add("MessageType", 8);
//			JsonArrayBuilder valuesSL = Json.createArrayBuilder();
//			SendList sl = (SendList)message;
//			job.add("DeviceName", sl.getDeviceName());
//			job.add("ServiceId", sl.getDeviceName());
//			Iterator<Integer> itSL = sl.getDeviceIds().iterator();
//			while (itSL.hasNext()) {
//				int deviceIdSL = itSL.next().intValue();
//				String descriptionSL = sl.getDeviceDescription(deviceIdSL);
//				JsonObjectBuilder valueSL = Json.createObjectBuilder();
//				valueSL.add("DeviceId", deviceIdSL);
//				valueSL.add("Description", descriptionSL);
//				valuesSL.add(valueSL);
//			}
//			job.add("Devices", valuesSL);
//			break;
//		case REQUESTCONNECTION:
//			job.add("MessageType", 9);
//			job.add("Status", ((RequestConnection)message).getStatus());
//			job.add("Name", ((RequestConnection)message).getName());
//			job.add("Password", ((RequestConnection)message).getPassword());
//			break;
//		default:
//			break;
//		}
		JsonWriter writer = Json.createWriter(os);
		writer.writeObject(job.build());
		writer.close();
		return output;
	}
}
