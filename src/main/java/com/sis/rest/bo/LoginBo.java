package com.sis.rest.bo;

import com.sis.rest.pojo.LoginCredentials;
import com.sis.rest.pojo.User;

/**
 * 
 * @author 618730
 *
 */
public interface LoginBo {
	User validateLogin(LoginCredentials credentials);
}
