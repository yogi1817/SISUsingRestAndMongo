package com.sis.rest.bo.impl;

import com.sis.rest.bo.LoginBo;
import com.sis.rest.dao.LoginDao;
import com.sis.rest.dao.impl.LoginDaoImpl;
import com.sis.rest.pojo.LoginCredentials;
import com.sis.rest.pojo.User;

/**
 * 
 * @author 618730
 *
 */
public class LoginBoImpl implements LoginBo{
	
	private LoginDao loginDao;
	
	public LoginBoImpl() {
		loginDao = new LoginDaoImpl();
	}

	@Override
	public User validateLogin(LoginCredentials credentials) {
		User user = loginDao.validateLogin(credentials);
		return user;
		//Test
	}
}
