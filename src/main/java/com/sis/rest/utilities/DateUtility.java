package com.sis.rest.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {
	public static boolean isDateBetweenTwoDates(Date startDate, Date endDate, Date givenDate){
		boolean dateExistInBetween = false;
		if((givenDate.before(endDate) && givenDate.after(startDate)) 
				|| givenDate.equals(endDate)){
			dateExistInBetween = true;
		}
		return dateExistInBetween;
	}
	
	public static Date getFirstDateOfMonth(String selectedDate){
		selectedDate = "01-"+selectedDate;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMMM-yyyy");
		Date date = null;
		try {
            	date = formatter.parse(selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return date;
	}
	
	public static Date getLastDateOfMonth(String selectedDate){
		selectedDate = "01-"+selectedDate;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMMM-yyyy");
		Date date = null;
		try {
            	date = formatter.parse(selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		Calendar calendar = Calendar.getInstance();  
	    calendar.setTime(date);  
	    
	    calendar.add(Calendar.MONTH, 1);  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        calendar.add(Calendar.DATE, -1);  
        
        Date lastDayOfMonth = calendar.getTime();  
        
		return lastDayOfMonth;
	}
	
	public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
	    Calendar startCal = Calendar.getInstance();
	    startCal.setTime(startDate);        

	    Calendar endCal = Calendar.getInstance();
	    endCal.setTime(endDate);

	    int workDays = 0;

	    //Return 0 if start and end are the same
	    if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
	        return 0;
	    }

	    if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
	        startCal.setTime(endDate);
	        endCal.setTime(startDate);
	    }

	    do {
	       //excluding start date
	        startCal.add(Calendar.DAY_OF_MONTH, 1);
	        if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	            ++workDays;
	        }
	    } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

	    return workDays;
	}
	
	/**
	 * This methid changes Strinf date fromat
	 * @param oldFormat
	 * @param newFormat
	 * @param oldDate
	 * @return
	 */
	public static String changeDateFormat(String oldFormat, String newFormat, String oldDate){
		String newDate = new String();
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		Date d = new Date();
		try {
			d = sdf.parse(oldDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdf.applyPattern(newFormat);
		newDate = sdf.format(d);
		return newDate;
	}
	
	/*public static long daysBetween(LocalDate start, LocalDate end, List<DayOfWeek> ignore) {
	    return Stream.iterate(start, d->d.plusDays(1))
	                 .limit(start.until(end, ChronoUnit.DAYS))
	                 .filter(d->!ignore.contains(d.getDayOfWeek()))
	                 .count();
	}*/
	
	public static Date changeDateFormat(Date inputDate, String outputFormat){
		String inputDateString = new SimpleDateFormat(outputFormat).format(inputDate);
		Date outputDate = null;
		try {
			outputDate = new SimpleDateFormat(outputFormat).parse(inputDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputDate;
	}
	
	public static Date getDateForDay(String day){
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(getDayToNumber(day));
		c.setTime(new Date());
		int today = c.get(Calendar.DAY_OF_WEEK);
		c.add(Calendar.DAY_OF_WEEK, -today+getDayToNumber(day));
		return changeDateFormat(c.getTime(), "yyyy-MM-dd");
	}
	
	public static Date stringToDate(String date){
		SimpleDateFormat formattor= new SimpleDateFormat("yyyy-MM-dd");
		Date formattedDate = null;
		try {
			formattedDate = formattor.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;
		//return changeDateFormat(formattor.parse(date), "yyyy-MM-dd");
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	private static int getDayToNumber(String day){
		switch(day){
			case "monday": return 1;
			case "tuesday": return 2;
			case "wednesday": return 3;
			case "thursday": return 4;
			case "friday": return 5;
			case "saturday": return 6;
		}
		return 1;
	}
}
