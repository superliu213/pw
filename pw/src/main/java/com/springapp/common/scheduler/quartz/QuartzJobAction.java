package com.springapp.common.scheduler.quartz;

import java.util.Collection;

import com.springapp.common.scheduler.Job;
import com.springapp.common.scheduler.JobAction;
import com.springapp.common.scheduler.JobContext;
import com.springapp.common.scheduler.JobListener;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;



public class QuartzJobAction implements org.quartz.InterruptableJob, org.quartz.Job {
    private final static Log logger = LogFactory.getLog(QuartzJobAction.class);

    public static final String JOB_ACTION_KEY = "com.jfly.commons.scheduler.quartz.jobaction";

    public static final String JOB_CTX_KEY = "com.jfly.commons.scheduler.quartz.jobctx";

    public static final String JOB_KEY = "com.jfly.commons.scheduler.quartz.job";

    public QuartzJobAction() {

    }

    /**
     * quartz内部执行job的线程
     */
    private Thread quartzInningThread;

    private JobExecutionContext ctx;

    private Job<Object> job;

    @SuppressWarnings("unchecked")
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        this.ctx = ctx;
        quartzInningThread = Thread.currentThread();

        JobContext jctx = (JobContext) ctx.getJobDetail().getJobDataMap().get(JOB_CTX_KEY);
        JobAction<Object> action = (JobAction<Object>) ctx.getJobDetail().getJobDataMap()
                .get(JOB_ACTION_KEY);
        job = (Job<Object>) ctx.getJobDetail().getJobDataMap().get(JOB_KEY);
        Collection<JobListener> listeners = job.getListeners();

        for (JobListener jobListener : listeners) {
            jobListener.beforStart(job);
        }

        if (action != null) {
            Object o = action.doBiz(jctx);
            if (o != null) {
                jctx.putResult(o);
            }
        }

        for (JobListener jobListener : listeners) {
            jobListener.afterFinished(job);
        }

    }

    /**
     * 中断job
     * 
     * @see org.quartz.InterruptableJob#interrupt()
     */
    @SuppressWarnings("deprecation")
    public void interrupt() throws UnableToInterruptJobException {
        if (quartzInningThread != null && quartzInningThread.isAlive()) {
            String date = DateFormatUtils.format(ctx.getFireTime(), "yyyy-MM-dd HH:mm:ss");
            logger.debug(String.format("开始强制中断Job:%s，当前运行时间%s，当前内部线程:%s", QuartzHelper.getNameByJobDetail(ctx.getJobDetail()), date, quartzInningThread));

            quartzInningThread.stop();
        }
        Collection<JobListener> listeners = job.getListeners();
        for (JobListener jobListener : listeners) {
            jobListener.interrupted(job);
        }
    }
}
