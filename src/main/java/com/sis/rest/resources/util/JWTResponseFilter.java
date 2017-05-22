package com.sis.rest.resources.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ExtendedUriInfo;

import com.sis.rest.utilities.security.JWTokenUtility;

@Provider
public class JWTResponseFilter implements ContainerResponseFilter {

	@Context 
	private ExtendedUriInfo uriInfo;
	
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        List<Object> jwt = new ArrayList<Object>();
        if (uriInfo.getPath().equals("login/validateUser") && responseContext.getHeaders().get("authenticatedUser")!=null){
            jwt.add(JWTokenUtility.buildJWT(responseContext.getHeaders().get("authenticatedUser").toString()));
            responseContext.getHeaders().put("jwt", jwt);
        }else{
        	if(requestContext.getProperty("auth-failed") != null) {
                Boolean failed = (Boolean) requestContext.getProperty("auth-failed");
                if (failed) {
                    System.out.println("JWT auth failed. No need to return JWT token");
                    return;
                }
            }
        }
    }
    
}