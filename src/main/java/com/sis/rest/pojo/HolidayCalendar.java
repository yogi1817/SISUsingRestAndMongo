package com.sis.rest.pojo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@XmlRootElement(name = "holidayCalendar")
@Document(collection = "holidayCalendar")
public class HolidayCalendar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5689992839054584351L;
	@Id
	private String id;
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
