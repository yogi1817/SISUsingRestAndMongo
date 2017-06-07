package com.sis.rest.dao;

import java.util.List;

import com.sis.rest.pojo.Assignment;
import com.sis.rest.pojo.User;

public interface AssignmentDao {
	boolean updateMongoForAssignment(Assignment assignmentTeacher, 
			Assignment assignmentStudent, String userId, List<User> userList);
	
	List<Assignment> getAssignments(String userName);
	
	User getUserDetails(String userName);
}
