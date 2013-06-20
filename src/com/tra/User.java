package com.tra;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	
	private int userId;
	private String fname;
	private String lname;
	
	public String getFname() {
		return fname;
	}
	@XmlElement
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	@XmlElement
	public void setLname(String lname) {
		this.lname = lname;
	}
	public int getUserId() {
		return userId;
	}
	@XmlAttribute
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
