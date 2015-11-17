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

import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
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
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void value(double value, int decoid) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		DeviceComponent deco = null;
		try{
			deco = Selects.deviceComponent(decoid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("DeviceComponent not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		deco.setCurrentValue(new BigDecimal(value));
		LogDeviceComponent logdeco = new LogDeviceComponent();
		logdeco.setDeviceComponent(deco);
		logdeco.setTimestamp(new Date());
		logdeco.setValue(new BigDecimal(value));
		
		session.saveOrUpdate(deco);
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
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void unit (int uid, String name, String symbol) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Unit unit = null;
		try{
			unit = Selects.unit(uid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Unit not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}	
		if (name != null){
			unit.setName(name);
		}
		if (symbol != null){
			unit.setSymbol(symbol);
		}
		
		session.saveOrUpdate(unit);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Component.
	 *
	 * @param coid the coid
	 * @param unit the unit
	 * @param name the name
	 * @param actor the actor
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void component (int coid, Unit unit, String name, boolean actor) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Component comp = null;
		try{
			comp = Selects.component(coid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Component not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		if (unit != null){
			comp.setUnit(unit);
		}
		if (name != null){
			comp.setName(name);
		}
		comp.setActor(actor);
		
		session.saveOrUpdate(unit);
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
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void DeviceComponent (int decoid, int status, String description, double logrule) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		DeviceComponent deco = null;
		try{
			deco = Selects.deviceComponent(decoid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("DeviceComponent not found");
			onfe.initCause(e.getCause());
			throw onfe;
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
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void device (int deid, String alias, String serial, Date purchase, Zone zone, Connector con) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		Device dev = null;
		try{
			dev = Selects.device(deid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Device not found");
			onfe.initCause(e.getCause());
			throw onfe;
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
		
		session.saveOrUpdate(dev);
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
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void connector(int conid, String name, String pw, int status) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		Connector con = null;
		try{
			con = Selects.connector(conid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Connector not found");
			onfe.initCause(e.getCause());
			throw onfe;
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
		
		session.saveOrUpdate(con);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Service.
	 *
	 * @param seid the seid
	 * @param description the description
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void service(int seid, String description) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		Service service = null;
		try{
			service = Selects.service(seid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Service not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		if (description != null){
			service.setDescription(description);
		}
		
		session.saveOrUpdate(service);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Group.
	 *
	 * @param grid the grid
	 * @param name the name
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void group(short grid, String name) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		Group grp = null;
		try{
			grp = Selects.group(grid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Group not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		if(name != null){
			grp.setName(name);
		}
		
		session.saveOrUpdate(grp);
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
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void user(int uid, String surname, String firstname, String pw, boolean gender, String username, String mail) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		User user = null;
		try{
			user = Selects.user(uid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("User not found");
			onfe.initCause(e.getCause());
			throw onfe;
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
		
		session.saveOrUpdate(user);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Zone.
	 *
	 * @param zoid the zoid
	 * @param name the name
	 * @param suzone the suzone
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void zone(int zoid, String name, Zone suzone) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Zone zone = null;
		try{
			zone = Selects.zone(zoid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Zone not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		if(name != null){
			zone.setName(name);
		}
		if(suzone != null){
			zone.setZone(suzone);
		}
		
		session.saveOrUpdate(zone);
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
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void rule(int ruid, boolean active, String permissions, String conditions, String actions) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Rule rule = null;
		try{
			rule = Selects.rule(ruid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Rule not found");
			onfe.initCause(e.getCause());
			throw onfe;
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
		
		session.saveOrUpdate(rule);
		session.getTransaction().commit();
		session.close();
		
		Distributor.changeInRule(ruid);
		new ToDoChecker().changeInToDo();
	}
	
	/**
	 * Activate rule.
	 *
	 * @param ruid the ruid
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void activateRule(int ruid) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Rule rule = null;
		try{
			Selects.rule(ruid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Rule not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		rule.setActive(true);
		
		session.saveOrUpdate(rule);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Deactivate rule.
	 *
	 * @param ruid the ruid
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void deactivateRule(int ruid) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Rule rule = null;
		try{
			Selects.rule(ruid);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Rule not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		rule.setActive(false);
		
		session.saveOrUpdate(rule);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * update status of a deviceComponent.
	 *
	 * @param status the status
	 * @param decoId the DeviceComponentID
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void deviceComponentStatus(int status, int decoId) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		DeviceComponent deco = null;
		try{
			deco = Selects.deviceComponent(decoId);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("DeviceComponent not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		deco.setStatus(status);
		
		session.saveOrUpdate(deco);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * update status of a connector.
	 *
	 * @param status the status
	 * @param coId the ConnectorID
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void connectorStatus(int status, int coId) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Connector co = null;
		try{
			co = Selects.connector(coId);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Connector not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		co.setStatus(status);
		
		session.saveOrUpdate(co);
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
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void repeatRule(int rrId, String repeat, Double value, int repeatsAfterEnd, ToDoChecker tdc) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		RepeatRule rr = null;
		try{
			rr = Selects.repeatRule(rrId);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("RepeatRule not found");
			onfe.initCause(e.getCause());
			throw onfe;
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
		
		session.saveOrUpdate(rr);
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
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void toDo(int toDoId, Date date, boolean active) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		ToDo todo = null;
		try{
			todo = Selects.toDo(toDoId);
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("ToDo not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		todo.setActive(active);
		if(date != null){
			todo.setDate(date);
		}
		
		session.saveOrUpdate(todo);
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
		
		session.saveOrUpdate(todo);
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
		
		session.saveOrUpdate(todo);
		session.getTransaction().commit();
		session.close();
	}
}
