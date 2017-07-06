package com.sis.rest.bo.impl;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sis.rest.bo.AttendanceBo;
import com.sis.rest.dao.AttendanceDao;
import com.sis.rest.pojo.Attendance;
import com.sis.rest.pojo.User;
import com.sis.rest.utilities.DateUtility;

/**
 * 
 * @author 618730
 *
 */
@Component
public class AttendanceBoImpl implements AttendanceBo{
	
	@Autowired
	private AttendanceDao attendanceDao;

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
		int year = Calendar.getInstance().get(Calendar.YEAR);//Year.now().getValue() Java 8
		List<Date> holidaysInCurrentYear = attendanceDao.getHolidaysForYear(year);
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
		Map<Integer, List<Date>> holidayForMonth = absenceDatesForMonth(holidaysInCurrentYear);
		Map<Integer, List<Date>> absenceDatesForMonth = absenceDatesForMonth(studentAttendance);
		List<Attendance> attendanceList = new ArrayList<Attendance>();
		Attendance attendance = null;
		for (Map.Entry<Integer, List<Date>> entry : absenceDatesForMonth.entrySet()) {
			attendance = new Attendance();
			attendance.setMonthNo(entry.getKey());
			attendance.setDatesAbsent(entry.getValue());
			
			int holidayCount = holidayForMonth.get(entry.getKey())==null?0:holidayForMonth.get(entry.getKey()).size();
			attendance.setPercentageAbsent(
					calculatePercentage(entry.getKey(), entry.getValue().size(), holidayCount)
					);
			
			attendanceList.add(attendance);
		}
		return attendanceList;
	}
	
	private float calculatePercentage(Integer monthNo, int absenceDaysCount, int holidayDayCount){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int daysInGivenMonth = calendar.getActualMaximum(Calendar.DATE); //Month.valueOf(monthName).length(true); Java 8
		return (((float)absenceDaysCount*100)/(daysInGivenMonth-holidayDayCount));
	}
	/**
	 * Convert list of dates into map of month and corresponding dates
	 * @param studentAttendance
	 * @return
	 */
	private Map<Integer, List<Date>> absenceDatesForMonth(List<Date> studentAttendance)
	{
		Map<Integer, List<Date>> absenceDatesForMonth = new HashMap<Integer, List<Date>>();
		/*Instant instant = null;
		LocalDate localDate = null;*/
		
		//Java 7
		Calendar now = Calendar.getInstance();
		Integer monthNo;
		//String monthName = null;
		List<Date> datesAbsent = null;
		for (Date absence : studentAttendance) {
			/*instant = absence.toInstant();
			localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
			monthName = localDate.getMonth().name();*/
			now.setTime(absence);
			monthNo = now.get(Calendar.MONTH);
			//monthName = getMonthForInt(month);
			if(absenceDatesForMonth.containsKey(monthNo)){
				datesAbsent = absenceDatesForMonth.get(monthNo);
			}else{
				datesAbsent = new ArrayList<Date>();
			}
			datesAbsent.add(absence);
			absenceDatesForMonth.put(monthNo, datesAbsent);
		}
		return absenceDatesForMonth;
	}
	
	
	/**
	 * Java7
	 * @param num
	 * @return
	 */
	String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
}
