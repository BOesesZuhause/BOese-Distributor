/*
 * 
 */
package de.bo.aid.boese.ruler;

// TODO: Auto-generated Javadoc
/**
 * The Class DayCalculator can calculate the right number of days of a month, which day will be or was.
 */
public class DayCalculator {
	
	/** The Constant MONTH_DAY save which month has how much days. */
	private static final int[] MONTH_DAY = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	/**
	 * Calculate the right number of days of a month.
	 *
	 * @param month The month as int. (January:0, December:11)
	 * @param year The year
	 * @return the number of Days
	 */
	protected static int numberOfDays(int month, int year){
		if(month == 1){	//February is second Month (0,1,2,3...) and has in leap years 29 days
			return MONTH_DAY[month] + isLeapYear(year);
		}
		else{
			return MONTH_DAY[month];
		}
	}
	
	/**
	 * Checks if the year is a leap year.
	 *
	 * @param year the year which have to be tested
	 * @return 1(for february) if the tested year is a leap year
	 */
	protected static int isLeapYear(int year){
		if(year % 4 == 0){
			if(year % 400 == 0){
				return 1;
			}
			else if(year % 100 == 0){
				return 0;
			}
			else{
				return 1;
			}
		}
		else{
			return 0;
		}
	}
	
	/**
	 * Calculate the wished Day in the future.
	 *
	 * @param grd a Int Array with how much Days are left and how much month was passed
	 * @param month the Month for which it has to be calculated
	 * @param year the Year for which it has to be calculated
	 * @return the day and how much month more
	 */
	protected static int[] getRealDayCalc(int[] grd, int month, int year){
		int rest = grd[0] - numberOfDays(month, year);
		if (rest < 1){
			return grd;
		}
		else{
			grd[0] = rest;
			grd[1]++;
			if(month == 12){
				return getRealDayCalc(grd, 1, ++year);
			}
			else{
				return getRealDayCalc(grd, ++month, year);
			}
			
		}
	}
	
	/**
	 * Calculate the wished Day in the future.
	 *
	 * @param day the start day
	 * @param grd a Int Array with how much Days are left and how much month was passed
	 * @param month the Month for which it has to be calculated
	 * @param year the Year for which it has to be calculated
	 * @return the day and how much month more
	 */
	protected static int[] getRealDayCalc(int day, int[] grd, int month, int year){
		int rest = grd[0] - (numberOfDays(month, year) - day);
		if (rest < 1){
			grd[0] = day;
			return grd;
		}
		else{
			grd[0] = rest;
			grd[1]++;
			if(month == 12){
				return getRealDayCalc(grd, 1, ++year);
			}
			else{
				return getRealDayCalc(grd, ++month, year);
			}
			
		}
	}
	
	/**
	 * Calculate the wished Day in the past.
	 *
	 * @param grd a Int Array with how much Days are left and how much month were to much
	 * @param month the Month for which it has to be calculated
	 * @param year the Year for which it has to be calculated
	 * @return the day and how much month less
	 */
	protected static int[] getRealDayCalcNeg(int[] grd, int month, int year){
		int rest = grd[0] - numberOfDays(month, year);
		if (rest < 1){
			grd[0] = rest * -1;
			return grd;
		}
		else{
			grd[0] = rest;
			grd[1]--;
			if(month == 1){
				return getRealDayCalcNeg(grd, 12, --year);
			}
			else{
				return getRealDayCalcNeg(grd, --month, year);
			}
			
		}
	}
	
	/**
	 * Calculate the wished Day in the past.
	 *
	 * @param day the start day
	 * @param grd a Int Array with how much Days are left and how much month were to much
	 * @param month the Month for which it has to be calculated
	 * @param year the Year for which it has to be calculated
	 * @return the day and how much month less
	 */
	protected static int[] getRealDayCalcNeg(int day, int[] grd, int month, int year){
		int rest = grd[0] - day;
		if (rest < 1){
			grd[0] = day;
			return grd;
		}
		else{
			grd[0] = rest;
			grd[1]--;
			if(month == 2){
				return getRealDayCalcNeg(grd, 12, --year);
			}
			else{
				return getRealDayCalcNeg(grd, --month, year);
			}
			
		}
	}

}
