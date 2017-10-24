package com.springapp.common.taskserver.interceptor;

import java.util.Calendar;

import com.springapp.common.taskserver.core.TaskInvocation;
import com.springapp.common.taskserver.core.TaskJobInterceptor;
import com.springapp.common.taskserver.entity.TaskTrigger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class TimeoutInterceptor implements TaskJobInterceptor {
	
    private static Log logger = LogFactory.getLog(TimeoutInterceptor.class);

    /**
     * 超时处理
     * 
     */
    public Object intercept(final TaskInvocation invocation) {
        logger.debug("start intercept...");
        final TaskTrigger task = invocation.getTaskTrigger();

        final Long _timeout = task.getTimeout();
        final Long timeout = _timeout == null ? invocation.getInvoker().getContext()
                .getTaskConfiguration().getDefaultTimeout() : _timeout;

        Calendar cal = Calendar.getInstance();
        cal.setTime(task.getFirstExecuteTime());
        cal.add(Calendar.SECOND, timeout.intValue());

        return "timeout_start";
    }

    @Override
    public String toString() {
        return "超时处理拦截器";
    }

}
