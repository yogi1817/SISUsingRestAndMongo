package com.sis.rest.dao.impl;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.mongodb.client.MongoDatabase;
import com.sis.rest.dao.LoginDao;
import com.sis.rest.pojo.LoginCredentials;
import com.sis.rest.pojo.User;
import com.sis.rest.utilities.MongoDataSourceFactory;

public class LoginDaoImpl implements LoginDao{

	MongoDataSourceFactory dataSourceFactory = null;
	Datastore morphiaDataStore = null; 
	
	MongoDatabase dbConnection = null;
	
	public LoginDaoImpl() {		
		dataSourceFactory = MongoDataSourceFactory.getInstance();
		morphiaDataStore = dataSourceFactory.getMorphiaSISDataStore();
	}
	
	@Override
	public User validateLogin(LoginCredentials credentials) {
		
			Query<User> authenticatedUserQuery = morphiaDataStore.find(User.class)
					.filter("userId", credentials.getUserName())
					.filter("password", credentials.getPassword());
			
			List<User> authenticatedUserList = authenticatedUserQuery.asList();
			if(authenticatedUserList!=null)
				return authenticatedUserList.get(0);
			
			return null;
	}
}
