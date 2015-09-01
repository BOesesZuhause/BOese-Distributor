package de.bo.aid.boese.ruler;

public class Checker {

	public boolean deCoInCondition(int decoid, String condition){
		if(condition.contains("<id>" + decoid + "</id>")){
			return true;
		}
		else{
			return false;
		}
	}
}
