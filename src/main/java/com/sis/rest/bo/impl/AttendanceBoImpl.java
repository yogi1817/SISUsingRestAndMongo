package com.sis.rest.bo.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sis.rest.bo.AttendanceBo;
import com.sis.rest.dao.AttendanceDao;
import com.sis.rest.dao.impl.AttendanceDaoImpl;
import com.sis.rest.pojo.Attendance;
import com.sis.rest.pojo.User;
import com.sis.rest.utilities.DateUtility;

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
				dateList.add(DateUtility.getDateForDay(attendance.getDay()));
				attendanceMap.put(attendance.getRollNo(), dateList);
			}
			submitFlag = attendanceDao.submitAttendance(attendanceMap, subject);
		}
		
		return submitFlag;
	}

	@Override
	public List<Attendance> getAttendance(String subjectName, String userName) {
		User studentObject = attendanceDao.getAttendance(subjectName, userName);
		List<Date> holidaysInCurrentYear = attendanceDao.getHolidaysForYear(Year.now().getValue());
		List<Attendance> attendance = convertStudentToAttendance(
				studentObject.getAttendance()!=null ? studentObject.getAttendance().get(0).getDatesAbsent() : null, holidaysInCurrentYear);
		return attendance;
	}
	
	/**
	 * This method takes data from user object and convert in to attendance object
	 * We created data for every month attendance and year attendance till todays date.
	 * @param studentObject
	 * @return
	 */
	private List<Attendance> convertStudentToAttendance(List<Date> studentAttendance, List<Date> holidaysInCurrentYear){
		Map<String, List<Date>> holidayForMonth = absenceDatesForMonth(holidaysInCurrentYear);
		Map<String, List<Date>> absenceDatesForMonth = absenceDatesForMonth(studentAttendance);
		List<Attendance> attendanceList = new ArrayList<Attendance>();
		Attendance attendance = null;
		for (Map.Entry<String, List<Date>> entry : absenceDatesForMonth.entrySet()) {
			attendance = new Attendance();
			attendance.setMonthName(entry.getKey());
			attendance.setDatesAbsent(entry.getValue());
			attendance.setPercentageAbsent(
					calculatePercentage(entry.getKey(), entry.getValue().size(), holidayForMonth.get(entry.getKey()).size())
					);
			
			attendanceList.add(attendance);
		}
		return attendanceList;
	}
	
	private float calculatePercentage(String monthName, int absenceDaysCount, int holidayDayCount){
		int daysInGivenMonth = Month.valueOf(monthName).length(true);
		return (((float)absenceDaysCount*100)/(daysInGivenMonth-holidayDayCount));
	}
	/**
	 * Convert list of dates into map of month and corresponding dates
	 * @param studentAttendance
	 * @return
	 */
	private Map<String, List<Date>> absenceDatesForMonth(List<Date> studentAttendance)
	{
		Map<String, List<Date>> absenceDatesForMonth = new HashMap<String, List<Date>>();
		Instant instant = null;
		LocalDate localDate = null;
		String monthName = null;
		List<Date> datesAbsent = null;
		for (Date absence : studentAttendance) {
			instant = absence.toInstant();
			localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
			monthName = localDate.getMonth().name();
			
			if(absenceDatesForMonth.containsKey(monthName)){
				datesAbsent = absenceDatesForMonth.get(monthName);
			}else{
				datesAbsent = new ArrayList<Date>();
			}
			datesAbsent.add(absence);
			absenceDatesForMonth.put(monthName, datesAbsent);
		}
		return absenceDatesForMonth;
	}
}
