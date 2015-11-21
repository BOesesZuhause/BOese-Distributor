/*
 * 
 */
package de.bo.aid.boese.ruler;

// TODO: Auto-generated Javadoc
/**
 * The Class DayCalculator.
 */
public class DayCalculator {
	
	/** The Constant MONTH_DAY. */
	private static final int[] MONTH_DAY = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	/**
	 * Number of days.
	 *
	 * @param month the month
	 * @param year the year
	 * @return the int
	 */
	protected static int numberOfDays(int month, int year){
		if(month == 2){
			return MONTH_DAY[month-1] + isLeapYear(year);
		}
		else{
			return MONTH_DAY[month-1];
		}
	}
	
	/**
	 * Checks if is leap year.
	 *
	 * @param year the year
	 * @return the int
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
	 * Gets the real day calc.
	 *
	 * @param grd the grd
	 * @param month the month
	 * @param year the year
	 * @return the real day calc
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
	 * Gets the real day calc.
	 *
	 * @param day the day
	 * @param grd the grd
	 * @param month the month
	 * @param year the year
	 * @return the real day calc
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
	 * Gets the real day calc neg.
	 *
	 * @param grd the grd
	 * @param month the month
	 * @param year the year
	 * @return the real day calc neg
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
	 * Gets the real day calc neg.
	 *
	 * @param day the day
	 * @param grd the grd
	 * @param month the month
	 * @param year the year
	 * @return the real day calc neg
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
