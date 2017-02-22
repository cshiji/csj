package com.cshiji.superviki.javaee.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * 使用Get的方式返回challenge和capthca_id,此方式以实现前后端完全分离的开发模式
 *
 */
@Controller
@RequestMapping("/captcha")
public class StartCaptcha{

	private static final long serialVersionUID = 1L;

	@RequestMapping("/startCaptcha")
	public void start(HttpSession session,HttpServletResponse response) throws IOException{
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key());

		String resStr = "{}";
		
		//自定义userid
		String userid = "test";

		//进行验证预处理
		int gtServerStatus = gtSdk.preProcess(userid);
		
		//将服务器状态设置到session中
		session.setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
		//将userid设置到session中
		session.setAttribute("userid", userid);
		
		resStr = gtSdk.getResponseStr();

		PrintWriter out = response.getWriter();
		out.println(resStr);
	}
}