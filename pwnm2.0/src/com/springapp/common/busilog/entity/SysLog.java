package com.springapp.common.busilog.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "SYS_LOG", catalog = "")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_SYS_LOG", allocationSize = 1)
public class SysLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "ID")
    private Long id;
	
	@Column(name = "OCCUR_TIME")
	private Date occurTime;
	
	@Column(name = "OPERATOR_ID")
	private String operatorId;
	
	@Column(name = "LOG_TYPE")
	private Short logType;
	
	@Column(name = "LOG_LEVEL")
	private Short logLevel;
	
	@Column(name = "LOG_DESC")
	private String logDesc;
	
	@Column(name = "IP_ADDRESS")
	private String ipAddress;
	
	public SysLog() {
		super();
	}

	public SysLog(Long id, Date occurTime, String operatorId, Short logType, Short logLevel) {
		super();
		this.id = id;
		this.occurTime = occurTime;
		this.operatorId = operatorId;
		this.logType = logType;
		this.logLevel = logLevel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Short getLogType() {
		return logType;
	}

	public void setLogType(Short logType) {
		this.logType = logType;
	}

	public Short getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(Short logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogDesc() {
		return logDesc;
	}

	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "SysLog [id=" + id + ", occurTime=" + occurTime + ", operatorId=" + operatorId + ", logType=" + logType
				+ ", logLevel=" + logLevel + ", logDesc=" + logDesc + ", ipAddress=" + ipAddress + "]";
	}

}
