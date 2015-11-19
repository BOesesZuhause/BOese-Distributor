package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.Service;

public class ServiceTest {

	private Service service1; 
	private Service service2;
	private Service service1Update;
	private Service service2Update;

	@Before
	public void setUp() throws Exception {
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boese", "localhost", "5432");
		
		service1 = new Service("test1");
		service2 = new Service("test2");
		service1Update = new Service("update1");
		service2Update = new Service("update2");
	}

	@Test
	public void test() {
		
		//Insert Service 1
		insert(service1);
		service1Update.setSeId(service1.getSeId());
		//Insert Service 2
		insert(service2);
		service2Update.setSeId(service2.getSeId());

		//Service 1 equal Service in DB?
		equal(service1, "1");
		//Service 2 equal Service in DB?
		equal(service2, "2");
		
		//Update Service 1
		update(service1, service1Update);
		//Update Service 2
		update(service2, service2Update);
		
		//Service 1 after update equal Service in DB?
		equal(service1Update, "1Update");
		//Service 1 after update equal Service in DB?
		equal(service2Update, "2Update");
		
	}
	
	private void insert(Service service){
		Inserts.service(service);
	}
	
	private Service select(int id){
		Service service = null;
		try {
			service = Selects.service(id);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + id);
		}
		return service;
	}
	
	private void equal(Service service, String name){
		Service serviceTest = select(service.getSeId());
		assertTrue("Service " + name + " Description not equal", service.getDescription().equals(serviceTest.getDescription()));
		assertTrue("Service " + name + " ID not equal", service.getSeId() == serviceTest.getSeId());
	}
	
	private void update(Service service, Service serviceUpdate){
		try {
			Updates.service(service, serviceUpdate.getDescription());
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + service.getSeId());
		}
	}
}
