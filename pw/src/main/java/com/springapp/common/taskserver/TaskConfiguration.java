package com.springapp.common.taskserver;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.springapp.common.config.Configuration;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("TaskServer")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskConfiguration extends Configuration {
    private int reloadConfigInterval;

    private Long defaultTimeout;

    private int taskServerType;

    private String taskJobProviderClass;
    
    //每次加载随记任务数量，-1为无上限
    private int everyRandomTaskNumber = -1;

    //每次加载最大任务数量，-1为无上限
    private int maxTaskNumber = -1;
    
    //在线检测间隔,一般大于reloadConfigInterval
    private int onlineCheckInterval;
    
    //最大的离线次数,超过此值,取消该任务服务器当前已分配的任务
    private int maxOfflineNumber = 3;
    
    public int getReloadConfigInterval() {
        return this.reloadConfigInterval;
    }

    public Long getDefaultTimeout() {
        return this.defaultTimeout;
    }

    public int getTaskServerType() {
        return this.taskServerType;
    }

    /**
     * @param reloadConfigInterval
     *            the reloadConfigInterval to set
     */
    public void setReloadConfigInterval(int reloadConfigInterval) {
        this.reloadConfigInterval = reloadConfigInterval;
    }

    /**
     * @param defaultTimeout
     *            the defaultTimeout to set
     */
    public void setDefaultTimeout(Long defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    /**
     * @param taskServerType
     *            the taskServerType to set
     */
    public void setTaskServerType(int taskServerType) {
        this.taskServerType = taskServerType;
    }

    public String getTaskJobProviderClass() {
        return taskJobProviderClass;
    }

    public void setTaskJobProviderClass(String provider) {
        this.taskJobProviderClass = provider;
    }

	public int getEveryRandomTaskNumber() {
		return everyRandomTaskNumber;
	}

	public void setEveryRandomTaskNumber(int everyRandomTaskNumber) {
		this.everyRandomTaskNumber = everyRandomTaskNumber;
	}

	public int getMaxTaskNumber() {
		return maxTaskNumber;
	}

	public void setMaxTaskNumber(int maxTaskNumber) {
		this.maxTaskNumber = maxTaskNumber;
	}

	public int getOnlineCheckInterval() {
		return onlineCheckInterval;
	}

	public void setOnlineCheckInterval(int onlineCheckInterval) {
		this.onlineCheckInterval = onlineCheckInterval;
	}

	public int getMaxOfflineNumber() {
		return maxOfflineNumber;
	}

	public void setMaxOfflineNumber(int maxOfflineNumber) {
		this.maxOfflineNumber = maxOfflineNumber;
	}

    
}
