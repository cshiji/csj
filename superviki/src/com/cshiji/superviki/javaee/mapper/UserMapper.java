
package com.cshiji.superviki.javaee.mapper;

import com.cshiji.superviki.base.annotation.MybatisMapper;
import com.cshiji.superviki.javaee.bo.LoginUser;
import com.cshiji.superviki.javaee.vo.User;

@MybatisMapper
public interface UserMapper {
	User findIsExist(LoginUser user);
}

