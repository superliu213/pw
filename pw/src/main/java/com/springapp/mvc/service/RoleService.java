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
import com.springapp.mvc.entiy.SysRole;
import com.springapp.mvc.entiy.SysRoleFunction;
import com.springapp.mvc.entiy.SysUserRole;

@Repository("roleService")
public class RoleService extends BaseHibernateDao implements RoleServiceImpl {

	Log logger = LogFactory.getLog(getClass());

	@Override
	public PageHolder<SysRole> getRoles(Integer page, Integer pageSize, String roleId, String roleDesc) {
		int totalCount = 0;

		List<SysRole> datas = null;

		String hql = "from SysRole t where 1=1 ";
		hql += SqlRestrictions.eq("t.roleId", roleId);
		hql += SqlRestrictions.like("t.roleDesc", roleDesc, LikeMatchMode.BOTHADD);

		try {
			datas = (List<SysRole>) this.query(hql, page - 1, pageSize);

			List<?> retrieveObjs = this.retrieveObjs(hql);
			if (retrieveObjs != null) {
				totalCount = retrieveObjs.size();
			}

		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}

		return new PageHolder<SysRole>(page, pageSize, totalCount, datas);
	}

	@Override
	public List<SysRole> getAllRoles() {
		String hql = "from SysRole";
		List<SysRole> result = null;
		try {
			result = (List<SysRole>) this.retrieveObjs(hql);
		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	@Override
	public void removeRoleByKey(Long id) {
		String sqlrf = "delete from SYS_ROLE_FUNCTION s where s.ROLE_ID in (select t.ROLE_ID from SYS_ROLE t where t.id = ?)";
		String sqlur = "delete from SYS_USER_ROLE s where s.ROLE_ID in (select t.ROLE_ID from SYS_ROLE t where t.id = ?)";
		String sqlgr = "delete from SYS_GROUP_ROLE s where s.ROLE_ID in (select t.ROLE_ID from SYS_ROLE t where t.id = ?)";
		String sql = "delete from SYS_ROLE t where t.id = ?";
		try {
			this.execSqlUpdateLP(sqlrf, id);
			this.execSqlUpdateLP(sqlur, id);
			this.execSqlUpdateLP(sqlgr, id);
			this.execSqlUpdateLP(sql, id);
		} catch (Exception e) {
			logger.error("删除失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public void addRole(SysRole role) {
		try {
			this.saveObj(role);
		} catch (OPException e) {
			logger.error("添加失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public void initData() {
		SysRole role1 = new SysRole(1L, "0", "超级管理员", "1");
		role1.setRemark("1");
		SysRole role2 = new SysRole(2L, "1", "一号线", "1");
		role2.setRemark("2");

		String hql = "delete from SysRole";

		try {
			this.execHqlUpdate(hql);
		} catch (OPException e) {
			logger.error("清理数据失败", e);
		}

		addRole(role1);
		addRole(role2);

	}

	@Override
	public void updateRole(SysRole role) {
		try {
			this.updateObj(role);
		} catch (OPException e) {
			logger.error("更新失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<String> getUserRole(String userId) {
		List<String> result = null;

		String hql = "select t.roleId from SysUserRole t where t.userId = ?";

		try {
			result = (List<String>) this.retrieveObjsLP(hql, userId);
		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}

		return result;
	}

	@Override
	public void userRole(String userId, String[] ids) {
		String hql = "delete from SysUserRole t where t.userId = ?";
		try {
			this.execHqlUpdateLP(hql, userId);
		} catch (OPException e) {
			logger.error("清理用户角色对应表失败", e);
		}
		SysUserRole[] objs = new SysUserRole[ids.length];

		for (int i = 0; i < ids.length; i++) {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setRoleId(ids[i]);
			sysUserRole.setUserId(userId);
			objs[i] = sysUserRole;
		}

		try {
			this.saveObjs(objs);
		} catch (OPException e) {
			logger.error("保存失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<String> getRoleFunction(String roleId) {
		List<String> result = null;

		String hql = "select t.functionId from SysRoleFunction t where t.roleId = ?";

		try {
			result = (List<String>) this.retrieveObjsLP(hql, roleId);
		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}

		return result;
	}

	@Override
	public void roleFunction(String roleId, String[] ids) {
		String hql = "delete from SysRoleFunction t where t.roleId = ?";
		try {
			this.execHqlUpdateLP(hql, roleId);
		} catch (OPException e) {
			logger.error("清理角色菜单对应表失败", e);
		}
		SysRoleFunction[] objs = new SysRoleFunction[ids.length];

		for (int i = 0; i < ids.length; i++) {
			SysRoleFunction sysRoleFunction = new SysRoleFunction();
			sysRoleFunction.setFunctionId(ids[i]);
			sysRoleFunction.setRoleId(roleId);
			objs[i] = sysRoleFunction;
		}

		try {
			this.saveObjs(objs);
		} catch (OPException e) {
			logger.error("保存失败", e);
			throw new ApplicationException(e);
		}
	}

}
