package de.bo.aid.boese.constants;

import java.util.ArrayList;
import java.util.Date;

public class TimeFormat {
	public static boolean timestingIsRepeated(String timestring) {
		return false;
	}
	
	private String generateCronExpression(final String seconds, final String minutes, final String hours, final String dayOfMonth, final String month, final String dayOfWeek, final String year) {
	    return String.format("%1s %2$s %3$s %4$s %5$s %6$s %7%s", seconds, minutes, hours, dayOfMonth, month, dayOfWeek, year);
	}
	
	private void getTimeFromString(String time, int currentTime, int timeModulo, ArrayList<Integer> timeList) {
		if (time.contains(",")) {
			String[] splitTime = time.split(",");
			for (int i = 0; i < splitTime.length; i++) {
				if (splitTime[i].contains("-")) { // range 
					String[] splitRange = splitTime[i].split("-");
					if (splitRange.length == 2) {
						int startTime = new Integer(splitRange[0]).intValue();
						int endTime = new Integer(splitRange[1]).intValue();
						if (endTime > startTime && endTime < 2400) {
							while (endTime >= startTime) {
								timeList.add(startTime++);
							}
						} else {
							// TODO error in year
						}
					} else {
						// TODO error in year
					}
				} else if (splitTime[i].contains("+")) { // add to current time
					timeList.add((currentTime + new Integer(splitTime[i].substring(splitTime[i].indexOf("+"))) % timeModulo));
				} else { // its just a year
					timeList.add(new Integer(splitTime[i]));
				}
			}
		} else {
			if (time.contains("-")) { // range 
				String[] splitRange = time.split("-");
				if (splitRange.length == 2) {
					int startTime = new Integer(splitRange[0]).intValue();
					int endTime = new Integer(splitRange[1]).intValue();
					if (endTime > startTime) {
						while (endTime >= startTime) {
							timeList.add(startTime++);
						}
					} else {
						// TODO error in year
					}
				} else {
					// TODO error in year
				}
			} else if (time.contains("+")) { // add to current time
				timeList.add((currentTime + new Integer(time.substring(time.indexOf("+")))) % timeModulo);
			} else { // its just a year
				timeList.add(new Integer(time));
			}
		}
	}
	
	public static Date nextDate(String timestring) {
		Date date = new Date();
		ArrayList<Integer> years = new ArrayList<>();
		ArrayList<Integer> months = new ArrayList<>();
		ArrayList<Integer> days = new ArrayList<>();
		ArrayList<Integer> weekDays = new ArrayList<>();
		ArrayList<Integer> hours = new ArrayList<>();
		ArrayList<Integer> minutes = new ArrayList<>();
		ArrayList<Integer> seconds = new ArrayList<>();
		
		String[] splitString = timestring.split(";");
		if (splitString.length != 7) {
			//TODO exception
			return null;
		}
		if (splitString[6].contains(",")) {
			String[] splitYear = splitString[6].split(",");
			for (int i = 0; i < splitYear.length; i++) {
				if (splitYear[i].contains("-")) { // range in years (2015-2017)
					String[] splitYearRange = splitYear[i].split("-");
					if (splitYearRange.length == 2) {
						int startYear = new Integer(splitYearRange[0]).intValue();
						int endYear = new Integer(splitYearRange[1]).intValue();
						if (endYear > startYear && endYear < 2400) {
							while (endYear >= startYear) {
								years.add(startYear++);
							}
						} else {
							// TODO error in year
						}
					} else {
						// TODO error in year
					}
				} else if (splitYear[i].contains("+")) { // add to current year (+3) -> this year + 3
					years.add(new Integer(splitYear[i].substring(splitYear[i].indexOf("+"))));
				} else { // its just a year
					years.add(new Integer(splitYear[i]));
				}
			}
		} else {
			if (splitString[6].contains("-")) { // range in years (2015-2017)
				String[] splitYearRange = splitString[6].split("-");
				if (splitYearRange.length == 2) {
					int startYear = new Integer(splitYearRange[0]).intValue();
					int endYear = new Integer(splitYearRange[1]).intValue();
					if (endYear > startYear && endYear < 2400) {
						while (endYear >= startYear) {
							years.add(startYear++);
						}
					} else {
						// TODO error in year
					}
				} else {
					// TODO error in year
				}
			} else if (splitString[6].contains("+")) { // add to current year (+3) -> this year + 3
				years.add(date.getYear() + new Integer(splitString[6].substring(splitString[6].indexOf("+"))));
			} else { // its just a year
				years.add(new Integer(splitString[6]));
			}
		}
		
		
		for (int i = 0; i < 7; i++) {
			if (splitString[i].contains(",")) { // List of values
				
			} else if (splitString[i].contains(",")){
				
			}
		}
		
		return date;
	}
}
