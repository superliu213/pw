package com.springapp.mvc.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.springapp.mvc.entiy.SysFunction;
import com.springapp.mvc.entiy.SysRole;
import com.springapp.mvc.service.FunctionServiceImpl;
import com.springapp.mvc.service.RoleServiceImpl;
import com.springapp.mvc.vo.CheckTreeDto;
import com.springapp.mvc.vo.Children;
import com.springapp.mvc.vo.DataDto;
import com.springapp.mvc.vo.State;
import com.springapp.mvc.vo.TreeCode;
import com.springapp.mvc.vo.TreeStyle;

@Controller
@RequestMapping("/api/role")
public class RoleController {

	private Log logger = LogFactory.getLog(getClass());

	FunctionServiceImpl functionService;

	RoleServiceImpl roleService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletResponse response, HttpServletRequest request, Integer page, Integer pageSize) {

		String result = "";
		String message = "";
		DataDto dto = new DataDto();

		try {
			if (roleService == null) {
				roleService = (RoleServiceImpl) Application.getService(RoleServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取RoleServiceImpl失败");
		}

		try {
			PageHolder<SysRole> roles = roleService.getRoles(page, pageSize);
			dto.setTotalItem(roles.getTotalCount());
			dto.setData(roles.getDatas());
		} catch (Exception e) {
			message = "查询失败";
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletResponse response, HttpServletRequest request, String flag, String id, String roleId,
			String roleDesc, String remark) {

		String result = "";
		String message = "添加成功";
		DataDto dto = new DataDto();

		SysRole role = new SysRole(roleId, roleDesc, remark);

		try {
			if (roleService == null) {
				roleService = (RoleServiceImpl) Application.getService(RoleServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取RoleServiceImpl失败");
		}

		try {
			roleService.addRole(role);
		} catch (Exception e) {
			message = "添加失败";
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletResponse response, HttpServletRequest request, String flag, String id,
			String roleId, String roleDesc, String remark) {

		String result = "";
		String message = "更新成功";
		DataDto dto = new DataDto();

		SysRole role = new SysRole(Long.valueOf(id), roleId, roleDesc, remark);

		try {
			if (roleService == null) {
				roleService = (RoleServiceImpl) Application.getService(RoleServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取RoleServiceImpl失败");
		}

		try {
			roleService.updateRole(role);
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
			if (roleService == null) {
				roleService = (RoleServiceImpl) Application.getService(RoleServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取RoleServiceImpl失败");
		}

		try {
			roleService.removeRoleByKey(id);
		} catch (Exception e) {
			message = "删除失败";
		}

		dto.setMessage(message);
		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/function", method = RequestMethod.POST)
	public String function(HttpServletResponse response, HttpServletRequest request, String roleId) {

		String result = "";
		String message = "";

		try {
			if (roleService == null) {
				roleService = (RoleServiceImpl) Application.getService(RoleServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取RoleServiceImpl失败");
		}

		List<String> roleFunction = null;

		try {
			roleFunction = roleService.getRoleFunction(roleId);
		} catch (Exception e) {
			message = "查询失败";
		}

		CheckTreeDto dto = checkFunctionTreeDto(roleFunction);

		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/rolefunction", method = RequestMethod.POST)
	public String rolefunction(HttpServletResponse response, HttpServletRequest request, String roleId, String idstr) {
		String result = "";
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
			roleService.roleFunction(roleId, ids);
		} catch (Exception e) {
			message = "保存失败";
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
			if (roleService == null) {
				roleService = (RoleServiceImpl) Application.getService(RoleServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取RoleServiceImpl失败");
		}

		roleService.initData();

		result = JSON.toJSONString(dto);
		return result;
	}

	private CheckTreeDto checkFunctionTreeDto(List<String> ids) {

		CheckTreeDto dto = CheckTreeDto.getInstance();

		dto.setCheckbox(new TreeStyle());

		List<String> list = new ArrayList<>();
		list.add("checkbox");
		dto.setPlugins(list);

		List<Object> coreList = new ArrayList<>();
		TreeCode treeCode = new TreeCode();
		dto.setCore(treeCode);
		treeCode.setData(coreList);

		Map<String, Children> childenMap1 = new LinkedHashMap<>();
		Map<String, Children> childenMap2 = new LinkedHashMap<>();

		try {
			if (functionService == null) {
				functionService = (FunctionServiceImpl) Application.getService(FunctionServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取FunctionServiceImpl失败");
		}

		List<SysFunction> dbFunction = functionService.getAllFunctions();

		for (SysFunction function : dbFunction) {
			Children children = new Children();
			if (function.getFunctionType() < 3) {
				childenMap1.put(function.getFunctionId(), children);
			} else if (function.getFunctionType() >= 3) {
				childenMap2.put(function.getFunctionId(), children);
				children.setIcon("none");
			}

			children.setState(new State(true));
			children.setText(function.getFunctionName());
			children.setId(function.getId() + "");
			children.setData(function.getFunctionId());
			children.setParentId(function.getFunctionParentId() + "");
			checkSelected(ids, children);
		}

		for (String s : childenMap2.keySet()) {
			Children cChilden = childenMap2.get(s);

			Children pChilden = childenMap1.get(cChilden.getParentId());

			if (pChilden != null && pChilden.getChildren() != null) {
				pChilden.getChildren().add(cChilden);
			} else {
				List<Children> tempList = new ArrayList<>();
				tempList.add(cChilden);
				pChilden.setChildren(tempList);
			}
		}

		for (String s : childenMap1.keySet()) {
			Children cChilden = childenMap1.get(s);

			if (("-1").equals(cChilden.getParentId())) {
				coreList.add(cChilden);
			} else {
				Children pChilden = childenMap1.get(cChilden.getParentId());

				if (pChilden != null && pChilden.getChildren() != null) {
					pChilden.getChildren().add(cChilden);
				} else {
					List<Children> tempList = new ArrayList<>();
					tempList.add(cChilden);
					pChilden.setChildren(tempList);
				}
			}
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