package com.sis.rest.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.client.MongoDatabase;
import com.sis.rest.dao.AdminDao;
import com.sis.rest.dao.AssignmentDao;
import com.sis.rest.pojo.Assignment;
import com.sis.rest.pojo.User;
import com.sis.rest.utilities.MongoDataSourceFactory;

public class AssignmentDaoImpl implements AssignmentDao{

	MongoDataSourceFactory dataSourceFactory = null;
	Datastore morphiaDataStore = null; 
	AdminDao adminDao = null;
	
	MongoDatabase dbConnection = null;
	
	private static final Logger logger = LogManager.getLogger(AttendanceDaoImpl.class);
	
	public AssignmentDaoImpl() {		
		dataSourceFactory = MongoDataSourceFactory.getInstance();
		morphiaDataStore = dataSourceFactory.getMorphiaSISDataStore();
		adminDao = new AdminDaoImpl();
	}

	@Override
	public boolean updateMongoForAssignment(Assignment assignmentTeacher, 
			Assignment assignmentStudent, String userId, List<User> userList) {
		//update teacher object
		Query<User> findUserQuery = morphiaDataStore.find(User.class)
				.filter("userId", userId);
		
		logger.info("findUserQuery in updateMongoForAssignment"+findUserQuery);
    	UpdateOperations<User> ops = morphiaDataStore.createUpdateOperations(User.class)
    		    			.addToSet("assignment", assignmentTeacher);
    	
    	logger.info("UpdateOperations in updateMongoForAssignment "+ops);
    	morphiaDataStore.update(findUserQuery, ops);
    	
    	//find out student list
    	for (User user : userList) {
    		findUserQuery = morphiaDataStore.find(User.class)
    				.filter("_id", user.getId());
    		
    		logger.info("findUserQuery for Student in updateMongoForAssignment"+findUserQuery);
        	ops = morphiaDataStore.createUpdateOperations(User.class)
        		    			.addToSet("assignment", assignmentStudent);
        	
        	logger.info("UpdateOperations in updateMongoForAssignment "+ops);
        	morphiaDataStore.update(findUserQuery, ops);
		}
    	
		return true;
	}
}
