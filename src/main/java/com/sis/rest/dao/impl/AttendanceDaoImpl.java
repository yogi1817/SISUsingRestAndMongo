package com.sis.rest.dao.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.client.MongoDatabase;
import com.sis.rest.dao.AttendanceDao;
import com.sis.rest.pojo.User;
import com.sis.rest.utilities.MongoDataSourceFactory;

public class AttendanceDaoImpl implements AttendanceDao{

	MongoDataSourceFactory dataSourceFactory = null;
	Datastore morphiaDataStore = null; 
	
	MongoDatabase dbConnection = null;
	
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
	    			.filter("attendance.subjectName", subject);
	    	
	    	UpdateOperations<User> ops = morphiaDataStore.createUpdateOperations(User.class)
	    		    			.push("attendance.$.datesAbsent", pair.getValue());
	    	
	    	morphiaDataStore.update(findUserQuery, ops);
	    }	
		return false;
	}
}
