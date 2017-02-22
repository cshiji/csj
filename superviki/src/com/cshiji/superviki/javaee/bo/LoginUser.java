
package com.cshiji.superviki.javaee.bo;


public class LoginUser {
	private String userName;
	private String passWord;
	private String challenge;
	private String validate;
	private String seccode;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassWord() {
		return passWord;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public String getChallenge() {
		return challenge;
	}
	
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
	
	public String getValidate() {
		return validate;
	}
	
	public void setValidate(String validate) {
		this.validate = validate;
	}
	
	public String getSeccode() {
		return seccode;
	}
	
	public void setSeccode(String seccode) {
		this.seccode = seccode;
	}
}

