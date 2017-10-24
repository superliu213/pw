package com.springapp.common.scheduler.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import com.springapp.common.scheduler.*;
import com.springapp.common.scheduler.quartz.listener.TimeoutListener;
import com.springapp.common.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;



public final class QuartzJobSchedualer extends JobScheduler {

    final Log log = LogFactory.getLog(getClass());

    private static final String QUARTZ_JOB_NAME_PREFIX = "QuartzJobFor";

    private static final String QUARTZ_TRIGGER_PREFIX_NAME = "QuartzTriggerFor";

    private static final long MILLISECONDS_PER_DAY = 1000L * 60L * 60L * 24L;

    SchedulerFactory schedFact = new StdSchedulerFactory();

    Scheduler sched;

    Calendar cal = Calendar.getInstance();

    public QuartzJobSchedualer() {
        try {
            synchronized (QuartzJobSchedualer.class) {
                sched = schedFact.getScheduler();
                //sched.addGlobalJobListener(new TimeoutListener(this));
                sched.getListenerManager().addJobListener(new TimeoutListener(this));
                
                sched.start();
            }

        } catch (SchedulerException e) {
            log.error("Quartz job schedule error", e);
        }

    }

    public QuartzJobSchedualer(String name) {

        try {
            synchronized (QuartzJobSchedualer.class) {
                sched = schedFact.getScheduler(name);
                sched.start();
            }
        } catch (SchedulerException e) {
            log.error("Quartz job schedule error", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void submitJob(Job job) {
        // timer triggered job
        Trigger t = null;
        if (job instanceof TimerJob) {
            log.debug("Using Quarts to schedule the timer job :" + job.getName());

            TimerJob tj = (TimerJob) job;
            if (tj.isRepeatable()) {
                log.debug("Using Quarts to schedule the repeated job :" + job.getName());
                t = getRepeatableTrigger(tj);
            } else {
                log.debug("Using Quarts to schedule the once execution job :" + job.getName());
//                t = new SimpleTrigger(_TRIGGER_NAME(job.getName()), tj.getGroupName(),
//                        tj.getTheStartDate());
                t = newTrigger().withIdentity(_TRIGGER_NAME(job.getName()), tj.getGroupName()).startAt(tj.getTheStartDate()).build();  
            }

            log.debug("Preparing Quarts jobdetail for job :" + job.getName());
            JobDetail detail = prepareJobDetail(job);
            try {
                sched.scheduleJob(detail, t);
            } catch (SchedulerException e) {
                log.error("scheduleJob Exception。", e);
            }
        } else if (job instanceof JobAfterJob) {
            // Job after job
            final JobAfterJob jaj = (JobAfterJob) job;
            final Collection<Job> preJobs = jaj.getPreviousJob();

            final Thread schedulerWaitThread = new Thread(new Runnable() {

                private boolean repeatedWait = jaj.isRepeatable();

                public void run() {
                    try {
                        while (true) {
                            jaj.setLatch(new CountDownLatch(preJobs == null ? 0 : preJobs.size()));

                            jaj.getLatch().await();

                            log.debug("Preparing Quarts jobdetail for job " + jaj.getName());
                            JobDetail detail = prepareJobDetail(jaj);
                            if (sched.getJobDetail(detail.getKey()) == null) {
                                sched.addJob(detail, true);
                            }
                            sched.triggerJob(detail.getKey());

                            if (!repeatedWait) {
                                break;
                            }
                        }

                    } catch (InterruptedException e) {
                        log.error("latch.await() Exception。", e);
                    } catch (SchedulerException e) {
                        log.error("scheduleJob Exception。", e);
                    }

                }
            });
            schedulerWaitThread.start();
            for (final Job preJob : preJobs) {
                preJob.addListener(new AfterJobListener(jaj, preJob));
            }
        }

    }

    public static class AfterJobListener extends JobListenerAdapter {
        private final Log log = LogFactory.getLog(getClass());

        private Job<Object> preJob;

        private JobAfterJob<Object> afterJob;

        public AfterJobListener(JobAfterJob<Object> afterJob, Job<Object> preJob) {
            this.preJob = preJob;
            this.afterJob = afterJob;
        }

        public void afterFinished(Job<Object> job) {
            log.debug("job " + preJob.getName() + " is finished");
            if (afterJob.getLatch() != null) {
                afterJob.getLatch().countDown();
            }
        }

    }

    /**
     * @version 根据如果是job是statefualable，创建阻塞job:statfulJob
     * @param job
     * @return
     */
    private JobDetail prepareJobDetail(Job<Object> job) {
        Class<? extends org.quartz.Job> jobclazz = null;
        if (job instanceof Statefulable) {
            jobclazz = SingletonQuartzJobAction.class;
        } else {
            jobclazz = QuartzJobAction.class;
        }
        //JobDetail detail = new JobDetail(_JOB_NAME(job.getName()), job.getGroupName(), jobclazz);
        JobDetail detail = newJob(jobclazz).withIdentity(_JOB_NAME(job.getName()), job.getGroupName()).build(); 
        detail.getJobDataMap().put(QuartzJobAction.JOB_ACTION_KEY, job.getAction());
        detail.getJobDataMap().put(QuartzJobAction.JOB_CTX_KEY, job.getJobContext());
        detail.getJobDataMap().put(QuartzJobAction.JOB_KEY, job);
        return detail;
    }

    /**
     * @param job
     * @return get quartz job trigger for this generic job
     */
    private Trigger getRepeatableTrigger(TimerJob<Object> job) {
        Trigger t = null;
        cal.setTime(job.getTheStartDate());
        
        Date startDate;
        
        IntervalUnit tu = job.getIntervalTimeUnit();
        if (tu == IntervalUnit.SECOND) {
//            t = TriggerUtils.makeSecondlyTrigger(job.getIntervalNum());
//            t.setStartTime(job.getTheStartDate());

        	t = newTrigger()
                    .withIdentity(_TRIGGER_NAME(job.getName()), job.getGroupName())
                    .startAt(job.getTheStartDate())
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(job.getIntervalNum())
                            .repeatForever())
                    .build();
        } else if (tu == IntervalUnit.MINUTE) {
//            t = TriggerUtils.makeMinutelyTrigger(job.getIntervalNum());
//            t.setStartTime(job.getTheStartDate());
            
            t = newTrigger()
                    .withIdentity(_TRIGGER_NAME(job.getName()), job.getGroupName())
                    .startAt(job.getTheStartDate())
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(job.getIntervalNum())
                            .repeatForever())
                    .build();
        } else if (tu == IntervalUnit.HOUR) {
//            t = TriggerUtils.makeHourlyTrigger(job.getIntervalNum());
//            t.setStartTime(job.getTheStartDate());
        	
        	t = newTrigger()
                    .withIdentity(_TRIGGER_NAME(job.getName()), job.getGroupName())
                    .startAt(job.getTheStartDate())
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(job.getIntervalNum())
                            .repeatForever())
                    .build();
        } else if (tu == IntervalUnit.DAY) {
//            t = new SimpleTrigger(_TRIGGER_NAME(job.getName()), job.getGroupName(),
//                    job.getTheStartDate(), null, SimpleTrigger.REPEAT_INDEFINITELY,
//                    MILLISECONDS_PER_DAY * job.getIntervalNum());
//            t.setStartTime(job.getTheStartDate());
        	
        	t = newTrigger()
                    .withIdentity(_TRIGGER_NAME(job.getName()), job.getGroupName())
                    .startAt(job.getTheStartDate())
                    .withSchedule(simpleSchedule()
                            .withIntervalInHours(job.getIntervalNum()*24)
                            .repeatForever())
                    .build();
        } else if (tu == IntervalUnit.WEEK) {
//            t = TriggerUtils.makeWeeklyTrigger(cal.get(Calendar.DAY_OF_WEEK),
//                    cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
//            // TriggerUtils.makeWeeklyTrigger 把秒忽略了（内部实现设置为0），导致循环触发的首次触发事件已经过期而无法触发，需要把开始时间倒退一分钟
//            cal.add(Calendar.MINUTE, -1);
//            t.setStartTime(cal.getTime());
        	
        	t = newTrigger()
                    .withIdentity(_TRIGGER_NAME(job.getName()), job.getGroupName())
                    .startAt(job.getTheStartDate())
                    .withSchedule(simpleSchedule()
                            .withIntervalInHours(job.getIntervalNum()*24*7)
                            .repeatForever())
                    .build();
        } else if (tu == IntervalUnit.MONTH) {
            // TriggerUtils.makeMonthlyTrigger 把秒忽略了（内部实现设置为0），导致循环触发的首次触发事件已经过期而无法触发，需要把开始时间倒退一分钟
//            t = TriggerUtils.makeMonthlyTrigger(cal.get(Calendar.DAY_OF_MONTH),
//                    cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
//            cal.add(Calendar.MINUTE, -1);
//            t.setStartTime(cal.getTime());
        	
        	t = newTrigger()
                    .withIdentity(_TRIGGER_NAME(job.getName()), job.getGroupName())
                    .startAt(job.getTheStartDate())
                    .withSchedule(simpleSchedule()
                            .withIntervalInHours(job.getIntervalNum()*24*7)
                            .repeatForever())
                    .build();
        	//Cron 格式: [秒] [分] [小时] [日] [月] [周] [年] 每月指定日期执行
        	String format = "{0} {1} {2} {3} * ?";
        	String schedule = StringUtil.format(format, cal.get(Calendar.SECOND), cal.get(Calendar.MINUTE), cal.get(Calendar.HOUR), cal.get(Calendar.HOUR_OF_DAY));
        	t = newTrigger()
        			.withIdentity(_TRIGGER_NAME(job.getName()), job.getGroupName())
        			.startAt(job.getTheStartDate())
                    .withSchedule(cronSchedule(schedule))
                    .build();
        } else {
            // TriggerUtils.makeDailyTriggerr 把秒忽略了（内部实现设置为0），导致循环触发的首次触发事件已经过期而无法触发，需要把开始时间倒退一分钟
//            t = TriggerUtils.makeDailyTrigger(cal.get(Calendar.HOUR_OF_DAY),
//                    cal.get(Calendar.MINUTE));
//            cal.add(Calendar.MINUTE, -1);
//            t.setStartTime(cal.getTime());
        	
        	t = newTrigger()
                    .withIdentity(_TRIGGER_NAME(job.getName()), job.getGroupName())
                    .startAt(job.getTheStartDate())
                    .withSchedule(simpleSchedule()
                            .withIntervalInHours(job.getIntervalNum()*24)
                            .repeatForever())
                    .build();
        }

//        t.setName(_TRIGGER_NAME(job.getName()));
//        t.setGroup(job.getGroupName());
        return t;
    }

    /**
     * get quartz trigger name for this generic job
     * 
     * @param jobName
     * @return
     */
    private static final String _TRIGGER_NAME(String jobName) {
        return QUARTZ_TRIGGER_PREFIX_NAME + jobName;
    }

    /**
     * get quartz job name for this generic job
     * 
     * @param jobName
     * @return
     */
    private static final String _JOB_NAME(String jobName) {
        return QUARTZ_JOB_NAME_PREFIX + jobName;
    }

//    @Override
//    public void cancelAll(String groupName) {
//        String[] names;
//        try {
//            names = sched.getJobNames(groupName);
//            for (String jobName : names) {
//                sched.deleteJob(jobName, groupName);
//            }
//        } catch (SchedulerException e) {
//            log.error("cancelAll job Exception。", e);
//        }
//    }

    @Override
    public void cancelAll(String groupName) {
        try {
            Set<JobKey> jobKeys = sched.getJobKeys(jobGroupEquals(groupName));
            for (JobKey jobKey : jobKeys) {
                sched.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("cancelAll job Exception。", e);
        }
    }
    
    /**
     * 中断某个组的所有job
     * 
     * @param groupName
     *            组名
     * @since 2010-8-16
     */
//    public void interrupt(String groupName) {
//        String[] names;
//        try {
//            names = sched.getJobNames(groupName);
//            for (String jobName : names) {
//                sched.interrupt(jobName, groupName);
//            }
//
//        } catch (SchedulerException e) {
//            log.error("interrupt job Exception。", e);
//        }
//    }
    
    public void interrupt(String groupName) {
        try {
            Set<JobKey> jobKeys = sched.getJobKeys(jobGroupEquals(groupName));
            for (JobKey jobKey : jobKeys) {
                sched.interrupt(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("interrupt job Exception。", e);
        }
    }

}
