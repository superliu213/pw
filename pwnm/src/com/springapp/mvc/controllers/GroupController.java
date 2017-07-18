package com.springapp.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.springapp.common.application.Application;
import com.springapp.common.collection.PageHolder;
import com.springapp.mvc.entiy.SysGroup;
import com.springapp.mvc.entiy.SysRole;
import com.springapp.mvc.service.GroupServiceImpl;
import com.springapp.mvc.service.RoleServiceImpl;
import com.springapp.mvc.vo.CheckTreeDto;
import com.springapp.mvc.vo.Children;
import com.springapp.mvc.vo.DataDto;
import com.springapp.mvc.vo.State;
import com.springapp.mvc.vo.TreeCode;
import com.springapp.mvc.vo.TreeStyle;

@Controller
@RequestMapping("/api/group")
public class GroupController {

	private Log logger = LogFactory.getLog(getClass());

	RoleServiceImpl roleService;

	GroupServiceImpl groupService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletResponse response, HttpServletRequest request, Integer page, Integer pageSize) {

		String result = "";
		String message = "";
		DataDto dto = new DataDto();

		try {
			if (groupService == null) {
				groupService = (GroupServiceImpl) Application.getService(GroupServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取GroupServiceImpl失败");
		}

		try {
			PageHolder<SysGroup> groups = groupService.getGroups(page, pageSize);
			dto.setTotalItem(groups.getTotalCount());
			dto.setData(groups.getDatas());
		} catch (Exception e) {
			message = "查询失败";
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletResponse response, HttpServletRequest request, String flag, Long id, String groupId,
			String groupName, Short groupLever, String groupParentId, Integer orderNo, String remark) {

		String result = "";
		String message = "添加成功";
		DataDto dto = new DataDto();

		SysGroup group = new SysGroup(groupId, groupName, groupLever, groupParentId, orderNo, remark);

		try {
			if (groupService == null) {
				groupService = (GroupServiceImpl) Application.getService(GroupServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取GroupServiceImpl失败");
		}

		try {
			groupService.addGroup(group);
		} catch (Exception e) {
			message = "添加失败";
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletResponse response, HttpServletRequest request, String flag, Long id, String groupId,
			String groupName, Short groupLever, String groupParentId, Integer orderNo, String remark) {

		String result = "";
		String message = "更新成功";
		DataDto dto = new DataDto();

		SysGroup group = new SysGroup(id, groupId, groupName, groupLever, groupParentId, orderNo, remark);

		try {
			if (groupService == null) {
				groupService = (GroupServiceImpl) Application.getService(GroupServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取GroupServiceImpl失败");
		}

		try {
			groupService.updateGroup(group);
		} catch (Exception e) {
			message = "更新失败";
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(HttpServletResponse response, HttpServletRequest request, long id) {

		String result = "";
		String message = "删除成功";
		DataDto dto = new DataDto();

		try {
			if (groupService == null) {
				groupService = (GroupServiceImpl) Application.getService(GroupServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取GroupServiceImpl失败");
		}

		try {
			groupService.removeGroupByKey(id);
		} catch (Exception e) {
			message = "删除失败";
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	public String init(HttpServletResponse response, HttpServletRequest request) {
		String result = "";
		DataDto dto = new DataDto();
		String message = "重置成功";
		dto.setMessage(message);

		try {
			if (groupService == null) {
				groupService = (GroupServiceImpl) Application.getService(GroupServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取GroupServiceImpl失败");
		}

		groupService.initData();

		result = JSON.toJSONString(dto);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/role", method = RequestMethod.POST)
	public String role(HttpServletResponse response, HttpServletRequest request, String groupId) {

		String result = "";

		try {
			if (groupService == null) {
				groupService = (GroupServiceImpl) Application.getService(GroupServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取GroupServiceImpl失败");
		}

		List<String> groupRole = groupService.getGroupRole(groupId);

		CheckTreeDto dto = checkRoleTreeDto(groupRole);

		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/grouprole", method = RequestMethod.POST)
	public String grouprole(HttpServletResponse response, HttpServletRequest request, String groupId, String idstr) {
		String result = null;
		String message = "保存成功";
		DataDto dto = new DataDto();

		String[] ids = idstr.split(",");

		try {
			if (groupService == null) {
				groupService = (GroupServiceImpl) Application.getService(GroupServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取GroupServiceImpl失败");
		}

		try {
			groupService.groupRole(groupId, ids);
		} catch (Exception e) {
			message = "保存失败";
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	private CheckTreeDto checkRoleTreeDto(List<String> ids) {

		CheckTreeDto dto = CheckTreeDto.getInstance();

		dto.setCheckbox(new TreeStyle());

		List<String> list = new ArrayList<>();
		list.add("checkbox");
		dto.setPlugins(list);

		List<Object> coreList = new ArrayList<>();
		TreeCode treeCode = new TreeCode();
		dto.setCore(treeCode);
		treeCode.setData(coreList);

		int num = 1;

		try {
			if (roleService == null) {
				roleService = (RoleServiceImpl) Application.getService(RoleServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取RoleServiceImpl失败");
		}

		List<SysRole> dbRole = roleService.getAllRoles();

		for (SysRole role : dbRole) {
			Children children = new Children();
			coreList.add(children);
			children.setState(new State(true));
			children.setText(role.getRoleDesc());
			children.setId(num + "");
			children.setData(role.getRoleId());
			checkSelected(ids, children);
			num++;
		}

		return dto;
	}

	private void checkSelected(List<String> ids, Children obj) {
		if (ids != null) {
			for (String id : ids) {
				if (id.equals(obj.getData())) {
					obj.getState().setSelected(true);
					break;
				}
			}
		}
	}

}