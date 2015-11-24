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
import java.util.Date;
import java.util.List;

import javax.swing.text.html.HTMLDocument.RunElement;

import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;

import de.bo.aid.boese.exceptions.DBForeignKeyNotFoundException;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.model.*;
import de.bo.aid.boese.ruler.Interpretor;
import de.bo.aid.boese.ruler.ToDoChecker;

// TODO: Auto-generated Javadoc
//TODO: Why session.get instead of Session.load
/**
 * The Class Inserts.
 */
public class Inserts {
	
	/** The connection. */
	private static Connection connection = Connection.getConnection();
	
	/**
	 * Device.
	 *
	 * @param coid the coid
	 * @param zoid the zoid
	 * @param device the Device Object with alias and Serialnumber
	 * @throws Exception the exception
	 */
	public static void device(int coid, int zoid, Device device) throws Exception{
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
	 * Component.
	 *
	 * @param unitId the unit id
	 * @param comp the Component to insert
	 * @throws Exception the exception
	 */
	public static void component(int unitId, Component comp) throws Exception{
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
	 * Device component.
	 *
	 * @param deid the deid
	 * @param coid the coid
	 * @param dc the DeviceComponent to Insert
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void deviceComponent(int deid, int coid, DeviceComponent dc) throws DBObjectNotFoundException{
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
	 * Connector.
	 *
	 * @param con the Connector to Insert
	 */
	public static void connector(Connector con){
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
	 * Value.
	 *
	 * @param decoid the decoid
	 * @param timestamp the timestamp
	 * @param value the value
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	//TODO Es kommt ein double und kein Date
	public static void value(int decoid, Date timestamp, double value) throws DBObjectNotFoundException{
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
			throw pve; //not null Value is null
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
	 * Unit.
	 *
	 * @param unit the Unit to Insert
	 */
	public static void unit(Unit unit){
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
	 * Rule.
	 *
	 * @param deCo the DeviceComponents in the Rule
	 * @param rule the Rule To Insert
	 * @param tdc the ToDoChecker of the Distributor
	 * @throws DBForeignKeyNotFoundException the DB foreign key not found exception
	 */
	public static void rule(List<DeviceComponent> deCo, Rule rule, ToDoChecker tdc) throws DBForeignKeyNotFoundException{
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
	 * Service.
	 *
	 * @param service the Service Object
	 */
	public static void service(Service service){
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
	 * Group.
	 *
	 * @param grp the Group to Insert
	 */
	public static void group(Group grp){
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
	 * User.
	 *
	 * @param user the User to Insert
	 */
	public static void user(User user){
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
	 * Zone.
	 *
	 * @param zone the Zone to Insert
	 * @param suzone the suzone
	 */
	public static void zone(Zone zone, Zone suzone){
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
	 * Device group.
	 *
	 * @param deid the deid
	 * @param grid the grid
	 * @param devgrp the DeviceGroup to Insert
	 * @throws Exception the exception
	 */
	public static void deviceGroup(int deid, short grid, DeviceGroup devgrp) throws Exception{
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
	 * Repeat rule.
	 *
	 * @param rr the RepeatRule to Insert
	 * @param ruleId the ID of the Rule
	 * @param deCoId the id of the DeviceComponent
	 * @param tdc the ToDoChecker of the Distributor
	 * @throws DBForeignKeyNotFoundException the DB foreign key not found exception
	 */
	public static void repeatRule(RepeatRule rr, int ruleId, int deCoId, ToDoChecker tdc) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		try{
			rr.setRule(Selects.rule(ruleId));
			rr.setDeviceComponent(Selects.deviceComponent(deCoId));
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("DeviceComponent-FK or Rule-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}		
		
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
		if(tdc != null)
			Interpretor.createTodos(tdc);
	}
	
	/**
	 * To do.
	 *
	 * @param todo the ToDo to Insert
	 * @param rrId the rr id
	 * @param tdc the ToDoChecker of the Distributor
	 * @throws DBForeignKeyNotFoundException the DB foreign key not found exception
	 */
	public static void toDo(ToDo todo, int rrId, ToDoChecker tdc) throws DBForeignKeyNotFoundException{
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
	 * To do without change.
	 *
	 * @param todo the ToDo to Insert
	 * @param rrId the rr id
	 * @throws DBForeignKeyNotFoundException the DB foreign key not found exception
	 */
	public static void toDoWithoutChange(ToDo todo, int rrId) throws DBForeignKeyNotFoundException{
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
	 * Defaults.
	 */
	public static void defaults(){
//		Session session = connection.getSession();
//		session.beginTransaction();

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
		
//		session.getTransaction().commit();
//		session.close();
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