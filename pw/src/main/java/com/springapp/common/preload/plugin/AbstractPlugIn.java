package com.springapp.common.preload.plugin;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstractPlugIn implements IPlugIn{
	protected static Log logger = LogFactory.getLog(AbstractPlugIn.class);
	
	protected static IPlugIn _instance;
	protected static boolean _initialized = false;
	
	protected ServletContext context;
	
	public void initialize() {
		if (_initialized) {
			logger.info("已经完成初始化,不需要再初始化!");
			return;
		}
	}
	
	public void destroy() {
		if (_initialized == false) {
			// 处理停止
			_initialized = true;
			logger.info("=============## AbstractPlugIn 销毁##================#");
		}
	}
}
