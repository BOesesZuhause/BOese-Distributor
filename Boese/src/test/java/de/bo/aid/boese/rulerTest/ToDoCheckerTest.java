package de.bo.aid.boese.rulerTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bo.aid.boese.DB.util.HibernateUtil;
import de.bo.aid.boese.db.AllSelects;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.exceptions.DBForeignKeyNotFoundException;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
import de.bo.aid.boese.model.Component;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.model.RepeatRule;
import de.bo.aid.boese.model.ToDo;
import de.bo.aid.boese.ruler.ToDoChecker;

/**
 * The Class ToDoCheckerTest.
 *
 * @author Spieki
 */
public class ToDoCheckerTest {
	
//	ToDoChecker tdc;
//	
//	Connector con;
//	Device dev;
//	Component comp;
//	DeviceComponent deco;
//	
//	RepeatRule rr2min;
//	RepeatRule rrfest;
//	
//	/**
//	 * 
//	 */
//	@Before
//	public void setUp(){
//		HibernateUtil.setDBUser("postgres");
//		HibernateUtil.setDBPassword("Di0bPWfw");
//		HibernateUtil.setDBURL("boeseTest", "localhost", "5432");
//		HibernateUtil.setDBAuto("create");
//		
//		tdc = new ToDoChecker();
//		tdc.start();
//		
//		Inserts.defaults();		
//		con = new Connector("leer", "123", false);
//		dev = new Device("leer", "123");
//		comp = new Component("leer", true);
//		deco = new DeviceComponent("leer", -1000.0, 1000.0, true);
//		rr2min = new RepeatRule("+2; *; *; *; *; *", new BigDecimal(100), 0);
//		int min = new Date().getMinutes()+1;
//		int hour = new Date().getHours();
//		if(min == 60){
//			min = 0;
//			hour++;
//		}
//		rrfest = new RepeatRule(min + "; " + hour + "; *; *; *; *", new BigDecimal(100), 0);
//		try {
//			Inserts.connector(con);
//			Inserts.device(con.getCoId(), 1, dev);
//			Inserts.component(1, comp);
//			Inserts.deviceComponent(dev.getDeId(), comp.getCoId(), deco);
//		}
//		catch (Exception e) {
//			fail(e.getMessage());
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 
//	 */
//	@Test
//	public void test() {
//		try {
//			Inserts.repeatRule(rr2min , 1, deco.getDeCoId(), tdc);
////			Inserts.repeatRule(rrfest , 1, deco.getDeCoId(), tdc);
//		} catch (DBForeignKeyNotFoundException e) {
//			fail(e.getMessage());
//			e.printStackTrace();
//		}
//		List<ToDo> todos = AllSelects.toDos();
//		for(ToDo todo : todos){
//			System.out.println(todo.getDate().toString());
//		}
//	}
}
