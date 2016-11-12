
package com.cshiji.superviki.base.util;

public class ObjectUtils {

	/**
	 * @description: 判断传入的对象是否全是非空
	 * @creator: Administrator
	 * @modifier:
	 * @modifiedDate:
	 * @param objs
	 * @return
	 */
	public static boolean allNotNull(Object... objs) {
		if (objs != null && objs.length > 0) {//已传入对象
			for (Object obj : objs) {
				if(obj == null){//如果有一个对象为空，则返回false
					return false;
				}
			}
			//如果没有对象为空，则返回真
			return true;
		}
		return false;
	}
}
