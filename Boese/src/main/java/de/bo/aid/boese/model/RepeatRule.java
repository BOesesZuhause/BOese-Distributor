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
 * The Class RepeatRule.
 */
public class RepeatRule {
	
	/** The rr id. */
	private int rrId;
	
	/** The repeat. */
	private String repeat;
	
	/**  The Value, which will be executed. */
	private BigDecimal value;
	
	/** The repeats after end. */
	private int repeatsAfterEnd;
	
	/** The Rule. */
	private Rule rule;
	
	/** The to do. */
	private Set<ToDo> toDo;
	
	/** The DeviceComponent. */
	private DeviceComponent deviceComponent;

	/**
	 * Instantiates a new repeat rule.
	 */
	public RepeatRule() {
	}

	/**
	 * Instantiates a new repeat rule.
	 *
	 * @param rrId the rr id
	 * @param repeat the repeat
	 * @param value the value
	 * @param repeatsAfterEnd the repeats after end
	 */
	public RepeatRule(int rrId, String repeat, BigDecimal value, int repeatsAfterEnd) {
		this.rrId = rrId;
		this.repeat = repeat;
		this.value = value;
		this.repeatsAfterEnd = repeatsAfterEnd;
	}

	/**
	 * Instantiates a new repeat rule.
	 *
	 * @param rrId the rr id
	 * @param repeat the repeat
	 * @param value the value
	 * @param repeatsAfterEnd the repeats after end
	 * @param rule the rule
	 * @param toDo the to do
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
	 * Gets the rr id.
	 *
	 * @return the rr id
	 */
	public int getRrId() {
		return rrId;
	}

	/**
	 * Sets the rr id.
	 *
	 * @param rrId the new rr id
	 */
	public void setRrId(int rrId) {
		this.rrId = rrId;
	}

	/**
	 * Gets the repeat.
	 *
	 * @return the repeat
	 */
	public String getRepeat() {
		return repeat;
	}

	/**
	 * Sets the repeat.
	 *
	 * @param repeat the new repeat
	 */
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * Gets the repeats after end.
	 *
	 * @return the repeats after end
	 */
	public int getRepeatsAfterEnd() {
		return repeatsAfterEnd;
	}

	/**
	 * Sets the repeats after end.
	 *
	 * @param repeatsAfterEnd the new repeats after end
	 */
	public void setRepeatsAfterEnd(int repeatsAfterEnd) {
		this.repeatsAfterEnd = repeatsAfterEnd;
	}

	/**
	 * Gets the rule.
	 *
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * Sets the rule.
	 *
	 * @param rule the new rule
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}

	/**
	 * Gets the to do.
	 *
	 * @return the to do
	 */
	public Set<ToDo> getToDo() {
		return toDo;
	}

	/**
	 * Sets the to do.
	 *
	 * @param toDo the new to do
	 */
	public void setToDo(Set<ToDo> toDo) {
		this.toDo = toDo;
	}

	/**
	 * Gets the device component.
	 *
	 * @return the device component
	 */
	public DeviceComponent getDeviceComponent() {
		return deviceComponent;
	}

	/**
	 * Sets the device component.
	 *
	 * @param deviceComponent the new device component
	 */
	public void setDeviceComponent(DeviceComponent deviceComponent) {
		this.deviceComponent = deviceComponent;
	}

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
