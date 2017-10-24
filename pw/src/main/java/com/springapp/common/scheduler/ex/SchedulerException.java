package com.springapp.common.scheduler.ex;

public class SchedulerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public SchedulerException(String message) {
        this.message = message;
    }

    public static final SchedulerException JOB_NAME_NOT_UNIQUE = new SchedulerException(
            "ERROR: The job with same name has been submitted");

    public static final SchedulerException CYCLIC_JOB_DETECTED = new SchedulerException(
            "ERROR: Cyclic job is detected in the job pool");

}
