
package com.cshiji.superviki.javaee.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cshiji.superviki.javaee.bo.LoginUser;
import com.cshiji.superviki.javaee.mapper.StudentMapper;
import com.cshiji.superviki.javaee.mapper.UserMapper;
import com.cshiji.superviki.javaee.service.UserService;
import com.cshiji.superviki.javaee.vo.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;
	
	@Override
	public User findIsExist(LoginUser user) {
		User result=userMapper.findIsExist(user);
		return result;
	}

}

