package de.bo.aid.boese.db.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.main.DistributorProperties;

public class TestdataTest {

	DistributorProperties props;

	@Before
	public void setUp() throws Exception {
		JPAUtil.setDBUser("postgres");
		JPAUtil.setDBPassword("Di0bPWfw");
		JPAUtil.setDBURL("boeseTest", "localhost", 5432);
		JPAUtil.init(); 
	}

	@Test
	public void test() {
		TestdataHelper.insertTestData();
	}
	
	@After
	public void tearDown(){
		JPAUtil.close();
	}

}
