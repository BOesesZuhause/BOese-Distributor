package de.bo.aid.boese.json;

public class SendNotification extends BoeseJson {
	/** The device id. */
	private int deviceId;
	
	/** The device component id. */
	private int deviceComponentId;
	
	private int notificationType;
	
	private long notificationTimestamp;
	
	private String notificationText;

	public SendNotification(int deviceId, int deviceComponentId, int notificationType, long notificationTimestamp, String notificationText,
			int connectorId, int seqNr, int ackNr, int status, long timestamp) {
		super(MessageType.SENDNOTIFICATION, connectorId, seqNr, ackNr, status, timestamp);
		this.deviceId = deviceId;
		this.deviceComponentId = deviceComponentId;
	}

	public int getDeviceId() {
		return deviceId;
	}
	
	public int getDeviceComponentId() {
		return deviceComponentId;
	}
	
	public int getNotificationType() {
		return notificationType;
	}
	
	public long getNotificationTimestamp() {
		return notificationTimestamp;
	}
	
	public String getNotificationText() {
		return notificationText;
	}
}
