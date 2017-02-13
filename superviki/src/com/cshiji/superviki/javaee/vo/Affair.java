package com.cshiji.superviki.javaee.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 事件信息
 * @author cj
 *
 */
public class Affair implements Serializable {
	private static final long serialVersionUID = 6509059804756937360L;
	/**
	 * 事件主键
	 */
	private String id; 
	/**
	 * 事件创建人
	 */
	private String userId;
	/**
	 * 事件标题
	 */
	private String title;
	/**
	 * 事件类型
	 */
	private int type;
	/**
	 * 事件文本内容
	 */
	private String detailId;
	/**
	 * 事件状态(优先级)
	 */
	private int status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 是否有附属文件（0=没有，1=有）
	 */
	private int hasAttachment;
	/**
	 * 是否有拓展信息（0=没有，1=有）
	 */
	private int affairExtend;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getHasAttachment() {
		return hasAttachment;
	}
	public void setHasAttachment(int hasAttachment) {
		this.hasAttachment = hasAttachment;
	}
	public int getAffairExtend() {
		return affairExtend;
	}
	public void setAffairExtend(int affairExtend) {
		this.affairExtend = affairExtend;
	}
	@Override
	public String toString() {
		return "Affair [id=" + id + ", userId=" + userId + ", title=" + title + ", type=" + type + ", detailId="
				+ detailId + ", status=" + status + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", hasAttachment=" + hasAttachment + ", affairExtend=" + affairExtend + "]";
	}
}
