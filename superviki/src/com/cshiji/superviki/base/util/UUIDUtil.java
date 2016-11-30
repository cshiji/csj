package com.cshiji.superviki.base.util;

import java.util.UUID;

/**
 * 
 * @author MrOrange
 * @time 2016-11-30
 */
public class UUIDUtil {
	/**
	 * 获取String类型UUID
	 * @return
	 */
	public static String getStrUUID(){
		return UUID.randomUUID().toString();
	}
	/**
	 * 获取UUID类型UUID
	 * @return
	 */
	public static UUID getUUID(){
		return UUID.randomUUID();
	}
	/**
	 * 获取去掉"-"的UUID
	 * @return
	 */
	public static String getParseUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
