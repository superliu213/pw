package com.springapp.common.taskserver.service;

import com.springapp.common.spring.BusinessService;
import com.springapp.common.taskserver.entity.Task;
import com.springapp.common.taskserver.entity.TaskExecuteList;
import com.springapp.common.taskserver.entity.TaskHostPollCheckin;
import com.springapp.common.taskserver.entity.TaskTrigger;

import java.util.List;



public interface ITaskScheduleService extends BusinessService {
	/**
	 * @return
	 * @Description 获取正在运行的计划任务信息表
	 */
	List<TaskTrigger> getAvaliableTrigger();

	/**
	 * 获取给定任务类型的任务列表
	 * 
	 * @param taskType
	 *            任务类型， null表示所有
	 * @return
	 * @Description 获取正在运行的计划任务信息表
	 */
	List<TaskTrigger> getAvaliableTrigger(int taskType);
	
	/**
	 * 获取给定任务类型和指定运行服务器的任务列表
	 * 
	 * @param taskType 任务类型， null表示所有
	 * @param runServer 指定运行服务器
	 *            
	 * @return
	 * @Description 获取正在运行的计划任务信息表
	 */
	List<TaskTrigger> getAvaliableTrigger(int taskType, String runServer);
	
	/**
	 * 获取还未被分配的随机任务列表
	 * @param taskType
	 * @return
	 */
	List<TaskTrigger> getRandomAvaliableTrigger(int taskType);
	
	/**
	 * 获取服务器要执行的任务,并修改当前任务执行信息
	 * 获取指定在本服务器上执行的任务和随机执行的任务
	 * @param taskType
	 * @return
	 */
	List<TaskTrigger> updateAndGetNextAvaliableTrigger(int taskType, int everyRandomTaskNumber, int maxTaskNumber);
	
	/**
	 * 根据任务编号获取任务明细记录数
	 * 
	 * @param taskId
	 * @return
	 */
	long getTaskExecuteListCountByTaskId(Integer taskId);

	/**
	 * 根据任务编号获取任务明细记录
	 * 
	 * @param taskId
	 * @return
	 */
	List<TaskExecuteList> getTaskExecuteListByTaskId(Integer taskId, int page,
													 int pageSize);

	/**
	 * 获取任务最后一次执行记录
	 * @param trigger
	 * @return
	 */
	public TaskExecuteList getLastTaskExecuteList(TaskTrigger trigger);
	
	/**
	 * @param newStatus
	 * @param oldStatus
	 * @Description 设置计划任务执行历史表信息
	 */
	void resetTaksExecuteListResult(short newStatus, short oldStatus);


	/**
	 * @return 返回所有的任务定义
	 */
	List<Task> getTasks();

	/**
	 * @param taskType
	 *            null表示所有
	 * @return 返回所有的任务定义
	 */
	List<Task> getTasks(int taskType);
	
	List<TaskHostPollCheckin> getTaskHostPollingCheckinList();
	
	List<TaskHostPollCheckin> getTaskHostPollingCheckinList(String runServer);
	
	void cancelCurrentTask(String runServer);
}
