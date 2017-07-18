package com.springapp.mvc.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_ROLE")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_SYS_ROLE", allocationSize = 1)
public class SysRole {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "ID")
    private Long id;

	@Column(name = "ROLE_ID")
    private String roleId;

	@Column(name = "ROLE_DESC")
    private String roleDesc;
    
	@Column(name = "REMARK")
    private String remark;

    public SysRole() {
    }
    
	public SysRole(String roleId, String roleDesc, String remark) {
		super();
		this.roleId = roleId;
		this.roleDesc = roleDesc;
		this.remark = remark;
	}

	public SysRole(Long id, String roleId, String roleDesc, String remark) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.roleDesc = roleDesc;
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getRoleDesc() {
		return roleDesc;
	}


	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Override
	public String toString() {
		return "SysRole [id=" + id + ", roleId=" + roleId + ", roleDesc=" + roleDesc + ", remark=" + remark + "]";
	}
}
