package com.springapp.common.scheduler;

import java.util.Date;

public class TimerJob<T> extends Job<T> {

    /**
     * interval number
     */
    private int intervalNum = 1;

    /**
     * interval time unit
     */
    private IntervalUnit intervalTimeUnit = IntervalUnit.DAY;

    private Date theStartDate;

    public TimerJob(JobAction<T> action) {
        super(action);
    }

    public TimerJob(String name, JobAction<T> action) {
        super(name, action);
    }
    


    public TimerJob(String name, String groupName, JobAction<T> action) {
        super(name, groupName, action);
    }

    public TimerJob<T> at(Date date) {
        this.theStartDate = date;
        return this;

    }

    public Date getTheStartDate() {
        return theStartDate;
    }

    public IntervalUnit getIntervalTimeUnit() {
        return intervalTimeUnit;
    }

    public int getIntervalNum() {
        return intervalNum;
    }

    public void every(int intervalNum, IntervalUnit unit) {
        setRepeatable(true);
        this.intervalNum = intervalNum;
        intervalTimeUnit = unit;
    }

    public Job<T> every(int intervalNum) {
        every(intervalNum, IntervalUnit.DAY);
        return this;
    }

    public void everyHour() {
        every(1, IntervalUnit.HOUR);
    }

    public void everyDay() {
        every(1, IntervalUnit.DAY);
    }

    public void everyWeek() {
        every(1, IntervalUnit.WEEK);
    }

    public void everyMonth() {
        every(1, IntervalUnit.MONTH);
    }

}
