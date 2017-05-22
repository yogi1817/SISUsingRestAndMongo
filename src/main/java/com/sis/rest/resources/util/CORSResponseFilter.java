package com.sis.rest.resources.util;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSResponseFilter implements ContainerResponseFilter {

	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		MultivaluedMap<String, Object> headers = responseContext.getHeaders();

		//headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Origin", "http://localhost:3000"); //allows CORS requests only coming from podcastpedia.org		
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");			
		headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia, userName, Authorization, role, customAuthorization");
		//The below line will allow these headers to be available on angular side
		headers.add("Access-Control-Expose-Headers", "authenticatedUser, jwt, role");
		//The below line tells what header are allowed in request header
		headers.add("Access-Control-Request-Headers", "customAuthorization, userName");
	}

}
