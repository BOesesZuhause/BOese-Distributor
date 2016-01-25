package de.bo.aid.boese.hibernateTest;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import de.bo.aid.boese.hibernate.util.HibernateUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class mappingTest.
 */
public class MappingTest {

	/**
	 * Test.
	 */
	@Test
	public void test() {
		//DEPRECATED
//		SessionFactory sessionFactory = new Configuration().configure()
//				.buildSessionFactory(); 
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boese", "localhost", 5432);
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.close();
	}

}
