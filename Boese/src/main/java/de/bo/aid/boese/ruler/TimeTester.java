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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.model.ToDo;

public class TimeTester{
	
	static List<ToDo> todo = new ArrayList<ToDo>();
	
	public static void main(String[] args){
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
		
		
//		Interpretor.createTodos();
		ToDoChecker tdc = new ToDoChecker();
		tdc.start();
//		tdc.changeInToDo();
	}
	
	public static void fillList(){
		
		
	}
	
}
