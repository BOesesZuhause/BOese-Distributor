
package de.bo.aid.boese.parser.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.ConfirmConnection;
import de.bo.aid.boese.json.ConfirmDeviceComponents;
import de.bo.aid.boese.json.ConfirmDevices;
import de.bo.aid.boese.json.ConfirmValue;
import de.bo.aid.boese.json.DeviceComponents;
import de.bo.aid.boese.json.RequestAllDevices;
import de.bo.aid.boese.json.RequestConnection;
import de.bo.aid.boese.json.RequestDeviceComponents;
import de.bo.aid.boese.json.SendDeviceComponents;
import de.bo.aid.boese.json.SendDevices;
import de.bo.aid.boese.json.SendValue;

// TODO: Auto-generated Javadoc
/**
 * The Class protokollTest.
 */
public class protokollTest {
	
	/**
	 * Parses the request connection.
	 */
	@Test
	public void parseRequestConnection(){
		
		String message = "{"
				+ "\"Header\":{"
				+ "\"MessageType\":1,"
				+ "\"ConnectorId\":1,"
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"ConnectorName\":\"Konnektor1\","
				+ "\"Password\":\"sicher!\""
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		RequestConnection recCon = new RequestConnection("Konnektor1", "sicher!", 1, 0, 0, 0, 111222334);
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Password\":\"geheim\""
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		ConfirmConnection confCon = new ConfirmConnection("geheim", 1, 0, 0, 0, 111222334);
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"Password\":\"geheim\""
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "}"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		RequestAllDevices reqAllDev = new RequestAllDevices(1, 0, 0, 0, 111222334);
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
		SendDevices sendDev = new SendDevices(devices, 1, 0, 0, 0, 111222334);
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
		ConfirmDevices sendDev = new ConfirmDevices(devices, 1, 0, 0, 0, 111222334);
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		RequestDeviceComponents reqDevComp = new RequestDeviceComponents(123, 1, 0, 0, 0, 111222334);
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
		SendDeviceComponents reqDevComp = new SendDeviceComponents(123, components, 1, 0, 0, 0, 111222334);
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
		ConfirmDeviceComponents confDevComp = new ConfirmDeviceComponents(123, components, 1, 0, 0, 0, 111222334);
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5,"
				+ "\"Value\":44.12,"
				+ "\"Timestamp\":111111114"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		SendValue sendVal = new SendValue(123, 5, 44.12, 111111114 , 1, 0, 0, 0, 111222334);
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
				+ "\"Status\":0,"
				+ "\"Timestamp\":111222334"
				+ "},"
				+ "\"DeviceId\":123,"
				+ "\"DeviceComponentId\":5"
				+ "}";
		
		OutputStream os = new ByteArrayOutputStream();
		ConfirmValue sendVal = new ConfirmValue(123, 5,1, 0, 0, 0, 111222334);
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
				+ "\"SequenceNr\":0,"
				+ "\"AcknowledgeNr\":0,"
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
	

}
