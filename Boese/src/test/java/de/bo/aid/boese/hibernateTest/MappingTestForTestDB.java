package de.bo.aid.boese.hibernateTest;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import de.bo.aid.boese.hibernate.util.HibernateUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class mappingTestForTestDB.
 */
public class MappingTestForTestDB {

	@Test
	public void test() {
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boeseTest", "localhost", "5432");
		HibernateUtil.setDBAuto("create");
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.close();
	}

}
