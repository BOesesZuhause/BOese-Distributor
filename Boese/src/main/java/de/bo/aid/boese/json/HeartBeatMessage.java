package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class HeartBeatMessage.
 */
public class HeartBeatMessage extends BoeseJson{


	/**
	 * Instantiates a new heart beat message.
	 *
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public HeartBeatMessage(int connectorId, int status, long timestamp) {
		super(MessageType.HEARTBEATMESSAGE, connectorId, status, timestamp);
	}
	
	

}
