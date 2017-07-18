package com.springapp.mvc.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_ROLE_FUNCTION")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_SYS_ROLE_FUNCTION", allocationSize = 1)
public class SysRoleFunction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "ID")
    private Long id;

	@Column(name = "ROLE_ID")
    private String roleId;

	@Column(name = "FUNCTION_ID")
    private String functionId;

	public SysRoleFunction() {
		super();
	}

	public SysRoleFunction(String roleId, String functionId) {
		super();
		this.roleId = roleId;
		this.functionId = functionId;
	}
	
	public SysRoleFunction(Long id, String roleId, String functionId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.functionId = functionId;
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

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	@Override
	public String toString() {
		return "SysRoleFunction [id=" + id + ", roleId=" + roleId + ", functionId=" + functionId + "]";
	}
	
}
