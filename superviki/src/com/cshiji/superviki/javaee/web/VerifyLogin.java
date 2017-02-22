package com.cshiji.superviki.javaee.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 使用post方式，返回验证结果, request表单中必须包含challenge, validate, seccode
 */
@Controller
@RequestMapping("/captcha")
public class VerifyLogin{

	private static final long serialVersionUID = 244554953219893949L;

	@RequestMapping("/captchaValidate")
	public void captchaValidate(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		
	}
}
