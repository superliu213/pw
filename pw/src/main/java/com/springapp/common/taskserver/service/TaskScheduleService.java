package com.springapp.common.taskserver.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.springapp.common.op.BaseHibernateDao;
import com.springapp.common.op.OPException;
import com.springapp.common.taskserver.GlobalTaskConstant;
import com.springapp.common.taskserver.entity.Task;
import com.springapp.common.taskserver.entity.TaskExecuteList;
import com.springapp.common.taskserver.entity.TaskHostPollCheckin;
import com.springapp.common.taskserver.entity.TaskTrigger;
import com.springapp.common.util.NetUtil;
import com.springapp.exception.ApplicationException;
import org.springframework.stereotype.Service;



@Service("taskScheduleService")
public class TaskScheduleService extends BaseHibernateDao implements
		ITaskScheduleService {

	/**
	 * 获取正在运行计划任务信息表
	 * 
	 * @return 正在运行计划任务信息表
	 */
	public List<TaskTrigger> getAvaliableTrigger() {
		try {
			String hql = "from TaskTrigger t where t.status=?";
			
			@SuppressWarnings("unchecked")
			List<TaskTrigger> list = (List<TaskTrigger>)retrieveObjsLP(hql,
					GlobalTaskConstant.TriggerStatus.AVALIABLE.getStatus());
			return list;
		} catch (OPException e) {
			throw new ApplicationException("获取正在运行计划任务信息表失败。", e);
		}
	}

	/**
	 */
	public List<TaskTrigger> getAvaliableTrigger(int taskType) {
		try {
			String hql = "select t from TaskTrigger t,Task task where  t.taskId=task.taskId and t.status=? and task.taskType=?";
			
			List<TaskTrigger> list = (List<TaskTrigger>)retrieveObjsLP(hql,
					GlobalTaskConstant.TriggerStatus.AVALIABLE.getStatus(), taskType);
			return list;
		} catch (OPException e) {
			e.printStackTrace();
			throw new ApplicationException("获取正在运行计划任务信息表失败。", e);
		}
	}

	@Override
	public List<TaskTrigger> getAvaliableTrigger(int taskType, String runServer) {
		try {
			String hql = "select t from TaskTrigger t,Task task where  t.taskId=task.taskId and t.status=? and task.taskType=? and task.runServer = ?";
			
			List<TaskTrigger> list = (List<TaskTrigger>)retrieveObjsLP(hql,
					GlobalTaskConstant.TriggerStatus.AVALIABLE.getStatus(), taskType, runServer);
			return list;
		} catch (OPException e) {
			e.printStackTrace();
			throw new ApplicationException("获取正在运行计划任务信息表失败。", e);
		}
	}
	
	@Override
	public List<TaskTrigger> getRandomAvaliableTrigger(int taskType) {
		try {
			String hql = "select t from TaskTrigger t,Task task where  t.taskId=task.taskId and t.status=? and task.taskType=? and task.runServer is null";
			
			List<TaskTrigger> list = (List<TaskTrigger>)retrieveObjsLP(hql,
					GlobalTaskConstant.TriggerStatus.AVALIABLE.getStatus(), taskType);
			return list;
		} catch (OPException e) {
			e.printStackTrace();
			throw new ApplicationException("获取正在运行计划任务信息表失败。", e);
		}
	}
	
	@Override
	public List<TaskTrigger> updateAndGetNextAvaliableTrigger(int taskType, int everyRandomTaskNumber, int maxTaskNumber) {
		try {
			String hql = "select t from TaskTrigger t,Task task where  t.taskId=task.taskId and t.status=? and task.taskType=?";
			
			List<TaskTrigger> list = (List<TaskTrigger>)retrieveObjsLP(hql,
					GlobalTaskConstant.TriggerStatus.AVALIABLE.getStatus(), taskType);
			List<TaskTrigger> nextTaskList = new ArrayList<TaskTrigger>();
			
			int randomTaskNumber = 0;
			int totalTaskNumber = 0;
			if (everyRandomTaskNumber == -1) {
				everyRandomTaskNumber = Integer.MAX_VALUE;
			}
			if (maxTaskNumber == -1) {
				maxTaskNumber = Integer.MAX_VALUE;
			}
			
			List<String> allLocalIpAddressList = NetUtil.getAllLocalIPAddress();
			String localIPAddress = allLocalIpAddressList.get(0);
			if (list != null) {
				for (Iterator<TaskTrigger> iterator = list.iterator(); iterator.hasNext();) {
					TaskTrigger taskTrigger = iterator.next();
					if (allLocalIpAddressList.contains(taskTrigger.getCurrentRunServer())) {
						if (totalTaskNumber <maxTaskNumber) {
							taskTrigger.setCurrentRunServer(localIPAddress);
							nextTaskList.add(taskTrigger);
							
							totalTaskNumber++;
						}
					} else {
						if (taskTrigger.getAppoindRunServer() == null || taskTrigger.getAppoindRunServer().equals("")) {
							if (randomTaskNumber <everyRandomTaskNumber && totalTaskNumber <maxTaskNumber) {
								taskTrigger.setCurrentRunServer(localIPAddress);
								nextTaskList.add(taskTrigger);
								
								randomTaskNumber++;
								totalTaskNumber++;
							}
						} else {
							if (allLocalIpAddressList.contains(taskTrigger.getAppoindRunServer()) && totalTaskNumber <maxTaskNumber) {
								taskTrigger.setCurrentRunServer(localIPAddress);
								nextTaskList.add(taskTrigger);
								
								totalTaskNumber++;
							}
						}
					}
				}
				updateObjs(nextTaskList.toArray());
			}
			
			return nextTaskList;
		} catch (OPException e) {
			e.printStackTrace();
			throw new ApplicationException("获取下一批计划任务信息表失败。", e);
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<Task> getTasks(int taskType) {
		try {
			return (List<Task>)retrieveObjsLP("from Task where taskType=?",
					taskType);
		} catch (OPException e) {
			throw new ApplicationException("获取任务表信息失败。", e);
		}
	}

	/**
	 * 根据任务编号获取任务明细记录数
	 * 
	 * @param taskId
	 *            任务id
	 * @return
	 */
	public long getTaskExecuteListCountByTaskId(Integer taskId) {
		try {
			String hql = "select count(*) from TaskExecuteList t where t.taskId=?";
			return getCountLP(hql, taskId);
		} catch (Exception e) {
			throw new ApplicationException("获取计划任务任务明细记录数失败。", e);
		}
	}

	/**
	 * 根据任务编号获取任务明细记录
	 * 
	 * @param taskId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskExecuteList> getTaskExecuteListByTaskId(Integer taskId,
			int pageIndex, int pageSize) {
		try {
			String hql = "select t from TaskExecuteList t where t.taskId=? order by t.executeTime desc";
			return (List<TaskExecuteList>)queryLP(hql, pageIndex,
					pageSize, new Object[]{taskId});
		} catch (Exception e) {
			throw new ApplicationException("获取计划任务任务明细记录表失败。", e);
		}
	}

	/**
	 * @param newStatus
	 * @param oldStatus
	 * @Description 设置计划任务执行历史表信息
	 */
	public void resetTaksExecuteListResult(short newStatus, short oldStatus) {
		String hql = "update TaskExecuteList set executeResult=? where executeResult=?";
		try {
			execHqlUpdateLP(hql, newStatus, oldStatus);
		} catch (Exception e) {
			throw new ApplicationException("设置触发器执行历史表信息失败。", e);
		}
	}


	@SuppressWarnings("unchecked")
	public List<Task> getTasks() {
		try {
			return (List<Task>)retrieveObjsLP("from Task");
		} catch (OPException e) {
			throw new ApplicationException("获取所有的任务表信息失败。", e);
		}
	}

	public TaskExecuteList getLastTaskExecuteList(TaskTrigger trigger) {
		try {
			String hqlg = "select t from TaskExecuteList t where t.taskId=? and t.executeTime=(select max(p.executeTime) from TaskExecuteList p where p.taskId=?) ";
			@SuppressWarnings("unchecked")
			List<TaskExecuteList> exeList = (List<TaskExecuteList>)retrieveObjsLP(
					hqlg, trigger.getTaskId(), trigger.getTaskId());
			if (exeList.size() == 0) {
				return null;
			} else {
				return exeList.get(0);
			}
		} catch (Exception e) {
			throw new ApplicationException("获取任务最后一次执行记录", e);
		}
	}

	public List<TaskHostPollCheckin> getTaskHostPollingCheckinList() {
		try {
			String hql = "select t from TaskHostPollingCheckin t";
			@SuppressWarnings("unchecked")
			List<TaskHostPollCheckin> list = (List<TaskHostPollCheckin>)retrieveObjs(
					hql);
			
			return list;
		} catch (Exception e) {
			throw new ApplicationException("获取主机轮询登记表出错", e);
		}
	}
	
	public List<TaskHostPollCheckin> getTaskHostPollingCheckinList(String runServer) {
		try {
			String hql = "select t from TaskHostPollingCheckin t where t.runServer=?";
			@SuppressWarnings("unchecked")
			List<TaskHostPollCheckin> list = (List<TaskHostPollCheckin>)retrieveObjsLP(
					hql, runServer);
			
			return list;
		} catch (Exception e) {
			throw new ApplicationException("获取主机轮询登记表出错", e);
		}
	}
	
	public void cancelCurrentTask(String runServer) {
		try {
			String hql = "update TaskTrigger set  currentRunServer=null where runServer=?";
			execHqlUpdateLP(hql, runServer);
		} catch (Exception e) {
			throw new ApplicationException("获取主机轮询登记表出错", e);
		}
	}

}
