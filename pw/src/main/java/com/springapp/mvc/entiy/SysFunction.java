package com.springapp.mvc.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_FUNCTION")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_SYS_FUNCTION", allocationSize = 1)
public class SysFunction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "ID")
    private Long id;

	@Column(name = "FUNCTION_ID")
    private String functionId;

	@Column(name = "FUNCTION_NAME")
    private String functionName;
	
	@Column(name = "FUNCTION_TYPE")
    private Short functionType;
	
	@Column(name = "FUNCTION_PARENT_ID")
    private String functionParentId;
	
	@Column(name = "FUNCTION_URL")
    private String functionUrl;
	
	@Column(name = "ORDER_NO")
    private Integer orderNo;
	
	@Column(name = "FUNCTION_LOGO")
    private String functionLogo;
	
	@Column(name = "BUTTON_POSITION")
    private String buttonPosition;
	
	@Column(name = "REMARK")    
    private String remark;

    public SysFunction() {
    }

	public SysFunction(Long id, String functionId, String functionName, Short functionType, String functionParentId,
			String functionUrl, Integer orderNo, String functionLogo, String buttonPosition, String remark) {
		super();
		this.id = id;
		this.functionId = functionId;
		this.functionName = functionName;
		this.functionType = functionType;
		this.functionParentId = functionParentId;
		this.functionUrl = functionUrl;
		this.orderNo = orderNo;
		this.functionLogo = functionLogo;
		this.buttonPosition = buttonPosition;
		this.remark = remark;
	}
	
	public SysFunction(String functionId, String functionName, Short functionType, String functionParentId,
			String functionUrl, Integer orderNo, String functionLogo, String buttonPosition, String remark) {
		super();
		this.functionId = functionId;
		this.functionName = functionName;
		this.functionType = functionType;
		this.functionParentId = functionParentId;
		this.functionUrl = functionUrl;
		this.orderNo = orderNo;
		this.functionLogo = functionLogo;
		this.buttonPosition = buttonPosition;
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Short getFunctionType() {
		return functionType;
	}

	public void setFunctionType(Short functionType) {
		this.functionType = functionType;
	}

	public String getFunctionParentId() {
		return functionParentId;
	}

	public void setFunctionParentId(String functionParentId) {
		this.functionParentId = functionParentId;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getFunctionLogo() {
		return functionLogo;
	}

	public void setFunctionLogo(String functionLogo) {
		this.functionLogo = functionLogo;
	}
	
	public String getButtonPosition() {
		return buttonPosition;
	}

	public void setButtonPosition(String buttonPosition) {
		this.buttonPosition = buttonPosition;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SysFunction [id=" + id + ", functionId=" + functionId + ", functionName=" + functionName
				+ ", functionType=" + functionType + ", functionParentId=" + functionParentId + ", functionUrl="
				+ functionUrl + ", orderNo=" + orderNo + ", functionLogo=" + functionLogo + ", buttonPosition="
				+ buttonPosition + ", remark=" + remark + "]";
	}

}
