package de.bo.aid.boese.hibernate.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	 	private static final EntityManagerFactory emFactory;
		static {
			   emFactory = Persistence.createEntityManagerFactory("Service");
		}
		public static EntityManager getEntityManager(){
			return emFactory.createEntityManager();
		}
		public static void close(){
			emFactory.close();
		}
}
