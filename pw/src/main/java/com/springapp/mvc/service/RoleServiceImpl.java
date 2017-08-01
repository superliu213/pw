package com.springapp.mvc.service;

import java.util.List;

import com.springapp.common.collection.PageHolder;
import com.springapp.common.spring.BusinessService;
import com.springapp.mvc.entiy.SysRole;

public abstract interface RoleServiceImpl extends BusinessService {

	PageHolder<SysRole> getRoles(Integer page, Integer pageSize, String roleId, String roleDesc);

	List<SysRole> getAllRoles();

	void removeRoleByKey(Long id);

	void addRole(SysRole role);

	void updateRole(SysRole role);

	void initData();

	List<String> getRoleFunction(String roleId);

	void roleFunction(String roleId, String[] ids);

	List<String> getUserRole(String userId);

	void userRole(String userId, String[] ids);

}
