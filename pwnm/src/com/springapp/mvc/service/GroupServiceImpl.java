package com.springapp.mvc.service;

import java.util.List;

import com.springapp.common.collection.PageHolder;
import com.springapp.common.spring.BusinessService;
import com.springapp.mvc.entiy.SysGroup;

public interface GroupServiceImpl extends BusinessService {
	
	PageHolder<SysGroup> getGroups(Integer page, Integer pageSize, String groupId, String groupName, String groupLever,
			String groupParentId);

	List<SysGroup> getAllGroups();

	void removeGroupByKey(Long id);

	void addGroup(SysGroup group);

	void updateGroup(SysGroup group);

	void initData();

	List<String> getGroupRole(String groupId);

	void groupRole(String groupId, String[] ids);

	List<String> getUserGroup(String userId);

	void userGroup(String userId, String[] ids);

}
