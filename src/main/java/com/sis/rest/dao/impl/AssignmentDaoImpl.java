package com.sis.rest.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.sis.rest.dao.AssignmentDao;
import com.sis.rest.dao.repository.UserRepository;
import com.sis.rest.pojo.Assignment;
import com.sis.rest.pojo.User;

@Repository
public class AssignmentDaoImpl implements AssignmentDao{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MongoOperations mongoOpers;
	
	private static final Logger logger = LogManager.getLogger(AssignmentDaoImpl.class);
	
	@Override
	public boolean updateMongoForAssignment(Assignment assignmentTeacher, 
			Assignment assignmentStudent, String userId, List<User> userList) {
		Query assignmentExistsForTeacher = Query.query(Criteria.where("userId").is(userId)
				.and("assignment.assignmentName").is(assignmentTeacher.getAssignmentName()));
		logger.info("assignmentExistsForTeacher in updateMongoForAssignment "+assignmentExistsForTeacher);
		List<User> assigmentListForTeacher = mongoOpers.find(assignmentExistsForTeacher, User.class);
		
		//assignment exists append Assignment to the existing array
		if(CollectionUtils.isNotEmpty(assigmentListForTeacher)){
			updateExistingAssignmentObject(assignmentTeacher, assignmentStudent, assignmentExistsForTeacher, userList);
		}
		//assignment does not exists add new assignment object
		else{
			insertNewAssignmentObject(assignmentTeacher, assignmentStudent, userList, userId);
		}
		
		return true;
	}
	
	/**
	 * Insert new Assignment object since its the first time object is inserted.
	 * @param assignmentTeacher
	 * @param assignmentStudent
	 * @param userList
	 * @param userId
	 */
	private void insertNewAssignmentObject(Assignment assignmentTeacher, Assignment assignmentStudent, 
					List<User> userList, String userId){
		Query findUserQuery = Query.query(Criteria.where("userId").is(userId));
		logger.info("findUserQuery for Student in updateMongoForAssignment"+findUserQuery);
		
		Update update = new Update();
    	update.push("assignment").each(assignmentTeacher);
    	
    	mongoOpers.upsert(findUserQuery, update, User.class);
    	
    	//update all student object
    	for (User user : userList) {
    		findUserQuery = Query.query(Criteria.where("userId").is(user.getUserId()));
    		logger.info("findUserQuery for Student in updateMongoForAssignment"+findUserQuery);
    		
    		update = new Update();
	    	update.push("assignment").each(assignmentStudent);
			logger.info("UpdateOperations in updateMongoForAssignment "+update);
			
			mongoOpers.upsert(findUserQuery, update, User.class);
    	}
	}
	
	/**
	 * update Assignment object since assignment already exists 
	 * and Teacher re-uploaded the assignment
	 * @param assignmentTeacher
	 * @param assignmentStudent
	 * @param assignmentExistsForTeacher
	 * @param userList
	 */
	private void updateExistingAssignmentObject(Assignment assignmentTeacher, Assignment assignmentStudent, 
							Query assignmentExistsForTeacher, List<User> userList){
		Update update = new Update();
    	update.set("assignment.$.completionDate",assignmentTeacher.getCompletionDate());
    	
    	logger.info("update in updateMongoForAssignment "+update);
    	mongoOpers.updateFirst(assignmentExistsForTeacher, update, User.class);
    	
    	//update Student object
    	for (User user : userList) {
    		Query assignmentExistsForStudent = Query.query(Criteria.where("userId").is(user.getUserId())
    				.and("assignment.assignmentName").is(assignmentStudent.getAssignmentName()));
    		logger.info("findUserQuery for Student in updateMongoForAssignment "+assignmentExistsForStudent);
    		
    		update = new Update();
	    	update.set("assignment.$.completionDate", assignmentStudent.getCompletionDate());
        	logger.info("UpdateOperations in updateMongoForAssignment "+update);
        	
        	mongoOpers.updateFirst(assignmentExistsForStudent, update, User.class);
		}
	}

	@Override
	public List<Assignment> getAssignments(String userName) {		
		User user = userRepo.findByUserId(userName);
		if(user!=null)
			return user.getAssignment();
		
		return null;
	}

	@Override
	public User getUserDetails(String userName) {
		
		User user = userRepo.findByUserId(userName);
		if(user!=null)
			return user;
		
		return null;
	}

	@Override
	public boolean updateMongoForStudentAssignment(Assignment assignmentStudent, 
			String userId, String assignmentPath) {
		Query query = Query.query(Criteria.where("userId").is(userId)
    			.and("assignment.assignmentName").is(assignmentStudent.getAssignmentName()));
    	
    	Update update = new Update();
    	update.set("assignment.$.completionDate",assignmentStudent.getCompletionDate())
    			.set("assignment.$.uploadLocation",assignmentPath)
    			.set("assignment.$.completedFlag",true);
    			
    	mongoOpers.updateFirst(query, update, User.class);
    	
		return true;
	}
}
