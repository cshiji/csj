
package com.cshiji.superviki.base.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.cshiji.superviki.javaee.bo.LoginUser;
import com.cshiji.superviki.javaee.web.GeetestConfig;
import com.cshiji.superviki.javaee.web.GeetestLib;

public class VerifyCaptcha {
	public static BaseResult<Object> verifyCaptcha(HttpSession session,LoginUser loginUser){
		BaseResult<Object> bResult=new BaseResult<Object>();
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key());		
		//从session中获取gt-server状态
		int gt_server_status_code = (Integer) session.getAttribute(gtSdk.gtServerStatusSessionKey);
		
		//从session中获取userid
		String userid = (String)session.getAttribute("userid");
		
		int gtResult = 0;

		if (gt_server_status_code == 1) {
			//gt-server正常，向gt-server进行二次验证
				
			gtResult = gtSdk.enhencedValidateRequest(loginUser.getChallenge(), loginUser.getValidate(), loginUser.getSeccode(), userid);
			System.out.println(gtResult);
		} else {
			// gt-server非正常情况下，进行failback模式验证
				
			System.out.println("failback:use your own server captcha validate");
			gtResult = gtSdk.failbackValidateRequest(loginUser.getChallenge(), loginUser.getValidate(), loginUser.getSeccode());
			System.out.println(gtResult);
		}
		if (gtResult == 1) {
			// 验证成功
			bResult.setSuccess(true);
		}
		else {
			// 验证失败
			bResult.setSuccess(false);
			bResult.setMsg("验证码错误");
		}
		return bResult;
	}
}

