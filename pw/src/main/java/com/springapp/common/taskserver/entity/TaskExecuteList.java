package com.springapp.common.taskserver.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.springapp.common.taskserver.service.TaskManageService;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.service.spi.ServiceException;



@Entity
@Table(name = "SYS_TASK_EXECUTE_LIST")
@DynamicUpdate(true)
@DynamicInsert(true)
public class TaskExecuteList implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private Long recordId;

    private Integer taskId;

    private Date executeTime;
    
    private String executeRunServer;

    /**
     * 执行结果
     * <ol>
     * <li>0:触发失败，显示“未进行调度</li>
     * <li>1:触发成功，但执行成功，显示“执行成功”</li>
     * <li>2:触发成功，但执行成功，显示“执行失败”</li>
     * <li>3: 正在运行触发器，显示“执行中”</li>
     * </ol>
     */
    private Short executeResult;

    /** full constructor */
    public TaskExecuteList(Integer taskId, Date executeTime, Short executeResult) {
        this.taskId = taskId;
        this.executeTime = executeTime;
        this.executeResult = executeResult;
    }

    /** default constructor */
    public TaskExecuteList() {
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

    @Transient
    public Task getTask() {
        try {
        	
            TaskManageService rep = TaskManageService.getInstance();
            Task task = rep.getTaskDefinition(taskId);
            return task;
        } catch (ServiceException e) {
            return null;
        }
    }

    @Column(name = "EXECUTE_TIME", nullable = false)
    public Date getExecuteTime() {
        return this.executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    @Column(name = "EXECUTE_RESULT", nullable = false)
    public Short getExecuteResult() {
        return this.executeResult;
    }

    public void setExecuteResult(Short executeResult) {
        this.executeResult = executeResult;
    }

    @Column(name = "EXECUTE_RUN_SERVER", length = 64)
    public String getExecuteRunServer() {
        return executeRunServer;
    }

    public void setExecuteRunServer(String executeRunServer) {
        this.executeRunServer = executeRunServer;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("recordid", getRecordId()).toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TaskExecuteList)) {
            return false;
        }
        TaskExecuteList castOther = (TaskExecuteList) other;
        return new EqualsBuilder().append(this.getRecordId(), castOther.getRecordId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getRecordId()).toHashCode();
    }

}
