package com.sis.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig{
/*    public Set<Class<?>> getClasses() {
    	final Set<Class<?>> resources = new HashSet<Class<?>>();
    	resources.add(LoginService.class);
    	resources.add(CORSResponseFilter.class);
    	resources.add(JWTAuthFilter.class);
    	resources.add(JWTResponseFilter.class);
    	resources.add(AssignmentService.class);
    	resources.add(AttendanceService.class);
    	resources.add(MultiPartFeature.class);
    	
        return resources;
    }*/
	
	public ApplicationConfig() {
        packages("com.sis.rest.resources, com.fasterxml.jackson.jaxrs.json");
        register(MultiPartFeature.class);
    }
}
