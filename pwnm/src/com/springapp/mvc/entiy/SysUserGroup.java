package com.springapp.mvc.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_USER_GROUP")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_SYS_USER_GROUP", allocationSize = 1)
public class SysUserGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "ID")
    private Long id;
	
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "GROUP_ID")
    private String groupId;

	public SysUserGroup() {
		super();
	}

	public SysUserGroup(String userId, String groupId) {
		super();
		this.userId = userId;
		this.groupId = groupId;
	}

	public SysUserGroup(Long id, String userId, String groupId) {
		super();
		this.id = id;
		this.userId = userId;
		this.groupId = groupId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "SysUserGroup [id=" + id + ", userId=" + userId + ", groupId=" + groupId + "]";
	}
	
}
