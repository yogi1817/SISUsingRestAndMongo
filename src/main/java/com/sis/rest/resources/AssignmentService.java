package com.sis.rest.resources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.collections4.CollectionUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.sis.rest.bo.AssignmentBo;
import com.sis.rest.bo.impl.AssignmentBoImpl;
import com.sis.rest.pojo.Assignment;

/**
 * 
 * @author 618730
 *
 */
@Path("/assignments")
public class AssignmentService {

	private AssignmentBo assignmentBo;
	
	public AssignmentService() {
		assignmentBo = new AssignmentBoImpl();
	}
	
	@POST
	@Path("/admin")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response loadAssignments(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, 
			@FormDataParam("class") String classNo, @FormDataParam("section") String section,
			@FormDataParam("subject") String subject, 
			@FormDataParam("completionDate") String completionDate,
			@HeaderParam("userName") String userName) {
		
		
		boolean loadAssignmentFlag = assignmentBo.uploadAssignment(classNo, section, 
										subject, completionDate, userName, uploadedInputStream, fileDetail);
		
		return Response.ok(loadAssignmentFlag)
				.build();
	}	
	
	@GET
	@Path("/student")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getAssignments(@HeaderParam("userName") String userName) {
		List<Assignment> assignmentList = assignmentBo.getAssignments(userName);
		
		if(CollectionUtils.isEmpty(assignmentList)){
			throw new NotFoundException("No Assignment Found");
		}
		
		return Response.ok(assignmentList)
				.build();
	}
	
	@GET
	@Path("/student/{subject}/{assignmentName}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFile(@PathParam("assignmentName") String assignmentName,
			@PathParam("subject") String subject,
			@HeaderParam("userName") String userName) {
		String filePath = assignmentBo.getFilePath(userName, subject);
	    
		if(null==filePath){
	    	throw new NotFoundException("You dont have access to this assignment");
	    }
		
		 StreamingOutput fileStream =  new StreamingOutput() 
	       	{
	            @Override
	            public void write(java.io.OutputStream output) throws IOException, WebApplicationException 
	            {
	                try
	                {
	                    java.nio.file.Path path = Paths.get(filePath+assignmentName);
	                    byte[] data = Files.readAllBytes(path);
	                    output.write(data);
	                    output.flush();
	                } 
	                catch (Exception e) 
	                {
	                    throw new WebApplicationException("File Not Found !!");
	                }
	            }
	        };
	        
        return Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition","attachment; filename = "+assignmentName)
                .build();
	        
		/*File file = new File(filePath+assignmentName);
	    
	    ResponseBuilder response = Response.ok((Object) file);
	    response.header("Content-Disposition", "attachment;filename="+assignmentName);
	    
	    return response.build();*/
	}
	
	@POST
	@Path("/student")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response loadStudentAssignments(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, 
			@FormDataParam("class") String classNo, @FormDataParam("section") String section,
			@FormDataParam("subject") String subject,
			@HeaderParam("userName") String userName) {
		
		
		boolean loadAssignmentFlag = assignmentBo.uploadStudentAssignment(classNo, section, 
										subject, userName, uploadedInputStream, fileDetail);
		
		return Response.ok(loadAssignmentFlag)
				.build();
	}
}