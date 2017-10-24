package com.springapp.common.scheduler;

/**
 * 默认适配器
 * 
 */
public class JobListenerAdapter implements JobListener {

    /**
     * @see com.jfly.commons.scheduler.JobListener#beforStart(com.jfly.commons.scheduler.Job)
     */
    public void beforStart(Job<Object> job) {
    }

    /**
     * @see com.jfly.commons.scheduler.JobListener#afterFinished(com.jfly.commons.scheduler.Job)
     */
    public void afterFinished(Job<Object> job) {
    }

    /**
     * @see com.jfly.commons.scheduler.JobListener#interrupted(com.jfly.commons.scheduler.Job)
     */
    public void interrupted(Job<Object> job) {
    }

    /**
     * @see com.jfly.commons.scheduler.JobListener#executeDuplicated(com.jfly.commons.scheduler
     *      .Job)
     */
    public void executeDuplicated(Job<Object> job) {
    }

}
