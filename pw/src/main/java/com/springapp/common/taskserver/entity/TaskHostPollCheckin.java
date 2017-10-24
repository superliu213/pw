package com.springapp.common.taskserver.entity;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 站点实体对象
 * @author jacker
 *
 */

@Entity
@Table(name = "SYS_TASK_HOST_POLL_CHECKIN", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class TaskHostPollCheckin implements Serializable {
	private static final long serialVersionUID = 1L;

    private String runServer;

    private Date lastPollingTime;

    private int offlineNumber = 0;
    
	/**
	 * 默认构造函数
	 */
    public TaskHostPollCheckin() {
    }

	@Id
	@Column(name = "RUN_SERVER", nullable = false, length = 64)
    public String getRunServer() {
        return runServer;
    }

    public void setRunServer(String runServer) {
        this.runServer = runServer;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_POLLING_TIME", length = 0)
    public Date getLastPollingTime() {
        return lastPollingTime;
    }

    public void setLastPollingTime(Date lastPollingTime) {
        this.lastPollingTime = lastPollingTime;
    }

    @Transient
	public int getOfflineNumber() {
		return offlineNumber;
	}

	public void setOfflineNumber(int offlineNumber) {
		this.offlineNumber = offlineNumber;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("runServer", getRunServer())
            .toString();
    }

	@Override
    public boolean equals(Object other) {
		if (!(other instanceof TaskHostPollCheckin)) {
			return false;
		}
        TaskHostPollCheckin castOther = (TaskHostPollCheckin) other;
        return new EqualsBuilder()
            .append(this.getRunServer(), castOther.getRunServer())
            .isEquals();
    }

	@Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRunServer())
            .toHashCode();
    }
}
