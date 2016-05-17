package de.bo.aid.boese.dao;

public class DAOHandler {
	
	private ComponentDAO componentDAO;
	private ConnectorDAO connectorDAO;
	private DeviceComponentDAO deviceComponentDAO;
	private DeviceComponentReplaceDAO deviceComponentReplaceDAO;
	private DeviceComponentRuleDAO deviceComponentRuleDAO;
	private DeviceDAO deviceDAO;
	private DeviceGroupDAO deviceGroupDAO;
	private GroupDAO groupDAO;
	private GroupUserDAO groupUserDAO;
	private GroupZoneDAO groupZoneDAO;
	private HistoryLogConnectorDAO historyLogConnectorDAO;
	private HistoryLogDeviceComponentDAO historyLogDeviceComponentDAO;
	private HistoryLogRuleDAO historyLogRuleDAO;
	private LogConnectorDAO logConnectorDAO;
	private LogDeviceComponentDAO logDeviceComponentDAO;
	private LogRuleDAO logRuleDAO;
	private RepeatRuleDAO repeatRuleDAO;
	private RuleDAO ruleDAO;
	private ServiceDAO serviceDAO;
	private ToDoDAO toDoDAO;
	private UnitDAO unitDAO;
	private UserDAO userDAO;
	private ZoneDAO zoneDAO;
	
	private static DAOHandler daoh;

	private DAOHandler(){
		componentDAO = new ComponentDAO();
		connectorDAO = new ConnectorDAO();
		deviceComponentDAO = new DeviceComponentDAO();
		deviceComponentReplaceDAO = new DeviceComponentReplaceDAO();
		deviceComponentRuleDAO = new DeviceComponentRuleDAO();
		deviceDAO = new DeviceDAO();
		deviceGroupDAO = new DeviceGroupDAO();
		groupDAO = new GroupDAO();
		groupUserDAO = new GroupUserDAO();
		groupZoneDAO = new GroupZoneDAO();
		historyLogConnectorDAO = new HistoryLogConnectorDAO();
		historyLogDeviceComponentDAO =  new HistoryLogDeviceComponentDAO();
		historyLogRuleDAO = new HistoryLogRuleDAO();
		logConnectorDAO = new LogConnectorDAO();
		logDeviceComponentDAO = new LogDeviceComponentDAO();
		logRuleDAO = new LogRuleDAO();
		repeatRuleDAO = new RepeatRuleDAO();
		ruleDAO = new RuleDAO();
		serviceDAO = new ServiceDAO();
		toDoDAO = new ToDoDAO();
		unitDAO = new UnitDAO();
		userDAO = new UserDAO();
		zoneDAO = new ZoneDAO();
	}
	
	public static DAOHandler getInstance(){
		if(daoh == null){
			daoh = new DAOHandler();
		}
		return daoh;
	}

	public ComponentDAO getComponentDAO() {
		return componentDAO;
	}

	public ConnectorDAO getConnectorDAO() {
		return connectorDAO;
	}

	public DeviceComponentDAO getDeviceComponentDAO() {
		return deviceComponentDAO;
	}

	public DeviceComponentReplaceDAO getDeviceComponentReplaceDAO() {
		return deviceComponentReplaceDAO;
	}
	
	public DeviceComponentRuleDAO getDeviceComponentRuleDAO() {
		return deviceComponentRuleDAO;
	}
	
	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	public DeviceGroupDAO getDeviceGroupDAO() {
		return deviceGroupDAO;
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}
	
	public GroupUserDAO getGroupUserDAO() {
		return groupUserDAO;
	}

	public GroupZoneDAO getGroupZoneDAO() {
		return groupZoneDAO;
	}

	public HistoryLogConnectorDAO getHistoryLogConnectorDAO() {
		return historyLogConnectorDAO;
	}

	public HistoryLogDeviceComponentDAO getHistoryLogDeviceComponentDAO() {
		return historyLogDeviceComponentDAO;
	}

	public HistoryLogRuleDAO getHistoryLogRuleDAO() {
		return historyLogRuleDAO;
	}

	public LogConnectorDAO getLogConnectorDAO() {
		return logConnectorDAO;
	}

	public LogDeviceComponentDAO getLogDeviceComponentDAO() {
		return logDeviceComponentDAO;
	}

	public LogRuleDAO getLogRuleDAO() {
		return logRuleDAO;
	}

	public RepeatRuleDAO getRepeatRuleDAO() {
		return repeatRuleDAO;
	}

	public RuleDAO getRuleDAO() {
		return ruleDAO;
	}

	public ServiceDAO getServiceDAO() {
		return serviceDAO;
	}

	public ToDoDAO getToDoDAO() {
		return toDoDAO;
	}

	public UnitDAO getUnitDAO() {
		return unitDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public ZoneDAO getZoneDAO() {
		return zoneDAO;
	}

}
