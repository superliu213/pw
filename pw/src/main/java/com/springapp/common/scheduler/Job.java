package com.springapp.common.scheduler;

import java.util.Collection;
import java.util.LinkedList;

public class Job<T> {

    public static final String ANONYMOUS_JOB_NAME = "com.jfly.commons.scheduler.anonymousjob";

    private String name = ANONYMOUS_JOB_NAME;

    public static final String ANONYMOUS_JOB_GROUP_NAME = "com.jfly.commons.scheduler.anonymousjobgroup";

    private String groupName = ANONYMOUS_JOB_GROUP_NAME;

    /**
     * job status
     */
    private JobStatus status = JobStatus.NEW;

    /**
     * Context of this job
     */
    private JobContext ctx = new JobContext();

    private JobScheduler sche = null;

    private boolean repeatable = false;

    /**
     * 超时时间，单位秒
     */
    private long timeout = -1;

    /**
     * job listener to catch time point at before job after job and status change
     */
    private Collection<JobListener> listeners = new LinkedList<JobListener>();

    /**
     * @return
     */
    public Collection<JobListener> getListeners() {
        return listeners;
    }

    /**
     * the executable task interface
     */
    private JobAction<T> action;

    public Job(String name, JobAction<T> action) {
        super();
        this.name = name;
        this.action = action;
    }

    public Job(String name, String groupName, JobAction<T> action) {
        super();
        this.name = name;
        this.groupName = groupName;
        this.action = action;
    }

    public Job(JobAction<T> action) {
        this(ANONYMOUS_JOB_NAME, action);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public JobAction<T> getAction() {
        return action;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public JobStatus getStatus() {
        return status;
    }

    @SuppressWarnings("unchecked")
    public T getResult() {
        return (T) ctx.waitForResult();
    }

    public JobContext getJobContext() {
        return ctx;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public void addListener(JobListener listener) {
        listeners.add(listener);
    }

    public void setScheduler(JobScheduler sche) {
        this.sche = sche;
    }

    public JobScheduler getScheduler() {
        return sche;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    /**
     * 单位：秒
     * @return the timeout
     */
    public long getTimeout() {
        return timeout;
    }

    /**
     * 单位：秒
     * @param timeout the timeout to set
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    
}
