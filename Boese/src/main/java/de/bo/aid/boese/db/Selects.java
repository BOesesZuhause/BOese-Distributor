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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Selects.
 *
 * @author Fabio
 * The Class Selects offers Methods to Select a Entity.
 */
public class Selects {
	
	/** The connection to the Database. */
	private static Connection connection = Connection.getConnection();
	
	/**
	 * You shouldn't create a instance of this Object.
	 */
	private Selects(){
		
	}
	
	/**
	 * Selects a Connector.
	 *
	 * @param coid the ID of the desired Connector
	 * @return the connector Entity
	 * @throws DBObjectNotFoundException when the Connector was not found
	 */
	public static Connector connector(int coid) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Connector con = (Connector)session.get(Connector.class, new Integer(coid));
		if(con != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("Connector not found");
		}
		
		session.close();
		
		return con;
	}
	
	/**
	 * Select a List of Devices connected to a specified Connector.
	 *
	 * @param coid the ID of the desired Connector
	 * @return a list of Devices which are connected with the desired Connector
	 */
	public static List<Device> connectorDeviceList(int coid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List<?> erg = session.createQuery("from Device where coid = " + coid).list();
		List<Device> device = new ArrayList<Device>();
		for(Object o: erg){
			device.add((Device) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return device;
	}
	
	/**
	 * Selects the Current value of a DeviceComponent.
	 *
	 * @param decoid the ID of the desired DeviceComponent
	 * @return the Value
	 * @throws DBObjectNotFoundException when the DeviceComponent was not found
	 */
	public static double currentValue(int decoid) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();

		double d = 0.0;
		DeviceComponent deco = (DeviceComponent)session.get(DeviceComponent.class, new Integer(decoid));
		if(deco != null){
			d = deco.getCurrentValue().doubleValue();
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("DeviceComponent mit ID: " + decoid + " not found");
		}
		
		session.close();
		
		return d;
	}
	
	/**
	 * Selects a Device.
	 *
	 * @param deid the ID of the desired Device
	 * @return the device Entity
	 * @throws DBObjectNotFoundException when the Device was not found
	 */
	public static Device device (int deid) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Device dev = (Device)session.get(Device.class, new Integer(deid));
		if(dev != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("Device not found");
		}
		
		session.close();
		
		return dev;
	}
	
	/**
	 * Selects a DeviceComponent.
	 *
	 * @param decoid the ID of the desired DeviceComponent
	 * @return the deviceComponent Entity
	 * @throws DBObjectNotFoundException when the DeviceComponent was not found
	 */
	public static DeviceComponent deviceComponent(int decoid) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		DeviceComponent deco = (DeviceComponent)session.get(DeviceComponent.class, new Integer(decoid));
		if(deco != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("DeviceComponent not found");
		}
		
		session.close();
		
		return deco;
	}
	
	/**
	 * Selects Rules by a deviceComponent.
	 *
	 * @param decoid the ID of a DeviceComponent
	 * @return a List of Rules belonging to the specified DeviceComponent
	 */
	public static List<Rule> rulesByDeviceComponent(int decoid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List<?> erg = session.createQuery( "from DeviceComponentRule where deCoId = " + decoid).list();
		List<DeviceComponentRule> decorule = new ArrayList<DeviceComponentRule>();
		for(Object o: erg){
			decorule.add((DeviceComponentRule) o);
		}
		List<Rule> rule = new ArrayList<Rule>();
		for(DeviceComponentRule dcr: decorule){
			Rule r = dcr.getRule();
			if(r.getActive()){
				rule.add(r);
			}
		}
		
		session.getTransaction().commit();
		session.close();
		
		return rule;
	}
	
	/**
	 * Selects DeviceComponents by a Rule.
	 *
	 * @param ruid the ID of a Rule
	 * @return a List of DeviceComponets belonging to the specified Rule
	 */
	public static List<DeviceComponent> deviceComponentsByRule(int ruid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List<?> erg = session.createQuery( "from DeviceComponentRule where ruId = " + ruid).list();
		List<DeviceComponentRule> decorule = new ArrayList<DeviceComponentRule>();
		for(Object o: erg){
			decorule.add((DeviceComponentRule) o);
		}
		List<DeviceComponent> deco = new ArrayList<DeviceComponent>();
		for(DeviceComponentRule dcr: decorule){
			deco.add(dcr.getDevicecomponent());
		}
		
		session.getTransaction().commit();
		session.close();
		
		return deco;
	}
	
	/**
	 * Selects a Rule.
	 *
	 * @param ruid the ID of the desired Rule
	 * @return the rule Entity
	 * @throws DBObjectNotFoundException when the Rule was not found
	 */
	public static Rule rule(int ruid) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Rule rule = (Rule)session.get(Rule.class, new Integer(ruid));
		if(rule != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("Rule not found");
		}
		
		session.close();		
		
		return rule;
	}
	
	/**
	 * Selects a Unit.
	 *
	 * @param uid the ID of the desired Unit
	 * @return the unit Entity
	 * @throws DBObjectNotFoundException when the Unit was not found
	 */
	public static Unit unit(int uid) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Unit unit = (Unit)session.get(Unit.class, new Integer(uid));
		if(unit != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("Unit not found");
		}
		
		session.close();
		return unit;
	}

	/**
	 * Selects a Component.
	 *
	 * @param coid the ID of the desired Component
	 * @return the component Entity
	 * @throws DBObjectNotFoundException when the Component was not found
	 */
	public static Component component(int coid) throws DBObjectNotFoundException {
		Session session = connection.getSession();
		session.beginTransaction();
		
		Component comp =(Component)session.get(Component.class, new Integer(coid));
		if(comp != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("Component not found");
		}
		
		session.close();
		return comp;
	}

	/**
	 * Selects a Service.
	 *
	 * @param seid the ID of the desired Service
	 * @return the service Entity
	 * @throws DBObjectNotFoundException when the Service was not found
	 */
	public static Service service (int seid) throws DBObjectNotFoundException {
		Session session = connection.getSession();
		session.beginTransaction();
		
		Service service = (Service)session.get(Service.class, new Integer(seid));
		if(service != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("Service not found");
		}
		
		session.close();
		return service;
	}

	/**
	 * Selects a Group.
	 *
	 * @param grid the ID of the desired Group
	 * @return the group Entity
	 * @throws DBObjectNotFoundException when the Group was not found
	 */
	public static Group group(short grid) throws DBObjectNotFoundException {
		Session session = connection.getSession();
		session.beginTransaction();
		
		Group grp = (Group)session.get(Group.class, new Short(grid));
		if(grp != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("Group not found");
		}
		
		session.close();
		return grp;
	}

	/**
	 * Selects a User.
	 *
	 * @param uid the ID of the desired User
	 * @return the user Entity
	 * @throws DBObjectNotFoundException when the User was not found
	 */
	public static User user(int uid) throws DBObjectNotFoundException {
		Session session = connection.getSession();
		session.beginTransaction();
		
		User user = (User)session.get(User.class, new Integer(uid));
		if(user != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("User not found");
		}
		
		session.close();
		return user;
	}

	/**
	 * Selects a Zone.
	 *
	 * @param zoid the ID of the desired Zone
	 * @return the zone Entity
	 * @throws DBObjectNotFoundException when the Zone was not found
	 */
	public static Zone zone(int zoid) throws DBObjectNotFoundException {
		Session session = connection.getSession();
		session.beginTransaction();
		
		Zone zone = (Zone)session.get(Zone.class, new Integer(zoid));
		if(zone != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("Zone not found");
		}
		
		session.close();
		return zone;
	}
	
	/**
	 * Select the status of a deviceComponent.
	 *
	 * @param decoId the ID of the desired DeviceComponent
	 * @return the status(short)
	 * @throws DBObjectNotFoundException when the DeviceComponent was not found
	 */
	public static int deviceComponentStatus(int decoId) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		DeviceComponent deco = (DeviceComponent)session.get(DeviceComponent.class, new Integer(decoId));
		if(deco != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("DeviceComponent not found");
		}
		
		session.close();
		
		return deco.getStatus();
	}
	
	/**
	 * Select the status of a Connector.
	 *
	 * @param coId the ID of the desired Connector
	 * @return the status(short)
	 * @throws DBObjectNotFoundException when the Connector was not found
	 */
	public static int ConnectorStatus(int coId) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
		
		Connector con = (Connector)session.get(Connector.class, new Integer(coId));
		if(con != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("Connector not found");
		}
		
		session.close();
		
		return con.getStatus();
	}

	/**
	 * Selects a RepeatRule.
	 *
	 * @param rrId the ID of the desired RepeatRule
	 * @return the repeatRule Entity
	 * @throws DBObjectNotFoundException when the RepeatRule was not found
	 */
	public static RepeatRule repeatRule(int rrId) throws DBObjectNotFoundException {
		Session session = connection.getSession();
		session.beginTransaction();
		
		RepeatRule rr = (RepeatRule)session.get(RepeatRule.class, new Integer(rrId));
		if(rr != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("RepeatRule not found");
		}
		
		session.close();
		return rr;
	}

	/**
	 * Selects a ToDo.
	 *
	 * @param toDoId the ID of the desired ToDo
	 * @return the toDo Entity
	 * @throws DBObjectNotFoundException when the ToDo was not found
	 */
	public static ToDo toDo(int toDoId) throws DBObjectNotFoundException {
		Session session = connection.getSession();
		session.beginTransaction();
		
		ToDo todo = (ToDo)session.get(ToDo.class, new Integer(toDoId));
		if(todo != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("ToDo not found");
		}
		
		session.close();
		return todo;
	}
	
	/**
	 * Selects ToDos by a RepeatRule.
	 *
	 * @param rrid the ID of the desired RepeatRule
	 * @return a List of ToDos belonging to the specified RepeatRule
	 */
	public static List<ToDo> toDoByRepeatRule(int rrid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List<?> erg = session.createQuery( "from ToDo where rrid = " + rrid).list();
		List<ToDo> todo = new ArrayList<ToDo>();
		for(Object o: erg){
			todo.add((ToDo) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return todo;
	}
	
	/**
	 * Selects a deviceGroup.
	 *
	 * @param deid the ID of the desired Device
	 * @param grpid the ID of the desired Group
	 * @return the Device Group Entity
	 * @throws DBObjectNotFoundException when the DeviceGroup was not found
	 */	
	public static DeviceGroup deviceGroup(int deid, short grpid) throws DBObjectNotFoundException{
		Session session = connection.getSession();
		session.beginTransaction();
 
		DeviceGroup devgrp = (DeviceGroup)session.get(DeviceGroup.class, new DeviceGroupId(deid, grpid));
		if(devgrp != null){
			session.getTransaction().commit();
		}
		else{
			session.getTransaction().rollback();
			session.close();
			throw new DBObjectNotFoundException("DeviceGroup not found");
		}
		
		session.close();
		return devgrp;
	}
}
