package com.springapp.mvc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.springapp.common.busilog.service.SyncLogService;
import com.springapp.common.collection.PageHolder;
import com.springapp.common.constants.DbCollection;
import com.springapp.common.op.BaseHibernateDao;
import com.springapp.mvc.entiy.SysRole;

@Repository("roleService")
public class RoleService extends BaseHibernateDao implements RoleServiceImpl {

	Log logger = LogFactory.getLog(getClass());

	@Override
	public PageHolder<SysRole> getRoles(Integer page, Integer pageSize) {
		int totalCount = 0;

		List<SysRole> datas = new ArrayList<>();

		int startIndex = (page - 1) * (pageSize) + 1;
		int endIndex = page * pageSize;

		if (DbCollection.dbRole == null) {
			initData();
		}

		for (int i = 0; i < DbCollection.dbRole.size(); i++) {
			if (i >= startIndex-1 && i < endIndex) {
				datas.add(DbCollection.dbRole.get(i));
			}
		}

		return new PageHolder<SysRole>(page, pageSize, totalCount, datas);
	}

	@Override
	public List<SysRole> getAllRoles() {
		List<SysRole> result = null;

		if (DbCollection.dbRole == null) {
			initData();
		}

		result = DbCollection.dbRole;

		return result;
	}

	@Override
	public void removeRoleByKey(Long id) {
		for (SysRole temp : DbCollection.dbRole) {
			if (temp.getId().equals(id)) {
				DbCollection.dbRole.remove(temp);
				break;
			}
		}
	}

	@Override
	public void addRole(SysRole role) {
		if (DbCollection.dbRole == null) {
			initData();
		}

		role.setId((long) (DbCollection.dbRole.size() + 1));
		DbCollection.dbRole.add(role);
	}

	@Override
	public void initData() {

		DbCollection.dbRole = new ArrayList<>();
		DbCollection.roleFunction = new HashMap<>();
		DbCollection.userRole = new HashMap<>();

		SysRole role1 = new SysRole(1L, "0", "超级管理员", "1");
		role1.setRemark("1");
		SysRole role2 = new SysRole(2L, "1", "一号线", "2");
		role2.setRemark("2");

		DbCollection.dbRole.add(role1);
		DbCollection.dbRole.add(role2);

		addRole(role1);
		addRole(role2);

		DbCollection.roleFunction.put("0", new String[] { "1", "2", "3", "4", "5" });
		DbCollection.roleFunction.put("1", new String[] { "2" });

		DbCollection.userRole.put("1", new String[] { "1", "2" });
		DbCollection.userRole.put("2", new String[] { "1" });

	}

	@Override
	public void updateRole(SysRole role) {
		int index = 0;
		for (SysRole temp : DbCollection.dbRole) {
			if (temp.getId().equals(role.getId())) {
				DbCollection.dbRole.remove(temp);
				break;
			}
			index++;
		}
		DbCollection.dbRole.add(index, role);
	}

	@Override
	public List<String> getUserRole(String userId) {
		List<String> result = new ArrayList<>();

		String[] roles = DbCollection.userRole.get(userId);
		for (String str : roles) {
			result.add(str);
		}

		return result;
	}

	@Override
	public void userRole(String userId, String[] ids) {
		DbCollection.userRole.put(userId, ids);
	}

	@Override
	public List<String> getRoleFunction(String roleId) {
		List<String> result = new ArrayList<>();

		String[] functions = DbCollection.roleFunction.get(roleId);
		for (String str : functions) {
			result.add(str);
		}

		return result;
	}

	@Override
	public void roleFunction(String roleId, String[] ids) {
		DbCollection.roleFunction.put(roleId, ids);
	}

}
