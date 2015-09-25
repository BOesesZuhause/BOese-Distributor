package de.bo.aid.boese.db;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import de.bo.aid.boese.constants.Errors;
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
	 */
	public static Connector connector(int coid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Connector con = new Connector();
		try{
			session.load(con, new Integer(coid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return new Connector(Errors.OBJECT_NOT_FOUND);
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
	 */
	public static double currentValue(int decoid){
		Session session = connection.getSession();
		session.beginTransaction();
 
		DeviceComponent deco = new DeviceComponent();
		double d = 0.0;
		try{
			session.load(deco, new Integer(decoid));
			d = deco.getCurrentValue().doubleValue();
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return Errors.OBJECT_NOT_FOUND;
		}
		
		session.close();
		
		return d;
	}
	
	/**
	 * Device.
	 *
	 * @param deid the deid
	 * @return the device
	 */
	public static Device device (int deid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Device dev = new Device();
		try{
			session.load(dev, new Integer(deid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return new Device(Errors.OBJECT_NOT_FOUND);
		}
		
		session.close();
		
		return dev;
	}
	
	/**
	 * Device component.
	 *
	 * @param decoid the decoid
	 * @return the device component
	 */
	public static DeviceComponent deviceComponent(int decoid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		DeviceComponent deco = new DeviceComponent();
		try{
			session.load(deco, new Integer(decoid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return new DeviceComponent(Errors.OBJECT_NOT_FOUND);
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
			Rule r = rule(dcr.getRule().getRuId());
			if(r.getActive()){
				rule.add(r);
			}
		}
		
		session.getTransaction().commit();
		session.close();
		
		return rule;
	}
	
	/**
	 * Rule.
	 *
	 * @param ruid the ruid
	 * @return the rule
	 */
	public static Rule rule(int ruid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Rule rule = new Rule();
		try{
			session.load(rule, new Integer(ruid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return new Rule(Errors.OBJECT_NOT_FOUND);
		}
		
		session.close();		
		
		return rule;
	}
	
	/**
	 * Unit.
	 *
	 * @param uid the uid
	 * @return the unit
	 */
	public static Unit unit(int uid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Unit unit = new Unit();
		try{
			session.load(unit, new Integer(uid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return new Unit(Errors.OBJECT_NOT_FOUND);
		}
		
		session.close();
		return unit;
	}

	/**
	 * Component.
	 *
	 * @param coid the coid
	 * @return the component
	 */
	public static Component component(int coid) {
		Session session = connection.getSession();
		session.beginTransaction();
		
		Component comp = new Component();
		try{
			session.load(comp, new Integer(coid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return new Component();
		}
		
		session.close();
		return comp;
	}

	/**
	 * Service.
	 *
	 * @param seid the seid
	 * @return the service
	 */
	public static Service service (int seid) {
		Session session = connection.getSession();
		session.beginTransaction();
		
		Service service = new Service();
		try{
			session.load(service, new Integer(seid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return new Service();
		}
		
		session.close();
		return service;
	}

	/**
	 * Group.
	 *
	 * @param grid the grid
	 * @return the group
	 */
	public static Group group(short grid) {
		Session session = connection.getSession();
		session.beginTransaction();
		
		Group grp = new Group();
		try{
			session.load(grp, new Short(grid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return new Group();
		}
		
		session.close();
		return grp;
	}

	/**
	 * User.
	 *
	 * @param uid the uid
	 * @return the user
	 */
	public static User user(int uid) {
		Session session = connection.getSession();
		session.beginTransaction();
		
		User user = new User();
		try{
			session.load(user, new Integer(uid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return new User();
		}
		
		session.close();
		return user;
	}

	/**
	 * Zone.
	 *
	 * @param zoid the zoid
	 * @return the zone
	 */
	public static Zone zone(int zoid) {
		Session session = connection.getSession();
		session.beginTransaction();
		
		Zone zone = new Zone();
		try{
			session.load(zone, new Integer(zoid));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return new Zone();
		}
		
		session.close();
		return zone;
	}
	
	/**
	 * All devices.
	 *
	 * @return the list
	 */
	public static List<Device> allDevices(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Device").list();
		List<Device> dev = new ArrayList<Device>();
		for(Object o: erg){
			dev.add((Device) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return dev;
	}
	
	/**
	 * Select status of a deviceComponent
	 *
	 * @param decoId the DeviceComponentID
	 * @return the status(short) 
	 */
	public static short deviceComponentStatus(int decoId){
		Session session = connection.getSession();
		session.beginTransaction();
		
		DeviceComponent deco = new DeviceComponent();
		try{
			session.load(deco, new Integer(decoId));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return -1;
		}
		
		session.close();
		
		return deco.getStatus();
	}
	
	/**
	 * Select status of a Connector
	 *
	 * @param coId the ConnectorID
	 * @return the status(short) 
	 */
	public static short ConnectorStatus(int coId){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Connector con = new Connector();
		try{
			session.load(con, new Integer(coId));
			session.getTransaction().commit();
		}
		catch (ObjectNotFoundException onfe){
			session.getTransaction().rollback();
			session.close();
			return -1;
		}
		
		session.close();
		
		return con.getStatus();
	}

}
