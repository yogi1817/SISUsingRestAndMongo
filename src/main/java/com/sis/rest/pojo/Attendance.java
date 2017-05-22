package com.sis.rest.pojo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Attendance implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subjectName;
	private List<Date> datesAbsent; 
	private int rollNo;
	private String day;
	/**
	 * @return the rollNo
	 */
	public int getRollNo() {
		return rollNo;
	}
	/**
	 * @param rollNo the rollNo to set
	 */
	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}
	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}
	
	/**
	 * This method converts day of the week to date of that week
	 * @param date
	 * @return
	 */
	public Date getDateForDay(String day){
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(getDayToNumber(day));
		c.setTime(new Date());
		int today = c.get(Calendar.DAY_OF_WEEK);
		c.add(Calendar.DAY_OF_WEEK, -today+getDayToNumber(day));
		return c.getTime();
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	private int getDayToNumber(String day){
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
	
	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * @return the datesAbsent
	 */
	public List<Date> getDatesAbsent() {
		return datesAbsent;
	}
	/**
	 * @param datesAbsent the datesAbsent to set
	 */
	public void setDatesAbsent(List<Date> datesAbsent) {
		this.datesAbsent = datesAbsent;
	}
}