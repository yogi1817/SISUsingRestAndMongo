package com.sis.rest.bo;

import java.util.List;

import com.sis.rest.pojo.User;

/**
 * 
 * @author 618730
 *
 */
public interface AdminBo {
	List<User> getStudentsForAdmin(int classNo, String section, String subject);
}
