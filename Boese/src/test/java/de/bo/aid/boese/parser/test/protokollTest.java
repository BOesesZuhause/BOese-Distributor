/*
 * 
 */
package de.bo.aid.boese.parser.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.BoeseJson.MessageType;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.ConfirmDeviceComponents;
import de.bo.aid.boese.json.ConfirmDevices;
import de.bo.aid.boese.json.ConfirmStatus;
import de.bo.aid.boese.json.ConfirmValue;
import de.bo.aid.boese.json.DeviceComponents;
import de.bo.aid.boese.json.HeartBeatMessage;
import de.bo.aid.boese.json.MultiMessage;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.RequestDeviceComponents;
import de.bo.aid.boese.json.RequestValue;
import de.bo.aid.boese.json.RuleJSON;
import de.bo.aid.boese.json.SendDeviceComponents;
import de.bo.aid.boese.json.SendDevices;
import de.bo.aid.boese.json.SendNotification;
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
import de.bo.aid.boese.json.ZoneJSON;
import de.bo.aid.boese.main.model.TempComponent;
import de.bo.aid.boese.main.model.TempConnector;
import de.bo.aid.boese.main.model.TempDevice;

// TODO: Auto-generated Javadoc
/**
 * The Class protokollTest.
 */
public class protokollTest {
	
	/**
	 * Parses the multi.
	 */
	@Test
	public void parseMulti(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":0,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Messages\":["
				
				+"{"
				+ "\"Header\":{"
				+ "\"MessageType\":8,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"Components\":"
				+ "[{"
				+ "\"DeviceComponentId\":5,"
				+ "\"ComponentName\":\"Horst\""
				+ "}]"
				+ "},"
				
				+"{"
				+ "\"Header\":{"
				+ "\"MessageType\":10,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5"
				+ "}"

				+ "]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		MultiMessage multi = new MultiMessage(1, 0, 111222334);
		multi.addMessage(new ConfirmValue(123, 5,1, 0, 111222334));
		
		HashMap<String, Integer> components = new HashMap<>();
		components.put("Horst", 5);
		multi.addMessage(new ConfirmDeviceComponents(123, components, 1, 0, 111222334));

		BoeseJson.parseMessage(multi, os);


		try {
			JSONAssert.assertEquals(os.toString(), message, false);
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	/**
	 * Read multi.
	 */
	@Test
	public void readMulti(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":0,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Messages\":["
				
				
				+"{"
				+ "\"Header\":{"
				+ "\"MessageType\":10,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5"
				+ "},"
				
				+"{"
				+ "\"Header\":{"
				+ "\"MessageType\":8,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"Components\":"
				+ "[{"
				+ "\"DeviceComponentId\":5,"
				+ "\"ComponentName\":\"Horst\""
				+ "}]"
				+ "}"
				
				+ "]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		try {
			JSONAssert.assertEquals(os.toString(), message, false);
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}		
	}
	
	/**
	 * Parses the request connection.
	 */
	@Test
	public void parseRequestConnection(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":1,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"ConnectorName\":\"Konnektor1\","
				+ "\"Password\":\"sicher!\","
				+ "\"IsUserConnector\":false"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		RequestConnection recCon = new RequestConnection("Konnektor1", "sicher!", 1, 0, 111222334, false);
		BoeseJson.parseMessage(recCon, os);
		assertEquals(os.toString(), message);			
	}
	
	/**
	 * Read request connection.
	 */
	@Test
	public void readRequestConnection(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":1,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"ConnectorName\":\"Konnektor1\","
				+ "\"Password\":\"sicher!\","
				+ "\"IsUserConnector\":false"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	/**
	 * Parses the confirm connection.
	 */
	@Test
	public void parseConfirmConnection(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":2,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Password\":\"geheim\","
				+ "\"ConnectorId\":1"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		ConfirmConnection confCon = new ConfirmConnection("geheim", 1, 0, 111222334);
		BoeseJson.parseMessage(confCon, os);
		assertEquals(os.toString(), message);			
	}
	
	/**
	 * Read confirm connection.
	 */
	@Test
	public void readConfirmConnection(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":2,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Password\":\"geheim\","
				+ "\"ConnectorId\":1"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	/**
	 * Parses the request all devices.
	 */
	@Test
	public void parseRequestAllDevices(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":3,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		RequestAllDevices reqAllDev = new RequestAllDevices(1, 0, 111222334);
		BoeseJson.parseMessage(reqAllDev, os);
		assertEquals(os.toString(), message);			
	}
	
	/**
	 * Read request all devices.
	 */
	@Test
	public void readRequestAllDevices(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":3,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	/**
	 * Parses the send devices.
	 */
	@Test
	public void parseSendDevices(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":4,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Devices\":[{"
				+ "\"DeviceName\":\"Igor\","
				+ "\"DeviceId\":-1"
				+ "}]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashMap<String, Integer> devices = new HashMap<>();
		devices.put("Igor", -1);
		SendDevices sendDev = new SendDevices(devices, 1, 0, 111222334);
		BoeseJson.parseMessage(sendDev, os);
		assertEquals(os.toString(), message);			
	}
	
	/**
	 * Read send devices.
	 */
	@Test
	public void readSendDevices(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":4,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Devices\":[{"
				+ "\"DeviceName\":\"Igor\","
				+ "\"DeviceId\":-1"
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	/**
	 * Parses the confirm devices.
	 */
	@Test
	public void parseConfirmDevices(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":5,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Devices\":[{"
				+ "\"DeviceName\":\"Igor\","
				+ "\"DeviceId\":123"
				+ "}]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashMap<String, Integer> devices = new HashMap<>();
		devices.put("Igor", 123);
		ConfirmDevices sendDev = new ConfirmDevices(devices, 1, 0, 111222334);
		BoeseJson.parseMessage(sendDev, os);
		assertEquals(os.toString(), message);			
	}
	
	/**
	 * Read confirm devices.
	 */
	@Test
	public void readConfirmDevices(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":5,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Devices\":[{"
				+ "\"DeviceName\":\"Igor\","
				+ "\"DeviceId\":123"
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	/**
	 * Parses the request device components.
	 */
	@Test
	public void parseRequestDeviceComponents(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":6,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		RequestDeviceComponents reqDevComp = new RequestDeviceComponents(123, 1, 0, 111222334);
		BoeseJson.parseMessage(reqDevComp, os);
		assertEquals(os.toString(), message);			
	}
	
	/**
	 * Read request device components.
	 */
	@Test
	public void readRequestDeviceComponents(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":6,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	/**
	 * Parses the send device components.
	 */
	//TODO Test mit mehreren Komponenten
	@Test
	public void parseSendDeviceComponents(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":7,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"Components\":"
				+ "[{"
				+ "\"DeviceComponentId\":-1,"
				+ "\"ComponentName\":\"Horst\","
				+ "\"Value\":42.1234,"
				+ "\"Timestamp\":111111111,"
				+ "\"Actor\":false"
				+ "}]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashSet<DeviceComponents> components = new HashSet<>();
		components.add(new DeviceComponents(-1, "Horst", 42.1234, 111111111, null, null, false));
		SendDeviceComponents reqDevComp = new SendDeviceComponents(123, components, 1, 0, 111222334);
		BoeseJson.parseMessage(reqDevComp, os);
		assertEquals(os.toString(), message);			
	}
	
	/**
	 * Read send device components.
	 */
	//TODO Test mit mehreren Komponenten
	@Test
	public void readSendDeviceComponents(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":7,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"Components\":"
				+ "[{"
				+ "\"DeviceComponentId\":-1,"
				+ "\"ComponentName\":\"Horst\","
				+ "\"Value\":42.1234,"
				+ "\"Timestamp\":111111111,"
				+ "\"Actor\":false"
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	/**
	 * Parses the confirm device components.
	 */
	//TODO Test mit mehreren Komponenten
	@Test
	public void parseConfirmDeviceComponents(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":8,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"Components\":"
				+ "[{"
				+ "\"DeviceComponentId\":5,"
				+ "\"ComponentName\":\"Horst\""
				+ "}]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashMap<String, Integer> components = new HashMap<>();
		components.put("Horst", 5);
		ConfirmDeviceComponents confDevComp = new ConfirmDeviceComponents(123, components, 1, 0, 111222334);
		BoeseJson.parseMessage(confDevComp, os);
		assertEquals(os.toString(), message);			
	}
	
	/**
	 * Read confirm device components.
	 */
	//TODO Test mit mehreren Komponenten
	@Test
	public void readConfirmDeviceComponents(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":8,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"Components\":"
				+ "[{"
				+ "\"DeviceComponentId\":5,"
				+ "\"ComponentName\":\"Horst\""
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	
	/**
	 * Parses the send value.
	 */
	@Test
	public void parseSendValue(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":9,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5,"
				+ "\"Value\":44.12,"
				+ "\"Timestamp\":111111114"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		SendValue sendVal = new SendValue(123, 5, 44.12, 111111114 , 1, 0, 111222334);
		BoeseJson.parseMessage(sendVal, os);
		assertEquals(os.toString(), message);			
	}
	
	/**
	 * Read send value.
	 */
	@Test
	public void readSendValue(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":9,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5,"
				+ "\"Value\":44.12,"
				+ "\"Timestamp\":111111114"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	/**
	 * Parses the confirm value.
	 */
	@Test
	public void parseConfirmValue(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":10,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		ConfirmValue sendVal = new ConfirmValue(123, 5,1, 0, 111222334);
		BoeseJson.parseMessage(sendVal, os);
		assertEquals(os.toString(), message);			
	}
	
	/**
	 * Read confirm value.
	 */
	@Test
	public void readConfirmValue(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":10,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	/**
	 * Parses the request value.
	 */
	@Test
	public void parseRequestValue(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":11,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		RequestValue reqVal = new RequestValue(123, 5,1, 0, 111222334);
		BoeseJson.parseMessage(reqVal, os);
		assertEquals(os.toString(), message);			
	}
	
	/**
	 * Read request value.
	 */
	@Test
	public void readRequestValue(){

		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":11,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	/**
	 * Parses the send notification.
	 */
	@Test
	public void parseSendNotification(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":12,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5,"
				+ "\"NotificationType\":1,"
				+ "\"Timestamp\":111222334,"
				+ "\"NotificationText\":\"Error\""
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		SendNotification sendNotif = new SendNotification(123, 5, 1, 111222334, "Error", 1, 0, 111222334);
		BoeseJson.parseMessage(sendNotif, os);
		assertEquals(os.toString(), message);	
	}
	
	/**
	 * Read send notification.
	 */
	@Test
	public void readSendNotification(){

		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":12,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5,"
				+ "\"NotificationType\":1,"
				+ "\"Timestamp\":111222334,"
				+ "\"NotificationText\":\"Error\""
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);	
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message); 
	}
	
	/**
	 * Parses the send status.
	 */
	@Test
	public void parseSendStatus(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":13,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceComponentId\":5,"
				+ "\"StatusCode\":1,"
				+ "\"Timestamp\":1234"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();//TODO was ist isSendStatus?
		SendStatus sendStat = new SendStatus(5, 1, 1234, true, 1, 0, 111222334);
		BoeseJson.parseMessage(sendStat, os);
		assertEquals(os.toString(), message);	
	}
	
	/**
	 * Read send status.
	 */
	@Test
	public void readSendStatus(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":13,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceComponentId\":5,"
				+ "\"StatusCode\":1,"
				+ "\"Timestamp\":1234"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the confirm status.
	 */
	@Test
	public void parseConfirmStatus(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":14,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceComponentId\":5,"
				+ "\"StatusCode\":1,"
				+ "\"Timestamp\":1234"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
		BoeseJson.parseMessage(confStat, os);
		assertEquals(os.toString(), message);	
	}
	
	/**
	 * Read confirm status.
	 */
	@Test
	public void readConfirmStatus(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":14,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceComponentId\":5,"
				+ "\"StatusCode\":1,"
				+ "\"Timestamp\":1234"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user request all devices.
	 */
	@Test
	public void parseUserRequestAllDevices(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":50,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"IsUserRequest\":true"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		RequestAllDevices urg = new RequestAllDevices(1, 0, 111222334, true);
		BoeseJson.parseMessage(urg, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user request all devices.
	 */
	@Test
	public void readUserRequestAllDevices(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":50,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"IsUserRequest\":true"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user send devices.
	 */
	@Test
	public void parseUserSendDevices(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":51,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Devices\":[{"
				+ "\"DeviceName\":\"Steckdose\","
				+ "\"DeviceId\":12,"
				+ "\"ZoneId\":23,"
				+ "\"ConnectorId\":2"
				+ "}]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		
		HashSet<UserDevice> deviceList = new HashSet<UserDevice>();
		UserDevice dev = new UserDevice("Steckdose", 12, 23, 2);
		deviceList.add(dev);
		
		UserSendDevices usd = new UserSendDevices(deviceList, 1, 0, 111222334);
		BoeseJson.parseMessage(usd, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user send devices.
	 */
	@Test
	public void readUserSendDevices(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":51,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Devices\":[{"
				+ "\"DeviceName\":\"Steckdose\","
				+ "\"DeviceId\":12,"
				+ "\"ZoneId\":23,"
				+ "\"ConnectorId\":2"
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user request device components.
	 */
	@Test
	public void parseUserRequestDeviceComponents(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":52,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceIds\":[5,6,7]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashSet<Integer> devices = new HashSet<Integer>();
		devices.add(5);
		devices.add(6);
		devices.add(7);
		UserRequestDeviceComponents urdc = new UserRequestDeviceComponents(devices, 1, 0, 111222334);
		BoeseJson.parseMessage(urdc, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user request device components.
	 */
	@Test
	public void readUserRequestDeviceComponents(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":52,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceIds\":[5,6,7]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user send device components.
	 */
	@Test
	public void parseUserSendDeviceComponents(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":53,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":5,"
				+ "\"Components\":[{"
				+ "\"DeviceComponentId\":1,"
				+ "\"Description\":\"Strom messen\","
				+ "\"ComponentName\":\"Strom\","
				+ "\"Value\":3.14,"
				+ "\"Timestamp\":1234,"
				+ "\"Status\":0,"
				+ "\"Actor\":true,"
				+ "\"Unit\":\"undefined\""
				+ "}]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashSet<DeviceComponents> components = new HashSet<DeviceComponents>();
		DeviceComponents devComp = new DeviceComponents(1, "Strom", 3.14, 1234, "undefined", "Strom messen", true, 0); 
		components.add(devComp);
		UserSendDeviceComponent usdc = new UserSendDeviceComponent(5, components, 1, 0, 111222334);
		BoeseJson.parseMessage(usdc, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user send device components.
	 */
	@Test
	public void readUserSendDeviceComponents(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":53,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":5,"
				+ "\"Components\":[{"
				+ "\"DeviceComponentId\":1,"
				+ "\"Description\":\"Strom messen\","
				+ "\"ComponentName\":\"Strom\","
				+ "\"Value\":3.14,"
				+ "\"Timestamp\":1234,"
				+ "\"Status\":0,"
				+ "\"Actor\":true,"
				+ "\"Unit\":\"undefined\""
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user request connectors.
	 */
	@Test
	public void parseUserRequestConnectors(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":54,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"ConnectorIds\":[5,6,7]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashSet<Integer> connectors = new HashSet<>();
		connectors.add(5);
		connectors.add(6);
		connectors.add(7);
		UserRequestConnectors urc = new UserRequestConnectors(connectors, 1, 0, 111222334);
		BoeseJson.parseMessage(urc, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user request connectors.
	 */
	@Test
	public void readUserRequestConnectors(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":54,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"ConnectorIds\":[5,6,7]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user request all connectors.
	 */
	@Test
	public void parseUserRequestAllConnectors(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":55,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
				
		OutputStream os = new ByteArrayOutputStream();
		UserRequestConnectors urc = new UserRequestConnectors(1, 0, 111222334);
		BoeseJson.parseMessage(urc, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user request all connectors.
	 */
	@Test
	public void readUserRequestAllConnectors(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":55,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user send connectors.
	 */
	@Test
	public void parseUserSendConnectors(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":56,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				//+ "\"DeviceId\":5," //TODO soll die dabei sein?
				+ "\"Connectors\":[{"
				+ "\"ConnectorId\":2,"
				+ "\"ConnectorName\":\"Homematic\""
				+ "}]"
				+ "}";
		OutputStream os = new ByteArrayOutputStream();
		HashMap<Integer, String> connectors = new HashMap<Integer, String>();
		connectors.put(2, "Homematic");
		UserSendConnectors usc = new UserSendConnectors(connectors, 1, 0, 111222334);
		BoeseJson.parseMessage(usc, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user send connectors.
	 */
	@Test
	public void readUserSendConnectors(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":56,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				//+ "\"DeviceId\":5," //TODO soll das dadrin sein?
				+ "\"Connectors\":[{"
				+ "\"ConnectorId\":1,"
				+ "\"ConnectorName\":\"Homematic\""
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user request all zones.
	 */
	@Test
	public void parseUserRequestAllZones(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":57,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		UserRequestGeneral urg = new UserRequestGeneral(MessageType.USERREQUESTALLZONES, 1, 0, 111222334);
		BoeseJson.parseMessage(urg, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user request all zones.
	 */
	@Test
	public void readUserRequestAllZones(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":57,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user send zones.
	 */
	@Test
	public void parseUserSendZones(){	
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":58,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Zones\":[{"
				+ "\"ZoneId\":1,"
				+ "\"SuperZoneId\":0,"
				+ "\"ZoneName\":\"Wohnzimmer\""
				+ "}]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashSet<ZoneJSON> zones = new HashSet<ZoneJSON>();
		ZoneJSON zone = new ZoneJSON(1, 0, "Wohnzimmer");
		zones.add(zone);
		UserSendZones usz = new UserSendZones(zones, 1, 0, 111222334);
		BoeseJson.parseMessage(usz, os);
		assertEquals(os.toString(), message);
	}

	/**
	 * Read user send zones.
	 */
	@Test
	public void readUserSendZones(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":58,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Zones\":[{"
				+ "\"ZoneId\":1,"
				+ "\"SuperZoneId\":0,"
				+ "\"ZoneName\":\"Wohnzimmer\""
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user request all rules.
	 */
	@Test
	public void parseUserRequestAllRules(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":59,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		UserRequestGeneral urg = new UserRequestGeneral(MessageType.USERREQUESTALLRULES, 1, 0, 111222334);
		BoeseJson.parseMessage(urg, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user request all rules.
	 */
	@Test
	public void readUserRequestAllRules(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":59,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the usersend rules.
	 */
	@Test
	public void parseUsersendRules(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":60,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Rules\":[{"
				+ "\"RuleId\":1,"
				+ "\"Active\":true,"
				+ "\"InsertDate\":123123,"
				+ "\"ModifyDate\":321321,"
				+ "\"Permissions\":\"<>\","
				+ "\"Conditions\":\"<>\","
				+ "\"Actions\":\"<>\""
				+ "}]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashSet<RuleJSON> rules = new HashSet<RuleJSON>();
		RuleJSON rule = new RuleJSON(1, true, 123123, 321321, "<>", "<>", "<>");
		rules.add(rule);
		UserSendRules usr = new UserSendRules(rules, 1, 0, 111222334);
		BoeseJson.parseMessage(usr, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user send rules.
	 */
	@Test
	public void readUserSendRules(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":60,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Rules\":[{"
				+ "\"RuleId\":1,"
				+ "\"Active\":true,"
				+ "\"InsertDate\":123123,"
				+ "\"ModifyDate\":321321,"
				+ "\"Permissions\":\"<>\","
				+ "\"Conditions\":\"<>\","
				+ "\"Actions\":\"<>\""
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user request temps.
	 */
	@Test
	public void parseUserRequestTemps(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":80,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		UserRequestGeneral urg = new UserRequestGeneral(MessageType.USERREQUESTTEMPS, 1, 0, 111222334);
		BoeseJson.parseMessage(urg, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user request temps.
	 */
	@Test
	public void readUserRequestTemps(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":80,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user send temps.
	 */
	@Test
	public void parseUserSendTemps(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":81,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"TmpConnectors\":[{"
				+ "\"ConnectorTmpId\":3,"
				+ "\"ConnectorName\":\"HUE\","
				+ "\"IsUserConnector\":false"
				+ "}],"
				+ "\"TmpDevices\":[{"
				+ "\"DeviceTmpId\":3,"
				+ "\"DeviceName\":\"Steckdose\","
				+ "\"ConnectorId\":2"
				+ "}],"
				+ "\"TmpDeviceComponents\":[{"
				+ "\"ComponentTmpId\":4,"
				+ "\"DeviceId\":3,"
				+ "\"ConnectorId\":2,"
				+ "\"Name\":\"Strom messen\","
				+ "\"Description\":\"misst den Strom\","
				+ "\"Actor\":false,"
				+ "\"Unit\":\"A\""
				+ "}]"
				+ "}";
		//TODO tempDevice und tempComponent als eigene Klassen
		OutputStream os = new ByteArrayOutputStream();
		HashMap<Integer, TempConnector> tempConnectors = new HashMap<Integer, TempConnector>();
		TempConnector tempCon = new TempConnector();
		tempCon.setName("HUE");
		tempCon.setUserConnector(false);
		tempConnectors.put(3, tempCon);
		HashMap<Integer, TempDevice> tempDevices = new HashMap<Integer, TempDevice>();
		TempDevice tempDev = new TempDevice(2, "Steckdose");
		tempDevices.put(3, tempDev);
		HashMap<Integer, TempComponent> tempDeviceComponents = new HashMap<Integer, TempComponent>();
		TempComponent tempComp = new TempComponent(3, "Strom messen", 0, 0, 2, "misst den Strom", "A", false);
		tempDeviceComponents.put(4, tempComp);
		UserSendTemps ust = new UserSendTemps(tempConnectors, tempDevices, tempDeviceComponents, 1, 0, 111222334);
		BoeseJson.parseMessage(ust, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user send temps.
	 */
	@Test
	public void readUserSendTemps(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":81,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"TmpConnectors\":[{"
				+ "\"ConnectorTmpId\":3,"
				+ "\"ConnectorName\":\"HUE\","
				+ "\"IsUserConnector\":false"
				+ "}],"
				+ "\"TmpDevices\":[{"
				+ "\"DeviceTmpId\":3,"
				+ "\"DeviceName\":\"Steckdose\","
				+ "\"ConnectorId\":2"
				+ "}],"
				+ "\"TmpDeviceComponents\":[{"
				+ "\"ComponentTmpId\":4,"
				+ "\"DeviceId\":3,"
				+ "\"ConnectorId\":2,"
				+ "\"Name\":\"Strom messen\","
				+ "\"Description\":\"misst den Strom\","
				+ "\"Actor\":false,"
				+ "\"Unit\":\"A\""
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user confirm temps.
	 */
	@Test
	public void parseUserConfirmTemps(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":82,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"TmpConnectors\":[1,2,3],"
				+ "\"TmpDevices\":[{"
				+ "\"DeviceTmpId\":3,"
				+ "\"ZoneId\":12"
				+ "}],"
				+ "\"TmpDeviceComponents\":[{"
				+ "\"ComponentTmpId\":4,"
				+ "\"UnitId\":1,"
				+ "\"Name\":\"Strom messen\""
				+ "}]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashSet<UserTempComponent> tempDeviceComponents = new HashSet<UserTempComponent>();
		UserTempComponent utc = new UserTempComponent(4, 1, "Strom messen");
		tempDeviceComponents.add(utc);
		
		HashMap<Integer, Integer> tempDevices = new HashMap<Integer, Integer>();
		tempDevices.put(3, 12);
		
		HashSet<Integer> tempConnectors = new HashSet<Integer>();
		tempConnectors.add(1);
		tempConnectors.add(2);
		tempConnectors.add(3);
		
		UserConfirmTemps uct = new UserConfirmTemps(tempConnectors, tempDevices, tempDeviceComponents, 1, 0, 111222334);
		BoeseJson.parseMessage(uct, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user confirm temps.
	 */
	@Test
	public void readUserConfirmTemps(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":82,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"TmpConnectors\":[1,2,3],"
				+ "\"TmpDevices\":[{"
				+ "\"DeviceTmpId\":3,"
				+ "\"ZoneId\":12"
				+ "}],"
				+ "\"TmpDeviceComponents\":[{"
				+ "\"ComponentTmpId\":4,"
				+ "\"UnitId\":1,"
				+ "\"Name\":\"Strom messen\""
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user create rules.
	 */
	@Test
	public void parseUserCreateRules(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":90,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Rules\":[{"
				+ "\"RuleId\":-1,"
				+ "\"TempRuleId\":1,"
				+ "\"Active\":true,"
				+ "\"Permissions\":\"<>\","
				+ "\"Conditions\":\"<>\","
				+ "\"Actions\":\"<>\""
				+ "}]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashSet<RuleJSON> rules = new HashSet<RuleJSON>();
		RuleJSON rule = new RuleJSON(-11, 1, true, 0, 0, "<>", "<>", "<>");
		rules.add(rule);
		UserCreateRules ucr = new UserCreateRules(rules, 1, 0, 111222334);
		BoeseJson.parseMessage(ucr, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user create rules.
	 */
	@Test
	public void readUserCreateRules(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":90,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Rules\":[{"
				+ "\"RuleId\":-1,"
				+ "\"TempRuleId\":1,"
				+ "\"Active\":true,"
				+ "\"Permissions\":\"<>\","
				+ "\"Conditions\":\"<>\","
				+ "\"Actions\":\"<>\""
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Parses the user confirm rules.
	 */
	@Test
	public void parseUserConfirmRules(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":91,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Rules\":[{"
				+ "\"RuleId\":2,"
				+ "\"TempRuleId\":1"
				+ "}]"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HashMap<Integer, Integer> tempRules = new HashMap<Integer, Integer>();
		tempRules.put(1, 2);
		UserConfirmRules ucr = new UserConfirmRules(tempRules, 1, 0, 111222334);
		BoeseJson.parseMessage(ucr, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read user confirm rules.
	 */
	@Test
	public void readUserConfirmRules(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":91,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Rules\":[{"
				+ "\"RuleId\":2,"
				+ "\"TempRuleId\":1"
				+ "}]"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	

	/**
	 * Parses the heart beat.
	 */
	@Test
	public void parseHeartBeat(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":120,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		HeartBeatMessage hm = new HeartBeatMessage(1, 0, 111222334);
		BoeseJson.parseMessage(hm, os);
		assertEquals(os.toString(), message);
	}
	
	/**
	 * Read heart beat.
	 */
	@Test
	public void readHeartBeat(){
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":120,"
				+ "\"ConnectorId\":1,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		
		InputStream is = new ByteArrayInputStream(message.getBytes());
		BoeseJson bs = BoeseJson.readMessage(is);
		assertNotNull(bs);
		
		OutputStream os = new ByteArrayOutputStream();
		BoeseJson.parseMessage(bs, os);
		assertEquals(os.toString(), message);
	}
	
}
