package com.sis.rest.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.sis.rest.dao.AttendanceDao;
import com.sis.rest.dao.repository.UserOperations;
import com.sis.rest.pojo.HolidayCalendar;
import com.sis.rest.pojo.Month;
import com.sis.rest.pojo.User;

@Repository
public class AttendanceDaoImpl implements AttendanceDao{
	
	@Autowired
	MongoOperations mongoOpers;
	
	@Autowired
	UserOperations userOpers;
	
	private static final Logger logger = LogManager.getLogger(AttendanceDaoImpl.class);
	
	@Override
	public boolean submitAttendance(Map<Integer, List<Date>> attendanceMap, String subject) {
		Iterator<Entry<Integer, List<Date>>> it = attendanceMap.entrySet().iterator();
		int rollNo;
	    while (it.hasNext()) {
	    	Map.Entry<Integer, List<Date>> pair = (Map.Entry<Integer, List<Date>>)it.next();
	    	rollNo = pair.getKey();
	    	
	    	Query query = Query.query(Criteria.where("rollNo").is(rollNo)
	    			.and("attendance.subjectName").is(subject));
	    	
	    	Update update = new Update();
	    	update.addToSet("attendance.$.datesAbsent").each(pair.getValue());
	    	
	    	mongoOpers.updateFirst(query, update, User.class);
	    }	
		return true;
	}

	@Override
	public User getAttendance(String subjectName, String userName) {
		return userOpers.getAttendanceForUserIdAndSubject(subjectName, userName);
	}

	@Override
	public List<Date> getHolidaysForYear(int year) {
		
		List<Date> holidayDates = new ArrayList<Date>();
		Query query = Query.query(Criteria.where("year").is(year));
		logger.debug("query in getHolidays "+query);
		
		HolidayCalendar holidayForYear = mongoOpers.findOne(query, HolidayCalendar.class);
		
		for (Month month : holidayForYear.getMonth()) {
			holidayDates.addAll(month.getHolidayDates());
		}
		return holidayDates;
	}
}
