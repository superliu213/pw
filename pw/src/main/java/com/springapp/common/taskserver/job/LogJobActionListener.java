package com.springapp.common.taskserver.job;

import java.util.Date;

import com.springapp.common.application.Application;
import com.springapp.common.scheduler.Job;
import com.springapp.common.scheduler.JobListener;
import com.springapp.common.taskserver.GlobalTaskConstant;
import com.springapp.common.taskserver.entity.TaskExecuteList;
import com.springapp.common.taskserver.entity.TaskTrigger;
import com.springapp.common.taskserver.service.ITaskScheduleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;



public class LogJobActionListener implements JobListener {
	
    private final Log logger = LogFactory.getLog(LogJobActionListener.class);

    private ITaskScheduleService taskScheduleService;
    
//    private ICommonDAOService commonDAOService;

    private TaskTrigger trigger;

    private TaskExecuteList executeList;

    public LogJobActionListener(TaskTrigger trigger) {
        try {
            taskScheduleService = (ITaskScheduleService) Application
                    .getService(ITaskScheduleService.class);
            
//            commonDAOService = (ICommonDAOService)Application.getClassByName("commonDAOService");
            
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        this.trigger = trigger;
    }

    /**
     * @param executeTask
     * @param result
     * @Description 在调用逻辑操作后进行触发器记录或修改，并将“立即执行”的触发器状态位设置为“未启用”
     */
    private void doLogAfterTaskExec(TaskExecuteList executeTask,
            short result) {
        try {
            if (null == executeTask) {
                saveTaskExecuteLog(result);
            } else {
				executeTask.setExecuteTime(new Date());
                executeTask.setExecuteResult(result);
//                commonDAOService.saveAndUpdateObjs(executeTask);
            }

            if (null != trigger) {
            	//把当前任务放回任务池
//            	trigger.setCurrentRunServer(null);
            	
                if (GlobalTaskConstant.TriggerType.IMMEDIATELY.getType() == trigger
                        .getTriggerType()
                        || GlobalTaskConstant.TriggerType.ONCE.getType() == trigger
                                .getTriggerType()) {
                    trigger.setStatus(GlobalTaskConstant.TriggerStatus.UNAVALIABLE.getStatus());
//                    commonDAOService.updateObj(trigger);
                    logger.warn("一次性任务触发完成，取消任务。" + trigger.getStatus());
                } 
//                else {
//                	commonDAOService.updateObj(trigger);
//                }
            } else {
                logger.warn("任务触发为空。");
            }
        } catch(HibernateOptimisticLockingFailureException he) {
        	logger.warn("任务调度执行列表已被更新。");
        } catch (Exception e) {
            logger.error("更新任务调度执行列表/立即执行策略状态位时失败。", e);
        }

    }

    /**
     * @param result
     * @return
     * @Description 保存触发器执行记录,并返回保存结果
     */
    private TaskExecuteList saveTaskExecuteLog(short result) {
        final TaskExecuteList executeTask = new TaskExecuteList();
        executeTask.setTaskId(trigger.getTaskId());
        Date executeTime = new Date();
        executeTask.setExecuteTime(executeTime);
        executeTask.setExecuteResult(result);

        try {
            ITaskScheduleService taskScheduleService = (ITaskScheduleService) Application
                    .getService(ITaskScheduleService.class);
//            commonDAOService.saveObj(executeTask);
        } catch (Exception e) {
            logger.error("保存任务调度执行列表时失败。", e);
        }

        return executeTask;
    }

    /**
     * 任务执行之前，在调用逻辑操作前进行触发器记录，并将状态位设置为“执行中”
     * 
     */
    public void beforStart(Job<Object> job) {
        executeList = saveTaskExecuteLog(GlobalTaskConstant.TRIGGER_EXECUTING);
    }

    /**
     * 根据返回结果，保存执行结果
     * 
     */
    public void afterFinished(Job<Object> job) {
        Object result = job.getResult();
        if (result == null) {
            return;
        }
        if (result.equals(GlobalTaskConstant.TRIGGER_EXECUTING_DUPLICATED)) {
            // 任务正在执行，无需再次触发执行
            saveTaskExecuteLog(GlobalTaskConstant.TRIGGER_EXECUTING_DUPLICATED);
        } else if (Boolean.TRUE.equals(result)) {
            doLogAfterTaskExec(executeList,
                    GlobalTaskConstant.TRIGGER_EXECUTE_SUCCESS);
        } else {
            doLogAfterTaskExec(executeList,
                    GlobalTaskConstant.TRIGGER_SUCCESS_EXECUTE_FAILED);
        }
    }

    public void interrupted(Job<Object> job) {
        // 任务被中断
        doLogAfterTaskExec(executeList,
                GlobalTaskConstant.TRIGGER_SUCCESS_EXECUTE_INTERRUPT);
    }

}
