package com.cshiji.superviki.javaee.quartz;

import java.util.Date;

import com.cshiji.superviki.javaee.vo.Student;
import com.cshiji.superviki.javaee.web.BaseController;

public class AddStudent extends BaseController{
	public void addStudent(){
		Student student=new Student();
		student.setAge(100);
		student.setInsertTime(new Date());
		student.setStuName("我拉个擦");
		save(student);
	}
}
