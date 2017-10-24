package com.springapp.common.taskserver.core;


import com.springapp.common.taskserver.entity.TaskTrigger;

/**
 * 单个任务的执行器
 * @author Administrator
 *
 */
public interface TaskJobExecutor {
    /**
     * 启动任务
     * 
     * @param job
     */
    public void start(TaskTrigger job);

    /**
     * 暂停任务
     * 
     * @param job
     */
    public void pause(TaskTrigger job);

    /**
     * 恢复任务
     * 
     * @param job
     */
    public void resume(TaskTrigger job);

    /**
     * 关闭任务
     * 
     * @param job
     */
    public void stop(TaskTrigger job);

    /**
     * 
     * @param interceptor
     */
    public void addInterceptor(TaskJobInterceptor interceptor);
}
