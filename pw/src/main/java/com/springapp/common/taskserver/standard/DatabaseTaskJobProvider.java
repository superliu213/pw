package com.springapp.common.taskserver.standard;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.springapp.common.op.OPException;
import com.springapp.common.taskserver.TaskConfiguration;
import com.springapp.common.taskserver.core.TaskJobProvider;
import com.springapp.common.taskserver.entity.Task;
import com.springapp.common.taskserver.entity.TaskHostPollCheckin;
import com.springapp.common.taskserver.entity.TaskTrigger;
import com.springapp.common.taskserver.service.ITaskScheduleService;
import com.springapp.common.taskserver.service.TaskManageService;
import com.springapp.common.util.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;



/**
 * 数据库提供 
 * 
 */
@Service("databaseTaskJobProvider")
public class DatabaseTaskJobProvider implements TaskJobProvider {
	@Autowired
    private ITaskScheduleService taskScheduleService;
	
	@Autowired
//	private ICommonDAOService commonDAOService;
	
    private TaskManageService taskDefinitionRepository;

    private List<TaskTrigger> currentJobs = new LinkedList<TaskTrigger>();

    private List<TaskTrigger> nextJobs = new LinkedList<TaskTrigger>();

    private boolean next;

    private Map<Integer, Task> cachedTaskIdMap = new HashMap<Integer, Task>();

    private TaskConfiguration config;

    public void init(TaskConfiguration config) {
        this.config = config;
        taskDefinitionRepository = TaskManageService.getInstance();
    }

    /**
     * @return the taskScheduleService
     */
    public ITaskScheduleService getTaskScheduleService() {
        return taskScheduleService;
    }
    
    /**
     * 获取当前的任务，直接从内存中取
     * 
     */
    public List<TaskTrigger> getCurrentTasks() {
        if (next) {
            currentJobs.clear();
            currentJobs.addAll(nextJobs);
            next = false;
        }
        return currentJobs;
    }

    /**
     * 获取下一次所有的需要执行的任务
     * 
     */
    public List<TaskTrigger> getNextTasks() {
        Assert.notNull(taskScheduleService);
        nextJobs.clear();
        List<TaskTrigger> taskTriggerList= taskScheduleService.getAvaliableTrigger(config.getTaskServerType());
        if (taskTriggerList != null) {
        	nextJobs.addAll(taskTriggerList);
        }
        next = true;
        return nextJobs;
    }

    /**
     * 获取下一次所有的需要执行的任务(集群模式)
     * 获取指定在本服务器上执行的任务，如果任务为空，获取随机执行的任务
     * @return
     */
    public List<TaskTrigger> getClusterNextTasks() {
        Assert.notNull(taskScheduleService);
        nextJobs.clear();
        
        List<TaskTrigger> taskTriggerList= taskScheduleService.updateAndGetNextAvaliableTrigger(config.getTaskServerType(), 
        		config.getEveryRandomTaskNumber(), config.getMaxTaskNumber());
        if (taskTriggerList != null) {
        	nextJobs.addAll(taskTriggerList);
        }
        next = true;
        return nextJobs;
    }
    
    public Collection<Task> getAllTasksDefinition() {
        if (cachedTaskIdMap.isEmpty()) {
            initLoadAllTaskDefinition();
        }
        return cachedTaskIdMap.values();
    }

    private void initLoadAllTaskDefinition() {
        Collection<Task> taskList = taskDefinitionRepository.getAllTasksDefinition();
        for (Task task : taskList) {
            if (task.getTaskType() == config.getTaskServerType()) {
                cachedTaskIdMap.put(task.getTaskId(), task);
            }
        }
    }


    public Task getTaskDefinition(Integer taskId) {
        if (cachedTaskIdMap.isEmpty()) {
            initLoadAllTaskDefinition();
        }
        return cachedTaskIdMap.get(taskId);
    }

    public void hostPollingCheckin() {
    	String runServer = NetUtil.getAllLocalIPAddress().get(0);
    	
    	TaskHostPollCheckin taskHostPollingCheckin = new TaskHostPollCheckin();
    	taskHostPollingCheckin.setRunServer(runServer);
    	taskHostPollingCheckin.setLastPollingTime(new Date());
    	
//        try {
//			commonDAOService.saveAndUpdateObj(taskHostPollingCheckin);
//		} catch (OPException e) {
//			e.printStackTrace();
//		}
    }
    
    /**
     * @param taskScheduleService
     *            the taskScheduleService to set
     */
    public void setTaskScheduleService(ITaskScheduleService taskScheduleService) {
        this.taskScheduleService = taskScheduleService;
    }

//	public ICommonDAOService getCommonDAOService() {
//		return commonDAOService;
//	}
//
//	public void setCommonDAOService(ICommonDAOService commonDAOService) {
//		this.commonDAOService = commonDAOService;
//	}
    
    

}
