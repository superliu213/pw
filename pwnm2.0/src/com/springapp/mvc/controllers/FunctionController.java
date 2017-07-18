package com.springapp.mvc.controllers;

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
import com.springapp.mvc.entiy.SysFunction;
import com.springapp.mvc.service.FunctionServiceImpl;
import com.springapp.mvc.vo.DataDto;
import com.springapp.common.application.Application;
import com.springapp.common.collection.PageHolder;
import com.springapp.common.constants.MessageCode;

@Controller
@RequestMapping("/api/function")
public class FunctionController {

	private Log logger = LogFactory.getLog(getClass());

	FunctionServiceImpl functionService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletResponse response, HttpServletRequest request, Integer page, Integer pageSize) {

		String result = "";
		String message = "查询成功";
		DataDto dto = new DataDto();

		try {
			if (functionService == null) {
				functionService = (FunctionServiceImpl) Application.getService(FunctionServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取FunctionServiceImpl失败");
		}

		try {
			PageHolder<SysFunction> functions = functionService.getFunctions(page, pageSize);
			dto.setTotalItem(functions.getTotalCount());
			dto.setData(functions.getDatas());
		} catch (Exception e) {
			message = "查询失败";
		}

		result = JSON.toJSONString(dto);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletResponse response, HttpServletRequest request, String flag, Long id, String functionId,
			String functionName, Short functionType, String functionParentId, String functionUrl, Integer orderNo,
			String functionLogo, String buttonPosition, String remark) {

		String result = "";
		String message = "添加成功";
		DataDto dto = new DataDto();

		SysFunction function = new SysFunction(functionId, functionName, functionType, functionParentId, functionUrl,
				orderNo, functionLogo, buttonPosition, remark);

		try {
			if (functionService == null) {
				functionService = (FunctionServiceImpl) Application.getService(FunctionServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取FunctionServiceImpl失败");
		}

		try {
			functionService.addFunction(function);
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
	public String update(HttpServletResponse response, HttpServletRequest request, String flag, Long id,
			String functionId, String functionName, Short functionType, String functionParentId, String functionUrl,
			Integer orderNo, String functionLogo, String buttonPosition, String remark) {

		String result = "";
		String message = "更新成功";
		DataDto dto = new DataDto();

		SysFunction function = new SysFunction(Long.valueOf(id), functionId, functionName, functionType,
				functionParentId, functionUrl, orderNo, functionLogo, buttonPosition, remark);

		try {
			if (functionService == null) {
				functionService = (FunctionServiceImpl) Application.getService(FunctionServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取FunctionServiceImpl失败");
		}

		try {
			functionService.updateFunction(function);
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
			if (functionService == null) {
				functionService = (FunctionServiceImpl) Application.getService(FunctionServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取FunctionServiceImpl失败");
		}

		try {
			functionService.removeFunctionByKey(id);
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
			if (functionService == null) {
				functionService = (FunctionServiceImpl) Application.getService(FunctionServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取FunctionServiceImpl失败");
		}

		functionService.initData();

		result = JSON.toJSONString(dto);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/pagefunction", method = RequestMethod.POST)
	public String pagefunction(HttpServletResponse response, HttpServletRequest request, String form, String userId) {
		String result = "";
		DataDto dto = new DataDto();

		try {
			if (functionService == null) {
				functionService = (FunctionServiceImpl) Application.getService(FunctionServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取FunctionServiceImpl失败");
		}

		List<String> buttonPosition = functionService.getButtonPosition(form, userId);
		dto.setData(buttonPosition);

		result = JSON.toJSONString(dto);
		return result;
	}

}