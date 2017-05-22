package com.sis.rest.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sis.rest.bo.AttendanceBo;
import com.sis.rest.dao.AttendanceDao;
import com.sis.rest.dao.impl.AttendanceDaoImpl;
import com.sis.rest.pojo.Attendance;

/**
 * 
 * @author 618730
 *
 */
public class AttendanceBoImpl implements AttendanceBo{
	
	private AttendanceDao attendanceDao;
	
	public AttendanceBoImpl() {
		attendanceDao = new AttendanceDaoImpl();
	}

	@Override
	public boolean submitAttendance(List<Attendance> attendanceList, String subject) {
		Map<Integer, List<Date>> attendanceMap = new HashMap<Integer, List<Date>>();
		boolean submitFlag = false;
		List<Date> dateList = null;
		if(attendanceList!=null){
			for (Attendance attendance : attendanceList) {
				if(attendanceMap.containsKey(attendance.getRollNo())){
					dateList = attendanceMap.get(attendance.getRollNo());
				}
				else{
					dateList = new ArrayList<Date>();
				}
				dateList.add(attendance.getDateForDay(attendance.getDay()));
				attendanceMap.put(attendance.getRollNo(), dateList);
			}
			submitFlag = attendanceDao.submitAttendance(attendanceMap, subject);
		}
		
		return submitFlag;
	}}
