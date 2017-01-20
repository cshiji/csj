package com.cshiji.superviki.javaee.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 事件删除信息
 * @author zyy
 *
 */
public class AffairDelete implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3631826727552317795L;
	private String Id;//主键ID
	private String userId;//事件创建人
	private String comment;//事件 类型
	private String affairId;//事件ID
	private Date deleteTime;//删除时间
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAffairId() {
		return affairId;
	}
	public void setAffairId(String affairId) {
		this.affairId = affairId;
	}
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
}
