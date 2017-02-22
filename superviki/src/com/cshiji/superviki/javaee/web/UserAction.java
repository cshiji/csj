
package com.cshiji.superviki.javaee.web;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cshiji.superviki.base.util.BaseResult;
import com.cshiji.superviki.base.util.VerifyCaptcha;
import com.cshiji.superviki.javaee.bo.LoginUser;
import com.cshiji.superviki.javaee.service.UserService;
import com.cshiji.superviki.javaee.vo.User;


@Controller
@RequestMapping("/user")
public class UserAction {
	@Resource
	private UserService userService;
	
	@RequestMapping("/login")
	@ResponseBody
	public BaseResult<Object> login(HttpServletRequest request){
		BaseResult<Object> bResult=new BaseResult<Object>();
		
		//获取验证验证码需要的数据
		String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
		String validate = request.getParameter(GeetestLib.fn_geetest_validate);
		String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
		String userName=request.getParameter("username");
		String passWord=request.getParameter("password");
		LoginUser loginUser=new LoginUser();
		loginUser.setChallenge(challenge);
		loginUser.setPassWord(passWord);
		loginUser.setSeccode(seccode);
		loginUser.setUserName(userName);
		loginUser.setValidate(validate);
		BaseResult<Object> result=VerifyCaptcha.verifyCaptcha(request.getSession(), loginUser);
		//验证码验证失败
		if (result.isSuccess()==false) {
			return result;
		}
		
		User user=userService.findIsExist(loginUser);
		if (user==null) {
			bResult.setSuccess(false);
			bResult.setMsg("用户名或者密码错误");
		}else{
			request.getSession().setAttribute("loginUser", user);
			bResult.setSuccess(true);
			bResult.setMsg("登录成功");
		}
		return bResult;
	}
}

