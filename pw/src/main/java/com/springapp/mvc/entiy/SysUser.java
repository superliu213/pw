package com.springapp.mvc.entiy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SYS_USER")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "S_SYS_USER", allocationSize = 1)
public class SysUser{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@Column(name = "ID")
	private Long id;

	@Column(name = "USER_ID")
    private String userId;

	@Column(name = "USER_PASS_WORD")
    private String userPassWord;

	@Column(name = "USER_NAME")
    private String userName;

	@Column(name = "USER_TELEPHONE")
    private String userTelephone;

	@Column(name = "USER_EMAIL")
    private String userEmail;

	@Column(name = "USER_BIRTHDAY")
	@Temporal(TemporalType.DATE)
	private Date userBirthday;

	@Column(name = "USER_ID_CARD")
    private String userIdCard;

	@Column(name = "IF_VALID")
    private Short ifValid;

	@Column(name = "USER_VALIDITY_PERIOD")
	@Temporal(TemporalType.DATE)
    private Date userValidityPeriod;

	@Column(name = "PW_VALIDITY_PERIOD")
	@Temporal(TemporalType.DATE)
    private Date pwValidityPeriod;

	@Column(name = "REMARK")
    private String remark;

    public SysUser() {
    }

	public SysUser(Long id, String userId, String userPassWord, String userName, Short ifValid) {
		super();
		this.id = id;
		this.userId = userId;
		this.userPassWord = userPassWord;
		this.userName = userName;
		this.ifValid = ifValid;
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

	public String getUserPassWord() {
		return userPassWord;
	}

	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTelephone() {
		return userTelephone;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}

	public String getUserIdCard() {
		return userIdCard;
	}

	public void setUserIdCard(String userIdCard) {
		this.userIdCard = userIdCard;
	}

	public Short getIfValid() {
		return ifValid;
	}

	public void setIfValid(Short ifValid) {
		this.ifValid = ifValid;
	}

	public Date getUserValidityPeriod() {
		return userValidityPeriod;
	}

	public void setUserValidityPeriod(Date userValidityPeriod) {
		this.userValidityPeriod = userValidityPeriod;
	}

	public Date getPwValidityPeriod() {
		return pwValidityPeriod;
	}

	public void setPwValidityPeriod(Date pwValidityPeriod) {
		this.pwValidityPeriod = pwValidityPeriod;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", userPassWord=" + userPassWord + ", userName=" + userName
				+ ", userTelephone=" + userTelephone + ", userEmail=" + userEmail + ", userBirthday=" + userBirthday
				+ ", userIdCard=" + userIdCard + ", ifValid=" + ifValid + ", userValidityPeriod=" + userValidityPeriod
				+ ", pwValidityPeriod=" + pwValidityPeriod + ", remark=" + remark + "]";
	}

}
