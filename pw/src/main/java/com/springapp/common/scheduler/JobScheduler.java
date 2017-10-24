package com.springapp.common.scheduler;

import com.springapp.common.scheduler.quartz.QuartzJobSchedualer;

public abstract class JobScheduler {

    JobManager jobManager = new JobManager();
    
    public static JobScheduler newQuartzScheduler() {
        return new QuartzJobSchedualer();
    }

    public static JobScheduler newQuartzScheduler(String name) {
        return new QuartzJobSchedualer(name);
    }

    protected abstract void submitJob(Job<?> job);

    public void submit(Job<?>... jobs) {

        if (jobs == null) {
            throw new NullPointerException();
        }
        for (Job<?> jobItem : jobs) {
            jobManager.manageJob(jobItem);
            submitJob(jobItem);
            jobItem.setScheduler(this);
        }

    }

    public abstract void cancelAll(String groupName);

}
