package com.springapp.common.taskserver;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.springapp.common.application.Application;
import com.springapp.common.preload.plugin.AbstractPlugIn;
import com.springapp.common.preload.plugin.IPlugIn;
import com.springapp.common.spring.BeanFactoryUtil;
import com.springapp.common.taskserver.core.TaskInvokerContext;
import com.springapp.common.taskserver.core.TaskJobExecutor;
import com.springapp.common.taskserver.core.TaskJobProvider;
import com.springapp.common.taskserver.entity.TaskTrigger;
import com.springapp.common.taskserver.standard.FrameworkTaskExecutor;
import com.springapp.common.thread.EnhancedThread;
import com.springapp.common.util.ClassUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;



/**
 * 任务执行机
 * 
 */
public class TaskInvoker extends AbstractPlugIn implements IPlugIn {
	private static final Log logger = LogFactory.getLog(TaskInvoker.class);

	private static final String TASK_INVOKER_NAME = "com.jfly.commons.taskserver.taskInvoker.loopThread";

	private TaskInvokerContext context;

	private TaskConfiguration config;

	public static boolean isModify = false;

	/**
	 * 任务类型
	 */
	private int taskServerType;
	
	public TaskInvoker() {
	}
	
	public TaskInvoker(TaskConfiguration config) {
		this.config = config;
	}

	public int getTaskServerType() {
		return taskServerType;
	}


	private EnhancedThread loopThread;

	/**
	 * 启动，开启loop线程从任务提供者中读取任务列表并执行任务
	 */
	@Override
	public void initialize() {
		logger.debug("开始启动任务执行框架,初始化任务框架上下文...");
		isModify = true;
		context = ContextFactory.buildContext(config, this);
		taskServerType = context.getTaskConfiguration().getTaskServerType();
		logger.debug("初始化任务框架上下文完成，任务框架类型：" + taskServerType);
		long s = System.currentTimeMillis();
		final List<TaskTrigger> add = new LinkedList<TaskTrigger>();
		final List<TaskTrigger> del = new LinkedList<TaskTrigger>();
		loopThread = new EnhancedThread(TASK_INVOKER_NAME, new Runnable() {

			public void run() {
				if (isModify){
					TaskJobProvider taskJobProvider = context.getTaskJobProvider();
					TaskJobExecutor taskexExecutor = context.getTaskexExecutor();

					List<TaskTrigger> currentJobs = taskJobProvider.getCurrentTasks();
					List<TaskTrigger> nextJobs = taskJobProvider.getClusterNextTasks();

					//登记到任务轮询表
					taskJobProvider.hostPollingCheckin();

					add.clear();
					del.clear();

					logger.info(String
							.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS:%1$tL,获取到下一次需执行的任务为：%2$s",
									new Date(), nextJobs));

					for (TaskTrigger job : currentJobs) {
						if (!nextJobs.contains(job)) {
							del.add(job);
							logger.debug(String.format("合并任务，取消已停止任务：%s", job));
						}
					}

					for (TaskTrigger job : nextJobs) {
						if (!currentJobs.contains(job)) {
							add.add(job);
							logger.info(String.format("合并任务，开始新任务：%s", job));
						} else if (currentJobs.get(currentJobs.indexOf(job))
								.getStatus() == GlobalTaskConstant.TriggerStatus.UNAVALIABLE
								.getStatus()) {
							add.add(job);
						}
					}

					for (TaskTrigger job : del) {
						taskexExecutor.stop(job);
					}

					for (TaskTrigger job : add) {
						taskexExecutor.start(job);
					}

					logger.info(String.format(
							"合并任务完成，当前正在运行%d个任务，\n\t本次新增：%d个,%s;\n\t本次取消：%d个,%s。",
							nextJobs.size(), add.size(), add, del.size(), del));
					isModify = false;
				}
			}
		});
		int reloadConfigInterval = context.getTaskConfiguration()
				.getReloadConfigInterval();
		logger.debug("获取循环读取任务时间间隔：" + reloadConfigInterval / 1000 + "秒");
		loopThread.setInterval(reloadConfigInterval);
		logger.debug("开始启动循环读取执行任务线程...");
		loopThread.start();
		logger.debug("成功启动循环读取执行任务线程...");
		logger.debug("成功启动任务执行框架,耗时：" + (System.currentTimeMillis() - s) + "毫秒");
		
		logger.info("=============## TaskInvoker 加载成功##================");
	}

	@Override
	public void destroy() {
		logger.debug("开始关闭任务执行框架，循环读取执行任务线程关闭中...");
		loopThread.setCanStop(true);
		loopThread.stopThread();
		logger.debug("成功关闭任务执行框架。");
		
		logger.info("=============## TaskInvoker 销毁##================");
	}

	public TaskInvokerContext getContext() {
		return this.context;
	}

	private final static class ContextFactory {
		@SuppressWarnings("unchecked")
		public static TaskInvokerContext buildContext(TaskConfiguration config,
				TaskInvoker invoker) {
			if (config == null) {
				// 加载默认配置
//				Application.setConfigProvider(new XMLConfigurationProvider());
//				Application.loadConfiguration("task-config.xml",
//						TaskConfiguration.class);
//				config = (TaskConfiguration) Application
//						.getConfig(TaskConfiguration.class);
			}
			if (config == null) {
				return null;
			}
			final TaskConfiguration _config = config;
			final FrameworkTaskExecutor executor = new FrameworkTaskExecutor(
					invoker);

			TaskJobProvider provider = null;
			if (config.getTaskJobProviderClass() == null) {
				TaskJobProvider taskJobProvider = (TaskJobProvider) BeanFactoryUtil
						.getBean("databaseTaskJobProvider");
				provider = taskJobProvider;
			} else {
				provider = (TaskJobProvider) ClassUtil.getClassInstance(config
						.getTaskJobProviderClass());
			}
			Assert.notNull(provider, "任务供应者无法初始化！");
			provider.init(_config);
			final TaskJobProvider _provider = provider;
			return new TaskInvokerContext() {

				public TaskJobExecutor getTaskexExecutor() {
					return executor;
				}

				public TaskJobProvider getTaskJobProvider() {
					return _provider;
				}

				public TaskConfiguration getTaskConfiguration() {
					return _config;
				}
			};
		}
	}

}
