package de.bo.aid.boese.hibernateTest;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import de.bo.aid.boese.hibernate.util.HibernateUtil;

public class mappingTest {

	@Test
	public void test() {
		//DEPRECATED
//		SessionFactory sessionFactory = new Configuration().configure()
//				.buildSessionFactory(); 
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.close();
	}

}
