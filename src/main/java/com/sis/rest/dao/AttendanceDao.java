package com.sis.rest.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AttendanceDao {
	boolean submitAttendance(Map<Integer, List<Date>> attendanceMap, String subject);
}
