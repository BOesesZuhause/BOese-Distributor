package de.bo.aid.boese.hibernateTest;


import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import de.bo.aid.boese.DB.util.HibernateUtil;
import de.bo.aid.boese.DB.util.JPAUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class mappingTestForTestDB.
 */
public class MappingTestForTestDB {

	/**
	 * Test.
	 */
	@Test
	public void test() {
		/*HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boeseTest", "localhost", 5432);
		HibernateUtil.setDBAuto("create");
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.close();*/
		EntityManager em = JPAUtil.getEntityManager();
	}

}
