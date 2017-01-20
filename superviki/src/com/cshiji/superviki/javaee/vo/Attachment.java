package com.cshiji.superviki.javaee.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 附件表（包含全部用户上传的除文字信息外的文件）
 * @author zyy
 *
 */
public class Attachment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8514694643225633904L;
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 附件所属类型（目前，用户相关或事件相关）
	 */
	private Integer type;
	/**
	 * 附件文件类型（文本，图片，音频等）
	 */
	private Integer fileType;
	/**
	 * 所属相应类型ID
	 */
	private String masterId;
	/**
	 * 文件存储url路径
	 */
	private String attachmentUrl;
	/**
	 * 创建时间
	 */
	private Date createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getFileType() {
		return fileType;
	}
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
