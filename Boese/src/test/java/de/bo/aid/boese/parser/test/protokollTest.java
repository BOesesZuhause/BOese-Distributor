package de.bo.aid.boese.parser.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import de.bo.aid.boese.json.BoeseJson;
import de.bo.aid.boese.json.RequestConnection;

public class protokollTest {
	
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

}