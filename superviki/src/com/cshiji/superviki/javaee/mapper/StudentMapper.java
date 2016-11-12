package com.cshiji.superviki.javaee.mapper;

import java.util.List;
import java.util.Map;

import com.cshiji.superviki.base.annotation.MybatisMapper;
import com.cshiji.superviki.javaee.vo.Student;

@MybatisMapper
public interface StudentMapper {
	/**
	 * 保存
	 */
	public void add(Student student);

	public long getTotal(Map<String, Object> map);

	public List<Student> page(Map<String, Object> map);
}
