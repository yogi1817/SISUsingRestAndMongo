package com.sis.rest.pojo;

import java.io.Serializable;
import java.util.List;

public class ClassDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int classNo;
	private String section;
	private List<String> subject;
	
	/**
	 * @return the classNo
	 */
	public int getClassNo() {
		return classNo;
	}
	/**
	 * @param classNo the classNo to set
	 */
	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}
	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}
	/**
	 * @return the subject
	 */
	public List<String> getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(List<String> subject) {
		this.subject = subject;
	}
}
