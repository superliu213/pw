package com.springapp.common.taskserver.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.springapp.common.taskserver.GlobalTaskConstant;
import com.springapp.common.taskserver.service.TaskManageService;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.service.spi.ServiceException;


@Entity
@Table(name = "SYS_TASK_TRIGGER")
@DynamicUpdate(true)
@DynamicInsert(true)
public class TaskTrigger implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long recordId;

	/**
	 * 任务id
	 */
	private Integer taskId;

	private String triggerName;

	/**
	 * 触发类型：
	 * <ol>
	 * <li>0:立即执行</li>
	 * <li>1:单次执行</li>
	 * <li>2:循环执行</li>
	 * </ol>
	 */
	private Short triggerType;

	private String triggerParam;
	
	/**
	 * 循环次数
	 */
	private Integer intervalNum;

	/**
	 * 循环单位：
	 * { "秒/SECOND", "分/MINUTE", "小时/HOUR", "天/DAY", "周/WEEK", "月/MONTH", "年/YEAR" }, values = {
			"SECOND", "MINUTE", "HOUR", "DAY", "WEEK", "MONTH", "YEAR" }
	 * <ol>
	 * <li>SECOND</li>
	 * <li>MINUTE</li>
	 * <li>HOUR</li>
	 * <li>DAY</li>
	 * <li>WEEK</li>
	 * <li>MONTH</li>
	 * <li>YEAR</li>
	 * </ol>
	 */
	private String intervalUnit;

	/**
	 * 首次触发时间
	 */
	private Date firstExecuteTime;

	/**
	 * 超时
	 */
	private Long timeout = -1L;

	/**
	 * 触发器状态
	 * <ol>
	 * <li>0:触发器未启用</li>
	 * <li>1:触发器已启用</li>
	 * </ol>
	 */
	private Short status;

	private Short runWay;

    private String appoindRunServer;

    private String currentRunServer;
    
    private int version;  
    
	/** full constructor */
	public TaskTrigger(Integer taskId, String triggerName, Short triggerType, Date firstExecuteTime,
			String intervalUnit, Integer intervalNum, Short status) {
		this.taskId = taskId;
		this.triggerName = triggerName;
		this.triggerType = triggerType;
		this.firstExecuteTime = firstExecuteTime;
		this.intervalUnit = intervalUnit;
		this.intervalNum = intervalNum;
		this.status = status;
	}

	/** default constructor */
	public TaskTrigger() {
	}

	/** minimal constructor */
	public TaskTrigger(Integer taskId, String triggerName, Short triggerType, Short status) {
		this.taskId = taskId;
		this.triggerName = triggerName;
		this.triggerType = triggerType;
		this.status = status;
	}

	/**
	 * @param i
	 * @param name
	 */
	public TaskTrigger(Integer i, String name) {
		this.taskId = i;
		this.triggerName = name;
	}

	public TaskTrigger(Integer taskId, Long timeout) {
		this.taskId = taskId;
		this.triggerName = "立即触发任务";
		this.triggerType = GlobalTaskConstant.TriggerType.IMMEDIATELY.getType();
		this.status = GlobalTaskConstant.TriggerStatus.AVALIABLE.getStatus();
		this.timeout = timeout;

	}

	@Id
	@Column(name = "RECORD_ID")
	@GenericGenerator(name = "native", strategy = "native")
	@GeneratedValue(generator = "native")
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	@Column(name = "TASK_ID", nullable = false)
	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Column(name = "TRIGGER_NAME", nullable = false)
	public String getTriggerName() {
		return this.triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	@Column(name = "TRIGGER_PARAM")
	public String getTriggerParam() {
		return triggerParam;
	}

	public void setTriggerParam(String triggerParam) {
		this.triggerParam = triggerParam;
	}

	@Column(name = "TRIGGER_TYPE", nullable = false)
	public Short getTriggerType() {
		return this.triggerType;
	}

	public void setTriggerType(Short triggerType) {
		this.triggerType = triggerType;
	}

	@Column(name = "FIRST_EXECUTE_TIME")
	public Date getFirstExecuteTime() {
		return this.firstExecuteTime;
	}

	public void setFirstExecuteTime(Date firstExecuteTime) {
		this.firstExecuteTime = firstExecuteTime;
	}

	/**
	 * @return the timeout
	 */
	@Column(name = "TIMEOUT")
	public Long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	@Column(name = "INTERVAL_UNIT")
	public String getIntervalUnit() {
		return this.intervalUnit;
	}

	public void setIntervalUnit(String intervalUnit) {
		this.intervalUnit = intervalUnit;
	}

	@Column(name = "INTERVAL_NUM")
	public Integer getIntervalNum() {
		return this.intervalNum;
	}

	public void setIntervalNum(Integer intervalNum) {
		this.intervalNum = intervalNum;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
	@Column(name = "RUN_WAY", length = 0)
    public Short getRunWay() {
        return runWay;
    }

    public void setRunWay(Short runWay) {
        this.runWay = runWay;
    }

	@Column(name = "APPOIND_RUN_SERVER", length = 64)
    public String getAppoindRunServer() {
        return appoindRunServer;
    }

    public void setAppoindRunServer(String appoindRunServer) {
        this.appoindRunServer = appoindRunServer;
    }

	@Column(name = "CURRENT_RUN_SERVER", length = 64)
    public String getCurrentRunServer() {
        return currentRunServer;
    }

    public void setCurrentRunServer(String currentRunServer) {
        this.currentRunServer = currentRunServer;
    }
    
    //@Version
    @Column(name = "VERSION",length = 11)
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Transient
	public Task getTask() {
		try {
			Task task = TaskManageService.getInstance().getTaskDefinition(taskId);
			return task;
		} catch (ServiceException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return String.format("%s(%s),任务：[%s]", triggerName, recordId, taskId);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof TaskTrigger)) {
			return false;
		}
		TaskTrigger castOther = (TaskTrigger) other;
		return new EqualsBuilder().append(this.getRecordId(), castOther.getRecordId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getRecordId()).toHashCode();
	}
}
