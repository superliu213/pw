package com.springapp.mvc.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_GROUP_ROLE")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_SYS_GROUP_ROLE", allocationSize = 1)
public class SysGroupRole {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "ID")
    private Long id;

	@Column(name = "GROUP_ID")
    private String groupId;

	@Column(name = "ROLE_ID")
    private String roleId;

	public SysGroupRole() {
		super();
	}

	public SysGroupRole(String groupId, String roleId) {
		super();
		this.groupId = groupId;
		this.roleId = roleId;
	}

	public SysGroupRole(Long id, String groupId, String roleId) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.roleId = roleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "SysGroupRole [id=" + id + ", groupId=" + groupId + ", roleId=" + roleId + "]";
	}

}
