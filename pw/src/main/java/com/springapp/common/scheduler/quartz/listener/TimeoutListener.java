package com.springapp.common.scheduler.quartz.listener;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.springapp.common.scheduler.Job;
import com.springapp.common.scheduler.JobStatus;
import com.springapp.common.scheduler.quartz.QuartzHelper;
import com.springapp.common.scheduler.quartz.QuartzJobAction;
import com.springapp.common.scheduler.quartz.QuartzJobSchedualer;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobExecutionContextImpl;



/**
 * 超时监听器
 * @author Administrator
 *
 */
public class TimeoutListener implements org.quartz.JobListener {
	final Log log = LogFactory.getLog(getClass());
	private final QuartzJobSchedualer quartzJobSchedualer;
	private ScheduledExecutorService timer = Executors
			.newScheduledThreadPool(10);

	/**
	 * @param quartzJobSchedualer
	 */
	public TimeoutListener(QuartzJobSchedualer quartzJobSchedualer) {
		this.quartzJobSchedualer = quartzJobSchedualer;
	}

	/**
	 * 超时时间
	 */
	private Date realFiredTime;

	public void jobWasExecuted(JobExecutionContext ctx,
			JobExecutionException jobException) {
		@SuppressWarnings("unchecked")
		final Job<Object> job = (Job<Object>) ctx.getJobDetail()
				.getJobDataMap().get(QuartzJobAction.JOB_KEY);
		//ctx.incrementRefireCount();
		((JobExecutionContextImpl)ctx).incrementRefireCount();
		
		if (jobException == null) {
			job.setStatus(JobStatus.FINISHED);
		} else {
			job.setStatus(JobStatus.INVALID);
			job.getJobContext().putResult(jobException);
		}
	}

	@SuppressWarnings("unchecked")
	public void jobToBeExecuted(final JobExecutionContext ctx) {
		final Job<Object> job = (Job<Object>) ctx.getJobDetail()
				.getJobDataMap().get(QuartzJobAction.JOB_KEY);
		job.setStatus(JobStatus.RUNNING);
		realFiredTime = new Date();
		final long timeout = job.getTimeout();
		if (timeout > 0) {
			log.debug(String.format("开始启动超时处理计数,超时时限：%d秒", job.getTimeout()));
			Thread command = new Thread() {

				public void run() {
					if (!job.getStatus().equals(JobStatus.RUNNING)) {
						return;
					}
					Date now = new Date();
					// 已经超时时间（毫秒）
					long off = now.getTime() - realFiredTime.getTime();
					log.info(String.format(
							"任务【%s】应该在%s开始，超时时间为：%d秒,当前时间为%s，已经超时：%d毫秒", job
									.getName(), DateFormatUtils.format(
									realFiredTime, "yyyy-MM-dd HH:mm:ss,SSS"),
							timeout, DateFormatUtils.format(now,
									"yyyy-MM-dd HH:mm:ss,SSS"), off - timeout
									* 1000));
					quartzJobSchedualer.interrupt(job.getGroupName());
					job.setStatus(JobStatus.INVALID);
				}
			};
			command.setName("timeout_" + QuartzHelper.getFullNameByJobDetail(ctx.getJobDetail()));
			timer.schedule(command, timeout * 1000 + 100, TimeUnit.MILLISECONDS);
		}
	}

	public void jobExecutionVetoed(JobExecutionContext ctx) {
	}

	public String getName() {
		return "all_job_timeout_listener";
	}
}