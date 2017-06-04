package com.sis.rest.resources.exceptions;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {
	 	@Override
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response toResponse(NotAuthorizedException e) {

	        ResponseBuilder rb = Response.status(Status.BAD_REQUEST);

	        rb.entity(e.getChallenges().get(0));
	        return rb.build();   
	    }
}
