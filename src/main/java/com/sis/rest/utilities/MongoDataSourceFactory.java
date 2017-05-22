package com.sis.rest.utilities;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoDataSourceFactory {

	private static final MongoDataSourceFactory instance = new MongoDataSourceFactory();
	
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
        final Datastore datastore = morphia.createDatastore(getMongoClient(), "sis");
        
        return datastore;
	}
	
	/**
	 * This is to get direct connection with Mongo DB using native jar
	 * right now we are not using this.
	 * @return
	 */
	public MongoDatabase getMySISDataSource() {
		MongoDatabase db = getMongoClient().getDatabase("sis");
		System.out.println("Connect to database successfully");
		return db;
	}	
	
	/**
	 * This is to close connection with Mongo DB using native jar
	 */
	public void closeMySISDataSource() {
		getMongoClient().close();
		System.out.println("Connection closed");
	}
	
	/**
	 * This is to generate Mongo Client using singletom method.
	 * @return
	 */
	private static MongoClient getMongoClient(){
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
		return mongoClient;
	}
}
