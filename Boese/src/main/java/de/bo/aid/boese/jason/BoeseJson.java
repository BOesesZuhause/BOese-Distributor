package de.bo.aid.boese.jason;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.json.*;

public class BoeseJson {
	protected MessageType messageType = null;
	
	public enum MessageType {
		SENDVALUE, REQUESTVALUE, ADDDEVICE, REMOVEDEVICE, SENDDEVICE, SENDDEVICECOMPONENT, REQUESTLIST, SENDLIST, REQUESTCONNECTION;
	}

	public MessageType getType() {
		return messageType;
	}
	
	public static BoeseJson readMessage(InputStream is) {
		JsonReader jr = Json.createReader(is);
		JsonObject jo = jr.readObject();
		jr.close();
		
		if(jo.getJsonNumber("MessageType") == null) {
			return null;
		} else {}
		BoeseJson bj = null;
		
		switch(jo.getInt("MessageType")) {
		case 1:	// SENDVALUE
			if ((jo.getJsonNumber("DeviceComponentId") != null) && (jo.getJsonArray("Values") != null)) {
				int idDeviceComponent = jo.getInt("DeviceComponentId");
				HashMap<Long, Double> valuesMap = new HashMap<>();
				JsonArray values = jo.getJsonArray("Values");
				for (int i = 0; i < values.size(); i++) {					
					JsonObject o = values.getJsonObject(i);
					if ((o.getJsonNumber("Timestamp") != null) && (o.getJsonNumber("Value") != null)) {
						valuesMap.put(o.getJsonNumber("Timestamp").longValue(), o.getJsonNumber("Value").doubleValue());
					} else {}
				}
				bj = new SendValue(idDeviceComponent, valuesMap);
			} else {}
			break;
		case 2:	// REQUESTVALUE
			if ((jo.getJsonNumber("DeviceComponentId") != null) && (jo.getJsonNumber("TsBegin") != null) && (jo.getJsonNumber("TsEnd") != null)) {
				bj = new RequestValue(jo.getInt("DeviceComponentId"), jo.getJsonNumber("TsBegin").longValue(), jo.getJsonNumber("TsEnd").longValue());
			} else {}
			break;
		case 3:	// ADDDEVICE
			if ((jo.getJsonString("Alias") != null)) {
				String alias = jo.getString("Alias");
				if ((jo.getJsonArray("Components") != null)) {
					Set<DeviceComponent> componentsSet = new HashSet<DeviceComponent>();
					JsonArray components = jo.getJsonArray("Components");
					for (int i = 0; i < components.size(); i++) {
						JsonObject o = components.getJsonObject(i);
						if ((o.getJsonString("Name") != null) && (o.getJsonString("Description") != null) && (o.getJsonNumber("UnitId") != null)) {
							componentsSet.add(new DeviceComponent(o.getString("Name"), o.getString("Description"), o.getInt("UnitId")));
						} else {}
					}
					bj = new AddDevice(alias, componentsSet);
				} else {
					bj = new AddDevice(alias);
				}
			} else {}
			break;
		case 4:	// REMOVEDEVICE
			if (jo.getJsonNumber("IdDevice") != null) {
				bj = new RemoveDevice(jo.getInt("IdDevice"));
			} else {}
			break;
		case 5:	// SENDDEVICE
			if ((jo.getJsonNumber("DeviceId") != null) && (jo.getJsonArray("Devices") != null)) {
				int deviceId = jo.getInt("DeviceId");
				HashMap<Integer, String> devicesMap = new HashMap<>();
				JsonArray devices = jo.getJsonArray("Devices");
				for (int i = 0; i < devices.size(); i++) {
					JsonObject o = devices.getJsonObject(i);
					if ((o.getJsonNumber("ComponentId") != null) && (o.getJsonString("ComponentName") != null)) {
						devicesMap.put(o.getInt("ComponentId"), o.getString("ComponentName"));
					} else {}
				}
				bj = new SendDevice(deviceId, devicesMap);
			} else {}
			break;
		case 6:	// SENDDEVICECOMPONENT
			if (jo.getJsonArray("DeviceComponents") != null) {
				HashMap<Integer, String> deviceComponentsMap = new HashMap<>();
				JsonArray deviceComponents = jo.getJsonArray("DeviceComponents");
				for (int i = 0; i < deviceComponents.size(); i++) {
					JsonObject o = deviceComponents.getJsonObject(i);
					if ((o.getJsonNumber("ComponentId") != null) && (o.getJsonString("ComponentName") != null)) {
						deviceComponentsMap.put(o.getInt("ComponentId"), o.getString("ComponentName"));
					} else {}
				}
				bj = new SendDeviceComponent(deviceComponentsMap);
			} else {}
			break;
		case 7:	// REQUESTLIST
			if ((jo.getJsonNumber("ConnectorId") != null) && (jo.getJsonNumber("ZoneId") != null) && (jo.getJsonNumber("ServiceId") != null)) {
				bj = new RequestList(jo.getInt("ConnectorId"), jo.getInt("ZoneId"), jo.getInt("ServiceId"));
			} else {}
			break;
		case 8:	// SENDLIST
			if ((jo.getJsonString("DeviceName") != null) && (jo.getJsonNumber("ServiceId") != null) && (jo.getJsonArray("Devices") != null)) {
				String deviceName = jo.getString("DeviceName");
				int serviceId = jo.getInt("ServiceId");
				HashMap<Integer, String> devicesMap = new HashMap<>();
				JsonArray devices = jo.getJsonArray("Devices");
				for (int i = 0; i < devices.size(); i++) {
					JsonObject o = devices.getJsonObject(i);
					if ((o.getJsonNumber("DeviceId") != null) && (o.getJsonString("Description") != null)) {
						devicesMap.put(o.getInt("DeviceId"), o.getString("Description"));
					} else {}
				}
				bj = new SendList(deviceName, serviceId, devicesMap);
			} else {}
			break;
		case 9:	// REQUESTCONNECTION
			if ((jo.getJsonNumber("Status") != null) && (jo.getJsonString("Name") != null)) {
				bj = new RequestConnection(jo.getInt("Status"), jo.getString("Name"), jo.getString("Password"));
			} 
			break;				
		default:
			break;
		}
		return bj;
	}
	
	public static boolean parseMessage(BoeseJson message, OutputStream os) {
		boolean output = false;
		JsonObjectBuilder job = Json.createObjectBuilder();
		switch (message.getType()) {
		case SENDVALUE:
			job.add("MessageType", 1);
			JsonArrayBuilder values = Json.createArrayBuilder();
			SendValue sv = (SendValue)message;
			job.add("DeviceComponentId", sv.getDeviceComponentId());
			Iterator<Long> itSV = sv.getTimestamps().iterator();
			while (itSV.hasNext()) {
				Long timestamp = itSV.next();
				double value = sv.getValue(timestamp);
				JsonObjectBuilder valueSV = Json.createObjectBuilder();
				valueSV.add("Timestamp", timestamp.longValue());
				valueSV.add("Value", value);
				values.add(valueSV);
			}
			job.add("Values", values);
			break;
		case REQUESTVALUE:
			job.add("MessageType", 2);
			job.add("DeviceComponentId", ((RequestValue)message).getDeviceComponentId());
			job.add("TsBegin", ((RequestValue)message).getBegin());
			job.add("TsEnd", ((RequestValue)message).getEnd());
			break;
		case ADDDEVICE:
			job.add("MessageType", 3);
			JsonArrayBuilder components = Json.createArrayBuilder();
			AddDevice ad = (AddDevice)message;
			job.add("Alias", ad.getAlias());
			Iterator<DeviceComponent> itDC = ad.getComponents().iterator();
			while (itDC.hasNext()) {
				DeviceComponent dc = itDC.next();
				JsonObjectBuilder componentsAD = Json.createObjectBuilder();
				componentsAD.add("Name", dc.getName());
				componentsAD.add("Description", dc.getDescription());
				componentsAD.add("UnitId", dc.getUnitId());
				components.add(components);
			}
			job.add("Components", components);
			break;
		case REMOVEDEVICE:
			job.add("MessageType", 4);
			job.add("IdDevice", ((RemoveDevice)message).getDeviceId());
			break;
		case SENDDEVICE:
			job.add("MessageType", 5);
			JsonArrayBuilder devicecsSD = Json.createArrayBuilder();
			SendDevice sd = (SendDevice)message;
			Iterator<Integer> itSD = sd.getComponentIDs().iterator();
			while (itSD.hasNext()) {
				int deviceIdSD = itSD.next().intValue();
				String componentNameSD = sd.getComponentName(deviceIdSD);
				JsonObjectBuilder componentSD = Json.createObjectBuilder();
				componentSD.add("ComponentId", deviceIdSD);
				componentSD.add("ComponentName", componentNameSD);
				devicecsSD.add(componentSD);
			}
			job.add("Devices", devicecsSD);
			break;
		case SENDDEVICECOMPONENT:
			job.add("MessageType", 6);
			JsonArrayBuilder componentsSDC = Json.createArrayBuilder();
			SendDeviceComponent sdc = (SendDeviceComponent)message;
			Iterator<Integer> itSDC = sdc.getComponentIds().iterator();
			while (itSDC.hasNext()) {
				int componentIdSDC = itSDC.next().intValue();
				String componentNameSDC = sdc.getComponentName(componentIdSDC);
				JsonObjectBuilder componentSDC = Json.createObjectBuilder();
				componentSDC.add("ComponentId", componentIdSDC);
				componentSDC.add("ComponentName", componentNameSDC);
				componentsSDC.add(componentSDC);
			}
			job.add("DeviceComponents", componentsSDC);
			break;
		case REQUESTLIST:
			job.add("MessageType", 7);
			job.add("ConnectorId", ((RequestList)message).getConnectorId());
			job.add("ZoneId", ((RequestList)message).getZoneId());
			job.add("ServiceId", ((RequestList)message).getServiceId());
			break;
		case SENDLIST:
			job.add("MessageType", 8);
			JsonArrayBuilder valuesSL = Json.createArrayBuilder();
			SendList sl = (SendList)message;
			job.add("DeviceName", sl.getDeviceName());
			job.add("ServiceId", sl.getDeviceName());
			Iterator<Integer> itSL = sl.getDeviceIds().iterator();
			while (itSL.hasNext()) {
				int deviceIdSL = itSL.next().intValue();
				String descriptionSL = sl.getDeviceDescription(deviceIdSL);
				JsonObjectBuilder valueSL = Json.createObjectBuilder();
				valueSL.add("DeviceId", deviceIdSL);
				valueSL.add("Description", descriptionSL);
				valuesSL.add(valueSL);
			}
			job.add("Devices", valuesSL);
			break;
		case REQUESTCONNECTION:
			job.add("MessageType", 9);
			job.add("Status", ((RequestConnection)message).getStatus());
			job.add("Name", ((RequestConnection)message).getName());
			job.add("Password", ((RequestConnection)message).getPassword());
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
