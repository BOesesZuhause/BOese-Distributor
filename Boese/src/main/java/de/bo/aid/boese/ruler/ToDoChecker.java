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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.bo.aid.boese.db.AllSelects;
import de.bo.aid.boese.db.Deletes;
//import de.bo.aid.boese.db.Empfänger;
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.main.ProtocolHandler;
import de.bo.aid.boese.model.RepeatRule;
import de.bo.aid.boese.model.ToDo;
import de.bo.aid.boese.xml.ComponentXML;

// TODO: Auto-generated Javadoc
/**
 * The Class ToDoChecker checks every Minute a List of ToDos if a ToDo has to be Executed. This Class runs in a own Thread
 */
public class ToDoChecker extends Thread{
	
	/** The List of TimeToDos. */
	List<TimeTodos> ttl;
	
	/** The instance of the ProtocolHandler to send the ToDos to the Connectors. */
	ProtocolHandler ph;
	
//	Empfänger e;
	
	/** A Variable to lock the Thread. */
	boolean b;
	
	/**
	 * Instantiates a new to do checker.
	 * 
	 * @param ph the ProtocolHandler instance of the Distributor
	 */
	public ToDoChecker(ProtocolHandler ph){
		this.ph = ph;
		b = true;
		List<ToDo> todos = AllSelects.toDos();
		ttl = new ArrayList<TimeTodos>();
		for(ToDo t : todos){
			ttl.add(new TimeTodos(t.getToDoId(), t.getDate(), t.getRepeatRule().getValue().doubleValue(), t.getRepeatRule().getDeviceComponent()));
		}
		Collections.sort(ttl);
	}
	
	/**
	 *  This Thread checks every Minute if a ToDo has to be Executed or is in the Past. Then all ToDos will be sent to The Protocol Handler.
	 *  After this they will deleted in the Database and if necessary with a new Date created.
	 */
	public void run(){
		while(true){
			if(ttl.isEmpty()){
				try {
					b = false;
					sleep(1000*5);
					b = true;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				List<TimeTodos> ttlnew = new ArrayList<>();
				try {
					TimeTodos tt = new TimeTodos();
					try{
						tt = ttl.iterator().next();
					}
					catch(Exception e){
						e.printStackTrace();
						System.exit(0);
					}
					List<ComponentXML> todos = new ArrayList<ComponentXML>();
					while(tt != null && isPast(tt.getDate())){					
						ToDo todo = Selects.toDo(tt.getId());
						RepeatRule rr = todo.getRepeatRule();
						Deletes.toDo(todo);
						if(rr.getRrId() == 0){
							todo = null;
						}
						else{
//							todo = Selects.toDo(Inserts.toDoWithoutChange(new TimeFormat(rr.getRepeat()).getDate(), rr.getRrId()));
							todo.setDate(new TimeFormat(rr.getRepeat()).getDate());
							Inserts.toDoWithoutChange(todo, rr.getRrId());
							ttlnew.add(new TimeTodos(todo.getToDoId(), todo.getDate(), todo.getRepeatRule().getValue().doubleValue(), todo.getRepeatRule().getDeviceComponent()));
						}
						ttl.remove(tt);
						if(!ttl.isEmpty()){
							tt = ttl.iterator().next();
						}
						else{
							tt = null;
						}
					}
					while(tt != null && sameTime(tt.getDate())){
						todos.add(new ComponentXML(tt.getDeco().getDeCoId(), tt.getValue()));
						ToDo todo = Selects.toDo(tt.getId());
						RepeatRule rr = todo.getRepeatRule();
						Deletes.toDo(todo);
						if(rr.getRrId() == 0){
							todo = null;
						}
						else{
//							todo = Selects.toDo(Inserts.toDoWithoutChange(new TimeFormat(rr.getRepeat()).getDate(), rr.getRrId()));
							todo.setDate(new TimeFormat(rr.getRepeat()).getDate());
							Inserts.toDoWithoutChange(todo, rr.getRrId());
							ttlnew.add(new TimeTodos(todo.getToDoId(), todo.getDate(), todo.getRepeatRule().getValue().doubleValue(), todo.getRepeatRule().getDeviceComponent()));
						}
						ttl.remove(tt);
						if(!ttl.isEmpty()){
							tt = ttl.iterator().next();
						}
						else{
							tt = null;
						}
					}
					Collections.sort(this.ttl);
					System.out.println(toDosToString(todos) + "Todos werden gesendet");
//					if(ph != null){
						ph.sendToDos(todos);
//					}
//					else{
//						e.send();
//					}
					b = false;
					sleep(1000*60);
					b = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				ttl.addAll(ttlnew);
			}
		}
	}
	
	/**
	 * Fills the ToDo List if something has been changed
	 */
	public void changeInToDo(){
		//TODO anders lösen
	    while(b){
	    	try {
				sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<ToDo> todos = AllSelects.toDos();
		this.ttl = new ArrayList<TimeTodos>();
		for(ToDo t : todos){
			this.ttl.add(new TimeTodos(t.getToDoId(), t.getDate(), t.getRepeatRule().getValue().doubleValue(), t.getRepeatRule().getDeviceComponent()));
		}
		Collections.sort(this.ttl);
	}
	
	/**
	 * Checks if the Date is now
	 *
	 * @param tf the Date to be checked as TimeFormat
	 * @return true, if the Date ist now
	 */
	private boolean sameTime(TimeFormat tf){
		return (tf.getDate().getTime() / 60000) == (new Date().getTime() / 60000);
	}
	
	/**
	 * Checks if the Date is past.
	 *
	 * @param tf the Date to be checked as TimeFormat
	 * @return true, if the Date is past
	 */
	private boolean isPast(TimeFormat tf){
		return (tf.getDate().getTime() / 60000) < (new Date().getTime() / 60000);
	}
	
	/**
	 * To dos to string only a test Method can be deleted.
	 *
	 * @param todos the todos
	 * @return the string
	 */
	private String toDosToString(List<ComponentXML> todos){
		String returner = "";
		for(ComponentXML todo : todos){
			returner += todo.toStringForToDoChecker() + "\n";
		}
		return returner;
	}
}
