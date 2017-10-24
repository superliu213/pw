package com.springapp.common.taskserver;


import com.springapp.common.application.Application;

public class TaskServer {
    private static TaskInvoker invoker = null;

    private TaskServer() {

    }

    public static void main(String[] args) {
        TaskInvoker invoker = TaskServer.getDefaultTaskInvoker();
        invoker.initialize();
    }

    @SuppressWarnings("unchecked")
    public static synchronized TaskInvoker getDefaultTaskInvoker() {
        if (invoker != null) {
            return invoker;
        }
        Application.initialize();
//        Application.setConfigProvider(new XMLConfigurationProvider());
//        Application.loadConfiguration("task-config.xml", TaskConfiguration.class);
//        TaskConfiguration config = (TaskConfiguration) Application
//                .getConfig(TaskConfiguration.class);
//        TaskInvoker invoker = new TaskInvoker(config);
        return invoker;
    }
}
