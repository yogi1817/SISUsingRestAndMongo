package com.sis.rest.pojo;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("holidayCalendar")
public class HolidayCalendar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5689992839054584351L;
	@Id
	private ObjectId id;
	private String year;
	private List<Month> month = null;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<Month> getMonth() {
		return month;
	}

	public void setMonth(List<Month> month) {
		this.month = month;
	}
}
