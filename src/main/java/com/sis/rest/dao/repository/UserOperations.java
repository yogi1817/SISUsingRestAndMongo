package com.sis.rest.dao.repository;

import com.sis.rest.pojo.User;

public interface UserOperations {
	User getAttendanceForUserIdAndSubject(String subjectName, String userName);
}
