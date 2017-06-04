package com.sis.rest.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.client.MongoDatabase;
import com.sis.rest.dao.AttendanceDao;
import com.sis.rest.pojo.HolidayCalendar;
import com.sis.rest.pojo.Month;
import com.sis.rest.pojo.User;
import com.sis.rest.utilities.MongoDataSourceFactory;

public class AttendanceDaoImpl implements AttendanceDao{

	MongoDataSourceFactory dataSourceFactory = null;
	Datastore morphiaDataStore = null; 
	
	MongoDatabase dbConnection = null;
	
	private static final Logger logger = LogManager.getLogger(AttendanceDaoImpl.class);
	
	public AttendanceDaoImpl() {		
		dataSourceFactory = MongoDataSourceFactory.getInstance();
		morphiaDataStore = dataSourceFactory.getMorphiaSISDataStore();
	}
	
	@Override
	public boolean submitAttendance(Map<Integer, List<Date>> attendanceMap, String subject) {
		Iterator<Entry<Integer, List<Date>>> it = attendanceMap.entrySet().iterator();
		int rollNo;
	    while (it.hasNext()) {
	    	Map.Entry<Integer, List<Date>> pair = (Map.Entry<Integer, List<Date>>)it.next();
	    	rollNo = pair.getKey();
	    	
	    	Query<User> findUserQuery = morphiaDataStore.find(User.class)
					.filter("rollNo", rollNo)
	    			.filter("attendance.subjectName ", subject);
	    	
	    	logger.info("findUserQuery in submitAttendance"+findUserQuery);
	    	UpdateOperations<User> ops = morphiaDataStore.createUpdateOperations(User.class)
	    		    			.addToSet("attendance.$.datesAbsent", pair.getValue());
	    	
	    	logger.info("UpdateOperations in submitAttendance "+ops);
	    	morphiaDataStore.update(findUserQuery, ops);
	    }	
		return true;
	}

	@Override
	public User getAttendance(String subjectName, String userName) {
		Query<User> userQuery = morphiaDataStore.find(User.class)
				.filter("userId", userName);
				
		logger.info("userQuery in getAttendance "+userQuery);
		Query<User> subjectQuery = morphiaDataStore.find(User.class)
    			.filter("attendance.subjectName", subjectName);
						
		logger.info("subjectQuery in getAttendance "+subjectQuery);
		AggregationPipeline pipeline = morphiaDataStore.createAggregation(User.class)
                .match(userQuery)
                .unwind("attendance")
                .match(subjectQuery);
		
		logger.info("pipeline in getAttendance "+pipeline);
		Iterator<User> user = pipeline.aggregate(User.class);
		
		return user.next();
	}

	@Override
	public List<Date> getHolidaysForYear(int year) {
		
		List<Date> holidayDates = new ArrayList<Date>();
		Query<HolidayCalendar> attendanceQuery = morphiaDataStore.find(HolidayCalendar.class)
				.filter("year", ""+year);
		
		logger.debug("attendanceQuery in getHolidaysForYear "+attendanceQuery);
		
		HolidayCalendar holidayForYear = attendanceQuery.get();
		for (Month month : holidayForYear.getMonth()) {
			holidayDates.addAll(month.getHolidayDates());
		}
		return holidayDates;
	}
}
