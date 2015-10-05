package de.bo.aid.boese.model;

public class RepeatRule {
	
	private int rrId;
	
	private String repeat;
	
	private int repeatsAfterEnd;
	
	private ToDo toDo;

	public RepeatRule() {
	}

	public RepeatRule(int rrId, String repeat, int repeatsAfterEnd) {
		this.rrId = rrId;
		this.repeat = repeat;
		this.repeatsAfterEnd = repeatsAfterEnd;
	}

	public RepeatRule(int rrId, String repeat, int repeatsAfterEnd, ToDo todo) {
		this.rrId = rrId;
		this.repeat = repeat;
		this.repeatsAfterEnd = repeatsAfterEnd;
		this.toDo = todo;
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

	public int getRepeatsAfterEnd() {
		return repeatsAfterEnd;
	}

	public void setRepeatsAfterEnd(int repeatsAfterEnd) {
		this.repeatsAfterEnd = repeatsAfterEnd;
	}

	public ToDo getTodo() {
		return toDo;
	}

	public void setTodo(ToDo todo) {
		this.toDo = todo;
	}

	public ToDo getToDo() {
		return toDo;
	}

	public void setToDo(ToDo toDo) {
		this.toDo = toDo;
	}

}
