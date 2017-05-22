package com.sis.rest.resources.util;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ExtendedUriInfo;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import com.google.common.base.Strings;
import com.sis.rest.utilities.security.RsaKeyProducer;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthFilter implements ContainerRequestFilter {

	@Context 
	private ExtendedUriInfo uriInfo;
	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeaderVal = requestContext.getHeaderString("customAuthorization");
        String userName = requestContext.getHeaderString("userName");

        if(!requestContext.getMethod().equals("OPTIONS") && Strings.isNullOrEmpty(userName)){
            requestContext.setProperty("auth-failed", true);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
        //consume JWT i.e. execute signature validation
        if (!(requestContext.getMethod().equals("OPTIONS") || uriInfo.getPath().equals("login/validateUser"))){
        	if(authHeaderVal.startsWith("Bearer")) {
                try {
                    final String subject = validate(authHeaderVal.split(" ")[1], userName);
                    if (subject == null) {
                    	System.out.println("The userName does not match with the logged in user");
                        requestContext.setProperty("auth-failed", true);
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    }
                } catch (InvalidJwtException ex) {
                    requestContext.setProperty("auth-failed", true);
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }

            } else {
                requestContext.setProperty("auth-failed", true);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }

    private String validate(String jwt, String userName) throws InvalidJwtException {
        String subject = null;
        RsaJsonWebKey rsaJsonWebKey = RsaKeyProducer.produce();

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireSubject() // the JWT must have a subject claim
                .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
                .build(); // create the JwtConsumer instance
        try {
            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
            subject = (String) jwtClaims.getClaimValue("sub");
            if(("["+userName+"]").equals(subject))
            	System.out.println("JWT validation succeeded! " + jwtClaims);
            else
            	subject = null;
        } catch (InvalidJwtException e) {
            e.printStackTrace(); //on purpose
            throw e;
        }
        return subject;
    }
}
