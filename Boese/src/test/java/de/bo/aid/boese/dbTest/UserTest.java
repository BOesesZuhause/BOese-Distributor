/*
 * 
 */
package de.bo.aid.boese.dbTest;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.db.Updates;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class UserTest.
 */
public class UserTest {

	/** The user1. */
	private User user1; 
	
	/** The user2. */
	private User user2;
	
	/** The user1 update. */
	private User user1Update;
	
	/** The user2 update. */
	private User user2Update;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boese", "localhost", "5432");
		user1 = new User("Person", "erste", "123", true, new Date(), "first", "erste.person@first.de");
		user2 = new User("Person", "zweite", "456", true, new Date(), "second", "zweite.person@second.de");
		user1Update = new User("update", "erste", "789", true, new Date(), "ufirst", "erste.update@ufirst.de");
		user2Update = new User("update", "erste", "abc", true, new Date(), "first", "erste.update@ufirst.de");
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		
		//Insert User 1
		insert(user1);
		user1Update.setUsId(user1.getUsId());
		//Insert User 2
		insert(user2);
		user2Update.setUsId(user2.getUsId());

		//User 1 equal User in DB?
		equal(user1, "1");
		//User 2 equal User in DB?
		equal(user2, "2");
		
		//Update User 1
		update(user1, user1Update);
		//Update User 2
		update(user2, user2Update);
		
		//User 1 after update equal User in DB?
		equal(user1Update, "1Update");
		//User 1 after update equal User in DB?
		equal(user2Update, "2Update");
		
	}
	
	/**
	 * Insert.
	 *
	 * @param user the user
	 */
	private void insert(User user){
		Inserts.user(user);;
	}
	
	/**
	 * Select.
	 *
	 * @param id the id
	 * @return the user
	 */
	private User select(int id){
		User user = null;
		try {
			user = Selects.user(id);
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + id);
		}
		return user;
	}
	
	/**
	 * Equal.
	 *
	 * @param user the user
	 * @param name the name
	 */
	private void equal(User user, String name){
		User userTest = select(user.getUsId());
		assertTrue("User " + name + " Firstname not equal", user.getFirstName().equals(userTest.getFirstName()));
		assertTrue("User " + name + " Surname not equal", user.getSurname().equals(userTest.getSurname()));
		assertTrue("User " + name + " Password not equal", user.getPassword().equals(userTest.getPassword()));
		assertTrue("User " + name + " Gender not equal", user.getGender().equals(userTest.getGender()));
//		assertTrue("User " + name + " Birthdate not equal", user.getBirthdate().equals(userTest.getBirthdate()));
		assertTrue("User " + name + " Username not equal", user.getUserName().equals(userTest.getUserName()));
		assertTrue("User " + name + " Mail not equal", user.getEmail().equals(userTest.getEmail()));
		assertTrue("User " + name + " ID not equal", user.getUsId() == userTest.getUsId());
	}
	
	/**
	 * Update.
	 *
	 * @param user the user
	 * @param userUpdate the user update
	 */
	private void update(User user, User userUpdate){
		try {
			Updates.user(user, userUpdate.getSurname(), userUpdate.getFirstName(), userUpdate.getPassword(), userUpdate.getGender(), userUpdate.getUserName(), userUpdate.getEmail());
		} catch (DBObjectNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage() + "with ID: " + user.getUsId());
		}
	}
}
