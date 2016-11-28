package com.cshiji.superviki.javaee.web;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cshiji.superviki.javaee.service.StudentService;
import com.cshiji.superviki.javaee.vo.Student;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
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
	@ApiOperation(value = "根据学生id，获取学生", httpMethod = "GET", response =Student.class, notes = "根据用户id获取用户对象")
	@ResponseBody
	public Student testHibernate(@ApiParam(required = true, name = "stuId", value 
			= "用户信息json数据") @RequestParam("stuId") String stuId){
		Student student=(Student) get(stuId,Student.class);
		return student;
	}
}
