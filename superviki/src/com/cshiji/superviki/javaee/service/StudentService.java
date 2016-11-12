package com.cshiji.superviki.javaee.service;

import java.util.Map;

import com.cshiji.superviki.base.mybatis.Pager;
import com.cshiji.superviki.javaee.vo.Student;

public interface StudentService {
	
	/**
	 * 保存
	 * @param student
	 */
	public void create(Student student);
	
	/**
	 * 分页对象
	 * @param pager
	 * @param student
	 * @return
	 */
	public Pager<Student> search(Pager<Student> pager, Map<String,Object> student); 
}
