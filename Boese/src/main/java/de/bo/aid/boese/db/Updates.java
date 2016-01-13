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

import org.hibernate.Session;

import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.model.*;
import de.bo.aid.boese.ruler.Interpreter;
import de.bo.aid.boese.ruler.ToDoChecker;

// TODO: Auto-generated Javadoc
/**
 * The Class Updates.
 *
 * @author Fabio
 * The Class Updates offers Methods to update Database Entities.
 */
public class Updates {
	
	/** The connection to the Database. */
	private static Connection connection = Connection.getConnection();
	
	
	/**
	 * You shouldn't create a instance of this Object.
	 */
	private Updates(){
		
	}
	
	/**
	 * Update a Value.
	 *
	 * @param value the new value
	 * @param decoid the ID of the DeviceComponent which will be updates
	 * @throws DBObjectNotFoundException when the DeviceComponent was not Found
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
	 * Update a Unit.
	 *
	 * @param unit the unit object which will be Updates
	 * @param name the new name
	 * @param symbol the mew symbol
	 * @throws DBObjectNotFoundException when the Unit was not found
	 */
	public static void unit (Unit unit, String name, String symbol) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
			
		if (name != null){
			unit.setName(name);
		}
		if (symbol != null){
			unit.setSymbol(symbol);
		}
		
		try{
			session.update(unit);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Unit not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
	}
	
	/**
	 * Update a Component.
	 *
	 * @param comp the Component object which will be Updates
	 * @param unit the new unit
	 * @param name the new name
	 * @param actor the new actor
	 * @throws DBObjectNotFoundException when the Component was not found
	 */
	public static void component (Component comp, Unit unit, String name, boolean actor) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		if (unit != null){
			comp.setUnit(unit);
		}
		if (name != null){
			comp.setName(name);
		}
		comp.setActor(actor);
		
		try{
			session.update(comp);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Component not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
	}

	/**
	 * Update a Device component.
	 *
	 * @param deco the DeviceComponent object which will be Updates
	 * @param dev the new Device
	 * @param comp the new Component
	 * @param status the new status
	 * @param description the new description
	 * @param logrule the new logrule
	 * @param loggen the new loggen
	 * @throws DBObjectNotFoundException when the DeviceComponent was not found
	 */
	public static void DeviceComponent (DeviceComponent deco, Device dev, Component comp, int status, String description, double logrule, boolean loggen) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		if (dev != null){
			deco.setDevice(dev);
		}
		if (comp != null){
			deco.setComponent(comp);
		}if (status != -1){
			deco.setStatus(status);
		}
		if (description != null){
			deco.setDescription(description);
		}
		if (logrule != -1){
			deco.setLogRule(new BigDecimal(logrule));
		}
		deco.setLoggen(loggen);
		
		try{
			session.update(deco);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("DeviceComponent not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
	}
	
	/**
	 * Update a Device.
	 *
	 * @param dev the Device object which will be Updates
	 * @param alias the new alias
	 * @param serial the new serial
	 * @param purchase the mew purchasedate
	 * @param zone the new zone
	 * @param con the new Connector
	 * @throws DBObjectNotFoundException when the Device was not found
	 */
	public static void device (Device dev, String alias, String serial, Date purchase, Zone zone, Connector con) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

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
		
		try{
			session.update(dev);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Device not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
	}
	
	/**
	 * Update a Connector.
	 *
	 * @param con the Connector object which will be Updates
	 * @param name the new name
	 * @param pw the new password
	 * @param status the new status
	 * @param userConnector the new userConnector
	 * @throws DBObjectNotFoundException when the Connector was not found
	 */
	public static void connector(Connector con, String name, String pw, int status, boolean userConnector) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		if (name != null){
			con.setName(name);
		}
		if (pw != null){
			con.setPassword(pw);
		}
		if (status != -1){
			con.setStatus(status);;
		}
		con.setUserConnector(userConnector);
		
		try{
			session.update(con);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Connector not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
	}
	
	/**
	 * Update a Service.
	 *
	 * @param service the Service object which will be Updates
	 * @param description the new description
	 * @throws DBObjectNotFoundException when the Serveice was not found
	 */
	public static void service(Service service, String description) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		if (description != null){
			service.setDescription(description);
		}
		
		try{
			session.update(service);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Service not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
	}
	
	/**
	 * Update a Group.
	 *
	 * @param grp the Group object which will be Updates
	 * @param name the new name
	 * @throws DBObjectNotFoundException when the Group was not found
	 */
	public static void group(Group grp, String name) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		if(name != null){
			grp.setName(name);
		}
		
		try{
			session.update(grp);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Group not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
	}
	
	/**
	 * Update a User.
	 *
	 * @param user the User object which will be Updates
	 * @param surname the new surname
	 * @param firstname the new firstname
	 * @param pw the new password
	 * @param gender the new gender
	 * @param username the new username
	 * @param mail the new E-Mail adress
	 * @throws DBObjectNotFoundException when the User was not found
	 */
	public static void user(User user, String surname, String firstname, String pw, boolean gender, String username, String mail) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

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
		
		try{
			session.update(user);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("User not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
	}
	
	/**
	 * Update a Zone.
	 *
	 * @param zone the Zone object which will be Updates
	 * @param name the new name
	 * @param suzone the new superzone
	 * @throws DBObjectNotFoundException the DB object not found exception
	 */
	public static void zone(Zone zone, String name, Zone suzone) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		if(name != null){
			zone.setName(name);
		}
		if(suzone != null){
			zone.setZone(suzone);
		}
		
		try{
			session.update(zone);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Zone not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
	}
	
	/**
	 * Update a Rule.
	 *
	 * @param rule the Rule object which will be Updates
	 * @param active the new active
	 * @param permissions the new permissions
	 * @param conditions the new conditions
	 * @param actions the new actions
	 * @param tdc the ToDoChecker instance of the Distributor
	 * @throws DBObjectNotFoundException when the Rule was not found
	 */
	public static void rule(Rule rule, boolean active, String permissions, String conditions, String actions, ToDoChecker tdc) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
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
		
		try{
			session.update(rule);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Rule not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}		
		session.close();
		if(tdc != null)
			tdc.changeInToDo();
	}
	
	/**
	 * Update a Rule for Test.
	 *
	 * @param rule the Rule object which will be Updates
	 * @param active the new active
	 * @param permissions the new permissions
	 * @param conditions the new conditions
	 * @param actions the new actions
	 * @throws DBObjectNotFoundException when the rule was not found
	 */
	public static void ruleForTest(Rule rule, boolean active, String permissions, String conditions, String actions) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
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
		
		try{
			session.update(rule);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("Rule not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
	}
	
	/**
	 * Activate a rule.
	 *
	 * @param ruid the rule object which will be Updates
	 * @throws DBObjectNotFoundException when the rule was not found
	 */
	public static void activateRule(int ruid) throws DBObjectNotFoundException{
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
		rule.setActive(true);
		
		session.saveOrUpdate(rule);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Deactivate a rule.
	 *
	 * @param ruid the rule object which will be Updates
	 * @throws DBObjectNotFoundException when the rule was not found
	 */
	public static void deactivateRule(int ruid) throws DBObjectNotFoundException{
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
		rule.setActive(false);
		
		session.saveOrUpdate(rule);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * update status of a deviceComponent.
	 *
	 * @param status the new status
	 * @param decoId the the ID of the desired DeviceComponent
	 * @throws DBObjectNotFoundException when the DeviceComponent was not found
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
	 * @param status the new status
	 * @param coId the the ID of the desired Connector
	 * @throws DBObjectNotFoundException when the Connector was not found
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
	 * Update a RepeatRule.
	 *
	 * @param rr the RepeatRule object which will be Updates
	 * @param repeat the new repeat
	 * @param value the new value
	 * @param repeatsAfterEnd the new repeatsAfterEnd
	 * @param tdc the ToDoChecker instance of the Distributor
	 * @throws DBObjectNotFoundException when the RepeatRule was not found
	 */
	public static void repeatRule(RepeatRule rr, String repeat, Double value, int repeatsAfterEnd, ToDoChecker tdc) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		if(repeat != null){
			rr.setRepeat(repeat);
		}
		if(value != null){
			rr.setValue(new BigDecimal(value));
		}
		if(repeatsAfterEnd >= 0){
			rr.setRepeatsAfterEnd(repeatsAfterEnd);
		}
		
		try{
			session.update(rr);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("RepeatRule not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
		if(tdc != null)
			Interpreter.createTodos(tdc);
	}
	
	/**
	 * Update a ToDo.
	 *
	 * @param todo the ToDo object which will be Updates
	 * @param date the new execute date
	 * @param active the new active
	 * @param tdc the ToDoChecker instance of the Distributor
	 * @throws DBObjectNotFoundException when the ToDo was not found
	 */
	public static void toDo(ToDo todo, Date date, boolean active, ToDoChecker tdc) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		todo.setActive(active);
		if(date != null){
			todo.setDate(date);
		}
		
		try{
			session.update(todo);
			session.getTransaction().commit();
		}
		catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			DBObjectNotFoundException onfe = new DBObjectNotFoundException("ToDo not found");
			onfe.initCause(e.getCause());
			throw onfe;
		}
		
		session.close();
		if(tdc != null)
			tdc.changeInToDo();
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
