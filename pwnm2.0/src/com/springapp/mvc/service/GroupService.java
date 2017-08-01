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
import com.springapp.mvc.entiy.SysGroup;

@Repository("groupService")
public class GroupService extends BaseHibernateDao implements GroupServiceImpl {

	Log logger = LogFactory.getLog(getClass());

	@Override
	public PageHolder<SysGroup> getGroups(Integer page, Integer pageSize, String groupId, String groupName,
			String groupLever, String groupParentId) {
		int totalCount = 0;

		List<SysGroup> datas = new ArrayList<>();

		int startIndex = (page - 1) * (pageSize) + 1;
		int endIndex = page * pageSize;

		if (DbCollection.dbGroup == null) {
			initData();
		}

		for (int i = 0; i < DbCollection.dbGroup.size(); i++) {
			if (i >= startIndex-1 && i < endIndex) {
				datas.add(DbCollection.dbGroup.get(i));
			}
		}

		return new PageHolder<SysGroup>(page, pageSize, totalCount, datas);
	}

	@Override
	public List<SysGroup> getAllGroups() {
		List<SysGroup> result = null;

		if (DbCollection.dbGroup == null) {
			initData();
		}

		result = DbCollection.dbGroup;

		return result;
	}

	@Override
	public void removeGroupByKey(Long id) {
		for (SysGroup temp : DbCollection.dbGroup) {
			if (temp.getId().equals(id)) {
				DbCollection.dbGroup.remove(temp);
				break;
			}
		}
	}

	@Override
	public void addGroup(SysGroup group) {
		if (DbCollection.dbGroup == null) {
			initData();
		}

		group.setId((long) (DbCollection.dbGroup.size() + 1));
		DbCollection.dbGroup.add(group);
	}

	@Override
	public void updateGroup(SysGroup group) {
		int index = 0;
		for (SysGroup temp : DbCollection.dbGroup) {
			if (temp.getId().equals(group.getId())) {
				DbCollection.dbGroup.remove(temp);
				break;
			}
			index++;
		}
		DbCollection.dbGroup.add(index, group);
	}

	@Override
	public void initData() {
		DbCollection.dbGroup = new ArrayList<>();
		SysGroup group1 = new SysGroup(1L, "1", "1号线", (short) 1, null, 1, "");
		SysGroup group2 = new SysGroup(2L, "2", "车站1", (short) 2, "1", 1, "");
		SysGroup group3 = new SysGroup(3L, "3", "车站2", (short) 2, "1", 2, "");

		DbCollection.dbGroup.add(group1);
		DbCollection.dbGroup.add(group2);
		DbCollection.dbGroup.add(group3);

		DbCollection.groupRole = new HashMap<>();
		DbCollection.groupRole.put("1", new String[] { "1" });
		DbCollection.groupRole.put("2", new String[] { "2" });
		DbCollection.groupRole.put("3", new String[] { "2" });

		DbCollection.userGroup = new HashMap<>();
		DbCollection.userGroup.put("1", new String[] { "1" });
		DbCollection.userGroup.put("2", new String[] { "2", "3" });
	}

	@Override
	public List<String> getGroupRole(String groupId) {
		List<String> result = new ArrayList<>();

		String[] roles = DbCollection.groupRole.get(groupId);
		for (String str : roles) {
			result.add(str);
		}

		return result;
	}

	@Override
	public void groupRole(String groupId, String[] ids) {
		DbCollection.groupRole.put(groupId, ids);
	}

	@Override
	public List<String> getUserGroup(String userId) {
		List<String> result = new ArrayList<>();

		String[] groups = DbCollection.userGroup.get(userId);
		for (String str : groups) {
			result.add(str);
		}

		return result;
	}

	@Override
	public void userGroup(String userId, String[] ids) {
		DbCollection.userGroup.put(userId, ids);
	}
}
