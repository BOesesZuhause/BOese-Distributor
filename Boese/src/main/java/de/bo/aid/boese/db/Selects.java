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

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Selects.
 */
public class Selects {
	
	/** The connection. */
	private static Connection connection = Connection.getConnection();
	
	/**
	 * Connector.
	 *
	 * @param coid the coid
	 * @return the connector
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Connector device list.
	 *
	 * @param coid the coid
	 * @return the list
	 */
	public static List<Device> connectorDeviceList(int coid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Device where coid = " + coid).list();
		List<Device> device = new ArrayList<Device>();
		for(Object o: erg){
			device.add((Device) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return device;
	}
	
	/**
	 * Current value.
	 *
	 * @param decoid the decoid
	 * @return the double
	 * @throws DBObjectNotFoundException the DB object not found exception
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
			throw new DBObjectNotFoundException("DeviceComponent not found");
		}
		
		session.close();
		
		return d;
	}
	
	/**
	 * Device.
	 *
	 * @param deid the deid
	 * @return the device
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Device component.
	 *
	 * @param decoid the decoid
	 * @return the device component
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Rules by device component.
	 *
	 * @param decoid the decoid
	 * @return the list
	 */
	public static List<Rule> rulesByDeviceComponent(int decoid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery( "from DeviceComponentRule where deCoId = " + decoid).list();
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
	 * Rules by device component.
	 *
	 * @param ruid the Rule ID
	 * @return the list
	 */
	public static List<DeviceComponent> deviceComponentsByRule(int ruid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery( "from DeviceComponentRule where ruId = " + ruid).list();
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
	 * Rule.
	 *
	 * @param ruid the ruid
	 * @return the rule
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Unit.
	 *
	 * @param uid the uid
	 * @return the unit
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Component.
	 *
	 * @param coid the coid
	 * @return the component
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Service.
	 *
	 * @param seid the seid
	 * @return the service
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Group.
	 *
	 * @param grid the grid
	 * @return the group
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * User.
	 *
	 * @param uid the uid
	 * @return the user
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Zone.
	 *
	 * @param zoid the zoid
	 * @return the zone
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Select status of a deviceComponent.
	 *
	 * @param decoId the DeviceComponentID
	 * @return the status(short)
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Select status of a Connector.
	 *
	 * @param coId the ConnectorID
	 * @return the status(short)
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Repeat rule.
	 *
	 * @param rrId the rr id
	 * @return the repeat rule
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * To do.
	 *
	 * @param toDoId the to do id
	 * @return the to do
	 * @throws DBObjectNotFoundException the DB object not found exception
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
	 * Rules by device component.
	 *
	 * @param rrid the Rule ID
	 * @return the list
	 */
	public static List<ToDo> toDoByRepeatRule(int rrid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery( "from ToDo where rrid = " + rrid).list();
		List<ToDo> todo = new ArrayList<ToDo>();
		for(Object o: erg){
			todo.add((ToDo) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return todo;
	}
	
	/**
	 * deviceGroup.
	 *
	 * @param deid the Device ID
	 * @param grpid the Group ID
	 * @return the Device Group
	 * @throws DBObjectNotFoundException the DB object not found exception
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
