package com.springapp.common.application;

import com.springapp.common.spring.BusinessService;

/**
 * 这个接口记录用户操作日志。
 */
public abstract interface ILogService extends  BusinessService{

	public abstract void doBizLog(Short LogType, String message, String userId, String ipAddress);

	public abstract void doBizWarningLog(Short LogType, String message, String userId, String ipAddress);

	public abstract void doBizErrorLog(Short LogType, String message, String userId, String ipAddress);

	public abstract void doBizWarningLog(Short LogType, String message, String userId, Throwable exception, String ipAddress);

	public abstract void doBizErrorLog(Short LogType, String message, String userId, Throwable exception, String ipAddress);
}
