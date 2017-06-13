package com.sis.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sis.rest.bo.AdminBo;
import com.sis.rest.bo.impl.AdminBoImpl;
import com.sis.rest.pojo.User;

/**
 * 
 * @author 618730
 *
 */
@Path("/admin")
public class AdminService {

	private AdminBo adminBo;
	
	public AdminService() {
		adminBo = new AdminBoImpl();
	}
	
	@GET
	@Path("/students")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getStudentsForAdmin(@QueryParam("classNo") int classNo,
			@QueryParam("section") String section, @QueryParam("subject") String subject,
			@Context Request request) {
		
		List<User> studentList = adminBo.getStudentsForAdmin(classNo, section, subject);
		
		/*CacheControl cc = new CacheControl();
		cc.setMaxAge(86400);
		cc.setPrivate(true);*/
		
		int hashcode = 0;
		for (User student : studentList) {
			hashcode+= (classNo+student.getRollNo());
		}
		EntityTag eTag = new EntityTag(Integer.toString(hashcode).hashCode()+"");
		ResponseBuilder builder = request.evaluatePreconditions(eTag);
		
		if(builder == null){
			builder = Response.ok(studentList);
			builder.tag(eTag);
		}
		
		//builder.cacheControl(cc);
		return builder.build();
	}	
}