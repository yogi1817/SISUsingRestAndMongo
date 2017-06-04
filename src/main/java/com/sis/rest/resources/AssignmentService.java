package com.sis.rest.resources;

import java.io.InputStream;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.sis.rest.bo.AssignmentBo;
import com.sis.rest.bo.impl.AssignmentBoImpl;

/**
 * 
 * @author 618730
 *
 */
@Path("/assignment")
public class AssignmentService {

	private AssignmentBo assignmentBo;
	
	public AssignmentService() {
		assignmentBo = new AssignmentBoImpl();
	}
	
	@POST
	@Path("")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response loadAssignments(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, 
			@FormDataParam("class") String classNo, @FormDataParam("section") String section,
			@FormDataParam("subject") String subject, 
			@FormDataParam("completionDate") String completionDate,
			@HeaderParam("userName") String userName) {
		
		
		boolean loadAssignmentFlag = assignmentBo.uploadAssignment(classNo, section, 
										subject, new Date(), userName, uploadedInputStream, fileDetail);
		
		return Response.ok(loadAssignmentFlag)
				.build();
	}	
	
}