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



package de.bo.aid.boese.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.PropertyValueException;
import org.hibernate.Session;

import de.bo.aid.boese.exceptions.DBForeignKeyNotFoundException;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.model.*;
import de.bo.aid.boese.ruler.TimeFormat;
import de.bo.aid.boese.ruler.ToDoChecker;

// TODO: Auto-generated Javadoc
/**
 * The Class Inserts.
 *
 * @author Fabio
 * The Class Inserts offers Methods to Insert Entities in the Database.
 */
public class Inserts {
	
	/** The connection to the Database. */
	private static Connection connection = Connection.getConnection();
	
	
	/**
	 * You shouldn't create a instance of this Object.
	 */
	private Inserts(){
		
	}
	
	/**
	 * Insert a new Device.
	 *
	 * @param coid the ID of the Connector the device is connected to
	 * @param zoid the ID of the Zone the device ist belonging to
	 * @param device the Device Object with alias and Serialnumber
	 * @throws DBObjectNotFoundException when the Connector or the Zone was not Found
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void device(int coid, int zoid, Device device) throws DBObjectNotFoundException, PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Connector con = (Connector)session.get(Connector.class, new Integer(coid));
		Zone zone = (Zone)session.get(Zone.class, new Integer(zoid));
		if(con != null && zone != null){
			device.setConnector(con);
			device.setZone(zone);
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException(((con == null) ? "Connector not found, " : "") + ((zone==null) ? "Zone not found, " : ""));
		}
 
		try{
			session.save(device);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){ //TODO Ordentliche Exception
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
	}
	
	/**
	 * Insert a new Component.
	 *
	 * @param unitId the ID of the related Unit
	 * @param comp the Component object with name and actor
	 * @throws DBObjectNotFoundException when the Unit was not found
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void component(int unitId, Component comp) throws DBObjectNotFoundException, PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Unit unit = (Unit)session.get(Unit.class, new Integer(unitId));
		if (unit != null){
			comp.setUnit(unit);			
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("Unit not found");
		}
		
		try{
			session.save(comp);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		

		session.close();
	}
	
	/**
	 * Insert a new DeviceComponent.
	 *
	 * @param deid the ID of the Device this DeviceComponent is part of
	 * @param coid the ID of the Component which specifies the DeviceComponent
	 * @param dc the DeviceComponent object with description
	 * @throws DBObjectNotFoundException when the Component or the Device was not Found
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void deviceComponent(int deid, int coid, DeviceComponent dc) throws DBObjectNotFoundException, PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Device device = (Device) session.get(Device.class, deid);
		//session.get, because of Lazy Loading(Same object)
		Component comp = (Component) session.get(Component.class, coid);
		if(device != null && comp != null){
			dc.setDevice(device);
			dc.setComponent(comp);
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException(((device == null) ? "Device not found, " : "") + ((comp == null) ? "Component not found, " : ""));
		}
 
		try{
			session.save(dc);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}		
		
		session.close();
	}
	
	/**
	 * Insert a new Connector.
	 *
	 * @param con the Connector object with name and password
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void connector(Connector con) throws PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
 
		try{
			session.save(con);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
	}
	
	/**
	 * Insert a new Value.
	 *
	 * @param decoid the ID of the DeviceComponent the Value belonging to
	 * @param timestamp the timestamp when this Value was measured/switched
	 * @param value the value
	 * @throws DBObjectNotFoundException when the DeviceComponent was not found
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void value(int decoid, Date timestamp, double value) throws DBObjectNotFoundException, PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
 
		DeviceComponent deco = (DeviceComponent)session.get(DeviceComponent.class, new Integer(decoid));
		if(deco != null){
			deco.setCurrentValue(BigDecimal.valueOf(value));
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("DeviceComponent not found");
		}
		try{
			session.save(deco);
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve;
		}
		
		if(deco.isLoggen()){
			LogDeviceComponent logcomp = new LogDeviceComponent();
			logcomp.setDeviceComponent(deco);
			logcomp.setTimestamp(timestamp);
			logcomp.setValue(BigDecimal.valueOf(value));
			
			try{
				session.save(logcomp);
			}
			catch(PropertyValueException pve){
				session.getTransaction().rollback();
				session.close();
				throw pve; //not null Value is null
			}
		}
		
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Insert a new Unit.
	 *
	 * @param unit the Unit Object with name and symbol
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void unit(Unit unit) throws PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			session.save(unit);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
	}
	
	/**
	 * Insert a new Rule.
	 *
	 * @param deCo a List of all DeviceComponent IDs in the Condition
	 * @param rule the Rule Object with permissions, conditions and actions
	 * @param tdc the ToDoChecker instance of the Distributor
	 * @throws DBForeignKeyNotFoundException the DB foreign key not found exception
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void rule(List<DeviceComponent> deCo, Rule rule, ToDoChecker tdc) throws DBForeignKeyNotFoundException, PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		rule.setInsertDate(new Date());
		rule.setModifyDate(new Date());
		
		try{
			session.save(rule);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		
		rule.getRuId();
		
		if (deCo != null){
			try{
				for(DeviceComponent deco : deCo){
					session.beginTransaction();
					DeviceComponentRule decorule = new DeviceComponentRule();
					decorule.setDevicecomponent(deco);
					decorule.setRule(rule);
					decorule.setId(new DeviceComponentRuleId(deco.getDeCoId(), rule.getRuId()));
					session.save(decorule);
					session.getTransaction().commit();
				}
			}
			catch(PropertyValueException pve){
				session.getTransaction().rollback();
				session.close();
				throw pve; //not null Value is null
			}
		}

		session.close();
		if(tdc != null)
			tdc.changeInToDo();
	}
	
	/**
	 * Insert a new Service.
	 *
	 * @param service the Service Object with description
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void service(Service service) throws PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			session.save(service);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
	}
	
	/**
	 * Insert a new Group.
	 *
	 * @param grp the Group Object with name
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void group(Group grp) throws PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			session.save(grp);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
	}
	
	/**
	 * Insert a new User.
	 *
	 * @param user the User object with surname, firstName, password, gender, birthdate, userName and email
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void user(User user) throws PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			session.save(user);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
	}
	
	/**
	 * Inser a new Zone.
	 *
	 * @param zone the Zone object with name
	 * @param suzone the super zonen Object
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void zone(Zone zone, Zone suzone)throws PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		zone.setZone(suzone);
		
		try{
			session.save(zone);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
	}
	
	/**
	 * Insert a new Device group.
	 *
	 * @param deid the ID of the related Device
	 * @param grid the ID of the related Group
	 * @param devgrp the DeviceGroup Object with rights
	 * @throws PropertyValueException when a not Null Field is null
	 * @throws DBObjectNotFoundException when the Device or the Group was not found
	 */
	public static void deviceGroup(int deid, short grid, DeviceGroup devgrp) throws PropertyValueException, DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Device dev = Selects.device(deid);
		Group grp = Selects.group(grid);
		devgrp.setDevice(dev);
		devgrp.setGroup(grp);
		devgrp.setId(new DeviceGroupId(deid, grid));
		
		try{
			session.save(devgrp);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
	}
	
	/**
	 * Insert a new Repeat rule.
	 *
	 * @param rr the RepeatRule object with repeat, value and repeatsAfterEnd
	 * @param ruleId the ID of the Rule the RepeatRule is belonging to
	 * @param deCoId the ID of the DeviceComponent the RepeatRule switch
	 * @param tdc the ToDoChecker instance of the Distributor
	 * @throws DBForeignKeyNotFoundException when the DeviceComponent or the Rule was not found
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void repeatRule(RepeatRule rr, ToDoChecker tdc) throws DBForeignKeyNotFoundException, PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();	
		
		try{
			session.save(rr);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
		
		ToDo todo = new ToDo(new TimeFormat(rr.getRepeat()).getDateForRepeatRule());
		System.out.println(todo.getDate().toString());
		if(tdc != null){
			Inserts.toDo(todo, rr.getRrId(), tdc);
		}
	}
	
	/**
	 * Insert a new To do and send a Message to the ToDoChecker.
	 *
	 * @param todo the ToDo object with date
	 * @param rrId the ID of the belonging RepearRule
	 * @param tdc the ToDoChecker instance of the Distributor
	 * @throws DBForeignKeyNotFoundException the RepeatRule was not found
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void toDo(ToDo todo, int rrId, ToDoChecker tdc) throws DBForeignKeyNotFoundException, PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			todo.setRepeatRule(Selects.repeatRule(rrId));
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("DeviceComponent-FK or Rule-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}	
		
		try{
			session.save(todo);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
		tdc.changeInToDo();
	}
	
	/**
	 * Insert a new ToDo without start the ToDoChecker.
	 *
	 * @param todo the ToDo object with date
	 * @param rrId the ID of the belonging RepearRule
	 * @throws DBForeignKeyNotFoundException the RepeatRule was not found
	 * @throws PropertyValueException when a not Null Field is null
	 */
	public static void toDoWithoutChange(ToDo todo, int rrId) throws DBForeignKeyNotFoundException, PropertyValueException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			todo.setRepeatRule(Selects.repeatRule(rrId));
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("DeviceComponent-FK or Rule-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		
		try{
			session.save(todo);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
	}
	
	/**
	 * Insert the specified default Entities.
	 */
	public static void defaults(){
		//Default Unit
		try {
			Selects.unit(1);
		} catch (DBObjectNotFoundException e) {
			Unit unit = new Unit("Undefined", "ud");
			unit(unit);
		}
		//Default Zone
		try{
			Selects.zone(1);
		} catch (DBObjectNotFoundException e) {
			Zone zone = new Zone("Global");
			zone(zone, zone);
		}
		//Default Service
		try{
			Selects.service(1);
		} catch(DBObjectNotFoundException e){
			Service service = new Service("Default");
			service(service);
		}
		//Default User
		try{
			Selects.user(1);
		} catch(DBObjectNotFoundException e){
			User user = new User("User", "Super", "MasterPassword", true, null, "root", null);
			user(user);
		}
		//Default Group
		try{
			Selects.group((short)1);
		} catch(DBObjectNotFoundException e){
			Group grp = new Group("Users");
			group(grp);
		}
		//Default Rule
		try{
			Selects.rule(1);
		} catch(DBObjectNotFoundException e){
			Rule rule = new Rule("<PERMISSION></PERMISSION>", "<CONDITION></CONDITION>", "<ACTION></ACTION>");
			try {
				rule(null, rule, null);
			} catch (DBForeignKeyNotFoundException e1) {
				// TODO Logger
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Insert default Units.
	 */
	public static void defaultUnits(){
		List <Unit> units = AllSelects.units();		
		
		final String volt = "Volt";
		final String ampere = "milliAmpere";
		final String percent = "Percent";
		final String bool = "OnOff";
		final String temp = "Temperature";
		final String dist = "Distance";
		final String rgb = "ColorRGB";
		final String watt = "Watt";
		final String time = "Time";
		final String vel = "Velocity";
		final String weight = "Weight";
		
		Map<String, String> defaults = new HashMap<>(); 
		defaults.put(volt, "V"); 
		defaults.put(ampere, "mA"); 
		defaults.put(percent, "%"); 
		defaults.put(bool, "Zeichen fehlt"); 
		defaults.put(temp, "°C"); 
		defaults.put(dist, "m"); 
		defaults.put(rgb, "rgb"); 
		defaults.put(watt, "W"); 
		defaults.put(time, "ms"); 
		defaults.put(vel, "m/s"); 
		defaults.put(weight, "g");
		
		List <String> namen = new ArrayList<>();
		for(Unit unit : units){
			namen.add(unit.getName());
		}
		
		if (!namen.contains(volt)){
			Inserts.unit(new Unit(volt, defaults.get(volt)));
		}
		if(!namen.contains(ampere)){
			Inserts.unit(new Unit(ampere, defaults.get(ampere)));
		}
		if(!namen.contains(percent)){
			Inserts.unit(new Unit(percent, defaults.get(percent)));
		}
		if(!namen.contains(bool)){
			Inserts.unit(new Unit(bool, defaults.get(bool)));
		}
		if(!namen.contains(temp)){
			Inserts.unit(new Unit(temp, defaults.get(temp)));
		}
		if(!namen.contains(dist)){
			Inserts.unit(new Unit(dist, defaults.get(dist)));
		}
		if(!namen.contains(rgb)){
			Inserts.unit(new Unit(rgb, defaults.get(rgb)));
		}
		if(!namen.contains(watt)){
			Inserts.unit(new Unit(watt, defaults.get(watt)));
		}
		if(!namen.contains(time)){
			Inserts.unit(new Unit(time, defaults.get(time)));
		}
		if(!namen.contains(vel)){
			Inserts.unit(new Unit(vel, defaults.get(vel)));
		}
		if(!namen.contains(weight)){
			Inserts.unit(new Unit(weight, defaults.get(weight)));
		}
	}
	
	/**
	 * LogConnector.
	 *
	 * @param logcon the Log_Connector to Insert
	 */
	public static void logConnector(LogConnector logcon){
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			session.save(logcon);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
	}
	
	/**
	 * LogRule.
	 *
	 * @param logrule the Log_Rule to Insert
	 */
	public static void logRule(LogRule logrule){
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			session.save(logrule);
			session.getTransaction().commit();
		}
		catch(PropertyValueException pve){
			session.getTransaction().rollback();
			session.close();
			throw pve; //not null Value is null
		}
		
		session.close();
	}
}