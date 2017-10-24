package com.springapp.common.taskserver.core;

/**
 * 任务执行拦截器
 * @author Administrator
 *
 */
public interface TaskJobInterceptor {
    public Object intercept(TaskInvocation invocation); 
}
