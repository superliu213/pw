package com.springapp.mvc.service;

import java.util.List;

import com.springapp.common.op.LikeMatchMode;
import com.springapp.common.op.SqlRestrictions;
import com.springapp.exception.ApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.springapp.common.collection.PageHolder;
import com.springapp.common.op.BaseHibernateDao;
import com.springapp.common.op.OPException;
import com.springapp.mvc.entiy.SysGroup;
import com.springapp.mvc.entiy.SysGroupRole;
import com.springapp.mvc.entiy.SysUserGroup;

@Repository("groupService")
public class GroupService extends BaseHibernateDao implements GroupServiceImpl {

	Log logger = LogFactory.getLog(getClass());

	@Override
	public PageHolder<SysGroup> getGroups(Integer page, Integer pageSize, String groupId, String groupName,
			String groupLever, String groupParentId) {
		int totalCount = 0;

		List<SysGroup> datas = null;

		String hql = "from SysGroup t where 1=1 ";
		hql += SqlRestrictions.eq("t.groupId", groupId);
		hql += SqlRestrictions.like("t.groupName", groupName, LikeMatchMode.BOTHADD);
		hql += SqlRestrictions.eq("t.groupLever", groupLever);
		hql += SqlRestrictions.eq("t.groupParentId", groupParentId);
		hql += " order by t.groupLever, t.orderNo";

		try {
			datas = (List<SysGroup>) this.queryLP(hql, page - 1, pageSize);

			List<?> retrieveObjs = retrieveObjs(hql);
			if (retrieveObjs != null) {
				totalCount = retrieveObjs.size();
			}

		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}

		return new PageHolder<SysGroup>(page, pageSize, totalCount, datas);
	}

	@Override
	public List<SysGroup> getAllGroups() {
		String hql = "from SysGroup t order by t.groupLever, t.orderNo";
		List<SysGroup> result = null;
		try {
			result = (List<SysGroup>) this.retrieveObjs(hql);
		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	@Override
	public void removeGroupByKey(Long id) {
		String hql = "delete from SysGroup t where t.id = ?";
		try {
			this.execHqlUpdateLP(hql, id);
		} catch (OPException e) {
			logger.error("删除失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public void addGroup(SysGroup group) {
		try {
			this.saveObj(group);
		} catch (OPException e) {
			logger.error("添加失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public void updateGroup(SysGroup group) {
		try {
			this.updateObj(group);
		} catch (OPException e) {
			logger.error("更新失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public void initData() {
		SysGroup group1 = new SysGroup(1L, "1", "1号线", (short) 1, null, 1, "");
		SysGroup group2 = new SysGroup(2L, "2", "车站1", (short) 2, "1", 1, "");
		SysGroup group3 = new SysGroup(3L, "3", "车站2", (short) 2, "1", 2, "");

		String hql = "delete from SysGroup";

		try {
			this.execHqlUpdate(hql);
		} catch (OPException e) {
			logger.error("清理数据失败", e);
		}

		addGroup(group1);
		addGroup(group2);
		addGroup(group3);

	}

	@Override
	public List<String> getGroupRole(String groupId) {
		List<String> result = null;

		String hql = "select t.roleId from SysGroupRole t where t.groupId = ?";

		try {
			result = (List<String>) this.retrieveObjsLP(hql, groupId);
		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}

		return result;
	}

	@Override
	public void groupRole(String groupId, String[] ids) {
		String hql = "delete from SysGroupRole t where t.groupId = ?";
		try {
			this.execHqlUpdateLP(hql, groupId);
		} catch (OPException e) {
			logger.error("清理机构角色对应表失败", e);
		}
		SysGroupRole[] objs = new SysGroupRole[ids.length];

		for (int i = 0; i < ids.length; i++) {
			SysGroupRole sysGroupRole = new SysGroupRole();
			sysGroupRole.setRoleId(ids[i]);
			sysGroupRole.setGroupId(groupId);
			objs[i] = sysGroupRole;
		}

		try {
			this.saveObj(objs);
		} catch (OPException e) {
			logger.error("保存失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<String> getUserGroup(String userId) {
		List<String> result = null;

		String hql = "select t.groupId from SysUserGroup t where t.userId = ?";

		try {
			result = (List<String>) this.retrieveObjsLP(hql, userId);
		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}

		return result;
	}

	@Override
	public void userGroup(String userId, String[] ids) {
		String hql = "delete from SysUserGroup t where t.userId = ?";
		try {
			this.execHqlUpdateLP(hql, userId);
		} catch (OPException e) {
			logger.error("清理用户机构对应表失败", e);
		}
		SysUserGroup[] objs = new SysUserGroup[ids.length];

		for (int i = 0; i < ids.length; i++) {
			SysUserGroup sysUserGroup = new SysUserGroup();
			sysUserGroup.setGroupId(ids[i]);
			sysUserGroup.setUserId(userId);
			objs[i] = sysUserGroup;
		}

		try {
			this.saveObjs(objs);
		} catch (OPException e) {
			logger.error("保存失败", e);
			throw new ApplicationException(e);
		}
	}
}
