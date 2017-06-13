package com.sis.rest.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.sis.rest.bo.AdminBo;
import com.sis.rest.dao.AdminDao;
import com.sis.rest.dao.impl.AdminDaoImpl;
import com.sis.rest.pojo.User;

/**
 * 
 * @author 618730
 *
 */
public class AdminBoImpl implements AdminBo{
	
	private AdminDao adminDao;
	
	public AdminBoImpl() {
		adminDao = new AdminDaoImpl();
	}

	@Override
	public List<User> getStudentsForAdmin(int classNo, String section, String subject) {
		List<User> studentList = new ArrayList<User>();
		
		List<User> users= adminDao.getStudentsForAdmin(classNo, section, subject);
		for (User user : users) {
			studentList.add(new User.StudentBuilder(
		 				user.getFirstName(), user.getLastName())
							.dob(user.getDob())
							.rollNo(user.getRollNo())
							.build()
							);
		}
		return studentList;
	}
}
