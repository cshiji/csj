package com.cshiji.superviki.base.util;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

public class ProperUtil {
	
	private static Properties dbProp;

	public static Properties getDbProp() {
		return dbProp;
	}

	public static void setDbProp(Properties dbProp) {
		ProperUtil.dbProp = dbProp;
	}

	public static Properties init(String sPropFile){
	    Properties props = new Properties();
	    ResourceBundle bundle = ResourceBundle.getBundle(sPropFile);
	    Enumeration<String> enums = bundle.getKeys();
	    Object key = null;
	    Object value = null;
	    while (enums.hasMoreElements()) {
	    	key = enums.nextElement();
	        value = bundle.getString(key.toString());
	        props.put(key, value);
	    }
	    bundle = null;
	    return props;
    }
	
	/**
	 * 获取app.propertes中的值
	 * @param key
	 * @return
	 */
	public static String getEnv(String key){
		return init("config.env.app").getProperty(key);
	}
	
}
