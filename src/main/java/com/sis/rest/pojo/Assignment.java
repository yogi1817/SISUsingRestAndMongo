package com.sis.rest.pojo;

import java.io.Serializable;
import java.util.Date;

public class Assignment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subject;
	private String assignmentName;
	private Date dateCreated;
	private Date completionDate;
	private String uploadLocation;
	private String downloadLocation;
	private String classAndSection;
	private Boolean completedFlag;
	
	public Assignment(){
		
	}
	
	public Assignment(String subject, String assignmentName, Date dateCreated,
						Date completionDate, String uploadLocation, String classAndSection){
		this.subject = subject;
		this.assignmentName = assignmentName;
		this.classAndSection = classAndSection;
		this.completionDate = completionDate;
		this.dateCreated = dateCreated;
		this.uploadLocation = uploadLocation;
	}
	
	public Assignment(String subject, String assignmentName, Date dateCreated,
			Date completionDate, String downloadLocation, boolean completedFlag){
		this.subject = subject;
		this.assignmentName = assignmentName;
		this.completionDate = completionDate;
		this.dateCreated = dateCreated;
		this.downloadLocation = downloadLocation;
		this.completedFlag = completedFlag;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the assignmentName
	 */
	public String getAssignmentName() {
		return assignmentName;
	}
	/**
	 * @param assignmentName the assignmentName to set
	 */
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}
	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	/**
	 * @return the completionDate
	 */
	public Date getCompletionDate() {
		return completionDate;
	}
	/**
	 * @param completionDate the completionDate to set
	 */
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	/**
	 * @return the uploadLocation
	 */
	public String getUploadLocation() {
		return uploadLocation;
	}
	/**
	 * @param uploadLocation the uploadLocation to set
	 */
	public void setUploadLocation(String uploadLocation) {
		this.uploadLocation = uploadLocation;
	}
	/**
	 * @return the classAndSection
	 */
	public String getClassAndSection() {
		return classAndSection;
	}
	/**
	 * @param classAndSection the classAndSection to set
	 */
	public void setClassAndSection(String classAndSection) {
		this.classAndSection = classAndSection;
	}

	/**
	 * @return the downloadLocation
	 */
	public String getDownloadLocation() {
		return downloadLocation;
	}

	/**
	 * @param downloadLocation the downloadLocation to set
	 */
	public void setDownloadLocation(String downloadLocation) {
		this.downloadLocation = downloadLocation;
	}

	/**
	 * @return the completedFlag
	 */
	public Boolean isCompletedFlag() {
		return completedFlag;
	}

	/**
	 * @param completedFlag the completedFlag to set
	 */
	public void setCompletedFlag(Boolean completedFlag) {
		this.completedFlag = completedFlag;
	}
}