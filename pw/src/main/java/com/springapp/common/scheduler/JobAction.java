package com.springapp.common.scheduler;

public interface JobAction<T> {

    /**
     * @param ctx
     * @return
     */
    T doBiz(JobContext ctx);
}
