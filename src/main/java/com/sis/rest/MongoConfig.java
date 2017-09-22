package com.sis.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories("com.sis.rest")
public class MongoConfig extends AbstractMongoConfiguration{
	
	@Override
	protected String getDatabaseName() {
		return "SIS";
	}

	/*@Override
	public Mongo mongo() throws Exception {
		return new MongoClient("localhost", 27017);
	}*/

	@Override
	public Mongo mongo() throws Exception {
		//this will nee latest 1.8 update f running Java 1.8.0_51 or 1.8.0_60, update to 1.8.0_65 as it contains a 
		//fix for the issue described in  BSERV-7741 - Secure LDAP connections are broken when using Java 1.8u51+, 
		//1.7.0_85+ and 1.6.0_101+
		MongoClientURI uri = new MongoClientURI(
									"mongodb://ysharma:Computer1@cluster0-shard-00-00-l1u5t.mongodb.net:27017,"+
									"cluster0-shard-00-01-l1u5t.mongodb.net:27017,"+
									"cluster0-shard-00-02-l1u5t.mongodb.net:27017/SIS?"+
									"ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");
		 
		MongoClient mongoClient = new MongoClient(uri);
		return mongoClient;	
	}
	
	/*@Override
	public Mongo mongo() throws Exception {
		MongoCredential credential = 
				MongoCredential.createMongoCRCredential("ysharma", "SIS", "Computer1".toCharArray());
		
		return new MongoClient(new ServerAddress(), 
				Arrays.asList(credential));
	}*/

}
