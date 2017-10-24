package com.springapp.common.taskserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_TASK")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer taskId;

	private Integer taskType;

	private String taskName;

	private String taskClass;

	private String taskComment;

	/** default constructor */
	public Task() {
	}

	/** full constructor */
    public Task(String taskName, String taskClass, String taskComment) {
        this.taskName = taskName;
        this.taskClass = taskClass;
        this.taskComment = taskComment;
    }
    
	/** minimal constructor */
	public Task(String taskName, String taskClass) {
		this.taskName = taskName;
		this.taskClass = taskClass;
	}

	@Id
	@Column(name = "TASK_ID")
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@GeneratedValue(generator = "assigned")
	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the type
	 */
	@Column(name = "TASK_TYPE", nullable = false)
	public Integer getTaskType() {
		return taskType;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	@Column(name = "TASK_NAME", nullable = false)
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(name = "TASK_CLASS", nullable = false)
	public String getTaskClass() {
		return this.taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	@Column(name = "TASK_COMMENT")
	public String getTaskComment() {
		return this.taskComment;
	}

	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}

	public String toString() {
		return String.format("%s(%s),任务类:%s", taskName, taskId, taskClass);
	}

	public boolean equals(Object other) {
		if (!(other instanceof Task))
			return false;
		Task castOther = (Task) other;
		return new EqualsBuilder().append(this.getTaskId(),
				castOther.getTaskId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getTaskId()).toHashCode();
	}

}
