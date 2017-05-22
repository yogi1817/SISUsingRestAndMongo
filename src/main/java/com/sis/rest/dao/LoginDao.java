package com.sis.rest.dao;

import com.sis.rest.pojo.LoginCredentials;
import com.sis.rest.pojo.User;

public interface LoginDao {
	User validateLogin(LoginCredentials credentials);
}
