package com.sis.rest.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sis.rest.pojo.User;

public interface AttendanceDao {
	boolean submitAttendance(Map<Integer, List<Date>> attendanceMap, String subject);
	User getAttendance(String subjectName, String userName);
	List<Date> getHolidaysForYear(int year);
}
