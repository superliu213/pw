package com.springapp.common.scheduler;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class JobAfterJob<T> extends Job<T> {

    private Collection<Job<?>> previousJob = new LinkedList<Job<?>>();
    /**
     * 倒计时锁
     */
    private CountDownLatch latch;

    public void after(Job<?>... job) {
        previousJob.addAll(Arrays.asList(job));
    }
    
    public void after(Job<?> job) {
        previousJob.add(job);
    }

    public Collection<Job<?>> getPreviousJob() {
        return previousJob;
    }

    public JobAfterJob(JobAction<T> action) {
        super(action);
    }

    public JobAfterJob(String name, JobAction<T> action) {
        super(name, action);
    }

    public JobAfterJob(String name, String groupName, JobAction<T> action) {
        super(name, groupName, action);
    }

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}
    
    

}
