/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 */



package de.bo.aid.boese.ruler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.model.Component;
import de.bo.aid.boese.model.Connector;
import de.bo.aid.boese.model.Device;
import de.bo.aid.boese.model.DeviceComponent;
import de.bo.aid.boese.model.RepeatRule;
import de.bo.aid.boese.model.Rule;
import de.bo.aid.boese.model.ToDo;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeTester.
 */
public class TimeTester{
	
	/** The todo. */
	static List<ToDo> todo = new ArrayList<ToDo>();
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		
		ToDoChecker tdc = new ToDoChecker();
		tdc.start();
		
		try{
			Connector con = new Connector("leer", "123");
			Inserts.connector(con);
			Device dev = new Device("leer", "123");
			Inserts.device(con.getCoId(), 0, dev);
			Component comp = new Component("leer", true);
			Inserts.component(0, comp);
			DeviceComponent deco = new DeviceComponent("leer", -1000.0, 1000.0);
			Inserts.deviceComponent(dev.getDeId(), comp.getCoId(), deco);
		
			List<DeviceComponent> decoList = new ArrayList<DeviceComponent>();
			decoList.add(deco);
			Rule rule = new Rule("", "", "");
			Inserts.rule(decoList, rule, tdc);
			RepeatRule rr = new RepeatRule("30; 21; *; *; *; *", new BigDecimal(100), 0);
			Inserts.repeatRule(rr , rule.getRuId(), deco.getDeCoId(), tdc);
			ToDo todo = new ToDo(new Date());
			Inserts.toDo(todo, rr.getRrId(), tdc);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
//		System.out.println(todoid);
		
		
//		Date now = new Date();
////		System.out.println("jetzt: " + now.toString());
//		String cron = "30, 21, *, *, *, *";
//		TimeFormat tf = new TimeFormat(cron);
//		System.out.println(tf.toString());
//		System.out.println(tf.getDate().toString());
//		int[] i = {99, 0};
//		i = DayCalculator.getRealDayCalc(26, i, 11, 1995);
//		System.out.println(i[0] + "   +" + i[1]);
//		i[0] = 99;
//		i[1] = 0;
//		i = DayCalculator.getRealDayCalcNeg(4, i, 3, 1996);
//		System.out.println(i[0] + "   " + i[1]);
		
		
////		Interpretor.createTodos();
//		ToDoChecker tdc = new ToDoChecker();
//		tdc.start();
//		tdc.changeInToDo();
	}
	
	/**
	 * Fill list.
	 */
	public static void fillList(){
		
		
	}
	
}
