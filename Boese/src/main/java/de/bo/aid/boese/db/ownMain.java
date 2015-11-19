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

import de.bo.aid.boese.constants.Status;
import de.bo.aid.boese.exceptions.DBObjectNotFoundException;
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
		
//		Connector con = new Connector("test", "123");
//		Inserts.connector(con);
//		Device dev = new Device("test", "test");
//		try {
//			Inserts.device(con.getCoId(), 0, dev);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(dev.getDeId() + "\t" + dev.getAlias());
		
		Zone z = new Zone("Without Sub");
		Inserts.zone(z, z);
		
		System.out.println("erledigt");
	}
}


