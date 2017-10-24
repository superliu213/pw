package com.springapp.common.taskserver.core;

import com.springapp.common.taskserver.TaskConfiguration;
import com.springapp.common.taskserver.entity.Task;
import com.springapp.common.taskserver.entity.TaskTrigger;

import java.util.Collection;
import java.util.List;


/**
 * 任务的提供者
 * @author Administrator
 *
 */
public interface TaskJobProvider {
    public void init(TaskConfiguration config);

    /**
     * 获取当前正在执行的job
     * 
     * @return
     */
    public List<TaskTrigger> getCurrentTasks();

    /**
     * 获取下一次要执行的job
     * 
     * @return
     */
    public List<TaskTrigger> getNextTasks();

    /**
     * 获取下一次所有的需要执行的任务(集群模式)
     * 获取指定在本服务器上执行的任务，如果任务为空，获取随机执行的任务
     * @return
     */
    public List<TaskTrigger> getClusterNextTasks();
    
    /**
     * 获取所有的任务定义列表
     * 
     * @return
     */
    public Collection<Task> getAllTasksDefinition();

    /**
     * 根据taskId获取任务定义
     * 
     * @param taskId
     * @return
     */
    public Task getTaskDefinition(Integer taskId);
    
    /**
     * 登记到任务轮询表
     */
    public void hostPollingCheckin();

}
