package com.sis.rest.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoDataSourceFactory {

	private static final MongoDataSourceFactory instance = new MongoDataSourceFactory();
	private static final Logger logger = LogManager.getLogger(MongoDataSourceFactory.class);
	private MongoDataSourceFactory(){
		
	}
	
	/**
	 * Singleton
	 * @return
	 */
	public static MongoDataSourceFactory getInstance(){
        return instance;
    }
	
	/**
	 * This is the method to get mongo DB connection using morphia api
	 * We are using this as this helps us gets the direct objects instead of json
	 * @return
	 */
	public Datastore getMorphiaSISDataStore(){
		final Morphia morphia = new Morphia();
		// tell morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.mapPackage("com.sis.rest.pojo");

        // create the Datastore connecting to the database running on the default port on the local host
        final Datastore datastore = morphia.createDatastore(getMongoClient(), "SIS");
        
        return datastore;
	}
	
	/**
	 * This is to get direct connection with Mongo DB using native jar
	 * right now we are not using this.
	 * @return
	 */
	public MongoDatabase getMySISDataSource() {
		MongoDatabase db = getMongoClient().getDatabase("SIS");
		logger.debug("Connect to database successfully");
		return db;
	}	
	
	/**
	 * This is to close connection with Mongo DB using native jar
	 */
	public void closeMySISDataSource() {
		getMongoClient().close();
		logger.debug("Connection closed");
	}
	
	/**
	 * This is to generate Mongo Client using singletom method.
	 * @return
	 */
	private static MongoClient getMongoClient(){
		//this will nee latest 1.8 update f running Java 1.8.0_51 or 1.8.0_60, update to 1.8.0_65 as it contains a 
		//fix for the issue described in  BSERV-7741 - Secure LDAP connections are broken when using Java 1.8u51+, 
		//1.7.0_85+ and 1.6.0_101+
		MongoClientURI uri = new MongoClientURI(
				   "mongodb://ysharma:Computer1@cluster0-shard-00-00-l1u5t.mongodb.net:27017,"
				   + "cluster0-shard-00-01-l1u5t.mongodb.net:27017,"
				   + "cluster0-shard-00-02-l1u5t.mongodb.net:27017/SIS?"
				   + "ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");

		MongoClient mongoClient = new MongoClient(uri);
		//MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
		return mongoClient;
	}
}
