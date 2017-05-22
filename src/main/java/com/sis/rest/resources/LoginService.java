package com.sis.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.sis.rest.bo.LoginBo;
import com.sis.rest.bo.impl.LoginBoImpl;
import com.sis.rest.pojo.LoginCredentials;
import com.sis.rest.pojo.User;
import com.sis.rest.utilities.security.JWTokenUtility;

/**
 * 
 * @author 618730
 *
 */
@Path("/login")
public class LoginService {

	private LoginBo loginBo;
	
	public LoginService() {
		loginBo = new LoginBoImpl();
	}
	@Context
    SecurityContext sctx;
	
	@POST
	@Path("/validateUser")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validateLogin(LoginCredentials credentials) {
		String authenticatedUser = null;
		User user = loginBo.validateLogin(credentials);
		if(user!=null){
			authenticatedUser = credentials.getUserName();
		}
		
		return Response.ok(user)
				.header("jwt", JWTokenUtility.buildJWT(authenticatedUser))
				.header("authenticatedUser", authenticatedUser)
				.header("role", user.getRole())
				.build();
	}	
}