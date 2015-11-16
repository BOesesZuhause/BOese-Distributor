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



package de.bo.aid.boese.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.bo.aid.boese.hibernate.util.HibernateUtil;
import de.bo.aid.boese.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ownMain.
 */
public class ownMain {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		HibernateUtil.setDBUser("postgres");
		HibernateUtil.setDBPassword("Di0bPWfw");
		HibernateUtil.setDBURL("boese", "localhost", "5432");
		try{
			int compid = Inserts.component("bla", 0, true);
			int conid = Inserts.connector("bla", "bla");
			int deid = Inserts.device(conid, 0, "bla", "123456789");
			int deco1 = Inserts.deviceComponent(deid, compid, "1");
			int deco2 = Inserts.deviceComponent(deid, compid, "2");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("erledigt");
	}
}


