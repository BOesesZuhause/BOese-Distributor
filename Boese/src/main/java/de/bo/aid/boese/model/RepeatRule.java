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


package de.bo.aid.boese.model;

import java.math.BigDecimal;
import java.util.Set;

public class RepeatRule {
	
	private int rrId;
	
	private String repeat;
	
	/** The Value, which will be executed */
	private BigDecimal value;
	
	private int repeatsAfterEnd;
	
	/** The Rule. */
	private Rule rule;
	
	private Set<ToDo> toDo;
	
	/** The DeviceComponent. */
	private DeviceComponent deviceComponent;

	public RepeatRule() {
	}

	public RepeatRule(int rrId, String repeat, BigDecimal value, int repeatsAfterEnd) {
		this.rrId = rrId;
		this.repeat = repeat;
		this.value = value;
		this.repeatsAfterEnd = repeatsAfterEnd;
	}

	public RepeatRule(int rrId, String repeat, BigDecimal value, int repeatsAfterEnd, Rule rule, Set<ToDo> toDo,
			DeviceComponent deviceComponent) {
		super();
		this.rrId = rrId;
		this.repeat = repeat;
		this.value = value;
		this.repeatsAfterEnd = repeatsAfterEnd;
		this.rule = rule;
		this.toDo = toDo;
		this.deviceComponent = deviceComponent;
	}

	public int getRrId() {
		return rrId;
	}

	public void setRrId(int rrId) {
		this.rrId = rrId;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public int getRepeatsAfterEnd() {
		return repeatsAfterEnd;
	}

	public void setRepeatsAfterEnd(int repeatsAfterEnd) {
		this.repeatsAfterEnd = repeatsAfterEnd;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public Set<ToDo> getToDo() {
		return toDo;
	}

	public void setToDo(Set<ToDo> toDo) {
		this.toDo = toDo;
	}

	public DeviceComponent getDeviceComponent() {
		return deviceComponent;
	}

	public void setDeviceComponent(DeviceComponent deviceComponent) {
		this.deviceComponent = deviceComponent;
	}

}
