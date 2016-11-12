package com.cshiji.superviki.base.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Map工具类
 *
 */
public class MapUtil {

	public static Map<String, Object> objectToMap(Object obj) {
		Map<String, Object> result = new HashMap<>();
		@SuppressWarnings("rawtypes")
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (int i=0; i<fields.length; i++) {
			Field f = fields[i];
			f.setAccessible(true);
			try {
				if (f.get(obj) == null) continue;
				result.put(f.getName(), f.get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
