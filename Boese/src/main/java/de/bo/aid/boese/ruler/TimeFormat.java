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

import java.util.Date;
import java.util.GregorianCalendar;

// TODO: Auto-generated Javadoc
/**
 * The Class is a own Implementation of the Cron-String for Time Controls.
 */
public class TimeFormat implements Comparable<TimeFormat>{
	
	/** Minute. */
	private int min;

	/** Hour. */
	private int hour;
	
	/** Day. */
	private int day;
	
	/** Month. */
	private int month;
	
	/** Year. */
	private int year;
	
	/** The day of the week. Boolean Array first bool: Monday and last bool: Sunday */
	private boolean[] dow;
	
	/** Which Value will be calculated. */
	private boolean[] calculate;
	
	/**
	 * Instantiates a new TimeFormat.
	 */
	public TimeFormat() {
		
	}

	/**
	 * Instantiates a new TimeFormat.
	 *
	 * @param min the minute
	 * @param hour the hour
	 * @param day the day
	 * @param month the month
	 * @param year the year
	 * @param dow the day of the week
	 * @param calculate the which value will be calculated (first: minute last:year)
	 */
	public TimeFormat(int min, int hour, int day, int month, int year, boolean[] dow, boolean[] calculate) {
		setMin(min, calculate[0]);
		setHour(hour, calculate[1]);
		setDay(day, calculate[2]);
		setMonth(month, calculate[3]);
		setYear(year, calculate[4]);
		this.dow = dow;
		this.calculate = calculate;
	}
	
	/**
	 * Instantiates a new TimeFormat.
	 *
	 * @param min the minute
	 * @param hour the hour
	 * @param day the day
	 * @param month the month
	 * @param year the year
	 * @param dow the day of the week as String: t for true and f for false (tffffff: only on Monday)
	 * @param calculate the which value will be calculated (first: minute last:year)
	 */
	public TimeFormat(int min, int hour, int day, int month, int year, String dow, boolean[] calculate) {
		setMin(min, calculate[0]);
		setHour(hour, calculate[1]);
		setDay(day, calculate[2]);
		setMonth(month, calculate[3]);
		setYear(year, calculate[4]);
		setDow(dow);
		this.calculate = calculate;
	}
	
	/**
	 * Instantiates a new TimeFormat.
	 *
	 * @param cron the cron-String
	 */
	public TimeFormat(String cron){
		this.calculate = new boolean[5];
		cron = cron.replace(" ", "");
		String[] cronElements = cron.split(";");
		if(("*;*;*;*;*;*").equals(cron)){
			setMin("+1");
		}
		else{
			setMin(cronElements[0]);
		}
		setHour(cronElements[1]);
		setMonth(cronElements[3]);
		setYear(cronElements[4]);
		setDay(cronElements[2]);
		setDow(cronElements[5]);
		Date now = new Date();
		if(this.getRealDay(0)[0] == now.getDate() && this.getRealMonth(0)[0] == (now.getMonth()+1) && this.getRealYear(0) == (now.getYear()+1900)){
			if(this.getRealHour(0)[0] < now.getHours() || (this.getRealHour(0)[0] == now.getHours() && this.getRealMin()[0] < now.getMinutes())){
				if(this.day == 31 && this.month == 12){
					this.day = 1;
					this.month = 1;
					this.year++;
				}
				else if(this.day == DayCalculator.numberOfDays(this.getRealMonth(0)[0], this.getRealYear(0))){
					this.day = 1;
					this.month++;
				}
				else{
					this.day++;
				}
			}
		}
	}
	
	/**
	 * Instantiates a new TimeFormat.
	 *
	 * @param d the java.date
	 */
	public TimeFormat(Date d){
		this.calculate = new boolean[5]; 
		for(int i = 0; i < this.calculate.length; i++){
			this.calculate[i] = false;
		}
		this.min = d.getMinutes();
		this.hour = d.getHours();
		this.day = d.getDate();
		this.month = d.getMonth();
		this.year = d.getYear();
		this.dow = new boolean[7];
		for(int i = 0; i < this.dow.length; i++){
			this.dow[i] = true;
		}
	}	
	
	/**
	 * Gets the minute.
	 *
	 * @return the minute
	 */
	public int getMin() {
		return min;
	}
	
	/**
	 * Gets the minute as string.
	 *
	 * @return the minute string
	 */
	public String getMinString(){
		if(this.calculate[0]){
			if(this.min < 0){
				return Integer.toString(this.min);
			}
			else if(this.min == 0){
				return "*";
			}
			else{
				return "+" + this.min;
			}
		}
		else{
			return Integer.toString(this.min);
		}
	}
	
	/**
	 * Gets the real minute.
	 *
	 * @return the calculated minute and how much hours are skipped
	 */
	public int[] getRealMin(){
		int[] i = new int[2];
		if(!this.calculate[0]){
			i[0] = this.min;
			i[1] = 0;
		}
		else{
			int min = new Date().getMinutes() + this.min;
			if(this.min < 0){
				min *= -1;
				i[0] = min % 60;
				i[1] = (min / 60) * -1;
			}
			else if(this.min >59){
				i[0] = min % 60;
				i[1] = min / 60;
			}
			else{
				i[0] = min;
				i[1] = 0;
			}
		}
		return i;
	}

	/**
	 * Sets the minute.
	 *
	 * @param min the new minute
	 * @param calc will it be calculated?
	 */
	public void setMin(int min, boolean calc) {
		this.min = min;
		this.calculate[0] = calc;
	}
	
	/**
	 * Sets the minute.
	 *
	 * @param min the new minute
	 */
	public void setMin(String min) {
		if("*".equals(min)){
			this.calculate[0] = true;
			this.min = 0;
		}
		else{
			this.min = Integer.parseInt(min);
			this.calculate[0] = min.contains("+") || min.contains("-");
			if (!this.calculate[0] && this.min < 0 || this.min > 59)
				throw new IllegalArgumentException("Fix Minutes are only between 0 & 59");
		}
	}

	/**
	 * Gets the hour.
	 *
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}
	
	/**
	 * Gets the hour as string.
	 *
	 * @return the hour string
	 */
	public String getHourString(){
		if(this.calculate[1]){
			if(this.hour < 0){
				return Integer.toString(this.hour);
			}
			else if(this.hour == 0){
				return "*";
			}
			else{
				return "+" + this.hour;
			}
		}
		else{
			return Integer.toString(this.hour);
		}
	}
	
	/**
	 * Gets the real hour.
	 *
	 * @param plus the plus
	 * @return the calculated Hour and how much days are skipped
	 */
	public int[] getRealHour(int plus){
		int[] i = new int[2];
		int hour = this.hour;
		if(!this.calculate[1]){
			hour += plus;
		}
		else{
			hour += new Date().getHours() + plus;
		}
		if(hour < 0){
			hour *= -1;
			i[0] = hour % 24 + 1;
			i[1] = ((hour - 1) / 24) * -1;
		}
		else if(hour > 23){
			i[0] = hour % 24 + 1;
			i[1] = (hour - 1) / 24;
		}
		else{
			i[0] = hour;
			i[1] = 0;
		}
		return i;
	}

	/**
	 * Sets the hour.
	 *
	 * @param hour the new hour
	 * @param calc will it be calculated?
	 */
	public void setHour(int hour, boolean calc) {
		this.hour = hour;
		this.calculate[1] = calc;
	}
	
	/**
	 * Sets the hour.
	 *
	 * @param hour the new hour
	 */
	public void setHour(String hour) {
		if(("*").equals(hour)){
			this.calculate[1] = true;
			this.hour = 0;
		}
		else{
			this.hour = Integer.parseInt(hour);
			this.calculate[1] = hour.contains("+") || hour.contains("-");
			if (!this.calculate[1] && this.hour < 0 || this.hour > 23)
				throw new IllegalArgumentException("Fix Hours are only between 0 & 23");
		}
	}

	/**
	 * Gets the day.
	 *
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * Gets the day as string.
	 *
	 * @return the day string
	 */
	public String getDayString(){
		if(this.calculate[2]){
			if(this.day < 0){
				return Integer.toString(this.day);
			}
			else if(this.day == 0){
				return "*";
			}
			else{
				return "+" + this.day;
			}
		}
		else{
			return Integer.toString(this.day);
		}
	}
	
	/**
	 * Gets the real day.
	 *
	 * @param plus the plus
	 * @return the calculated Day and how much Months are skipped
	 */
	public int[] getRealDay(int plus){
		int md = DayCalculator.numberOfDays((calculate[3]) ? new Date().getMonth() + this.month : this.month, (calculate[4]) ? new Date().getYear() + this.year : this.year);
		int[] i = new int[2];
		int day = this.day;
		if(!this.calculate[2]){
			day += plus;
		}
		else{
			day += new Date().getDate() + plus;
		}
		if(day < 1){
			i[0] = day;
			i[1] = 0;
			i = DayCalculator.getRealDayCalcNeg(new Date().getDate(), i, (calculate[3]) ? new Date().getMonth() + this.month : this.month, (calculate[4]) ? new Date().getYear() + this.year : this.year);
		}
		else if(day > md){
			i[0] = day;
			i[1] = 0;
			i = DayCalculator.getRealDayCalc(new Date().getDate(), i, (calculate[3]) ? new Date().getMonth() + this.month : this.month, (calculate[4]) ? new Date().getYear() + this.year : this.year);
		}
		else{
			i[0] = day;
			i[1] = 0;
		}
		return i;
	}

	/**
	 * Sets the day.
	 *
	 * @param day the new day
	 * @param calc will it be calculated?
	 */
	public void setDay(int day, boolean calc) {
		this.day = day;
		this.calculate[2] = calc;
	}
	
	/**
	 * Sets the day.
	 *
	 * @param day the new day
	 */
	public void setDay(String day) {
		if("*".equals(day)){
			this.day = 0;
			this.calculate[4] = true;
		}
		else{
			this.day = Integer.parseInt(day);
			this.calculate[2] = day.contains("+") || day.contains("-");
			int md = DayCalculator.numberOfDays(this.month, this.year);
			if (!this.calculate[2] && this.day < 1 || this.day > md)
				throw new IllegalArgumentException("Fix Days for the month number " + this.month + "are only between 0 & " + md);
		}
	}

	/**
	 * Gets the month.
	 *
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	
	/**
	 * Gets the month as string.
	 *
	 * @return the month string
	 */
	public String getMonthString(){
		if(this.calculate[3]){
			if(this.month < 0){
				return Integer.toString(this.month);
			}
			else if(this.month == 0){
				return "*";
			}
			else{
				return "+" + this.month;
			}
		}
		else{
			return Integer.toString(this.month);
		}
	}
	
	/**
	 * Gets the real month.
	 *
	 * @param plus the plus
	 * @return the calculated Month and how much years are skipped
	 */
	public int[] getRealMonth(int plus){
		int[] i = new int[2];
		int month = this.month;
		if(!this.calculate[3]){
			month += plus;
		}
		else{
			month += new Date().getMonth() + 1 + plus;
		}
		if(month < 1){
			month *= -1;
			i[0] = month % 12 + 1;
			i[1] = ((month - 1) / 12) * -1;
		}
		else if(month > 12){
			i[0] = month % 12 + 1;
			i[1] = (month - 1) / 12;
		}
		else{
			i[0] = month;
			i[1] = 0;
		}
		return i;
	}

	/**
	 * Sets the month.
	 *
	 * @param month the new month
	 * @param calc will it be calculated?
	 */
	public void setMonth(int month, boolean calc) {
		this.month = month;
		this.calculate[3] = calc;
	}

	/**
	 * Sets the month.
	 *
	 * @param month the new month
	 */
	public void setMonth(String month) {
		if("*".equals(month)){
			this.calculate[3] = true;
			this.month = 0;
		}
		else {
			this.month = Integer.parseInt(month);
			this.calculate[3] = month.contains("+") || month.contains("-");
			if (!this.calculate[3] && this.month < 1 || this.month > 12)
				throw new IllegalArgumentException("Fix Seconds are only between 0 & 59");
		}
	}
	
	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Gets the year as string.
	 *
	 * @return the year string
	 */
	public String getYearString(){
		if(this.calculate[4]){
			if(this.year < 0){
				return Integer.toString(this.year);
			}
			else if(this.year == 0){
				return "*";
			}
			else{
				return "+" + this.year;
			}
		}
		else{
			return Integer.toString(this.year);
		}
	}
	
	/**
	 * Gets the real year.
	 *
	 * @param plus how much years was skipped
	 * @return the real year
	 */
	public int getRealYear(int plus){
		if(!this.calculate[4]){
			return this.year + plus;
		}
		else{
			return new Date().getYear() + 1900 + this.year + plus;
		}
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 * @param calc will it be calculated?
	 */
	public void setYear(int year, boolean calc) {
		this.year = year;
		this.calculate[4] = calc;
	}
	
	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(String year) {
		if("*".equals(year)){
			this.year = 0;
			this.calculate[4] = true;
		}
		else{
			this.year = Integer.parseInt(year);
			this.calculate[4] = year.contains("+") || year.contains("-");
		}
	}

	/**
	 * Gets the day of the week.
	 *
	 * @return the day of the week
	 */
	public boolean[] getDow() {
		return dow;
	}
	
	/**
	 * Gets the day of the week as string.
	 *
	 * @return the day of the week string
	 */
	public String getDowString() {
		String s = "";
		for(boolean b : this.dow){
			if(b){
				s += "t";
			}
			else{
				s += "f";
			}
		}
		return s;
	}

	/**
	 * Sets the day of the week.
	 *
	 * @param dow the new day of the week
	 */
	public void setDow(boolean[] dow) {
		this.dow = dow;
	}

	/**
	 * Sets the day of the week as String.
	 *
	 * @param dow the day of the week as String: t for true and f for false (tffffff: only on Monday)
	 */
	public void setDow(String dow) {
		if("*".equals(dow)){
			this.dow = new boolean[7];
			for(int i = 0; i < this.dow.length; i++){
				this.dow[i] = true;
			}
		}
		else{
			this.dow = new boolean[7];
			int i = 0;
			for(char c : dow.toCharArray()){
				if(c == 't'){
					this.dow[i] = true;
				}
				else if(c == 'f'){
					this.dow[i] = false;
				}
				else{
					throw new IllegalArgumentException("nur t und f werden als chars bei Day of Week erkannt");
				}
			}
		}
	}

	/**
	 * Sets the the Date.
	 *
	 * @param cron the new time as Cron-String
	 */
	public void setTime(String cron){
		this.calculate = new boolean[6]; 
		String[] cronElements = cron.split(",");
		setMin(cronElements[1]);
		setHour(cronElements[2]);
		setDay(cronElements[3]);
		setMonth(cronElements[4]);
		setYear(cronElements[5]);
	}
	
	/**
	 * The saved Date as String.
	 *
	 * @return the string
	 */
	@Override
	public String toString(){
		return 	getMinString() + ", " + getHourString() + ", " + getDayString() 
				+ ", " + getMonthString() + ", " + getYearString() + ", " + getDowString();
	}
	
	/**
     * Gets the saved date.
     *
     * @return the java.util.date
     */
    public Date getDate(){
        int[] mi = getRealMin();
        int[] h = getRealHour(mi[1]);
        int[] d = getRealDay(h[1]);
        int[] mo = getRealMonth(d[1]);
        int y = getRealYear(mo[1]);
        if(y < 2000){
            y += 1900; 
            mo[0]++;
        }
        return new GregorianCalendar(y, mo[0]-1, d[0], h[0], mi[0]).getTime();
    }
    
    /**
     * Gets the saved date.
     *
     * @return the java.util.date
     */
    public Date getDateForRepeatRule(){
        int[] mi = getRealMin();
        int[] h = getRealHour(mi[1]);
        int[] d = getRealDay(h[1]);
        if(!calculate[3]){
        	this.setMonth(this.getMonth()-1, false);
        }
        int[] mo = getRealMonth(d[1]);
        int y = getRealYear(mo[1]);
        if(y < 2000){
            y += 1900; 
            mo[0]++;
        }
        return new GregorianCalendar(y, mo[0]-1, d[0], h[0], mi[0]).getTime();
    }

	/**
	 * Compares two TimeFormats which Date is more in the future.
	 *
	 * @param tf The Timeformat which to compare
	 * @return the int
	 */
	@Override
	public int compareTo(TimeFormat tf) {
		return this.getDate().compareTo(tf.getDate());
	}
}
