package de.bo.aid.boese.dao;

public class DAOHandler {
	
	private ComponentDAO comp;
	private ConnectorDAO con;
	private DeviceComponentDAO deco;
	private DeviceComponentReplaceDAO decore;
	private DeviceComponentRuleDAO decoru;
	private DeviceDAO dev;
	private DeviceGroupDAO devgrp;
	private GroupDAO grp;
	private GroupUserDAO grpuser;
	private GroupZoneDAO grpzo;
	private HistoryLogConnectorDAO hilocon;
	private HistoryLogDeviceComponentDAO hilodeco;
	private HistoryLogRuleDAO hiloru;
	private LogConnectorDAO locon;
	private LogDeviceComponentDAO lodeco;
	private LogRuleDAO loru;
	private RepeatRuleDAO rr;
	private RuleDAO ru;
	private ServiceDAO ser;
	private ToDoDAO todo;
	private UnitDAO unit;
	private UserDAO user;
	private ZoneDAO zone;
	
	private static DAOHandler daoh;

	private DAOHandler(){
		comp = new ComponentDAO();
		con = new ConnectorDAO();
		deco = new DeviceComponentDAO();
		decore = new DeviceComponentReplaceDAO();
		decoru = new DeviceComponentRuleDAO();
		dev = new DeviceDAO();
		devgrp = new DeviceGroupDAO();
		grp = new GroupDAO();
		grpuser = new GroupUserDAO();
		grpzo = new GroupZoneDAO();
		hilocon = new HistoryLogConnectorDAO();
		hilodeco =  new HistoryLogDeviceComponentDAO();
		hiloru = new HistoryLogRuleDAO();
		locon = new LogConnectorDAO();
		lodeco = new LogDeviceComponentDAO();
		loru = new LogRuleDAO();
		rr = new RepeatRuleDAO();
		ru = new RuleDAO();
		ser = new ServiceDAO();
		todo = new ToDoDAO();
		unit = new UnitDAO();
		user = new UserDAO();
		zone = new ZoneDAO();
	}
	
	public static DAOHandler getInstance(){
		if(daoh == null){
			daoh = new DAOHandler();
		}
		return daoh;
	}

	public ComponentDAO getComp() {
		return comp;
	}

	public void setComp(ComponentDAO comp) {
		this.comp = comp;
	}

	public ConnectorDAO getCon() {
		return con;
	}

	public void setCon(ConnectorDAO con) {
		this.con = con;
	}

	public DeviceComponentDAO getDeco() {
		return deco;
	}

	public void setDeco(DeviceComponentDAO deco) {
		this.deco = deco;
	}

	public DeviceComponentReplaceDAO getDecore() {
		return decore;
	}

	public void setDecore(DeviceComponentReplaceDAO decore) {
		this.decore = decore;
	}

	public DeviceComponentRuleDAO getDecoru() {
		return decoru;
	}

	public void setDecoru(DeviceComponentRuleDAO decoru) {
		this.decoru = decoru;
	}

	public DeviceDAO getDev() {
		return dev;
	}

	public void setDev(DeviceDAO dev) {
		this.dev = dev;
	}

	public DeviceGroupDAO getDevgrp() {
		return devgrp;
	}

	public void setDevgrp(DeviceGroupDAO devgrp) {
		this.devgrp = devgrp;
	}

	public GroupDAO getGrp() {
		return grp;
	}

	public void setGrp(GroupDAO grp) {
		this.grp = grp;
	}

	public GroupUserDAO getGrpuser() {
		return grpuser;
	}

	public void setGrpuser(GroupUserDAO grpuser) {
		this.grpuser = grpuser;
	}

	public GroupZoneDAO getGrpzo() {
		return grpzo;
	}

	public void setGrpzo(GroupZoneDAO grpzo) {
		this.grpzo = grpzo;
	}

	public HistoryLogConnectorDAO getHilocon() {
		return hilocon;
	}

	public void setHilocon(HistoryLogConnectorDAO hilocon) {
		this.hilocon = hilocon;
	}

	public HistoryLogDeviceComponentDAO getHilodeco() {
		return hilodeco;
	}

	public void setHilodeco(HistoryLogDeviceComponentDAO hilodeco) {
		this.hilodeco = hilodeco;
	}

	public HistoryLogRuleDAO getHiloru() {
		return hiloru;
	}

	public void setHiloru(HistoryLogRuleDAO hiloru) {
		this.hiloru = hiloru;
	}

	public LogConnectorDAO getLocon() {
		return locon;
	}

	public void setLocon(LogConnectorDAO locon) {
		this.locon = locon;
	}

	public LogDeviceComponentDAO getLodeco() {
		return lodeco;
	}

	public void setLodeco(LogDeviceComponentDAO lodeco) {
		this.lodeco = lodeco;
	}

	public LogRuleDAO getLoru() {
		return loru;
	}

	public void setLoru(LogRuleDAO loru) {
		this.loru = loru;
	}

	public RepeatRuleDAO getRr() {
		return rr;
	}

	public void setRr(RepeatRuleDAO rr) {
		this.rr = rr;
	}

	public RuleDAO getRu() {
		return ru;
	}

	public void setRu(RuleDAO ru) {
		this.ru = ru;
	}

	public ServiceDAO getSer() {
		return ser;
	}

	public void setSer(ServiceDAO ser) {
		this.ser = ser;
	}

	public ToDoDAO getTodo() {
		return todo;
	}

	public void setTodo(ToDoDAO todo) {
		this.todo = todo;
	}

	public UnitDAO getUnit() {
		return unit;
	}

	public void setUnit(UnitDAO unit) {
		this.unit = unit;
	}

	public UserDAO getUser() {
		return user;
	}

	public void setUser(UserDAO user) {
		this.user = user;
	}

	public ZoneDAO getZone() {
		return zone;
	}

	public void setZone(ZoneDAO zone) {
		this.zone = zone;
	}

}
