package com.sis.rest.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name = "attendance")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attendance implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subjectName;
	private List<Date> datesAbsent; 
	private int rollNo;
	private String day;
	private String monthName;
	private float percentageAbsent;
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
	/**
	 * @return the monthName
	 */
	public String getMonthName() {
		return monthName;
	}
	/**
	 * @param monthName the monthName to set
	 */
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	/**
	 * @return the percentageAbsent
	 */
	public float getPercentageAbsent() {
		return percentageAbsent;
	}
	/**
	 * @param percentageAbsent the percentageAbsent to set
	 */
	public void setPercentageAbsent(float percentageAbsent) {
		this.percentageAbsent = percentageAbsent;
	}
}