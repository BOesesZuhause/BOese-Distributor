

package de.bo.aid.boese.socket;

// TODO: Auto-generated Javadoc
/**
 * The Interface MessageHandler.
 */
public interface MessageHandler {
	
	/**
	 * Handle message.
	 *
	 * @param message the message
	 * @param connectorId the connector id
	 */
	public void handleMessage(String message, int connectorId);

}
