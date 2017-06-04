package com.sis.rest.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.mongodb.client.MongoDatabase;
import com.sis.rest.dao.AdminDao;
import com.sis.rest.pojo.User;
import com.sis.rest.utilities.MongoDataSourceFactory;

public class AdminDaoImpl implements AdminDao{

	MongoDataSourceFactory dataSourceFactory = null;
	Datastore morphiaDataStore = null; 
	
	MongoDatabase dbConnection = null;
	
	private static final Logger logger = LogManager.getLogger(LoginDaoImpl.class);
	
	public AdminDaoImpl() {		
		dataSourceFactory = MongoDataSourceFactory.getInstance();
		morphiaDataStore = dataSourceFactory.getMorphiaSISDataStore();
	}
	
	@Override
	public List<User> getStudentsForAdmin(int classNo, String section, String subject){
			Query<User> studentQuery = morphiaDataStore.find(User.class)
					.filter("classDetails.classNo", classNo)
					.filter("classDetails.section", section)
					.filter("classDetails.subject", subject)
					.filter("role", "student");
			
			logger.debug("studentQuery in getStudentsForAdmin "+studentQuery);
			
			List<User> studentList = studentQuery.asList();
			
			return studentList;
	}
}
