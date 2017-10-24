package com.springapp.common.taskserver.job;

import com.springapp.common.scheduler.JobAction;
import com.springapp.common.scheduler.JobContext;
import com.springapp.common.scheduler.JobListener;
import com.springapp.common.taskserver.GlobalTaskConstant;
import com.springapp.common.taskserver.entity.TaskTrigger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;
import java.util.List;



public abstract class SingletonJobAction implements JobAction<Object> {

	protected final Log logger = LogFactory.getLog(SingletonJobAction.class);

	protected volatile boolean isRun = false;

	private Object syncronizedObject = new Object();

	private List<JobListener> listeners = new LinkedList<JobListener>();

	public SingletonJobAction() {
		super();
	}

	public SingletonJobAction(TaskTrigger trigger) {
		super();
		this.trigger = trigger;
	}

	private TaskTrigger trigger;

	public TaskTrigger getTrigger() {
		return trigger;
	}

	public void addJobActionListener(JobListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	/**
	 * 重新设置trigger,该操作会导致运行状态复位,如果该任务正在执行，会抛出异常。
	 * 
	 * @param trigger
	 *            新的trigger
	 */
	public void setTrigger(TaskTrigger trigger) {
		if (tryLock()) {
			// 运行状态复位
			isRun = false;
			this.trigger = trigger;
		} else {
			String msg = String.format("任务(%s)正在执行，不能设置新的任务。",
					trigger.getTriggerName());
			logger.info(msg);
			throw new IllegalStateException(msg);
		}
	}

	public Object doBiz(JobContext ctx) {

		if (trigger == null) {
			throw new IllegalStateException("任务没有设置或初始化");
		}
		String taskName = trigger.getTask().getTaskName();
		if (tryLock()) {
			isRun = true;
			boolean result = false;
			try {
				String startMsg = String.format("开始调度任务(%s)", taskName);
				logger.info(startMsg);
				result = doBizAction(trigger.getTriggerParam());
			} catch (Exception e) {
				String errorMsg = String.format("调度任务(%s)处理时失败。", taskName);
				logger.error(errorMsg);
			}

			isRun = false;
			String endMsg = String.format("调度任务(%s)完成，返回结果：%s", taskName,
					result);
			logger.info(endMsg);
			return result;
		} else {
			String errorMsg = String.format("任务(%s)正在执行，无需再次触发执行相关计划(%s)",
					taskName, trigger.getTriggerName());
			logger.info(errorMsg);
			return GlobalTaskConstant.TRIGGER_EXECUTING_DUPLICATED;
		}
	}

	/**
	 * @return
	 */
	public abstract boolean doBizAction(String triggerParam);

	private boolean tryLock() {
		synchronized (syncronizedObject) {
			if (isRun) {
				return false;
			} else {
				return true;
			}
		}

	}

	public void interrupt() {
		synchronized (syncronizedObject) {
			String msg = trigger.getTask() + "任务被中断";
			logger.info(msg);
			isRun = false;
		}
	}
}
