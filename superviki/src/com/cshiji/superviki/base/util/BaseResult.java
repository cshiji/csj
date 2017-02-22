package com.cshiji.superviki.base.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseResult<T> {

	private boolean success=true;
	
	private String msg;
	
	private T obj;
	
	private String xml;
	
	private String uri;
	
	private List<T> list;
	
	//存放系统参数
	private Map<String, Object> sysMap;
	//存放多个对象值
	private Map<String, Object> map;
	
	
	public BaseResult() {
	}

	public BaseResult(boolean success, String msg, T obj, String xml,
			List<T> list) {
		super();
		this.success = success;
		this.msg = msg;
		this.obj = obj;
		this.xml = xml;
		this.list = list;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
 

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Map<String, Object> getSysMap() {
		return sysMap;
	}

	public void setSysMap(Map<String, Object> sysMap) {
		this.sysMap = sysMap;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	 
}
