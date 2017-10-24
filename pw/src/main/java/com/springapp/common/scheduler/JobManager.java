package com.springapp.common.scheduler;

import com.springapp.common.scheduler.ex.SchedulerException;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class JobManager {

   
    Set<Job> jobSet = new HashSet<Job>();

    public void manageJob(Job job) throws SchedulerException {
        if (jobSet.contains(job)) {
            throw SchedulerException.JOB_NAME_NOT_UNIQUE;
        }
        if (checkCycle(job)) {
            throw SchedulerException.CYCLIC_JOB_DETECTED;
        }

        jobSet.add(job);
    }

    private boolean checkCycle(Job job) {
        return false;
    }

}
