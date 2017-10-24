package com.springapp.common.taskserver.service;

import com.springapp.common.application.Application;
import com.springapp.common.taskserver.entity.Task;
import com.springapp.exception.ApplicationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class TaskManageService{
    private ITaskScheduleService taskScheduleService;

	private Map<Integer, Task> cachedTaskIdMap = new HashMap<Integer, Task>();

    private static TaskManageService instance;

    private TaskManageService() {
    	try{
    		taskScheduleService = Application.getService(ITaskScheduleService.class);
    	} catch (ApplicationException e) {
            
        }
    }

    public static TaskManageService getInstance() {
        synchronized (TaskManageService.class) {
            instance = new TaskManageService();
        }
        return instance;
    }

    public Collection<Task> getAllTasksDefinition() {
        if (cachedTaskIdMap.isEmpty()) {
            initLoadAllTaskDefinition();
        }
        return cachedTaskIdMap.values();
    }

    private void initLoadAllTaskDefinition() {
        List<Task> taskList = taskScheduleService.getTasks();
        for (Task task : taskList) {
            cachedTaskIdMap.put(task.getTaskId(), task);
        }
    }

    public Task getTaskDefinition(Integer taskId) {
        if (cachedTaskIdMap.isEmpty()) {
            initLoadAllTaskDefinition();
        }
        return cachedTaskIdMap.get(taskId);
    }
}
