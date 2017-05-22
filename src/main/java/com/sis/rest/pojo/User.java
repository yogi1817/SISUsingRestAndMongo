package com.sis.rest.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author 618730
 *
 */
@XmlRootElement(name = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity("users")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5689992839054584351L;
	@Id
	private ObjectId id;
	private String firstName;
	private String lastName;
	private String password;
	private Date dob;
	private String passwordHint;
	private Date updateDate;
	private String userId;
	private String role;
	private int rollNo;
	private List<ClassDetail> classDetails;
	private List<Address> address;
	private List<Assignment> assignment;
	private List<Attendance> attendance;
	
	public User(){
		
	}
	
	public User(StudentBuilder builder) {
	    this.firstName = builder.firstName;
	    this.lastName = builder.lastName;
	    this.dob = builder.dob;
	    this.rollNo = builder.rollNo;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}
	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}
	/**
	 * @return the passwordHint
	 */
	public String getPasswordHint() {
		return passwordHint;
	}
	/**
	 * @param passwordHint the passwordHint to set
	 */
	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}
	/**
	 * @return the classDetailList
	 */
	public List<ClassDetail> getClassDetails() {
		return classDetails;
	}
	/**
	 * @param classDetailList the classDetailList to set
	 */
	public void setClassDetails(List<ClassDetail> classDetails) {
		this.classDetails = classDetails;
	}
	/**
	 * @return the address
	 */
	public List<Address> getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	/**
	 * @return the assignment
	 */
	public List<Assignment> getAssignment() {
		return assignment;
	}
	/**
	 * @param assignment the assignment to set
	 */
	public void setAssignment(List<Assignment> assignment) {
		this.assignment = assignment;
	}
	/**
	 * @return the rollNo
	 */
	public int getRollNo() {
		return rollNo;
	}
	/**
	 * @param rollNo the rollNo to set
	 */
	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}

	/**
	 * @return the attendance
	 */
	public List<Attendance> getAttendance() {
		return attendance;
	}

	/**
	 * @param attendance the attendance to set
	 */
	public void setAttendance(List<Attendance> attendance) {
		this.attendance = attendance;
	}

	/**
	 * Student factory pattern
	 * @author 618730
	 *
	 */
	public static class StudentBuilder {
	    private final String firstName;
	    private final String lastName;
	    private Date dob;
	    private int rollNo;

	    public StudentBuilder(String firstName, String lastName) {
	      this.firstName = firstName;
	      this.lastName = lastName;
	    }

	    public StudentBuilder dob(Date dob) {
	      this.dob = dob;
	      return this;
	    }

	    public StudentBuilder rollNo(int rollNo) {
	      this.rollNo = rollNo;
	      return this;
	    }

	    public User build() {
	      return new User(this);
	    }
	  }
}
