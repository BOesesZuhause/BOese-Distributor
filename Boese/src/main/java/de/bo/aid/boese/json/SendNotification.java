/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 */


package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class SendNotification.
 */
public class SendNotification extends BoeseJson {
	/** The device id. */
	private int deviceId;
	
	/** The device component id. */
	private int deviceComponentId;
	
	/** The notification type. */
	private int notificationType;
	
	/** The notification timestamp. */
	private long notificationTimestamp;
	
	/** The notification text. */
	private String notificationText;

	/**
	 * Instantiates a new send notification.
	 *
	 * @param deviceId the device id
	 * @param deviceComponentId the device component id
	 * @param notificationType the notification type
	 * @param notificationTimestamp the notification timestamp
	 * @param notificationText the notification text
	 * @param connectorId the connector id
	 * @param status the status
	 * @param timestamp the timestamp
	 */
	public SendNotification(int deviceId, int deviceComponentId, int notificationType, long notificationTimestamp, String notificationText,
			int connectorId, int status, long timestamp) {
		super(MessageType.SENDNOTIFICATION, connectorId, status, timestamp);
		this.deviceId = deviceId;
		this.deviceComponentId = deviceComponentId;
		this.notificationType = notificationType;
		this.notificationTimestamp = notificationTimestamp;
		this.notificationText = notificationText;
		this.connectorId = connectorId;
		this.status = status;
		this.timestamp = timestamp;
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public int getDeviceId() {
		return deviceId;
	}
	
	/**
	 * Gets the device component id.
	 *
	 * @return the device component id
	 */
	public int getDeviceComponentId() {
		return deviceComponentId;
	}
	
	/**
	 * Gets the notification type.
	 *
	 * @return the notification type
	 */
	public int getNotificationType() {
		return notificationType;
	}
	
	/**
	 * Gets the notification timestamp.
	 *
	 * @return the notification timestamp
	 */
	public long getNotificationTimestamp() {
		return notificationTimestamp;
	}
	
	/**
	 * Gets the notification text.
	 *
	 * @return the notification text
	 */
	public String getNotificationText() {
		return notificationText;
	}
}
