package com.springapp.mvc.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_USER_ROLE")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_SYS_USER_ROLE", allocationSize = 1)
public class SysUserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "ID")
    private Long id;
	
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "ROLE_ID")
    private String roleId;

	public SysUserRole() {
		super();
	}

	public SysUserRole(String userId, String roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

	public SysUserRole(Long id, String userId, String roleId) {
		super();
		this.id = id;
		this.userId = userId;
		this.roleId = roleId;
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "SysUserRole [id=" + id + ", userId=" + userId + ", roleId=" + roleId + "]";
	}
	
}
