package com.sis.rest.bo;

import java.util.List;

import com.sis.rest.pojo.Attendance;


/**
 * 
 * @author 618730
 *
 */
public interface AttendanceBo {
	boolean submitAttendance(List<Attendance> attendanceMatrixJson, String subject);
}
