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
}
