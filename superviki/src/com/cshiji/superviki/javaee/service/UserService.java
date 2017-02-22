
package com.cshiji.superviki.javaee.service;

import com.cshiji.superviki.javaee.bo.LoginUser;
import com.cshiji.superviki.javaee.vo.User;

public interface UserService {
	/**
	 * 用户登录验证
	 * @description: TODO
	 * @creator: cj
	 * @createDate: 2017年2月22日 
	 * @modifier:
	 * @modifiedDate:
	 * @param user
	 * @return
	 */
	public User findIsExist(LoginUser user);
}

