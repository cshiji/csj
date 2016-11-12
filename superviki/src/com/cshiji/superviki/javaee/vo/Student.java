package com.cshiji.superviki.javaee.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("Student")
public class Student {
	private String stuId;
	private String stuName;
	private int age;
	private Date insertTime;
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
	
}
