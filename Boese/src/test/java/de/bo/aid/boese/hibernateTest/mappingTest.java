package de.bo.aid.boese.hibernateTest;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.Zone;

public class mappingTest {

	@Test
	public void test() {
		//DEPRECATED
//		SessionFactory sessionFactory = new Configuration().configure()
//				.buildSessionFactory(); 
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		//Beispiel zum Datenbanktugriff
//		session.beginTransaction();
// 
//		Zone zone = new Zone();
//		zone.setName("test");
//		session.save(zone);
// 
//		session.getTransaction().commit();
//		session.close();
	}

}
