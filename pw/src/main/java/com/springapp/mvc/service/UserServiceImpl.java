package com.springapp.mvc.service;

import java.util.List;

import com.springapp.common.collection.PageHolder;
import com.springapp.common.spring.BusinessService;
import com.springapp.mvc.entiy.SysUser;

public interface UserServiceImpl extends BusinessService {
	
	PageHolder<SysUser> getUsers(Integer page, Integer pageSize, String userId, String userName, String ifValid);

	List<SysUser> getAllUsers();

	void removeUserByKey(Long id);

	void addUser(SysUser user);

	void updateUser(SysUser user);

	void initData();
	
	Boolean checkLoginUser(String userId, String password);

	void passwordreset(Long id);

	String updatepassword(Long id, String oldPassword, String newPassword);

}
