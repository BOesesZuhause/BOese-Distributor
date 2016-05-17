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
import java.util.Set;

import javax.persistence.EntityManager;

import de.bo.aid.boese.DB.util.JPAUtil;
import de.bo.aid.boese.dao.DAOHandler;
import de.bo.aid.boese.dao.ToDoDAO;
import de.bo.aid.boese.main.ProtocolHandler;
import de.bo.aid.boese.modelJPA.RepeatRule;
import de.bo.aid.boese.modelJPA.ToDo;
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
	
	private DAOHandler daohandler;
	
//	Empfänger e;
	
	/** A Variable to lock the Thread. */
	boolean b;
	
	/**
	 * Instantiates a new to do checker.
	 * 
	 * @param ph the ProtocolHandler instance of the Distributor
	 */
	public ToDoChecker(ProtocolHandler ph){
		daohandler = DAOHandler.getInstance();
		this.ph = ph;
		b = true;
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Set<ToDo> todos = daohandler.getToDoDAO().getAll(em);
		em.getTransaction().commit();
		em.close();
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
		EntityManager em;
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
					ToDoDAO tododao = daohandler.getToDoDAO();
					em = JPAUtil.getEntityManager();
					while(tt != null && isPast(tt.getDate())){
						em.getTransaction().begin();
						ToDo todo = tododao.get(em, tt.getId());
						RepeatRule rr = todo.getRepeatRule();
						tododao.delete(em, todo);
						if(rr.getRrId() == 0){
							todo = null;
						}
						else{
							todo.setDate(new TimeFormat(rr.getRepeat()).getDateForRepeatRule());
							todo.setRepeatRule(rr);
							tododao.create(em, todo);
							ttlnew.add(new TimeTodos(todo.getToDoId(), todo.getDate(), todo.getRepeatRule().getValue().doubleValue(), todo.getRepeatRule().getDeviceComponent()));
						}
						ttl.remove(tt);
						if(!ttl.isEmpty()){
							tt = ttl.iterator().next();
						}
						else{
							tt = null;
						}
						em.getTransaction().commit();
					}
					while(tt != null && sameTime(tt.getDate())){
						em.getTransaction().begin();
						todos.add(new ComponentXML(tt.getDeco().getDeCoId(), tt.getValue()));
						ToDo todo = tododao.get(em, tt.getId());
						RepeatRule rr = todo.getRepeatRule();
						tododao.delete(em, todo);
						if(rr.getRrId() == 0){
							todo = null;
						}
						else{
							todo.setDate(new TimeFormat(rr.getRepeat()).getDateForRepeatRule());
							todo.setRepeatRule(rr);
							tododao.create(em, todo);
							ttlnew.add(new TimeTodos(todo.getToDoId(), todo.getDate(), todo.getRepeatRule().getValue().doubleValue(), todo.getRepeatRule().getDeviceComponent()));
						}
						ttl.remove(tt);
						if(!ttl.isEmpty()){
							tt = ttl.iterator().next();
						}
						else{
							tt = null;
						}
						em.getTransaction().commit();
					}
					em.close();
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
	 * Fills the ToDo List if something has been changed.
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

		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Set<ToDo> todos = daohandler.getToDoDAO().getAll(em);
		em.getTransaction().commit();
		em.close();
		this.ttl = new ArrayList<TimeTodos>();
		for(ToDo t : todos){
			this.ttl.add(new TimeTodos(t.getToDoId(), t.getDate(), t.getRepeatRule().getValue().doubleValue(), t.getRepeatRule().getDeviceComponent()));
		}
		Collections.sort(this.ttl);
	}
	
	/**
	 * Checks if the Date is now.
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
