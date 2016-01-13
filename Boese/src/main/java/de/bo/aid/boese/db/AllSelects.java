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

import de.bo.aid.boese.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class AllSelects.
 *
 * @author Fabio
 * The Class AllSelects offers methods to get all Entities of a Table.
 */
public class AllSelects {

	/** The connection to the Database. */
    private static Connection connection = Connection.getConnection();

    /**
     * You shouldn't create a instance of this Object.
     */
    private AllSelects() {

    }

    /**
	 * Select all saved Units.
	 *
	 * @return a list filled with all Unit Entities
	 */
    public static List<Unit> units() {
        Session session = connection.getSession();
        session.beginTransaction();

        List<?>  erg = session.createQuery("from Unit").list();
        List<Unit> unit = new ArrayList<Unit>();
        for (Object o : erg) {
            unit.add((Unit) o);
        }

        session.getTransaction().commit();
        session.close();

        return unit;
    }

    /**
	 * Select all saved Components.
	 *
	 * @return a list filled with all Component Entities
	 */
    public static List<Component> components() {
        Session session = connection.getSession();
        session.beginTransaction();

        List<?> erg = session.createQuery("from Component").list();
        List<Component> comp = new ArrayList<Component>();
        for (Object o : erg) {
            comp.add((Component) o);
        }

        session.getTransaction().commit();
        session.close();

        return comp;
    }

	/**
	 * Select all saved Devices.
	 *
	 * @return a list filled with all Device Entities
	 */
    public static List<Device> devices() {
        Session session = connection.getSession();
        session.beginTransaction();

        List<?> erg = session.createQuery("from Device").list();
        List<Device> dev = new ArrayList<Device>();
        for (Object o : erg) {
            dev.add((Device) o);
        }

        session.getTransaction().commit();
        session.close();

        return dev;
    }

    /**
	 * Select all saved Services.
	 *
	 * @return a list filled with all Service Entities
	 */
    public static List<Service> services() {
        Session session = connection.getSession();
        session.beginTransaction();

        List<?> erg = session.createQuery("from Service").list();
        List<Service> serv = new ArrayList<Service>();
        for (Object o : erg) {
            serv.add((Service) o);
        }

        session.getTransaction().commit();
        session.close();

        return serv;
    }

    /**
	 * Select all saved Connectors.
	 *
	 * @return a list filled with all Connector Entities
	 */
    public static List<Connector> connector() {
        Session session = connection.getSession();
        session.beginTransaction();

        List<?> erg = session.createQuery("from Connector").list();
        List<Connector> con = new ArrayList<Connector>();
        for (Object o : erg) {
            con.add((Connector) o);
        }

        session.getTransaction().commit();
        session.close();

        return con;
    }

    /**
	 * Select all saved Rules.
	 *
	 * @return a list filled with all Rule Entities
	 */
    public static List<Rule> rules() {
        Session session = connection.getSession();
        session.beginTransaction();

        List<?> erg = session.createQuery("from Rule").list();
        List<Rule> rule = new ArrayList<Rule>();
        for (Object o : erg) {
            rule.add((Rule) o);
        }

        session.getTransaction().commit();
        session.close();

        return rule;
    }

    /**
	 * Select all saved Groups.
	 *
	 * @return a list filled with all Groups Entities
	 */
    public static List<Group> groups() {
        Session session = connection.getSession();
        session.beginTransaction();

        List<?> erg = session.createQuery("from Group").list();
        List<Group> grp = new ArrayList<Group>();
        for (Object o : erg) {
            grp.add((Group) o);
        }

        session.getTransaction().commit();
        session.close();

        return grp;
    }

    /**
	 * Select all saved Zones.
	 *
	 * @return a list filled with all Zone Entities
	 */
    public static List<Zone> zones() {
        Session session = connection.getSession();
        session.beginTransaction();

        List<?> erg = session.createQuery("from Zone").list();
        List<Zone> zone = new ArrayList<Zone>();
        for (Object o : erg) {
            zone.add((Zone) o);
        }

        session.getTransaction().commit();
        session.close();

        return zone;
    }

    /**
	 * Select all saved Users.
	 *
	 * @return a list filled with all User Entities
	 */
    public static List<User> users() {
        Session session = connection.getSession();
        session.beginTransaction();

        List<?> erg = session.createQuery("from User").list();
        List<User> user = new ArrayList<User>();
        for (Object o : erg) {
            user.add((User) o);
        }

        session.getTransaction().commit();
        session.close();

        return user;
    }

    /**
	 * Select all saved RepeatRules.
	 *
	 * @return a list filled with all RepeatRule Entities
	 */
    public static List<RepeatRule> repeatRules() {
        Session session = connection.getSession();
        session.beginTransaction();

        List<?> erg = session.createQuery("from RepeatRule").list();
        List<RepeatRule> rr = new ArrayList<RepeatRule>();
        for (Object o : erg) {
            rr.add((RepeatRule) o);
        }

        session.getTransaction().commit();
        session.close();

        return rr;
    }

    /**
	 * Select all saved ToDos.
	 *
	 * @return a list filled with all ToDo Entities
	 */
    public static List<ToDo> toDos() {
        Session session = connection.getSession();
        session.beginTransaction();

        List<?> erg = session.createQuery("from ToDo").list();
        List<ToDo> todo = new ArrayList<ToDo>();
        for (Object o : erg) {
            if (((ToDo) o).isActive())
                todo.add((ToDo) o);
        }

        session.getTransaction().commit();
        session.close();

        return todo;
    }

}
