package com.cshiji.superviki.javaee.vo;

import java.io.Serializable;

/**
 * 一般事件详细信息表
 * @author zyy
 *
 */
public class AffairDetailNormal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6148821832017363574L;
	/**
	 * 事件详细信息主键ID
	 */
	private String id;
	/**
	 * 事件类型
	 */
	private String themeType;
	/**
	 * 事件详细类容
	 */
	private String detailInfo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getThemeType() {
		return themeType;
	}
	public void setThemeType(String themeType) {
		this.themeType = themeType;
	}
	public String getDetailInfo() {
		return detailInfo;
	}
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
