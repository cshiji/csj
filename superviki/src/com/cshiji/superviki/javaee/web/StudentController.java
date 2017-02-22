package com.cshiji.superviki.javaee.web;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static Logger log = LoggerFactory.getLogger(StudentController.class);  
	
	@Resource(name="studentService")
	private StudentService studentService;
	
	@RequestMapping("/helloMybatis")
	public String testMybatis(){
		log.info("消息!!!!!!!!!!");
		log.warn("警告！！！！！！！");
		Student student=new Student();
		student.setStuId("1");
		student.setAge(20);
		student.setStuName("张三");
		Date date=new Date();
		student.setInsertTime(date);
		studentService.create(student);
		
		return "Hello";
	}
	
	/*swagger 测试*/
	@RequestMapping("/helloHibernate")
	@ApiOperation(value = "根据学生id，获取学生", httpMethod = "GET", response =Student.class, notes = "根据用户id获取用户对象")
	@ResponseBody
	public Student testHibernate(@ApiParam(required = true, name = "stuId", value 
			= "用户信息json数据") @RequestParam("stuId") String stuId){
		Student student=(Student) get(stuId,Student.class);
		return student;
	}
	
	public static void main(String[] args) {
		Map<String, String> map=new Hashtable<>();
		map.put("s1", "dd");
		map.put("s2", "dd");
		System.out.println(map.get("s1").hashCode());
		System.out.println(map.get("s2").hashCode());
		log.debug("debug!!!!!!!!!!");
		log.error("错误！！！！！！！");
	}
}
