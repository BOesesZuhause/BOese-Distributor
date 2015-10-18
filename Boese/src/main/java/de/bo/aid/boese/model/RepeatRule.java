

package de.bo.aid.boese.model;

// TODO: Auto-generated Javadoc
/**
 * The Class RepeatRule.
 */
public class RepeatRule {
	
	/** The rr id. */
	private int rrId;
	
	/** The repeat. */
	private String repeat;
	
	/** The repeats after end. */
	private int repeatsAfterEnd;
	
	/** The to do. */
	private ToDo toDo;

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
	 * @param repeatsAfterEnd the repeats after end
	 */
	public RepeatRule(int rrId, String repeat, int repeatsAfterEnd) {
		this.rrId = rrId;
		this.repeat = repeat;
		this.repeatsAfterEnd = repeatsAfterEnd;
	}

	/**
	 * Instantiates a new repeat rule.
	 *
	 * @param rrId the rr id
	 * @param repeat the repeat
	 * @param repeatsAfterEnd the repeats after end
	 * @param todo the todo
	 */
	public RepeatRule(int rrId, String repeat, int repeatsAfterEnd, ToDo todo) {
		this.rrId = rrId;
		this.repeat = repeat;
		this.repeatsAfterEnd = repeatsAfterEnd;
		this.toDo = todo;
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
	 * Gets the todo.
	 *
	 * @return the todo
	 */
	public ToDo getTodo() {
		return toDo;
	}

	/**
	 * Sets the todo.
	 *
	 * @param todo the new todo
	 */
	public void setTodo(ToDo todo) {
		this.toDo = todo;
	}

	/**
	 * Gets the to do.
	 *
	 * @return the to do
	 */
	public ToDo getToDo() {
		return toDo;
	}

	/**
	 * Sets the to do.
	 *
	 * @param toDo the new to do
	 */
	public void setToDo(ToDo toDo) {
		this.toDo = toDo;
	}

}
