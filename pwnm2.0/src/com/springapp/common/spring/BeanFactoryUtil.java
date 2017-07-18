package com.springapp.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring bean使用类，web app加载的时候把spring applicationContext set给该类
 *
 */
public class BeanFactoryUtil {

    private static ApplicationContext  applicationContext = null;
    private static String[] contextStrings = new String[] {"spring-config.xml"};
	
    /**
     * 得到bean对象
     * @param beanName bean名称
     * @return
     */
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		BeanFactoryUtil.applicationContext = applicationContext;
	}

	/**
	 * 初始bean
	 * 
	 * @param strs
	 *            configLocations eg：spring-config.xml(默认)
	 */
	public static void initBean(String... strs) {
		try {
			if (strs == null || strs.length == 0) {
				contextStrings = new String[] {"spring-config.xml"};
			} else {
				contextStrings = strs;
			}
			BeanFactoryUtil.applicationContext = new ClassPathXmlApplicationContext(
					contextStrings);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Exception building BeanFactory: " + ex.getMessage(), ex);
			//System.exit(-1);
		}
	}
}
