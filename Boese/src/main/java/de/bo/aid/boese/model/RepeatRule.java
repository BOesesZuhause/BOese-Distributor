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

// TODO: Auto-generated Javadoc
/**
 * RepeatRule Model for Hibernate.
 */
public class RepeatRule {
	
	/** The RepeatRule id. */
	private int rrId;
	
	/** The repeat Cron String. */
	private String repeat;
	
	/**  The Value, which will be executed. */
	private BigDecimal value;
	
	/** The number of repeats after end. */
	private int repeatsAfterEnd;
	
	/** The Rule belonging to this object. */
	private Rule rule;
	
	/** The ToDos  which were created. */
	private Set<ToDo> toDo;
	
	/** The DeviceComponent which will be switched. */
	private DeviceComponent deviceComponent;

	/**
	 * Instantiates a new repeat rule.
	 */
	public RepeatRule() {
		
	}

	/**
	 * Instantiates a new repeat rule for DB Insert.
	 *
	 * @param repeat the repeat Cron String
	 * @param value The Value, which will be executed
	 * @param repeatsAfterEnd the number of repeats after end
	 * @param rule the rule
	 * @param deco the deviceComponent
	 */
	public RepeatRule(String repeat, BigDecimal value, int repeatsAfterEnd, Rule rule, DeviceComponent deco) {
		this.repeat = repeat;
		this.value = value;
		this.repeatsAfterEnd = repeatsAfterEnd;
		this.rule = rule;
		this.deviceComponent = deco;
	}

	/**
	 * Instantiates a new repeat rule with all parameters.
	 *
	 * @param rrId the RepeatRule id
	 * @param repeat the repeat Cron String
	 * @param value The Value, which will be executed
	 * @param repeatsAfterEnd the number of repeats after end
	 */
	public RepeatRule(int rrId, String repeat, BigDecimal value, int repeatsAfterEnd) {
		this.rrId = rrId;
		this.repeat = repeat;
		this.value = value;
		this.repeatsAfterEnd = repeatsAfterEnd;
	}

	/**
	 * Instantiates a new repeat rule with all parameters and foreign keys.
	 *
	 * @param rrId the RepeatRule id
	 * @param repeat the repeat Cron String
	 * @param value The Value, which will be executed
	 * @param repeatsAfterEnd the number of repeats after end
	 * @param rule the rule this belonging to
	 * @param toDo the ToDos  which were created
	 * @param deviceComponent the device component
	 */
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

	/**
	 * Gets the RepeatRule id.
	 *
	 * @return the RepeatRule id
	 */
	public int getRrId() {
		return rrId;
	}

	/**
	 * Sets the RepeatRule id.
	 *
	 * @param rrId the new RepeatRule id
	 */
	public void setRrId(int rrId) {
		this.rrId = rrId;
	}

	/**
	 * Gets the repeat cron-String.
	 *
	 * @return the repeat cron-String
	 */
	public String getRepeat() {
		return repeat;
	}

	/**
	 * Sets the repeat cron-String.
	 *
	 * @param repeat the new repeat cron-String
	 */
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	/**
	 * Gets the value which will be executed.
	 *
	 * @return the value which will be executed
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * Sets the value which will be executed.
	 *
	 * @param value the new value which will be executed
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * Gets the number of repeats after end.
	 *
	 * @return the number of repeats after end
	 */
	public int getRepeatsAfterEnd() {
		return repeatsAfterEnd;
	}

	/**
	 * Sets the number of repeats after end.
	 *
	 * @param repeatsAfterEnd the new number of repeats after end
	 */
	public void setRepeatsAfterEnd(int repeatsAfterEnd) {
		this.repeatsAfterEnd = repeatsAfterEnd;
	}

	/**
	 * Gets the rule belonging to this object.
	 *
	 * @return the rule belonging to this object
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * Sets the rule belonging to this object.
	 *
	 * @param rule the new rule belonging to this object
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}

	/**
	 * Gets the ToDos  which were created.
	 *
	 * @return the ToDos  which were created
	 */
	public Set<ToDo> getToDo() {
		return toDo;
	}

	/**
	 * Sets the ToDos  which were created.
	 *
	 * @param toDo the new ToDos  which were created
	 */
	public void setToDo(Set<ToDo> toDo) {
		this.toDo = toDo;
	}

	/**
	 * Gets the DeviceComponent which will be switched.
	 *
	 * @return the DeviceComponent which will be switched
	 */
	public DeviceComponent getDeviceComponent() {
		return deviceComponent;
	}

	/**
	 * Sets the DeviceComponent which will be switched.
	 *
	 * @param deviceComponent the new DeviceComponent which will be switched
	 */
	public void setDeviceComponent(DeviceComponent deviceComponent) {
		this.deviceComponent = deviceComponent;
	}

	/**
	 *  
	 * To compare two RepeatRules.
	 *
	 * @param obj the RepeatRule object to compare
	 * @return true if both RepeatRules are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RepeatRule other = (RepeatRule) obj;
		if (deviceComponent == null) {
			if (other.deviceComponent != null)
				return false;
		} else if (!deviceComponent.equals(other.deviceComponent))
			return false;
		if (repeat == null) {
			if (other.repeat != null)
				return false;
		} else if (!repeat.equals(other.repeat))
			return false;
		if (repeatsAfterEnd != other.repeatsAfterEnd)
			return false;
		if (rrId != other.rrId)
			return false;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		if (toDo == null) {
			if (other.toDo != null)
				return false;
		} else if (!toDo.equals(other.toDo))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}