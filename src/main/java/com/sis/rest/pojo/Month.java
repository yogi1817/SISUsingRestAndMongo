package com.sis.rest.pojo;

import java.util.Date;
import java.util.List;

public class Month {

	private String name;
	private List<Date> holidayDates = null;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Date> getHolidayDates() {
		return holidayDates;
	}

	public void setHolidayDates(List<Date> holidayDates) {
		this.holidayDates = holidayDates;
	}
}
