package de.bo.aid.boese.ruler;

import java.util.Date;
import java.util.GregorianCalendar;

public class TimeFormat {
	
	private int min;

	private int hour;
	
	private int day;
	
	private int month;
	
	private int year;
	
	private boolean[] dow;
	
	private boolean[] calculate;
	
	public TimeFormat() {
		
	}

	public TimeFormat(int min, int hour, int day, int month, int year, boolean[] dow, boolean[] calculate) {
		setMin(min, calculate[0]);
		setHour(hour, calculate[1]);
		setDay(day, calculate[2]);
		setMonth(month, calculate[3]);
		setYear(year, calculate[4]);
		this.dow = dow;
		this.calculate = calculate;
	}
	
	public TimeFormat(int min, int hour, int day, int month, int year, String dow, boolean[] calculate) {
		setMin(min, calculate[0]);
		setHour(hour, calculate[1]);
		setDay(day, calculate[2]);
		setMonth(month, calculate[3]);
		setYear(year, calculate[4]);
		setDow(dow);
		this.calculate = calculate;
	}
	
	public TimeFormat(String cron){
		this.calculate = new boolean[6]; 
		String[] cronElements = cron.split(", ");
		setMin(cronElements[0]);
		setHour(cronElements[1]);
		setMonth(cronElements[3]);
		setYear(cronElements[4]);
		setDay(cronElements[2]);
		setDow(cronElements[5]);
	}	
	
	public int getMin() {
		return min;
	}
	
	public String getMinString(){
		if(this.calculate[0]){
			if(this.min < 0){
				return this.min + "";
			}
			else if(this.min == 0){
				return "*";
			}
			else{
				return "+" + this.min;
			}
		}
		else{
			return this.min + "";
		}
	}
	
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
				i[1] = (min / 60);
			}
			else{
				i[0] = min;
				i[1] = 0;
			}
		}
		return i;
	}

	public void setMin(int min, boolean calc) {
		this.min = min;
		this.calculate[0] = calc;
	}
	
	public void setMin(String min) {
		if(min.equals("*")){
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

	public int getHour() {
		return hour;
	}
	
	public String getHourString(){
		if(this.calculate[1]){
			if(this.hour < 0){
				return this.hour + "";
			}
			else if(this.hour == 0){
				return "*";
			}
			else{
				return "+" + this.hour;
			}
		}
		else{
			return this.hour + "";
		}
	}
	
	public int[] getRealHour(int plus){
		int[] i = new int[2];
		int hour = this.hour;
		if(!this.calculate[3]){
			hour += plus;
		}
		else{
			hour += new Date().getHours() + plus;
		}
		if(hour < 0){
			hour *= -1;
			i[0] = hour % 12 + 1;
			i[1] = ((hour - 1) / 12) * -1;
		}
		else if(hour > 23){
			i[0] = hour % 12 + 1;
			i[1] = ((hour - 1) / 12);
		}
		else{
			i[0] = hour;
			i[1] = 0;
		}
		return i;
	}

	public void setHour(int hour, boolean calc) {
		this.hour = hour;
		this.calculate[1] = calc;
	}
	
	public void setHour(String hour) {
		if(hour.equals("*")){
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

	public int getDay() {
		return day;
	}
	
	public String getDayString(){
		if(this.calculate[2]){
			if(this.day < 0){
				return this.day + "";
			}
			else if(this.day == 0){
				return "*";
			}
			else{
				return "+" + this.day;
			}
		}
		else{
			return this.day + "";
		}
	}
	
	public int[] getRealDay(int plus){
		int md = DayCalculator.numberOfDays((calculate[3]) ? new Date().getMonth() + this.month : this.month, (calculate[4]) ? new Date().getYear() + this.year : this.year);
		int[] i = new int[2];
		int day = this.day;
		if(!this.calculate[3]){
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

	public void setDay(int day, boolean calc) {
		this.day = day;
		this.calculate[2] = calc;
	}
	
	public void setDay(String day) {
		if(day.equals("*")){
			this.day = 0;
			this.calculate[2] = true;
		}
		else{
			this.day = Integer.parseInt(day);
			this.calculate[2] = day.contains("+") || day.contains("-");
			int md = DayCalculator.numberOfDays(this.month, this.year);
			if (!this.calculate[2] && this.day < 1 || this.day > md)
				throw new IllegalArgumentException("Fix Days for the month number " + this.month + "are only between 0 & " + md);
		}
	}

	public int getMonth() {
		return month;
	}
	
	public String getMonthString(){
		if(this.calculate[3]){
			if(this.month < 0){
				return this.month + "";
			}
			else if(this.month == 0){
				return "*";
			}
			else{
				return "+" + this.month;
			}
		}
		else{
			return this.month + "";
		}
	}
	
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
			i[1] = ((month - 1) / 12);
		}
		else{
			i[0] = month;
			i[1] = 0;
		}
		return i;
	}

	public void setMonth(int month, boolean calc) {
		this.month = month;
		this.calculate[3] = calc;
	}

	public void setMonth(String month) {
		if(month.equals("*")){
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
	
	public int getYear() {
		return year;
	}
	
	public String getYearString(){
		if(this.calculate[4]){
			if(this.year < 0){
				return this.year + "";
			}
			else if(this.year == 0){
				return "*";
			}
			else{
				return "+" + this.year;
			}
		}
		else{
			return this.year + "";
		}
	}
	
	public int getRealYear(int plus){
		if(!this.calculate[4]){
			return this.year + plus;
		}
		else{
			return new Date().getYear() + 1900 + this.year + plus;
		}
	}

	public void setYear(int year, boolean calc) {
		this.year = year;
		this.calculate[4] = calc;
	}
	
	public void setYear(String year) {
		if(year.equals("*")){
			this.year = 0;
			this.calculate[4] = true;
		}
		else{
			this.year = Integer.parseInt(year);
			this.calculate[4] = year.contains("+") || year.contains("-");
		}
	}

	public boolean[] getDow() {
		return dow;
	}
	
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

	public void setDow(boolean[] dow) {
		this.dow = dow;
	}

	public void setDow(String dow) {
		if(dow.equals("*")){
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

	public void setTime(String cron){
		this.calculate = new boolean[6]; 
		String[] cronElements = cron.split(",");
		setMin(cronElements[1]);
		setHour(cronElements[2]);
		setDay(cronElements[3]);
		setMonth(cronElements[4]);
		setYear(cronElements[5]);
	}
	
	public String toString(){
		return 	getMinString() + ", " + getHourString() + ", " + getDayString() 
				+ ", " + getMonthString() + ", " + getYearString() + ", " + getDowString();
	}
	
	public Date getDate(){
		int[] mi = getRealMin();
		int[] h = getRealHour(mi[1]);
		int[] d = getRealDay(h[1]);
		int[] mo = getRealMonth(d[1]);
		int y = getRealYear(mo[1]);
		System.out.println("... " + mo[0] + "  " + d[0] + " " + h[0] + ":" + mi[0] + ":00 CET " + y);
		return new GregorianCalendar(y, mo[0]-1, d[0], h[0], mi[0]).getTime();
	}
}
