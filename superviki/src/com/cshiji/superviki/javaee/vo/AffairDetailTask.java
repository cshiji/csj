package com.cshiji.superviki.javaee.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务事件表
 * @author zyy
 *
 */
public class AffairDetailTask implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2135992103991191963L;
	/**
	 * 任务事件主键ID
	 */
	private String id;
	/**
	 * 任务主题类型
	 */
	private Integer themeType;
	/**
	 * 任务开始时间（前端提供）
	 */
	private Date startTime;
	/**
	 * 任务结束时间（前端提供）
	 */
	private Date endTime;
	/**
	 * 任务参与者（待定）
	 */
	private String taskParticipant;
	/**
	 * 任务地点
	 */
	private String taskWhere;
	/**
	 * 任务详细描述
	 */
	private String detailInfo;
	/**
	 * 提醒时间（如果需要提醒）
	 */
	private Date cautionTime;
	/**
	 * 提醒方式（如果需要提醒）
	 */
	private Integer cautionType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getThemeType() {
		return themeType;
	}
	public void setThemeType(Integer themeType) {
		this.themeType = themeType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getTaskParticipant() {
		return taskParticipant;
	}
	public void setTaskParticipant(String taskParticipant) {
		this.taskParticipant = taskParticipant;
	}
	public String getTaskWhere() {
		return taskWhere;
	}
	public void setTaskWhere(String taskWhere) {
		this.taskWhere = taskWhere;
	}
	public String getDetailInfo() {
		return detailInfo;
	}
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
	public Date getCautionTime() {
		return cautionTime;
	}
	public void setCautionTime(Date cautionTime) {
		this.cautionTime = cautionTime;
	}
	public Integer getCautionType() {
		return cautionType;
	}
	public void setCautionType(Integer cautionType) {
		this.cautionType = cautionType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
