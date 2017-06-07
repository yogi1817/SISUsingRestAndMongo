package com.sis.rest.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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
		UpdateOperations<User> ops = null;
		Query<User> findUserQuery = null;
		//update teacher object
		Query<User> findAssignmentQuery = morphiaDataStore.find(User.class)
				.filter("userId", userId)
				.filter("assignment.subject", assignmentTeacher.getSubject())
				.filter("assignment.assignmentName", assignmentTeacher.getAssignmentName());
		
		List<User> assigmentList = findAssignmentQuery.asList();
		if(!CollectionUtils.isEmpty(assigmentList)){
			logger.info("findAssignmentQuery in updateMongoForAssignment"+findAssignmentQuery);
	    	ops = morphiaDataStore.createUpdateOperations(User.class)
	    		    			.addToSet("assignment.$", assignmentTeacher);
	    	
	    	logger.info("UpdateOperations in updateMongoForAssignment "+ops);
	    	morphiaDataStore.update(findAssignmentQuery, ops);
	    	
	    	//find out student list
	    	for (User user : userList) {
	    		findUserQuery = morphiaDataStore.find(User.class)
	    				.filter("_id", user.getId())
	    				.filter("assignment.subject", assignmentTeacher.getSubject())
	    				.filter("assignment.assignmentName", assignmentTeacher.getAssignmentName());
	    		
	    		logger.info("findUserQuery for Student in updateMongoForAssignment"+findUserQuery);
	        	ops = morphiaDataStore.createUpdateOperations(User.class)
	        		    			.addToSet("assignment.$", assignmentStudent);
	        	
	        	logger.info("UpdateOperations in updateMongoForAssignment "+ops);
	        	morphiaDataStore.update(findUserQuery, ops);
			}
	    	
	    	return true;
		}
		
		findUserQuery = morphiaDataStore.find(User.class)
				.filter("userId", userId);
		
		logger.info("findUserQuery in updateMongoForAssignment"+findUserQuery);
    	ops = morphiaDataStore.createUpdateOperations(User.class)
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

	@Override
	public List<Assignment> getAssignments(String userName) {
		Query<User> findUser = morphiaDataStore.find(User.class)
				.filter("userId", userName)
				.project("assignment", true);
		
		logger.debug("findUser in getAssignments "+findUser);
		User user = findUser.get();
		if(user!=null)
			return user.getAssignment();
		
		return null;
	}

	@Override
	public User getUserDetails(String userName) {
		Query<User> findUser = morphiaDataStore.find(User.class)
				.filter("userId", userName);
		
		logger.debug("findUser in getAssignments "+findUser);
		User user = findUser.get();
		if(user!=null)
			return user;
		
		return null;
	}
}
