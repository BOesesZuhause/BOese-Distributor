

package de.bo.aid.boese.db;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import de.bo.aid.boese.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class AllSelects.
 */
public class AllSelects {
	
	/** The connection. */
	private static Connection connection = Connection.getConnection();

	/**
	 * All Units.
	 *
	 * @return the list
	 */
	public static List<Unit> Units(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Unit").list();
		List<Unit> unit = new ArrayList<Unit>();
		for(Object o: erg){
			unit.add((Unit) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return unit;
	}
	
	/**
	 * All Components.
	 *
	 * @return the list
	 */
	public static List<Component> Components(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Component").list();
		List<Component> comp = new ArrayList<Component>();
		for(Object o: erg){
			comp.add((Component) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return comp;
	}
	
	/**
	 * All devices.
	 *
	 * @return the list
	 */
	public static List<Device> Devices(){
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
	 * All services.
	 *
	 * @return the list
	 */
	public static List<Service> Services(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Service").list();
		List<Service> serv = new ArrayList<Service>();
		for(Object o: erg){
			serv.add((Service) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return serv;
	}
	
	/**
	 * All connectors.
	 *
	 * @return the list
	 */
	public static List<Connector> Connector(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Connector").list();
		List<Connector> con = new ArrayList<Connector>();
		for(Object o: erg){
			con.add((Connector) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return con;
	}
	
	/**
	 * All rules.
	 *
	 * @return the list
	 */
	public static List<Rule> Rules(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Rule").list();
		List<Rule> rule = new ArrayList<Rule>();
		for(Object o: erg){
			rule.add((Rule) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return rule;
	}
	
	/**
	 * All groups.
	 *
	 * @return the list
	 */
	public static List<Group> Groups(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Group").list();
		List<Group> grp = new ArrayList<Group>();
		for(Object o: erg){
			grp.add((Group) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return grp;
	}
	
	/**
	 * All zones.
	 *
	 * @return the list
	 */
	public static List<Zone> Zones(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Zone").list();
		List<Zone> zone = new ArrayList<Zone>();
		for(Object o: erg){
			zone.add((Zone) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return zone;
	}
	
	/**
	 * All users.
	 *
	 * @return the list
	 */
	public static List<User> Users(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from User").list();
		List<User> user = new ArrayList<User>();
		for(Object o: erg){
			user.add((User) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return user;
	}
	
	/**
	 * All repeatRules.
	 *
	 * @return the list
	 */
	public static List<RepeatRule> repeatRules(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from Repeat_Rule").list();
		List<RepeatRule> rr = new ArrayList<RepeatRule>();
		for(Object o: erg){
			rr.add((RepeatRule) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return rr;
	}
	
	/**
	 * All ToDos.
	 *
	 * @return the list
	 */
	public static List<ToDo> toDos(){
		Session session = connection.getSession();
		session.beginTransaction();
 
		List erg = session.createQuery("from ToDo").list();
		List<ToDo> todo = new ArrayList<ToDo>();
		for(Object o: erg){
			todo.add((ToDo) o);
		}
		
		session.getTransaction().commit();
		session.close();
		
		return todo;
	}
	
}
