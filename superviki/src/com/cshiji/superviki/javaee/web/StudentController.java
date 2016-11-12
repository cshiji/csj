package com.cshiji.superviki.javaee.web;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cshiji.superviki.javaee.service.StudentService;
import com.cshiji.superviki.javaee.vo.Student;

@Controller
public class StudentController extends BaseController{
	@Resource(name="studentService")
	private StudentService studentService;
	
	@RequestMapping("/helloMybatis")
	public String testMybatis(){
		Student student=new Student();
		student.setStuId("1");
		student.setAge(20);
		student.setStuName("张三");
		Date date=new Date();
		student.setInsertTime(date);
		studentService.create(student);
		
		return "Hello";
	}
	
	@RequestMapping("/helloHibernate")
	public String testHibernate(){
		Student student=new Student();
		student.setStuId("2");
		student.setAge(30);
		student.setStuName("李四");
		Date date=new Date();
		student.setInsertTime(date);
		save(student);
		return "Hello";
	}
}
