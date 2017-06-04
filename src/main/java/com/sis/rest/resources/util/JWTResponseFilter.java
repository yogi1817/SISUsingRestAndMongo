package com.sis.rest.resources.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.glassfish.jersey.server.ExtendedUriInfo;

import com.sis.rest.utilities.security.JWTokenUtility;

@Provider
public class JWTResponseFilter implements ContainerResponseFilter {

	@Context 
	private ExtendedUriInfo uriInfo;
	
	private static final Logger logger = LogManager.getLogger(JWTResponseFilter.class);
	
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
                    logger.error("JWT auth failed. No need to return JWT token");
                    return;
                }
            }
        }
        
        ThreadContext.remove("myUuid");
    }
    
}