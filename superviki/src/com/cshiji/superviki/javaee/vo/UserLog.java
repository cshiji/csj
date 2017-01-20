package com.cshiji.superviki.javaee.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户主要操作信息表，保存用户主要操作历史信息
 * @author zyy
 *
 */
public class UserLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4106803955187216202L;
	/**
	 * logID
	 */
	private String logId;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户类型
	 */
	private Integer userType;
	/**
	 * log类型（用户操作类型信息）
	 */
	private Integer logType;
	/**
	 * 用户操作详细信息
	 */
	private String logDetail;
	/**
	 * log拓展
	 */
	private Integer logExtend;
	/**
	 *log时间 
	 */
	private Date logTime;
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getLogType() {
		return logType;
	}
	public void setLogType(Integer logType) {
		this.logType = logType;
	}
	public String getLogDetail() {
		return logDetail;
	}
	public void setLogDetail(String logDetail) {
		this.logDetail = logDetail;
	}
	public Integer getLogExtend() {
		return logExtend;
	}
	public void setLogExtend(Integer logExtend) {
		this.logExtend = logExtend;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
