package com.cshiji.superviki.webservice.service;

import javax.jws.WebService;

import com.cshiji.superviki.javaee.vo.Student;

@WebService(name = "WSStudentService")
public interface WSStudentService  {
 public Student getStudent();
}
