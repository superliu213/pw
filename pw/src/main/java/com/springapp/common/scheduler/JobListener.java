package com.springapp.common.scheduler;

public interface JobListener {

    /**
     * job将要开始
     */
    void beforStart(Job<Object> job);

    /**
     * job结束
     */
    void afterFinished(Job<Object> job);

    /**
     * Job被中断
     */
    void interrupted(Job<Object> job);

}
