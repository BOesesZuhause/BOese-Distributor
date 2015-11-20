


package de.bo.aid.boese.parser.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;

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
import de.bo.aid.boese.json.MultiMessage;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.RequestDeviceComponents;
import de.bo.aid.boese.json.RequestValue;
import de.bo.aid.boese.json.SendDeviceComponents;
import de.bo.aid.boese.json.SendDevices;
import de.bo.aid.boese.json.SendNotification;
import de.bo.aid.boese.json.SendStatus;
import de.bo.aid.boese.json.SendValue;
import de.bo.aid.boese.json.UserDevice;
import de.bo.aid.boese.json.UserRequestGeneral;
import de.bo.aid.boese.json.UserSendDevices;

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
				+ "\"Password\":\"sicher!\""
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		RequestConnection recCon = new RequestConnection("Konnektor1", "sicher!", 1, 0, 111222334);
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
				+ "\"Password\":\"sicher!\""
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
//	
//	@Test
//	public void parseUserRequestDeviceComponents(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	@Test
//	public void readUserRequestDeviceComponents(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void parseUserSendDeviceComponents(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	@Test
//	public void readUserSendDeviceComponents(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void parseUserRequestConnectors(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	@Test
//	public void readUserRequestConnectors(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void parseUserRequestAllConnectors(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	@Test
//	public void readUserRequestAllConnectors(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void parseUserSendConnectors(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	@Test
//	public void readUserSendConnectors(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void parseUserRequestAllZones(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	@Test
//	public void readUserRequestAllZones(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void parseUserSendZones(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//
//	@Test
//	public void readUserSendZones(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void parseUserRequestAllRules(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void readUserRequestAllRules(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void parseUsersendRules(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void readUserSendRules(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void parseUserRequestTemps(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void readUserRequestTemps(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void parseUserSendTemps(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void readUserSendTemps(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	@Test
//	public void parseUserConfirmTemps(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void readUserConfirmTemps(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	@Test
//	public void parseUserCreateRules(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void readUserCreateRules(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
//	@Test
//	public void parseUserConfirmRules(){
//		OutputStream os = new ByteArrayOutputStream();
//		//ConfirmStatus confStat = new ConfirmStatus(5, 1, 1234, true, 1, 0, 111222334);
//		BoeseJson.parseMessage(confStat, os);
//		assertEquals(os.toString(), message);
//	}
//	
//	@Test
//	public void readUserConfirmRules(){
//		InputStream is = new ByteArrayInputStream(message.getBytes());
//		BoeseJson bs = BoeseJson.readMessage(is);
//		assertNotNull(bs);
//		
//		OutputStream os = new ByteArrayOutputStream();
//		BoeseJson.parseMessage(bs, os);
//		assertEquals(os.toString(), message);
//	}
	
}
