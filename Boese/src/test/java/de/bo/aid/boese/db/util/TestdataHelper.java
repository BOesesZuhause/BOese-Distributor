package de.bo.aid.boese.db.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import de.bo.aid.boese.DB.util.DBDefaults;
import de.bo.aid.boese.DB.util.DefaultRuleConstants;
import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.dao.StandardDAO;
import de.bo.aid.boese.modelJPA.*;
import de.bo.aid.boese.ruler.TimeFormat;

public class TestdataHelper {
	
	private static EntityManager em;
	private static DAOHandler daoHandler = DAOHandler.getInstance();
	private static StandardDAO<Object> std = new StandardDAO<Object>() {			
		@Override
		public Set<Object> getAll(EntityManager em) {return null;}
		@Override
		public Object get(EntityManager em, int id) {return null;}
		@Override
		public long count(EntityManager em) {return 0;}
	};

	private static List<Object[]> objects = new ArrayList<Object[]>();
	
	private static Zone defaultZone;
	private static Service defaultService;
	private static User defaultUser;
	private static Group defaultGroup;
	private static Rule defaultRule;
	
	private static Connector[] connectors;
	private static Map<Integer, Unit> units;
	private static Zone[] zones;
	private static User[] users;
	private static Component[] components;
	private static Device[] devices;
	private static DeviceComponent[] deviceComponents;
	private static DeviceComponentReplace[] deviceComponentReplaces;
	private static Group[] groups;
	private static GroupUser[] groupUsers;
	private static GroupZone[] groupZones;
	private static DeviceGroup[] deviceGroups;
	private static Service[] services;
	private static DeviceService[] deviceServices;
	private static Rule[] rules;
	private static DeviceComponentRule[] deviceComponentRules;
	private static RepeatRule[] repeatrules;
	private static ToDo[] todos;
	private static LogConnector[] logConnectors;
	private static HistoryLogConnector[] historyLogConnectors;
	private static LogDeviceComponent[] logDeviceComponents;
	private static HistoryLogDeviceComponent[] historyLogDeviceComponents;
	private static LogRule[] logRules;
	private static HistoryLogRule[] historyLogRules;
	
	private static Date actTime = new Date();
	
	public static void insertTestData(){
		em = JPAUtil.getEntityManager();
		
		DBDefaults.defaults();
		
		getDefaults();
		
		createConnectors(); 
		objects.add(connectors);
		
		units = daoHandler.getUnitDAO().getAllAsMap(em);
		
		createZones();
		objects.add(zones);
		
		createUsers();
		objects.add(users);
		
		createComponents();
		objects.add(components);
		
		createDevices();
		objects.add(devices);
		
		createDeviceComponents();
		objects.add(deviceComponents);
		
		createDeviceComponentReplaces();
		objects.add(deviceComponentReplaces);
		
		createGroups();
		objects.add(groups);
		
		createGroupUsers();
		objects.add(groupUsers);
		
		createGroupZones();
		objects.add(groupZones);
		
		createDeviceGroups();
		objects.add(deviceGroups);
		
		createServices();
		objects.add(services);
		
		createDeviceServices();
		objects.add(deviceServices);
		
		createRules();
		objects.add(rules);
		
		createDeviceComponentRules();
		objects.add(deviceComponentRules);
		
		createRepeatRules();
		objects.add(repeatrules);
		
		createToDos();
		objects.add(todos);
		
		createLogConnectors();
		objects.add(logConnectors);
		
		createHistoryLogConnectors();
		objects.add(historyLogConnectors);
		
		createLogDeviceComponents();
		objects.add(logDeviceComponents);
		
		createHistoryLogDeviceComponents();
		objects.add(historyLogDeviceComponents);
		
		createLogRules();
		objects.add(logRules);
		
		createHistoryLogRules();
		objects.add(historyLogRules);
		
		insert();
		em.close();
		killAll();
	}

	private static void createConnectors(){
		connectors = new Connector[3];
		connectors[0] =	new Connector("HomeMatic Connector", "secret", false);
		connectors[1] =	new Connector("Philips Hue Connector", "12345", false);
		connectors[2] =	new Connector("GUI Connector", "qwertz", true);
	}
	
	private static void createZones(){
		zones = new Zone[6];
		zones[0] = new Zone("First Floor");
		zones[1] = new Zone("Second Floor");
		zones[2] = new Zone("Kitchen");
		zones[3] = new Zone("Bathroom");
		zones[4] = new Zone("Livingroom");
		zones[5] = new Zone("Bedroom");
		
		zones[0].setZone(defaultZone);
		zones[1].setZone(defaultZone);
		zones[2].setZone(zones[0]);
		zones[3].setZone(zones[1]);
		zones[4].setZone(zones[0]);
		zones[5].setZone(zones[1]);
	}
	
	private static void createUsers() {
		users = new User[4];
		users[0] = new User("Pe", "erste", "123", true, actTime, "first", "erste.person@first.de");
		users[1] = new User("rs", "zweite", "456", false, actTime, "second", "zweite.person@second.de");
		users[2] = new User("on", "dritte", "789", false, actTime, "third", "erste.person@third.de");
		users[3] = new User("Person", "vierte", "klo", true, actTime, "fourth", "zweite.person@fourth.de");
	}
	
	private static void createComponents(){
		components = new Component[4];
		components[0] = new Component("Leuchte", true, units.get(1));
		components[1] = new Component("Taster", true, units.get(2));
		components[2] = new Component("TuerOffen", false, units.get(3));
		components[3] = new Component("Helligkeit", false, units.get(4));
	}
	
	private static void createDevices(){
		devices = new Device[3];
		devices[0] = new Device("Fernbedinung", "123-456-789", connectors[0], defaultZone);
		devices[1] = new Device("Lampe", "456-789-123", connectors[1], zones[3]);
		devices[2] = new Device("Tuer", "789-123-456", connectors[0], zones[4]);
	}
	
	private static void createDeviceComponents(){
		deviceComponents = new DeviceComponent[9];
		deviceComponents[0] = new DeviceComponent("Taste 1", 0, 1, 0.0, true, components[1], devices[0]);
		deviceComponents[1] = new DeviceComponent("Taste 2", 0, 1, 0.0, true, components[1], devices[0]);
		deviceComponents[2] = new DeviceComponent("Bestaetigungs Leuchte", 0, 1, 0.0, true, components[0], devices[0]);
		deviceComponents[3] = new DeviceComponent("Leuchten?", 0, 1, 0.0, true, components[0], devices[1]);
		deviceComponents[4] = new DeviceComponent("UmgebungsHelligkeit", 0, 1, 0.0, true, components[3], devices[1]);
		deviceComponents[5] = new DeviceComponent("Offen?", 0, 1, 0.0, true, components[2], devices[2]);deviceComponents[1] = new DeviceComponent("Taste 2", 0, 1, 0.0, true, components[1], devices[0]);
		//For Replace
		deviceComponents[6] = new DeviceComponent("Taste 3(replace)", 0, 1, 0.0, true, components[1], devices[0]);
		deviceComponents[7] = new DeviceComponent("UmgebungsDunkelheit(replace)", 0, 1, 0.0, true, components[3], devices[1]);
		deviceComponents[8] = new DeviceComponent("Zu?(replace)", 0, 1, 0.0, true, components[2], devices[2]);deviceComponents[1] = new DeviceComponent("Taste 2", 0, 1, 0.0, true, components[1], devices[0]);
	}
	
	private static void createDeviceComponentReplaces(){
		deviceComponentReplaces = new DeviceComponentReplace[3];
		deviceComponentReplaces[0] = new DeviceComponentReplace(deviceComponents[6], deviceComponents[1]);
		deviceComponentReplaces[1] = new DeviceComponentReplace(deviceComponents[7], deviceComponents[4]);
		deviceComponentReplaces[2] = new DeviceComponentReplace(deviceComponents[8], deviceComponents[5]);
	}
	
	private static void createGroups(){
		groups = new Group[2];
		groups[0] = new Group("Admin");
		groups[1] = new Group("Kinder");
	}
	
	private static void createGroupUsers(){
		groupUsers = new GroupUser[7];
		groupUsers[0] = new GroupUser(groups[1], users[0], (short)1);
		groupUsers[1] = new GroupUser(defaultGroup, users[1], (short)1);
		groupUsers[2] = new GroupUser(defaultGroup, users[2], (short)1);
		groupUsers[3] = new GroupUser(groups[1], users[3], (short)1);
		groupUsers[4] = new GroupUser(groups[0], users[1], (short)1);
		groupUsers[5] = new GroupUser(groups[0], users[3], (short)1);
		groupUsers[6] = new GroupUser(defaultGroup, defaultUser, (short)1);
	}
	
	private static void createGroupZones(){
		groupZones = new GroupZone[7];
		groupZones[0] = new GroupZone(groups[0], zones[0], (short)0);
		groupZones[1] = new GroupZone(groups[0], zones[1], (short)0);
		groupZones[2] = new GroupZone(groups[1], zones[3], (short)0);
		groupZones[3] = new GroupZone(groups[1], zones[5], (short)0);
		groupZones[4] = new GroupZone(defaultGroup, zones[2], (short)0);
		groupZones[5] = new GroupZone(defaultGroup, zones[4], (short)0);
		groupZones[6] = new GroupZone(defaultGroup, defaultZone, (short)0);
	}
	
	private static void createDeviceGroups(){
		deviceGroups = new DeviceGroup[3];
		deviceGroups[0] = new DeviceGroup(devices[0], groups[0], (short)0);
		deviceGroups[1] = new DeviceGroup(devices[1], groups[1], (short)0);
		deviceGroups[2] = new DeviceGroup(devices[2], defaultGroup, (short)0);
	}
	
	private static void createServices(){
		services = new Service[3];
		services[0] = new Service("Licht");
		services[1] = new Service("Sensor");
		services[2] = new Service("Schalten");
	}
	
	private static void createDeviceServices(){
		deviceServices = new DeviceService[3];
		deviceServices[0] = new DeviceService(devices[0], services[2]);
		deviceServices[1] = new DeviceService(devices[1], services[0]);
		deviceServices[2] = new DeviceService(devices[2], services[1]);
	}
	
	private static void createRules(){
		rules = new Rule[3];
		rules[0] = new Rule(DefaultRuleConstants.permission1, DefaultRuleConstants.condition1, DefaultRuleConstants.action1);
		rules[1] = new Rule(DefaultRuleConstants.permission2, DefaultRuleConstants.condition2, DefaultRuleConstants.action2);
		rules[2] = new Rule(DefaultRuleConstants.permission3, DefaultRuleConstants.condition3, DefaultRuleConstants.action3);
	}
	
	private static void createDeviceComponentRules(){
		deviceComponentRules = new DeviceComponentRule[6];
		deviceComponentRules[0] = new DeviceComponentRule(deviceComponents[0], rules[1]);
		deviceComponentRules[1] = new DeviceComponentRule(deviceComponents[1], rules[1]);
		deviceComponentRules[2] = new DeviceComponentRule(deviceComponents[0], rules[2]);
		deviceComponentRules[3] = new DeviceComponentRule(deviceComponents[1], rules[2]);
		deviceComponentRules[4] = new DeviceComponentRule(deviceComponents[2], rules[2]);
		deviceComponentRules[5] = new DeviceComponentRule(deviceComponents[3], rules[2]);
	}
	
	private static void createRepeatRules(){
		repeatrules = new RepeatRule[3];
		repeatrules[0] = new RepeatRule("*;*;*;*;*;*", BigDecimal.valueOf(10.1), 1, rules[0], deviceComponents[1]);
		repeatrules[1] = new RepeatRule("*;*;*;*;*;*", BigDecimal.valueOf(20.0), 2, rules[1], deviceComponents[2]);
		repeatrules[2] = new RepeatRule("1;1;1;1;2070;ttttttt", BigDecimal.valueOf(15.5), 3, rules[2], deviceComponents[3]);
	}
	
	private static void createToDos(){
		todos = new ToDo[3];
		todos[0] = new ToDo(new Date());
		todos[1] = new ToDo(new Date(1464048000000L));
		boolean[] wd = {true, true, true, true, true, true, true};
		boolean[] calc = {false, false, false, false, true};
		todos[2] = new ToDo(new TimeFormat(15, 15, 31, 12, +1, wd, calc).getDate());
	}
	
	private static void createLogConnectors(){
		logConnectors = new LogConnector[3];
		logConnectors[0] = new LogConnector(connectors[0], new Date(actTime.getTime() - (86400000*1)), "");
		logConnectors[1] = new LogConnector(connectors[1], new Date(actTime.getTime() - (86400000*2)), "");
		logConnectors[2] = new LogConnector(connectors[2], new Date(actTime.getTime() - (86400000*3)), "");
	}
	
	private static void createHistoryLogConnectors(){
		historyLogConnectors = new HistoryLogConnector[3];
		historyLogConnectors[0] = new HistoryLogConnector(connectors[0], new Date(actTime.getTime() - (31536000000L + 86400000*1)), "");
		historyLogConnectors[1] = new HistoryLogConnector(connectors[1], new Date(actTime.getTime() - (31536000000L + 86400000*2)), "");
		historyLogConnectors[2] = new HistoryLogConnector(connectors[2], new Date(actTime.getTime() - (31536000000L + 86400000*3)), "");
	}
	
	private static void createLogDeviceComponents(){
		logDeviceComponents = new LogDeviceComponent[3];
		logDeviceComponents[0] = new LogDeviceComponent(deviceComponents[0], new BigDecimal(10.0), new Date(actTime.getTime() - (86400000*1)));
		logDeviceComponents[1] = new LogDeviceComponent(deviceComponents[1], new BigDecimal(20.0), new Date(actTime.getTime() - (86400000*2)));
		logDeviceComponents[2] = new LogDeviceComponent(deviceComponents[2], new BigDecimal(30.0), new Date(actTime.getTime() - (86400000*3)));
	}
	
	private static void createHistoryLogDeviceComponents(){
		historyLogDeviceComponents = new HistoryLogDeviceComponent[3];
		historyLogDeviceComponents[0] = new HistoryLogDeviceComponent(deviceComponents[0], new BigDecimal(10.0), new Date(actTime.getTime() - (31536000000L + 86400000*1)));
		historyLogDeviceComponents[1] = new HistoryLogDeviceComponent(deviceComponents[1], new BigDecimal(20.0), new Date(actTime.getTime() - (31536000000L + 86400000*2)));
		historyLogDeviceComponents[2] = new HistoryLogDeviceComponent(deviceComponents[2], new BigDecimal(30.0), new Date(actTime.getTime() - (31536000000L + 86400000*3)));
	}
	
	private static void createLogRules(){
		logRules = new LogRule[3];
		logRules[0] = new LogRule(rules[0], new Date(actTime.getTime() - (86400000*1)));
		logRules[1] = new LogRule(rules[1], new Date(actTime.getTime() - (86400000*2)));
		logRules[2] = new LogRule(rules[2], new Date(actTime.getTime() - (86400000*3)));
	}
	
	private static void createHistoryLogRules(){
		historyLogRules = new HistoryLogRule[3];
		historyLogRules[0] = new HistoryLogRule(rules[0], new Date(actTime.getTime() - (31536000000L + 86400000*1)));
		historyLogRules[1] = new HistoryLogRule(rules[1], new Date(actTime.getTime() - (31536000000L + 86400000*2)));
		historyLogRules[2] = new HistoryLogRule(rules[2], new Date(actTime.getTime() - (31536000000L + 86400000*3)));
	}
	
	private static void getDefaults(){
		em.getTransaction().begin();
		defaultZone = daoHandler.getZoneDAO().get(em, 1);
		defaultService = daoHandler.getServiceDAO().get(em, 1);
		defaultUser = daoHandler.getUserDAO().get(em, 1);
		defaultGroup = daoHandler.getGroupDAO().get(em, 1);
		defaultRule = daoHandler.getRuleDAO().get(em, 1);
		em.getTransaction().commit();
	}
	
	private static void insert(){
		for(Object[] o : objects){
			em.getTransaction().begin();
			std.createMore(em, o);
			em.getTransaction().commit();
		}
	}
	
	private static void killAll(){
		connectors = null;
		units = null;
		defaultZone = null;
		zones = null;
		users = null;
		components = null;
		devices = null;
		deviceComponents = null;
		deviceComponentReplaces = null;
		groups = null;
		groupUsers = null;
	}
}
