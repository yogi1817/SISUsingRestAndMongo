package com.sis.rest.dao.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sis.rest.pojo.User;

public interface UserRepository extends MongoRepository<User, String>{

	List<User> findByUserIdAndPassword(String userId, String password);
		
	@Query("{'classDetails.classNo': ?0, 'classDetails.section': ?1, "
			+ "'classDetails.subject': ?2, 'role': 'student'}")
	List<User> getStudentsForAdmin(int classNo, String section, String subject);
	
	User findByUserId(String userId);
}
