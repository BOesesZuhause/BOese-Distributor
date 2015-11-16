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

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import de.bo.aid.boese.exceptions.DBForeignKeyNotFoundException;
import de.bo.aid.boese.main.Distributor;
import de.bo.aid.boese.model.*;
import de.bo.aid.boese.ruler.Control;
import de.bo.aid.boese.ruler.Interpretor;
import de.bo.aid.boese.ruler.TimeTodos;
import de.bo.aid.boese.ruler.ToDoChecker;

// TODO: Auto-generated Javadoc
/**
 * The Class Updates.
 */
public class Updates {
	
	/** The connection. */
	private static Connection connection = Connection.getConnection();
	
	/**
	 * Value.
	 *
	 * @param value the value
	 * @param decoid the decoid
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void value(double value, int decoid) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		DeviceComponent deco = null;
		try{
			deco = Selects.deviceComponent(decoid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("DeviceComponent-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		deco.setCurrentValue(new BigDecimal(value));
		LogDeviceComponent logdeco = new LogDeviceComponent();
		logdeco.setDeviceComponent(deco);
		logdeco.setTimestamp(new Date());
		logdeco.setValue(new BigDecimal(value));
		
		session.save(deco);
		session.save(logdeco);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Unit.
	 *
	 * @param uid the uid
	 * @param name the name
	 * @param symbol the symbol
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void unit (int uid, String name, String symbol) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Unit unit = null;
		try{
			unit = Selects.unit(uid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("Unit-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}	
		if (name != null){
			unit.setName(name);
		}
		if (symbol != null){
			unit.setSymbol(symbol);
		}
		
		session.save(unit);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Component.
	 *
	 * @param coid the coid
	 * @param unit the unit
	 * @param name the name
	 * @param sensor the sensor
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void component (int coid, Unit unit, String name, boolean sensor) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Component comp = null;
		try{
			comp = Selects.component(coid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("Component-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		if (unit != null){
			comp.setUnit(unit);
		}
		if (name != null){
			comp.setName(name);
		}
		comp.setSensor(sensor);
		
		session.save(unit);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Device component.
	 *
	 * @param decoid the decoid
	 * @param status the status
	 * @param description the description
	 * @param logrule the logrule
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void DeviceComponent (int decoid, int status, String description, double logrule) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		DeviceComponent deco = null;
		try{
			deco = Selects.deviceComponent(decoid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("DeviceComponent-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		if (status != -1){
			deco.setStatus(status);
		}
		if (description != null){
			deco.setDescription(description);
		}
		if (logrule != -1){
			deco.setLogRule(new BigDecimal(logrule));
		}
		
		session.save(deco);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Device.
	 *
	 * @param deid the deid
	 * @param alias the alias
	 * @param serial the serial
	 * @param purchase the purchase
	 * @param zone the zone
	 * @param con the con
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void device (int deid, String alias, String serial, Date purchase, Zone zone, Connector con) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		Device dev = null;
		try{
			dev = Selects.device(deid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("Device-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		if (alias != null){
			dev.setAlias(alias);
		}
		if (serial != null){
			dev.setSerialNumber(serial);
		}
		if (purchase != null){
			dev.setPurchaseDate(purchase);
		}
		if (zone != null){
			dev.setZone(zone);
		}
		if (con != null){
			dev.setConnector(con);
		}
		
		session.save(dev);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Connector.
	 *
	 * @param conid the conid
	 * @param name the name
	 * @param pw the pw
	 * @param status the status
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void connector(int conid, String name, String pw, int status) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		Connector con = null;
		try{
			con = Selects.connector(conid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("Connector-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		if (name != null){
			con.setName(name);
		}
		if (pw != null){
			con.setPassword(pw);
		}
		if (status != -1){
			con.setStatus(status);;
		}
		
		session.save(con);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Service.
	 *
	 * @param seid the seid
	 * @param description the description
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void service(int seid, String description) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		Service service = null;
		try{
			service = Selects.service(seid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("Service-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		if (description != null){
			service.setDescription(description);
		}
		
		session.save(service);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Group.
	 *
	 * @param grid the grid
	 * @param name the name
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void group(short grid, String name) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		Group grp = null;
		try{
			grp = Selects.group(grid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("Group-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		if(name != null){
			grp.setName(name);
		}
		
		session.save(grp);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * User.
	 *
	 * @param uid the uid
	 * @param surname the surname
	 * @param firstname the firstname
	 * @param pw the pw
	 * @param gender the gender
	 * @param username the username
	 * @param mail the mail
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void user(int uid, String surname, String firstname, String pw, boolean gender, String username, String mail) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		User user = null;
		try{
			user = Selects.user(uid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("User-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		if(surname != null){
			user.setSurname(surname);
		}
		if(firstname != null){
			user.setFirstName(firstname);
		}
		if(pw != null){
			user.setPassword(pw);
		}
		user.setGender(gender);
		if(username != null){
			user.setUserName(username);
		}
		if(mail != null){
			user.setEmail(mail);
		}
		
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Zone.
	 *
	 * @param zoid the zoid
	 * @param name the name
	 * @param suzone the suzone
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void zone(int zoid, String name, Zone suzone) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Zone zone = null;
		try{
			zone = Selects.zone(zoid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("Zone-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		if(name != null){
			zone.setName(name);
		}
		if(suzone != null){
			zone.setZone(suzone);
		}
		
		session.save(zone);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Rule.
	 *
	 * @param ruid the ruid
	 * @param active the active
	 * @param permissions the permissions
	 * @param conditions the conditions
	 * @param actions the actions
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void rule(int ruid, boolean active, String permissions, String conditions, String actions) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Rule rule = null;
		try{
			rule = Selects.rule(ruid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("Rule-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		rule.setActive(active);
		if(permissions != null){
			rule.setPermissions(permissions);
		}
		if(conditions != null){
			rule.setConditions(conditions);
		}
		if(actions != null){
			rule.setActions(actions);
		}
		rule.setModifyDate(new Date());
		
		session.save(rule);
		session.getTransaction().commit();
		session.close();
		
		Distributor.changeInRule(ruid);
		new ToDoChecker().changeInToDo();
	}
	
	/**
	 * Activate rule.
	 *
	 * @param ruid the ruid
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void activateRule(int ruid) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Rule rule = null;
		try{
			Selects.rule(ruid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("Rule-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		rule.setActive(true);
		
		session.save(rule);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Deactivate rule.
	 *
	 * @param ruid the ruid
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void deactivateRule(int ruid) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Rule rule = null;
		try{
			Selects.rule(ruid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("Rule-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		rule.setActive(false);
		
		session.save(rule);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * update status of a deviceComponent.
	 *
	 * @param status the status
	 * @param decoId the DeviceComponentID
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void deviceComponentStatus(int status, int decoId) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		DeviceComponent deco = null;
		try{
			deco = Selects.deviceComponent(decoId);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("DeviceComponent-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		deco.setStatus(status);
		
		session.save(deco);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * update status of a connector.
	 *
	 * @param status the status
	 * @param coId the ConnectorID
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void connectorStatus(int status, int coId) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Connector co = null;
		try{
			co = Selects.connector(coId);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("Connector-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		co.setStatus(status);
		
		session.save(co);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * RepeatRule.
	 *
	 * @param rrId the rrId
	 * @param repeat the repeat
	 * @param value the value
	 * @param repeatsAfterEnd the repeatsAfterEnd
	 * @param tdc the ToDoChecker of the Distributor
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void repeatRule(int rrId, String repeat, Double value, int repeatsAfterEnd, ToDoChecker tdc) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		RepeatRule rr = null;
		try{
			rr = Selects.repeatRule(rrId);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("RepeatRule-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		if(repeat != null){
			rr.setRepeat(repeat);
		}
		if(value != null){
			rr.setValue(new BigDecimal(value));
		}
		if(repeatsAfterEnd >= 0){
			rr.setRepeatsAfterEnd(repeatsAfterEnd);
		}
		
		session.save(rr);
		session.getTransaction().commit();
		session.close();
		Interpretor.createTodos(tdc);
	}
	
	/**
	 * ToDo.
	 *
	 * @param toDoId the toDoId
	 * @param date the date
	 * @param active the active
	 * @throws DBForeignKeyNotFoundException 
	 */
	public static void toDo(int toDoId, Date date, boolean active) throws DBForeignKeyNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		ToDo todo = null;
		try{
			todo = Selects.toDo(toDoId);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBForeignKeyNotFoundException dfknfe = new DBForeignKeyNotFoundException("ToDo-FK not found");
			dfknfe.initCause(e.getCause());
			throw dfknfe;
		}
		todo.setActive(active);
		if(date != null){
			todo.setDate(date);
		}
		
		session.save(todo);
		session.getTransaction().commit();
		session.close();
		new ToDoChecker().changeInToDo();
	}
	
	/**
	 * Activate Todos.
	 *
	 * @param rrid the repeat rule id
	 */
	public static void activateTodo(int rrid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		List<ToDo> todo = Selects.toDoByRepeatRule(rrid);
		for(ToDo t : todo){
			t.setActive(true);
		}
		
		session.save(todo);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Deactivate Todos.
	 *
	 * @param rrid the repeat rule id
	 */
	public static void deactivateTodo(int rrid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		List<ToDo> todo = Selects.toDoByRepeatRule(rrid);
		for(ToDo t : todo){
			t.setActive(false);
		}
		
		session.save(todo);
		session.getTransaction().commit();
		session.close();
	}
}
