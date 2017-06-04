package com.sis.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sis.rest.bo.AttendanceBo;
import com.sis.rest.bo.impl.AttendanceBoImpl;
import com.sis.rest.pojo.Attendance;

/**
 * 
 * @author 618730
 *
 */
@Path("/attendance")
public class AttendanceService {

	private AttendanceBo attendanceBo;
	
	public AttendanceService() {
		attendanceBo = new AttendanceBoImpl();
	}
	
	@POST
	@Path("")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitAttendance(List<Attendance> attendanceList, @QueryParam("subjectName") String subjectName) {
		boolean submitFlag = attendanceBo.submitAttendance(attendanceList, subjectName);
		return Response.ok(submitFlag)
				.build();
	}	
	
	@GET
	@Path("")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getAttendance(@QueryParam("subjectName") String subjectName,
			@HeaderParam("userName") String userName) {
		List<Attendance> attendance = attendanceBo.getAttendance(subjectName, userName);
		return Response.ok(attendance)
				.build();
	}
}