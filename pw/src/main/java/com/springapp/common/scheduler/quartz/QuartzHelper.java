package com.springapp.common.scheduler.quartz;

import org.quartz.JobDetail;
import org.quartz.impl.JobDetailImpl;

public class QuartzHelper {
	public static String getNameByJobDetail(JobDetail jobDetail) {
		JobDetailImpl jobDetailImpl = (JobDetailImpl)jobDetail;
		
		return jobDetailImpl.getName();
	}
	
	public static String getFullNameByJobDetail(JobDetail jobDetail) {
		JobDetailImpl jobDetailImpl = (JobDetailImpl)jobDetail;
		
		return jobDetailImpl.getFullName();
	}
}
