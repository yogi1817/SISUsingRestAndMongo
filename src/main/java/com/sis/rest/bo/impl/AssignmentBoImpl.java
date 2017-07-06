package com.sis.rest.bo.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sis.rest.bo.AssignmentBo;
import com.sis.rest.dao.AdminDao;
import com.sis.rest.dao.AssignmentDao;
import com.sis.rest.pojo.Assignment;
import com.sis.rest.pojo.ClassDetail;
import com.sis.rest.pojo.User;
import com.sis.rest.utilities.DateUtility;

/**
 * 
 * @author 618730
 *
 */
@Component
public class AssignmentBoImpl implements AssignmentBo{
	
	@Autowired
	private AssignmentDao assignmentDao;
	@Autowired
	private AdminDao adminDao;
	
	private final String teacherDirectory = "C:/Users/618730/Documents/SIS codebase/shareDrive/assignments/teacher/";
	private final String studentDirectory = "C:/Users/618730/Documents/SIS codebase/shareDrive/assignments/student/";
	
	/**
	 * 
	 * @param classNo
	 * @param section
	 * @param subject
	 * @param fileName
	 * @param uploadedInputStream
	 * @param parentDirectory
	 * @return
	 */
	private String loadFileOnDrive(String classNo, String section, String subject, 
			String fileName, InputStream uploadedInputStream, String parentDirectory){
		File file = new File(parentDirectory+classNo+"/"+section+"/"+subject+"/");
		try {
			FileUtils.forceMkdir(file);
			OutputStream out = new FileOutputStream(file.getAbsolutePath()+"/"+fileName);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
			//throw UserException;
		}
		return file.getAbsolutePath();
	}

	/**
	 * 
	 */
	@Override
	public boolean uploadAssignment(String classNo, String section, String subject, 
			String completionDate, String userId,
			InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {
		boolean uploadAssignmentFlag = false;
		String fullFileName = loadFileOnDrive(classNo, section, subject, 
				fileDetail.getFileName(), uploadedInputStream, teacherDirectory);
		
		Assignment assignmentTeacher = new Assignment(subject, fileDetail.getFileName(),
				DateUtility.changeDateFormat(new Date(), "yyyy/MM/dd"), 
				DateUtility.stringToDate(completionDate), 
				fullFileName, classNo+section);
		
		Assignment assignmentStudent = new Assignment(subject, fileDetail.getFileName(),
				DateUtility.changeDateFormat(new Date(), "yyyy/MM/dd"), 
				DateUtility.stringToDate(completionDate), 
				fullFileName, false);
		
		List<User> userList = adminDao.getStudentsForAdmin(Integer.parseInt(classNo), section, subject);
		if(fullFileName!=null)
			uploadAssignmentFlag = assignmentDao.updateMongoForAssignment(assignmentTeacher, assignmentStudent, userId, userList);
		
		return uploadAssignmentFlag;
	}

	/**
	 * 
	 */
	@Override
	public List<Assignment> getAssignments(String userName) {
		return assignmentDao.getAssignments(userName);
	}

	/**
	 * 
	 */
	@Override
	public String getFilePath(String userName, String subject) {
		User user = assignmentDao.getUserDetails(userName);
		
		if(user==null)
			return null;
		
		ClassDetail classDetail = user.getClassDetails().get(0);
		String filePath = teacherDirectory+classDetail.getClassNo()+"/"+
					classDetail.getSection()+"/"+subject+"/";
					
		return filePath;
	}

	/**
	 * 
	 */
	@Override
	public boolean uploadStudentAssignment(String classNo, String section, String subject, String userId,
			InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {
		boolean uploadAssignmentFlag = false;
		
		String fullFileName = loadFileOnDrive(classNo, section, subject, 
				fileDetail.getFileName(), uploadedInputStream, studentDirectory);
		
		Assignment assignmentStudent = new Assignment(subject, fileDetail.getFileName(),
				null, DateUtility.changeDateFormat(new Date(), "yyyy/MM/dd"), 
				fullFileName, false);
		
		if(fullFileName!=null)
			uploadAssignmentFlag = assignmentDao.updateMongoForStudentAssignment(assignmentStudent, userId, fullFileName);
		
		return uploadAssignmentFlag;
	}
}
