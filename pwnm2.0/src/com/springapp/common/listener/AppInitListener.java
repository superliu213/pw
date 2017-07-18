package com.springapp.common.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.springapp.common.spring.BeanFactoryUtil;

public class AppInitListener implements javax.servlet.ServletContextListener {
	private static Log logger = LogFactory.getLog(AppInitListener.class);
		
	public void contextInitialized(ServletContextEvent servletConfig) {
		ServletContext context = servletConfig.getServletContext();
		try {					
			//初始化 Hibernate-Context.xml 加载到BeanFactoryUtil
			initSpringConfig(context);
		} catch (Exception e) {
			logger.info("appInit 失败");
		}
	}

	/**
	 * 初始化spring配置文件
	 * @param context
	 */
	private void initSpringConfig(ServletContext context) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		BeanFactoryUtil.setApplicationContext(webApplicationContext);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {		
	}
}
