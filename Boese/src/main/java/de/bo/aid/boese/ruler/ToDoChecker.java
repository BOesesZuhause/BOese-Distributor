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
import de.bo.aid.boese.db.Inserts;
import de.bo.aid.boese.db.Selects;
import de.bo.aid.boese.model.RepeatRule;
import de.bo.aid.boese.model.ToDo;
import de.bo.aid.boese.xml.ComponentXML;

// TODO: Auto-generated Javadoc
/**
 * The Class ToDoChecker.
 */
public class ToDoChecker extends Thread{
	
	/** The ttl. */
	List<TimeTodos> ttl;
	
	/** The b. */
	boolean b;
	
	/**
	 * Instantiates a new to do checker.
	 */
	public ToDoChecker(){
		b = true;
		List<ToDo> todos = AllSelects.toDos();
		ttl = new ArrayList<TimeTodos>();
		for(ToDo t : todos){
			ttl.add(new TimeTodos(t.getToDoId(), t.getDate(), t.getRepeatRule().getValue().doubleValue(), t.getRepeatRule().getDeviceComponent()));
		}
		Collections.sort(ttl);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		while(true){
			if(ttl.size() == 0){
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
					while(isPast(tt.getDate())){
						
						ToDo todo = Selects.toDo(tt.getId());
						RepeatRule rr = todo.getRepeatRule();
						Deletes.ToDo(todo);
						if(rr.getRrId() == 0){
							todo = null;
						}
						else{
//							todo = Selects.toDo(Inserts.toDoWithoutChange(new TimeFormat(rr.getRepeat()).getDate(), rr.getRrId()));
							todo.setDate(new TimeFormat(rr.getRepeat()).getDate());
							Inserts.toDoWithoutChange(todo, rr.getRrId());
							ttl.add(new TimeTodos(todo.getToDoId(), todo.getDate(), todo.getRepeatRule().getValue().doubleValue(), todo.getRepeatRule().getDeviceComponent()));
						}
						ttl.remove(tt);
						tt = ttl.iterator().next();
					}
					while(sameTime(tt.getDate())){
						todos.add(new ComponentXML(tt.getDeco().getDeCoId(), tt.getValue()));
						ToDo todo = Selects.toDo(tt.getId());
						RepeatRule rr = todo.getRepeatRule();
						Deletes.ToDo(todo);
						if(rr.getRrId() == 0){
							todo = null;
						}
						else{
//							todo = Selects.toDo(Inserts.toDoWithoutChange(new TimeFormat(rr.getRepeat()).getDate(), rr.getRrId()));
							todo.setDate(new TimeFormat(rr.getRepeat()).getDate());
							Inserts.toDoWithoutChange(todo, rr.getRrId());
							ttl.add(new TimeTodos(todo.getToDoId(), todo.getDate(), todo.getRepeatRule().getValue().doubleValue(), todo.getRepeatRule().getDeviceComponent()));
						}
						ttl.remove(tt);
						tt = ttl.iterator().next();
					}
					Collections.sort(this.ttl);
					// TODO MainClass.sendToDos(todos);
					b = false;
					sleep(1000*60);
					b = true;
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		}
	}
	
	/**
	 * Change in to do.
	 */
	public void changeInToDo(){
		while(b){
		}
		List<ToDo> todos = AllSelects.toDos();
		this.ttl = new ArrayList<TimeTodos>();
		for(ToDo t : todos){
			this.ttl.add(new TimeTodos(t.getToDoId(), t.getDate(), t.getRepeatRule().getValue().doubleValue(), t.getRepeatRule().getDeviceComponent()));
		}
		Collections.sort(this.ttl);
	}
	
	/**
	 * Same time.
	 *
	 * @param tf the tf
	 * @return true, if successful
	 */
	private boolean sameTime(TimeFormat tf){
		return (tf.getDate().getTime() / 60000) == (new Date().getTime() / 60000);
	}
	
	/**
	 * Checks if is past.
	 *
	 * @param tf the tf
	 * @return true, if is past
	 */
	private boolean isPast(TimeFormat tf){
		return (tf.getDate().getTime() / 60000) < (new Date().getTime() / 60000);
	}
}
