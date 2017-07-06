package com.sis.rest.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sis.rest.bo.LoginBo;
import com.sis.rest.dao.LoginDao;
import com.sis.rest.pojo.LoginCredentials;
import com.sis.rest.pojo.User;

/**
 * 
 * @author 618730
 *
 */
@Component
public class LoginBoImpl implements LoginBo{
	
	@Autowired
	private LoginDao loginDao;

	@Override
	public User validateLogin(LoginCredentials credentials) {
		User user = loginDao.validateLogin(credentials);
		return user;
	}
}
