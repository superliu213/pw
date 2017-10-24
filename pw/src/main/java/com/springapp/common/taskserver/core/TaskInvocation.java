package com.springapp.common.taskserver.core;


import com.springapp.common.taskserver.TaskInvoker;
import com.springapp.common.taskserver.entity.TaskTrigger;
import com.springapp.common.taskserver.job.SingletonJobAction;

/**
 * 
 * @author Administrator
 *
 */
public interface TaskInvocation {
    public TaskTrigger getTaskTrigger();

    public TaskInvoker getInvoker();

    public TaskJobExecutor getTaskExecutor();

    public SingletonJobAction getJobAction();

    public void setResult(Object result);
}
