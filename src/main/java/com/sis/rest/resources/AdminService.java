package com.sis.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
			@QueryParam("section") String section, @QueryParam("subject") String subject) {
		
		List<User> studentList = adminBo.getStudentsForAdmin(classNo, section, subject);
		return Response.ok(studentList)
				.build();
	}	
}