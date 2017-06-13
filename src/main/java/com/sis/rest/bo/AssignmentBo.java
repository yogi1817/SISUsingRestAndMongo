package com.sis.rest.bo;

import java.io.InputStream;
import java.util.List;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.sis.rest.pojo.Assignment;


/**
 * 
 * @author 618730
 *
 */
public interface AssignmentBo {
	boolean uploadAssignment(String classNo, String section, String subject, 
			String completionDate, String userId,
			InputStream uploadedInputStream, FormDataContentDisposition fileDetail);
	
	List<Assignment> getAssignments(String userName);
	
	String getFilePath(String userName, String subject);
	
	boolean uploadStudentAssignment(String classNo, String section, String subject, String userId,
			InputStream uploadedInputStream, FormDataContentDisposition fileDetail);
}
