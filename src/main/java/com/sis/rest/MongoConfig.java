package com.sis.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories("com.sis.rest")
public class MongoConfig extends AbstractMongoConfiguration{
	
	@Override
	protected String getDatabaseName() {
		return "sis";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient("localhost", 27017);
	}

	
	/*@Override
	public Mongo mongo() throws Exception {
		MongoCredential credential = 
				MongoCredential.createMongoCRCredential("ysharma", "SIS", "Computer1".toCharArray());
		
		return new MongoClient(new ServerAddress(), 
				Arrays.asList(credential));
	}*/

}
