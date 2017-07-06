package com.sis.rest.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sis.rest.dao.LoginDao;
import com.sis.rest.dao.repository.UserRepository;
import com.sis.rest.pojo.LoginCredentials;
import com.sis.rest.pojo.User;

@Repository
public class LoginDaoImpl implements LoginDao{
	
	@Autowired
	UserRepository mongo;
	
	@Override
	public User validateLogin(LoginCredentials credentials) {
			List<User> users = mongo.findByUserIdAndPassword(
					credentials.getUserName(), credentials.getPassword());
			
			if(CollectionUtils.isNotEmpty(users))
				return users.get(0);
			
			return null;
	}
}
