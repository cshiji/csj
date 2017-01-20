package com.cshiji.superviki.javaee.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户类型
 * @author zyy
 *
 */
public class UserClass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4912452671989709628L;
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户类型（1 = 系统用户， 2 = 普通用户）
	 */
	private String userType;
	/**
	 * 注册时间
	 */
	private Date createDate;
	/**
	 * 注册IP地址
	 */
	private String registerIp;
	/**
	 * 注册附加信息（JSON）
	 */
	private String registerInfo;
	/**
	 * 最近一次登录时间
	 */
	private Date lastLoginTime;
	/**
	 * 最近一次登录IP
	 */
	private String lastLoginIp;
	/**
	 * 最近一次登录附件信息（JSON）
	 */
	private String lastLoginInfo;
	/**
	 * 信息更新时间
	 */
	private Date updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	public String getRegisterInfo() {
		return registerInfo;
	}
	public void setRegisterInfo(String registerInfo) {
		this.registerInfo = registerInfo;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public String getLastLoginInfo() {
		return lastLoginInfo;
	}
	public void setLastLoginInfo(String lastLoginInfo) {
		this.lastLoginInfo = lastLoginInfo;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
