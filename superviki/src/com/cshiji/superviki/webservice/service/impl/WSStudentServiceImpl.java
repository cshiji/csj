package com.cshiji.superviki.webservice.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.cshiji.superviki.javaee.vo.Student;
import com.cshiji.superviki.webservice.service.WSStudentService;

@Service("WSStudentService")
@WebService(endpointInterface = "com.cshiji.superviki.webservice.service.WSStudentService")
public class WSStudentServiceImpl implements WSStudentService {

 @Override
 public Student getStudent() {
	 Student student=new Student();
		student.setStuId("1");
		student.setAge(20);
		student.setStuName("张三");
		Date date=new Date();
		student.setInsertTime(date);
		return student;
 }
}
