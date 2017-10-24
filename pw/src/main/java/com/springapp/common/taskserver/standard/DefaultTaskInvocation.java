package com.springapp.common.taskserver.standard;


import com.springapp.common.taskserver.TaskInvoker;
import com.springapp.common.taskserver.core.TaskInvocation;
import com.springapp.common.taskserver.core.TaskJobExecutor;
import com.springapp.common.taskserver.entity.TaskTrigger;
import com.springapp.common.taskserver.job.SingletonJobAction;

public class DefaultTaskInvocation implements TaskInvocation {
    private TaskTrigger taskTrigger;

    private TaskJobExecutor executor;

    private SingletonJobAction jobAction;

    private TaskInvoker invoker;

    public DefaultTaskInvocation(TaskJobExecutor executor, TaskInvoker invoker) {
        this.executor = executor;
        this.invoker = invoker;
    }


    public TaskTrigger getTaskTrigger() {
        return this.taskTrigger;
    }


    public TaskInvoker getInvoker() {
        return this.invoker;
    }


    public TaskJobExecutor getTaskExecutor() {
        return this.executor;
    }

    public SingletonJobAction getJobAction() {
        return this.jobAction;
    }


    public void setResult(Object result) {

    }

    /**
     * @param taskTrigger
     *            the taskTrigger to set
     */
    public void setTaskTrigger(TaskTrigger taskTrigger) {
        this.taskTrigger = taskTrigger;
    }

    /**
     * @param jobAction
     *            the jobAction to set
     */
    public void setJobAction(SingletonJobAction jobAction) {
        this.jobAction = jobAction;
    }

}
