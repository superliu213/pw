package com.springapp.common.taskserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.springapp.common.application.Application;
import com.springapp.common.preload.plugin.AbstractPlugIn;
import com.springapp.common.preload.plugin.IPlugIn;
import com.springapp.common.taskserver.entity.TaskHostPollCheckin;
import com.springapp.common.taskserver.service.ITaskScheduleService;
import com.springapp.common.thread.EnhancedThread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * 任务执行服务器在线检查插件,如果在3次轮询期内,任务执行服务器没有进行注册登记,取消任务执行服务器分配的当前任务,但对指定的任务不做更改
 * 
 */
public class TaskInvokeOnlineCheckPlugin extends AbstractPlugIn implements
		IPlugIn {
	private static final Log logger = LogFactory
			.getLog(TaskInvokeOnlineCheckPlugin.class);

	private static final String TASK_INVOKER_ONLINE_CHECK_NAME = "com.jfly.commons.taskserver.taskInvokeOnlineCheckPlugin.loopThread";

	private TaskConfiguration config;

	private ITaskScheduleService taskScheduleService;

	private EnhancedThread loopThread;

	private List<TaskHostPollCheckin> checkinList;

	public TaskInvokeOnlineCheckPlugin() {
	}

	private void loadConfig() {
//		config = (TaskConfiguration) Application
//				.getConfig(TaskConfiguration.class);
//
//		if (config == null) {
//			// 加载默认配置
//			Application.setConfigProvider(new XMLConfigurationProvider());
//			Application.loadConfiguration("task-config.xml",
//					TaskConfiguration.class);
//			config = (TaskConfiguration) Application
//					.getConfig(TaskConfiguration.class);
//		}
	}
	
	private TaskHostPollCheckin getTaskHostPollingCheckinOfCheckList(TaskHostPollCheckin taskHostPollingCheckin) {
		for (Iterator<TaskHostPollCheckin> iterator = checkinList.iterator(); iterator.hasNext();) {
			TaskHostPollCheckin tpc = iterator.next();
			if (tpc.getRunServer().equals(taskHostPollingCheckin.getRunServer())) {
				return tpc;
			}
		}
		
		return null;
	}
	/**
	 * 启动，开启loop线程检测任务主机是否在线
	 */
	@Override
	public void initialize() {
		logger.info("开始启动任务主机在线检测插件...");
		logger.debug("开始加载上下文配置信息");

		loadConfig();
		if (config == null) {
			logger.error("加载配置文件失败,在线检测插件启动失败");
			return;
		}

		long s = System.currentTimeMillis();

		taskScheduleService = Application.getClassByName("taskScheduleService");
		checkinList = taskScheduleService.getTaskHostPollingCheckinList();
		if (checkinList == null) {
			checkinList = new ArrayList<TaskHostPollCheckin>();
		}

		loopThread = new EnhancedThread(TASK_INVOKER_ONLINE_CHECK_NAME,
				new Runnable() {
					public void run() {
						List<TaskHostPollCheckin> tmpCheckinList = taskScheduleService
								.getTaskHostPollingCheckinList();
						for (Iterator<TaskHostPollCheckin> iterator = tmpCheckinList
								.iterator(); iterator.hasNext();) {
							TaskHostPollCheckin taskHostPollingCheckin = iterator
									.next();
							//在checkinList是否存在,不存在则添加到checkinList
							//存在则判断lastPollingTime是否与checkinList中的不一致，不一致则更新checkinList的lastPollingTime，一致则认为该任务运行服务器离线（离线次数加1，超过maxOfflineNumber则取消该服务器的当前分配任务）
							TaskHostPollCheckin tpc = getTaskHostPollingCheckinOfCheckList(taskHostPollingCheckin);
							if (tpc == null) {
								checkinList.add(taskHostPollingCheckin);
							} else {
								if (tpc.getLastPollingTime().equals(taskHostPollingCheckin.getLastPollingTime()) ){
									tpc.setOfflineNumber(tpc.getOfflineNumber() + 1);
									if (tpc.getOfflineNumber() >= config.getMaxOfflineNumber()) {
										taskScheduleService.cancelCurrentTask(taskHostPollingCheckin.getRunServer());
									}
								} else {
									tpc.setLastPollingTime(taskHostPollingCheckin.getLastPollingTime());
								}
							}
						}
					}
				});

		int onlineCheckInterval = config.getOnlineCheckInterval();
		logger.debug("获取任务服务器循环在线检测时间间隔：" + onlineCheckInterval / 1000 + "秒");
		loopThread.setInterval(onlineCheckInterval);
		logger.debug("开始启动循环检测任务服务器在线检测线程...");
		loopThread.start();
		logger.debug("成功启动循环检测任务服务器在线检测线程...");
		logger.debug("成功启动任务服务器在线检测插件,耗时：" + (System.currentTimeMillis() - s)
				+ "毫秒");

		logger.info("=============## TaskInvokeOnlineCheckPlugin 加载成功##================");
	}

	@Override
	public void destroy() {
		logger.debug("开始关闭任务服务器在线检测插件，关闭中...");
		loopThread.setCanStop(true);
		loopThread.stopThread();
		logger.debug("成功关闭任务服务器在线检测插件。");

		logger.info("=============## TaskInvokeOnlineCheckPlugin 销毁##================");
	}

}
