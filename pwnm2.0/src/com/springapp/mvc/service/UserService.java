package com.springapp.mvc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.common.busilog.service.SyncLogService;
import com.springapp.common.collection.PageHolder;
import com.springapp.common.constants.DbCollection;
import com.springapp.common.op.BaseHibernateDao;
import com.springapp.mvc.entiy.SysUser;

@Repository("userService")
public class UserService extends BaseHibernateDao implements UserServiceImpl {

	Log logger = LogFactory.getLog(getClass());

	public void initData() {
		DbCollection.dbUser = new ArrayList<>();

		Date date = new Date();
		SysUser user1 = new SysUser(1L, "admin", "admin", "超级管理员", (short) 1);
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

		SysUser user2 = new SysUser(2L, "1", "1", "2", (short) 1);
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

		DbCollection.dbUser.add(user1);
		DbCollection.dbUser.add(user2);

	}

	@Override
	public PageHolder<SysUser> getUsers(Integer page, Integer pageSize) {
		int totalCount = 0;

		List<SysUser> datas = new ArrayList<>();

		int startIndex = (page - 1) * (pageSize) + 1;
		int endIndex = page * pageSize;

		if (DbCollection.dbUser == null) {
			initData();
		}

		for (int i = 0; i < DbCollection.dbUser.size(); i++) {
			if (i >= startIndex-1 && i < endIndex) {
				datas.add(DbCollection.dbUser.get(i));
			}
		}

		totalCount = DbCollection.dbUser.size();

		return new PageHolder<SysUser>(page, pageSize, totalCount, datas);
	}

	@Override
	@Transactional
	public List<SysUser> getAllUsers() {
		List<SysUser> result = null;

		if (DbCollection.dbUser == null) {
			initData();
		}

		result = DbCollection.dbUser;

		return result;
	}

	@Override
	public void removeUserByKey(Long id) {
		for (SysUser temp : DbCollection.dbUser) {
			if (temp.getId().equals(id)) {
				DbCollection.dbUser.remove(temp);
				break;
			}
		}
	}

	@Override
	public void addUser(SysUser user) {
		if (DbCollection.dbUser == null) {
			initData();
		}

		user.setId((long) (DbCollection.dbUser.size() + 1));
		DbCollection.dbUser.add(user);
	}

	@Override
	public void updateUser(SysUser user) {
		int index = 0;
		for (SysUser temp : DbCollection.dbUser) {
			if (temp.getId().equals(user.getId())) {
				DbCollection.dbUser.remove(temp);
				break;
			}
			index++;
		}
		DbCollection.dbUser.add(index, user);
	}

	@Override
	public Boolean checkLoginUser(String userId, String password) {
		Boolean result = false;

		if (DbCollection.dbUser == null) {
			initData();
		}

		for (SysUser temp : DbCollection.dbUser) {
			if (userId.equals(temp.getUserId()) && password.equals(temp.getUserPassWord())) {
				result = true;
				break;
			}
		}

		return result;
	}
}
