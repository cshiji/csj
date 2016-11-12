package com.cshiji.superviki.base.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取applicationContext 中的bean <br/>
 * 需要在配置文件中加入如下配置：<br/>
 * 
 */
public class SpringContextHelper implements ApplicationContextAware{
	private static ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringContextHelper.context = context;
	}
	
	/**
	 * 获得 applicationContext
	 * @return
	 */
	public static ApplicationContext getApplicationContext(){
		return SpringContextHelper.context;
	}
	
	/**
	 * 根据beanName 获取bean
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
		return SpringContextHelper.context.getBean(beanName);
	}
	
	/**
	 * 获取类型为requiredType的对象
	 * @param beanName
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(String beanName,Class<T> requiredType){
		return SpringContextHelper.context.getBean(beanName, requiredType);
	}
	
	/**
	 * BeanFactory包含一个是否与所给名称匹配的bean定义
	 * @param beanName
	 * @return 包含返回true，否则返回false
	 */
	public static boolean containsBean(String beanName){
		return SpringContextHelper.context.containsBean(beanName);
	}
	
	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype
	 * @param beanName
	 * @return
	 */
	public static boolean isSingleton(String beanName){
		return SpringContextHelper.context.isSingleton(beanName);
	}
	
	/**
	 * 注册对象的类型
	 * @param beanName
	 * @return
	 */
	public static Class<?> getType(String beanName){
		return SpringContextHelper.context.getType(beanName);
	}
	
	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名 
	 * @param beanName
	 * @return
	 */
	public static String[] getAliases(String beanName){
		return SpringContextHelper.context.getAliases(beanName);
	}

}
