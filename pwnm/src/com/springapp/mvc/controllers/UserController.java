package com.springapp.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springapp.common.util.MD5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.springapp.common.application.Application;
import com.springapp.common.application.ApplicationGlobalNames;
import com.springapp.common.collection.PageHolder;
import com.springapp.common.constants.MessageCode;
import com.springapp.common.util.DateTimeUtil;
import com.springapp.mvc.entiy.SysGroup;
import com.springapp.mvc.entiy.SysRole;
import com.springapp.mvc.entiy.SysUser;
import com.springapp.mvc.service.FunctionServiceImpl;
import com.springapp.mvc.service.GroupServiceImpl;
import com.springapp.mvc.service.RoleServiceImpl;
import com.springapp.mvc.service.UserServiceImpl;
import com.springapp.mvc.vo.CheckTreeDto;
import com.springapp.mvc.vo.Children;
import com.springapp.mvc.vo.DataDto;
import com.springapp.mvc.vo.SessionInfo;
import com.springapp.mvc.vo.State;
import com.springapp.mvc.vo.TreeCode;
import com.springapp.mvc.vo.TreeStyle;
import smartbi.util.MD5HashUtil;

@Controller
@RequestMapping("/api/user")
public class UserController {

	private Log logger = LogFactory.getLog(getClass());

	UserServiceImpl userService;

	GroupServiceImpl groupService;

	RoleServiceImpl roleService;

	FunctionServiceImpl functionService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletResponse response, HttpServletRequest request, Integer page, Integer pageSize,
			String userId, String userName, String ifValid) {

		String result = "";
		String message = "查询成功";
		DataDto dto = new DataDto();

		try {
			if (userService == null) {
				userService = (UserServiceImpl) Application.getService(UserServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取UserServiceImpl失败");
		}

		try {
			PageHolder<SysUser> users = userService.getUsers(page, pageSize, userId, userName, ifValid);
			dto.setTotalItem(users.getTotalCount());
			dto.setData(users.getDatas());
		} catch (Exception e) {
			message = "查询失败";
			dto.setCode(MessageCode.failure);
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletResponse response, HttpServletRequest request, String flag, String id, String userId,
			String userName, String userTelephone, String userEmail, String userBirthday, String userIdCard,
			Short ifValid, String userValidityPeriod, String pwValidityPeriod, String remark) {

		String result = "";
		String message = "添加成功";
		DataDto dto = new DataDto();

		SysUser user = new SysUser();
		user.setIfValid(ifValid);
		String pattern = "YYYY-MM-DD";
		user.setPwValidityPeriod(DateTimeUtil.parseStringToDate(pwValidityPeriod, pattern));
		user.setRemark(remark);
		user.setUserBirthday(DateTimeUtil.parseStringToDate(userBirthday, pattern));
		user.setUserEmail(userEmail);
		user.setUserId(userId);
		user.setUserIdCard(userIdCard);
		user.setUserName(userName);
		user.setUserPassWord(MD5Util.MD5(ApplicationGlobalNames.RESET_PASSWD));
		user.setUserTelephone(userTelephone);
		user.setUserValidityPeriod(DateTimeUtil.parseStringToDate(userValidityPeriod, pattern));

		try {
			if (userService == null) {
				userService = (UserServiceImpl) Application.getService(UserServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取UserServiceImpl失败");
		}

		try {
			userService.addUser(user);
		} catch (Exception e) {
			message = "添加失败";
			dto.setCode(MessageCode.failure);
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletResponse response, HttpServletRequest request, String flag, String id,
			String userId, String userName, String userTelephone, String userEmail, String userBirthday,
			String userIdCard, Short ifValid, String userValidityPeriod, String pwValidityPeriod, String remark) {

		String result = "";
		String message = "更新成功";
		DataDto dto = new DataDto();

		SysUser user = new SysUser();
		user.setId(Long.valueOf(id));
		user.setIfValid(ifValid);
		String pattern = "yyyy-MM-dd";
		user.setPwValidityPeriod(DateTimeUtil.parseStringToDate(pwValidityPeriod, pattern));
		user.setRemark(remark);
		user.setUserBirthday(DateTimeUtil.parseStringToDate(userBirthday, pattern));
		user.setUserEmail(userEmail);
		user.setUserId(userId);
		user.setUserIdCard(userIdCard);
		user.setUserName(userName);
		user.setUserPassWord(userId);
		user.setUserTelephone(userTelephone);
		user.setUserValidityPeriod(DateTimeUtil.parseStringToDate(userValidityPeriod, pattern));

		try {
			if (userService == null) {
				userService = (UserServiceImpl) Application.getService(UserServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取UserServiceImpl失败");
		}

		try {
			userService.updateUser(user);
		} catch (Exception e) {
			message = "更新失败";
			dto.setCode(MessageCode.failure);
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
			if (userService == null) {
				userService = (UserServiceImpl) Application.getService(UserServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取UserServiceImpl失败");
		}

		try {
			userService.removeUserByKey(id);
		} catch (Exception e) {
			message = "删除失败";
			dto.setCode(MessageCode.failure);
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
			if (userService == null) {
				userService = (UserServiceImpl) Application.getService(UserServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取UserServiceImpl失败");
		}

		userService.initData();

		result = JSON.toJSONString(dto);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/group", method = RequestMethod.POST)
	public String group(HttpServletResponse response, HttpServletRequest request, String userId) {

		String result = "";
		String message = "查询成功";

		try {
			if (groupService == null) {
				groupService = (GroupServiceImpl) Application.getService(GroupServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取GroupServiceImpl失败");
		}

		List<String> userGroup = null;

		try {
			userGroup = groupService.getUserGroup(userId);
		} catch (Exception e) {
			message = "查询失败";
		}

		CheckTreeDto dto = checkGroupTreeDto(userGroup);

		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/usergroup", method = RequestMethod.POST)
	public String usergroup(HttpServletResponse response, HttpServletRequest request, String userId, String idstr) {
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
			groupService.userGroup(userId, ids);
		} catch (Exception e) {
			message = "保存失败";
			dto.setCode(MessageCode.failure);
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	private CheckTreeDto checkGroupTreeDto(List<String> ids) {

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
			if (groupService == null) {
				groupService = (GroupServiceImpl) Application.getService(GroupServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取GroupServiceImpl失败");
		}

		List<SysGroup> allGroups = groupService.getAllGroups();

		// TODO 添加节点级别
		for (SysGroup group : allGroups) {
			Children children = new Children();
			coreList.add(children);
			children.setState(new State(true));
			children.setText(group.getGroupName());
			children.setId(num + "");
			children.setData(group.getGroupId());
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

	@ResponseBody
	@RequestMapping(value = "/role", method = RequestMethod.POST)
	public String role(HttpServletResponse response, HttpServletRequest request, String userId) {

		String result = "";
		String message = "";

		try {
			if (roleService == null) {
				roleService = (RoleServiceImpl) Application.getService(RoleServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取RoleServiceImpl失败");
		}

		List<String> userRole = null;

		try {
			userRole = roleService.getUserRole(userId);
		} catch (Exception e) {
			message = "查询失败";
		}

		CheckTreeDto dto = checkRoleTreeDto(userRole);

		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/userrole", method = RequestMethod.POST)
	public String userrole(HttpServletResponse response, HttpServletRequest request, String userId, String idstr) {
		String result = null;
		String message = "保存成功";
		DataDto dto = new DataDto();

		String[] ids = idstr.split(",");

		try {
			if (roleService == null) {
				roleService = (RoleServiceImpl) Application.getService(RoleServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取RoleServiceImpl失败");
		}

		try {
			roleService.userRole(userId, ids);
		} catch (Exception e) {
			message = "保存失败";
			dto.setCode(MessageCode.failure);
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

	@ResponseBody
	@RequestMapping(value = "/passwordreset", method = RequestMethod.POST)
	public String passwordreset(HttpServletResponse response, HttpServletRequest request, String id) {
		String result = null;
		String message = "重置成功";
		DataDto dto = new DataDto();

		try {
			if (userService == null) {
				userService = (UserServiceImpl) Application.getService(UserServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取UserServiceImpl失败");
		}

		try {
			userService.passwordreset(Long.valueOf(id));
		} catch (Exception e) {
			message = "重置失败";
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
	public String updatepassword(HttpServletResponse response, HttpServletRequest request, String id,
			String oldPassword, String newPassword) {
		String result = null;
		String message = "更新成功";
		DataDto dto = new DataDto();

		try {
			if (userService == null) {
				userService = (UserServiceImpl) Application.getService(UserServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取UserServiceImpl失败");
		}

		try {
			message = userService.updatepassword(Long.valueOf(id), oldPassword, newPassword);
		} catch (Exception e) {
			message = "更新失败";
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

}
