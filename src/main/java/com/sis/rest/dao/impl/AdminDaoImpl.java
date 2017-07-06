package com.sis.rest.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sis.rest.dao.AdminDao;
import com.sis.rest.dao.repository.UserRepository;
import com.sis.rest.pojo.User;

@Repository
public class AdminDaoImpl implements AdminDao{
	
	@Autowired
	UserRepository mongo;
	
	@Override
	public List<User> getStudentsForAdmin(int classNo, String section, String subject){			
			List<User> studentList = mongo.getStudentsForAdmin(
					classNo, section, subject);
			
			return studentList;
	}
}
