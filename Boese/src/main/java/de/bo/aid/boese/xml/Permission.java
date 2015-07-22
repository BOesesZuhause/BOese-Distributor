package de.bo.aid.boese.xml;

public class Permission extends BoeseXML {
	private int groupID;
	
	public Permission(int groupId) {
		this.groupID = groupId;
	}
	
	public int getGroupId() {
		return groupID;
	}

}
