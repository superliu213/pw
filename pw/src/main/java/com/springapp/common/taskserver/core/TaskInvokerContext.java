package com.springapp.common.taskserver.core;


import com.springapp.common.taskserver.TaskConfiguration;

/**
 * 
 * @author Administrator
 *
 */
public interface TaskInvokerContext {

    public TaskJobProvider getTaskJobProvider();

    public TaskJobExecutor getTaskexExecutor();

    public TaskConfiguration getTaskConfiguration();

}
