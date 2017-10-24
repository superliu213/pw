package com.springapp.common.scheduler.quartz;

import org.quartz.StatefulJob;

/**
 * 如果不需要并发的job，直接实现改job即可。quartz框架会自动处理阻塞执行
 * 
 */
public class SingletonQuartzJobAction extends QuartzJobAction implements StatefulJob {

}
