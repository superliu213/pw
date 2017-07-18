package com.springapp.mvc.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_GROUP")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_SYS_GROUP", allocationSize = 1)
public class SysGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "ID")
    private Long id;

	@Column(name = "GROUP_ID")
    private String groupId;

	@Column(name = "GROUP_NAME")
    private String groupName;

	@Column(name = "GROUP_LEVER")
    private Short groupLever;
    
	@Column(name = "GROUP_PARENT_ID")
    private String groupParentId;

	@Column(name = "ORDER_NO")
    private Integer orderNo;
    
	@Column(name = "REMARK")
    private String remark;

    public SysGroup() {
    }
	
	public SysGroup(Long id, String groupId, String groupName, Short groupLever, String groupParentId, Integer orderNo,
			String remark) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.groupName = groupName;
		this.groupLever = groupLever;
		this.groupParentId = groupParentId;
		this.orderNo = orderNo;
		this.remark = remark;
	}

	public SysGroup(String groupId, String groupName, Short groupLever, String groupParentId, Integer orderNo,
			String remark) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.groupLever = groupLever;
		this.groupParentId = groupParentId;
		this.orderNo = orderNo;
		this.remark = remark;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Short getGroupLever() {
		return groupLever;
	}

	public void setGroupLever(Short groupLever) {
		this.groupLever = groupLever;
	}

	public String getGroupParentId() {
		return groupParentId;
	}

	public void setGroupParentId(String groupParentId) {
		this.groupParentId = groupParentId;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SysGroup [id=" + id + ", groupId=" + groupId + ", groupName=" + groupName + ", groupLever=" + groupLever
				+ ", groupParentId=" + groupParentId + ", orderNo=" + orderNo + ", remark=" + remark + "]";
	}

}
