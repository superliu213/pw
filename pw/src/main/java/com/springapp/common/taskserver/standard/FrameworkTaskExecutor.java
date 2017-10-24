package com.springapp.common.taskserver.standard;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.springapp.common.scheduler.*;
import com.springapp.common.taskserver.GlobalTaskConstant;
import com.springapp.common.taskserver.TaskInvoker;
import com.springapp.common.taskserver.core.TaskJobExecutor;
import com.springapp.common.taskserver.core.TaskJobInterceptor;
import com.springapp.common.taskserver.entity.Task;
import com.springapp.common.taskserver.entity.TaskTrigger;
import com.springapp.common.taskserver.exception.JobNotFoundException;
import com.springapp.common.taskserver.job.LogJobActionListener;
import com.springapp.common.taskserver.job.SingletonJobAction;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class FrameworkTaskExecutor implements TaskJobExecutor {
	private Log logger = LogFactory.getLog(FrameworkTaskExecutor.class);

	/**
	 * 保存已经实例化后但不再使用的JobAction对象。
	 */
	private final static Map<String, SingletonJobAction> jobActionCached = new HashMap<String, SingletonJobAction>();

	private final static Map<String, SingletonJobAction> jobActionCurrentCached = new HashMap<String, SingletonJobAction>();

	private JobScheduler jobScheduler;

	private DefaultTaskInvocation invocation;

	private List<TaskJobInterceptor> intercptors;

	public FrameworkTaskExecutor(TaskInvoker invoker) {
		jobScheduler = JobScheduler.newQuartzScheduler();
		intercptors = new LinkedList<TaskJobInterceptor>();
		invocation = new DefaultTaskInvocation(this, invoker);
	}

	public void addInterceptor(TaskJobInterceptor interceptor) {
		this.intercptors.add(interceptor);
	}

	/**
	 * 开始任务
	 * 
	 */
	public void start(final TaskTrigger task) {
		TimerJob<Object> job;
		try {
			synchronized (this) {
				job = JobActionBuilder.buildTimeJobByJobAction(task, invocation
						.getInvoker().getContext().getTaskConfiguration()
						.getDefaultTimeout());
			}
			if (job == null) {
				logger.debug(task + "无法转化成TimerJob，无法执行，放弃本次任务。");
				task.setStatus(GlobalTaskConstant.TriggerStatus.UNAVALIABLE.getStatus());
				return;
			}
			logger.debug("submit a new JobAction:" + job);
			jobScheduler.submit(job);
			invocation.setJobAction((SingletonJobAction) job.getAction());
			invocation.setTaskTrigger(task);
			for (TaskJobInterceptor interceptor : intercptors) {
				logger.debug("开始拦截器：" + interceptor);
				interceptor.intercept(invocation);
				logger.debug("成功执行拦截器：" + interceptor);
			}
			logger.debug(String.format("任务:%s开始，加入到当前任务列表中", task));
			jobActionCurrentCached.put(task.getRecordId().toString(),
					(SingletonJobAction) job.getAction());
		} catch (JobNotFoundException e) {
			logger.error("任务定义无法找到，无法执行，放弃本次任务。", e);
			task.setStatus(GlobalTaskConstant.TriggerStatus.UNAVALIABLE.getStatus());
		}
	}

	public void pause(TaskTrigger task) {

	}

	/**
	 * 未实现
	 * 
	 */
	public void resume(TaskTrigger job) {
	}

	public void stop(TaskTrigger task) {
		// 从jobActin缓存从找
		String key = task.getRecordId().toString();
		if (jobActionCurrentCached.containsKey(key)) {
			logger.debug("从当前运行的jobActin缓存从找到JobAction,key=" + key);
			jobScheduler.cancelAll(JobActionBuilder.getJobGroupName(task));
			logger.debug(String.format("任务:%s结束，释放到空闲任务列表中", task));
			jobActionCurrentCached.remove(key);
		} else {
			logger.error("从当前运行的jobActin缓存没有找到JobAction,key=" + key
					+ ",无法停止，请检查传入参数。");
		}
	}

	public static class JobActionBuilder {

		private final static Log logger = LogFactory
				.getLog(JobActionBuilder.class);

		private final static String _JOBGROUP_NAME = "GroupName_";

		private final static String _TASK_NAME = "TaskName_";

		private final static String _TRIGGER_NAME = "TriggerName_";

		private final static String _TRIGGER_ID = "TriggerID_";

		public static TimerJob<Object> buildTimeJobByJobAction(
				TaskTrigger trigger, Long defaultTimeout)
				throws JobNotFoundException {
			if (null == trigger) {
				logger.warn("trigger为空");
				return null;
			}
			final Task task = trigger.getTask();

			if (task == null) {
				logger.warn("trigger没有关联的任务定义");
				return null;
			}
			final String taskName = task.getTaskName();

			final SingletonJobAction jobAction = getJobAction(trigger);
			TaskJob actionJob = new TaskJob(taskName, getJobGroupName(trigger),
					jobAction);
			Long timeout = trigger.getTimeout();
			if (timeout == null) {
				timeout = defaultTimeout;
			}
			actionJob.setTimeout(timeout);
			short triggerType = trigger.getTriggerType().shortValue();
			StringBuffer buffer = new StringBuffer();
			if (triggerType == GlobalTaskConstant.TriggerType.ONCE.getType()) {
				actionJob.at(trigger.getFirstExecuteTime());
				buffer.append("任务类型：只执行一次");
				buffer.append("，执行时间：" + trigger.getFirstExecuteTime());
			} else if (triggerType == GlobalTaskConstant.TriggerType.CYCLE.getType()) {
				actionJob.at(trigger.getFirstExecuteTime()).every(
						trigger.getIntervalNum().intValue(),
						getIntervalUnitValue(trigger.getIntervalUnit()));
				buffer.append("任务类型：循环执行");
				buffer.append("，执行时间：" + trigger.getFirstExecuteTime());
				buffer.append("，执行频率：" + trigger.getIntervalNum().intValue()
						+ "/" + trigger.getIntervalUnit());
			} else if (triggerType == GlobalTaskConstant.TriggerType.IMMEDIATELY.getType()) {
				Date today = new Date();
				actionJob.at(today);
				buffer.append("任务类型：立即执行");
				buffer.append("，执行时间：" + today);
				if (null != trigger.getIntervalNum()
						&& null != trigger.getIntervalUnit()) {
					buffer.append("，执行时间：" + trigger.getFirstExecuteTime());
				}
			} else {
				logger.debug("创建计划任务" + taskName + "触发器"
						+ trigger.getTriggerName() + "异常。");
				return null;
			}
			logger.debug("创建计划任务" + taskName + "触发器" + trigger.getTriggerName()
					+ "成功，" + buffer.toString());

			actionJob.addListener(new JobListenerAdapter() {

				@Override
				public void interrupted(Job<Object> job) {
					jobAction.interrupt();
				}

			});
			actionJob.addListener(new LogJobActionListener(trigger));
			return actionJob;
		}

		/**
		 * @param trigger
		 * @return
		 * @throws JobNotFoundException
		 */
		@SuppressWarnings("unchecked")
		private static SingletonJobAction getJobAction(TaskTrigger trigger)
				throws JobNotFoundException {
			Task task = trigger.getTask();
			String clsName = task.getTaskClass();

			if (jobActionCached.containsKey(clsName)) {
				logger.debug("从jobActin缓存从找到空闲的JobAction,key=" + clsName);
				SingletonJobAction jobAction = jobActionCached.get(clsName);
				jobAction.setTrigger(trigger);
				logger.debug("return cached:" + jobAction);
				return jobAction;
			}

			Class<SingletonJobAction> clazz;
			try {
				clazz = (Class<SingletonJobAction>) Class.forName(clsName);
				return newJobActionInstance(clazz, trigger);
			} catch (ClassNotFoundException e) {
				String msg = String.format("任务类:%s不存在，无法实例化", clsName);
				handlerNewInstanceException(msg, e);
				throw new JobNotFoundException(msg, e);
			}
		}

		private static SingletonJobAction newJobActionInstance(
				Class<SingletonJobAction> clazz, TaskTrigger trigger)
				throws JobNotFoundException {
			SingletonJobAction instance = null;
			try {
				instance = clazz.newInstance();
				instance.setTrigger(trigger);
			} catch (Exception e) {
				e.printStackTrace();
				handlerNewInstanceException(e.getMessage(), e);
				throw new JobNotFoundException(e.getMessage(), e);
			}
			String className = clazz.getName();
			logger.debug(String.format("任务：%s 的单实例创建完成", instance));
			jobActionCached.put(className, instance);
			return instance;
		}

		private static void handlerNewInstanceException(String msg, Exception e) {
			logger.error("实例化JobAction异常：" + msg, e);
		}

		private static String getJobGroupName(TaskTrigger trigger) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(_JOBGROUP_NAME);
			buffer.append(_TASK_NAME);
			String taskName = trigger.getTask().getTaskName();
			buffer.append(taskName);
			buffer.append(_TRIGGER_NAME);
			buffer.append(trigger.getTriggerName());
			buffer.append(_TRIGGER_ID);
			buffer.append(trigger.getRecordId());

			return buffer.toString();
		}

		private static IntervalUnit getIntervalUnitValue(String interval) {
			return interval == null ? IntervalUnit.DAY : IntervalUnit
					.valueOf(interval.toUpperCase());

		}

	}

	/**
	 * 单例阻塞job Ticket:
	 * 
	 */
	static class TaskJob extends TimerJob<Object> implements Statefulable {
		public TaskJob(String name, String groupName, JobAction<Object> action) {
			super(name, groupName, action);
		}

	}
}
