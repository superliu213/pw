package com.springapp.mvc.service;

import java.util.Date;
import java.util.List;

import com.springapp.common.application.ApplicationGlobalNames;
import com.springapp.common.op.LikeMatchMode;
import com.springapp.common.op.SqlRestrictions;
import com.springapp.common.util.MD5Util;
import com.springapp.exception.ApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.common.busilog.service.SyncLogService;
import com.springapp.common.collection.PageHolder;
import com.springapp.common.op.BaseHibernateDao;
import com.springapp.common.op.OPException;
import com.springapp.mvc.entiy.SysUser;

@Repository("userService")
public class UserService extends BaseHibernateDao implements UserServiceImpl {

	Log logger = LogFactory.getLog(UserService.class);

	public void initData() {
		Date date = new Date();
		SysUser user1 = new SysUser(1L, "admin", "21232f297a57a5a743894a0e4a801fc3", "超级管理员", (short) 1);//密码admin
		Date pwValidityPeriod = date;
		String remark = "1";
		Date userBirthday = date;
		String userEmail = "liuliu@163.com";
		String userIdCard = "111111";
		String userTelephone = "123456789";
		Date userValidityPeriod = date;
		user1.setPwValidityPeriod(pwValidityPeriod);
		user1.setRemark(remark);
		user1.setUserBirthday(userBirthday);
		user1.setUserEmail(userEmail);
		user1.setUserIdCard(userIdCard);
		user1.setUserTelephone(userTelephone);
		user1.setUserValidityPeriod(userValidityPeriod);

		SysUser user2 = new SysUser(2L, "1", "c4ca4238a0b923820dcc509a6f75849b", "2", (short) 1);//密码1
		Date pwValidityPeriod2 = date;
		String remark2 = "1";
		Date userBirthday2 = date;
		String userEmail2 = "liuliu@163.com";
		String userIdCard2 = "111111";
		String userTelephone2 = "123456789";
		user2.setPwValidityPeriod(pwValidityPeriod2);
		user2.setRemark(remark2);
		user2.setUserBirthday(userBirthday2);
		user2.setUserEmail(userEmail2);
		user2.setUserIdCard(userIdCard2);
		user2.setUserTelephone(userTelephone2);

		String hql = "delete from SysUser";

		try {
			this.execHqlUpdate(hql);
		} catch (OPException e) {
			logger.error("清理数据失败", e);
		}

		addUser(user1);
		addUser(user2);

	}

	@Override
	public PageHolder<SysUser> getUsers(Integer page, Integer pageSize, String userId, String userName,
			String ifValid) {
		int totalCount = 0;

		List<SysUser> datas = null;

		String hql = "from SysUser t where 1=1 ";
		hql += SqlRestrictions.eq("t.userId", userId);
		hql += SqlRestrictions.like("t.userName", userName, LikeMatchMode.BOTHADD);
		hql += SqlRestrictions.eq("t.ifValid", ifValid);

		try {
			datas = (List<SysUser>) this.query(hql, page - 1, pageSize);

			List<?> retrieveObjs = this.retrieveObjs(hql);
			if (retrieveObjs != null) {
				totalCount = retrieveObjs.size();
			}

		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}

		return new PageHolder<SysUser>(page, pageSize, totalCount, datas);
	}

	@Override
	@Transactional
	public List<SysUser> getAllUsers() {
		String hql = "from SysUser";
		List<SysUser> result = null;
		try {
			result = (List<SysUser>) this.retrieveObjs(hql);
		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	@Override
	public void removeUserByKey(Long id) {
		String hql = "delete from SysUser t where t.id = ?";
		try {
			this.execHqlUpdateLP(hql, id);
		} catch (OPException e) {
			logger.error("删除失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public void addUser(SysUser user) {
		try {
			this.saveObj(user);
		} catch (OPException e) {
			logger.error("添加失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public void updateUser(SysUser user) {
		try {
			this.saveOrUpdateObj(user);
		} catch (OPException e) {
			logger.error("更新失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public Boolean checkLoginUser(String userId, String password) {
		Boolean result = true;

		String hql = "from SysUser t where t.userId = ? and t.userPassWord = ?";

		try {
			List<?> retrieveObjsLP = this.retrieveObjsLP(hql, userId, MD5Util.MD5(password));
			if (retrieveObjsLP == null || retrieveObjsLP.size() == 0) {
				result = false;
			}
		} catch (Exception e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}

		return result;
	}

	@Override
	public void passwordreset(Long id) {
		try {
			SysUser sysUser = (SysUser) this.findObj(SysUser.class, id);
			sysUser.setUserPassWord(MD5Util.MD5(ApplicationGlobalNames.RESET_PASSWD));
			this.updateObj(sysUser);
		} catch (Exception e) {
			logger.error("重置失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public String updatepassword(Long id, String oldPassword, String newPassword) {
		String result = "更新成功";
		try {
			SysUser sysUser = (SysUser) this.findObj(SysUser.class, id);
			if (sysUser.getUserPassWord().equals(MD5Util.MD5(oldPassword))) {
				sysUser.setUserPassWord(MD5Util.MD5(newPassword));
				this.updateObj(sysUser);
			} else {
				result = "旧密码同数据库数据不一致，请检查";
			}
		} catch (Exception e) {
			logger.error("更新失败", e);
			throw new ApplicationException("更新失败", e);
		}

		return result;
	}
}
