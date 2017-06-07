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

import com.sis.rest.bo.AssignmentBo;
import com.sis.rest.dao.AdminDao;
import com.sis.rest.dao.AssignmentDao;
import com.sis.rest.dao.impl.AdminDaoImpl;
import com.sis.rest.dao.impl.AssignmentDaoImpl;
import com.sis.rest.pojo.Assignment;
import com.sis.rest.pojo.ClassDetail;
import com.sis.rest.pojo.User;

/**
 * 
 * @author 618730
 *
 */
public class AssignmentBoImpl implements AssignmentBo{
	
	private AssignmentDao assignmentDao;
	private AdminDao adminDao;
	private final String parentdDirectory = "C:/Users/618730/Documents/SIS codebase/shareDrive/";
	public AssignmentBoImpl() {
		assignmentDao = new AssignmentDaoImpl();
		adminDao = new AdminDaoImpl();
	}
	
	private String loadFileOnDrive(String classNo, String section, String subject, 
			String fileName, InputStream uploadedInputStream){
		File file = new File(parentdDirectory+classNo+"/"+section+"/"+subject+"/");
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

	@Override
	public boolean uploadAssignment(String classNo, String section, String subject, 
			Date completionDate, String userId,
			InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {
		boolean uploadAssignmentFlag = false;
		String fullFileName = loadFileOnDrive(classNo, section, subject, 
				fileDetail.getFileName(), uploadedInputStream);
		
		Assignment assignmentTeacher = new Assignment(subject, fileDetail.getFileName(),
				new Date(), completionDate, fullFileName, classNo+section);
		
		Assignment assignmentStudent = new Assignment(subject, fileDetail.getFileName(),
				new Date(), completionDate, fullFileName, false);
		
		List<User> userList = adminDao.getStudentsForAdmin(Integer.parseInt(classNo), section, subject);
		if(fullFileName!=null)
			uploadAssignmentFlag = assignmentDao.updateMongoForAssignment(assignmentTeacher, assignmentStudent, userId, userList);
		
		return uploadAssignmentFlag;
	}

	@Override
	public List<Assignment> getAssignments(String userName) {
		return assignmentDao.getAssignments(userName);
	}

	@Override
	public String getFilePath(String userName, String subject) {
		User user = assignmentDao.getUserDetails(userName);
		
		if(user==null)
			return null;
		
		ClassDetail classDetail = user.getClassDetails().get(0);
		String filePath = parentdDirectory+classDetail.getClassNo()+"/"+
					classDetail.getSection()+"/"+subject+"/";
					
		return filePath;
	}
}
