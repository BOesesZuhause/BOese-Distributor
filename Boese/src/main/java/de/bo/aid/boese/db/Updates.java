package de.bo.aid.boese.db;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import de.bo.aid.boese.model.*;

public class Updates {
	
	private static Connection connection = Connection.getConnection();
	
	public static void value(double value, int decoid){
		Session session = connection.getSession();
		session.beginTransaction();
		
		DeviceComponent deco = Selects.deviceComponent(decoid);
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
	
	public static void unit (int uid, String name, String symbol){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Unit unit = Selects.unit(uid);
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
	
	public static void component (int coid, Unit unit, String name, boolean sensor){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Component comp = Selects.component(coid);
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

	public static void DeviceComponent (int decoid, short status, String description, double logrule){
		Session session = connection.getSession();
		session.beginTransaction();

		DeviceComponent deco = Selects.deviceComponent(decoid);
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
	
	public static void device (int deid, String alias, String serial, Date purchase, Zone zone, Connector con){
		Session session = connection.getSession();
		session.beginTransaction();

		Device dev = Selects.device(deid);
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
	
	public static void connector(int conid, String name, String pw){
		Session session = connection.getSession();
		session.beginTransaction();

		Connector con = Selects.connector(conid);
		if (name != null){
			con.setName(name);
		}
		if (pw != null){
			con.setPassword(pw);
		}
		
		session.save(con);
		session.getTransaction().commit();
		session.close();
	}
	
	public static void service(int seid, String description){
		Session session = connection.getSession();
		session.beginTransaction();

		Service service = Selects.service(seid);
		if (description != null){
			service.setDescription(description);
		}
		
		session.save(service);
		session.getTransaction().commit();
		session.close();
	}
	
	public static void group(int grid, String name){
		Session session = connection.getSession();
		session.beginTransaction();

		Group grp = Selects.group(grid);
		if(name != null){
			grp.setName(name);
		}
		
		session.save(grp);
		session.getTransaction().commit();
		session.close();
	}
	
	public static void user(int uid, String surname, String firstname, String pw, boolean gender, String username, String mail){
		Session session = connection.getSession();
		session.beginTransaction();

		User user = Selects.user(uid);
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
	
	public static void zone(int zoid, String name, Zone suzone){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Zone zone = Selects.zone(zoid);
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
	
	public static void rule(int ruid, boolean active, String permissions, String conditions, String actions){
		Session session = connection.getSession();
		session.beginTransaction();
		
		Rule rule = Selects.rule(ruid);
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
	}

}