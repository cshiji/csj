package com.cshiji.superviki.javaee.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cshiji.superviki.base.mybatis.Pager;
import com.cshiji.superviki.javaee.mapper.StudentMapper;
import com.cshiji.superviki.javaee.service.StudentService;
import com.cshiji.superviki.javaee.vo.Student;

@Service("studentService")
@SuppressWarnings({ "rawtypes"})
public class StudentServiceImpl implements StudentService{
	@Resource
	private StudentMapper studentMapper;
	@Transactional

	@Override
	public void create(Student student) {
		studentMapper.add(student);
	}
	
	/**
	 * 分页对象
	 */
	@Override
	public Pager<Student> search(Pager<Student> pager, Map<String, Object> param) {
		Map<String,Object> map = (param == null)?new HashMap<String,Object>():param;
		pager = (pager == null)?new Pager<Student>():pager;
		long count = studentMapper.getTotal(map);
		map.put("pager", pager);
		List<Student> students = studentMapper.page(map);
		pager.setTotal(count);
		pager.setRows(students);
		return pager;
	}

}
