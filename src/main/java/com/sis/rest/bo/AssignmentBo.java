package com.sis.rest.bo;

import java.io.InputStream;
import java.util.Date;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;


/**
 * 
 * @author 618730
 *
 */
public interface AssignmentBo {
	boolean uploadAssignment(String classNo, String section, String subject, 
			Date completionDate, String userId,
			InputStream uploadedInputStream, FormDataContentDisposition fileDetail);
}
