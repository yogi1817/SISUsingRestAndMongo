package com.sis.rest.dao;

import java.util.List;

import com.sis.rest.pojo.User;

public interface AdminDao {
	List<User> getStudentsForAdmin(int classNo, String section, String subject);
}
