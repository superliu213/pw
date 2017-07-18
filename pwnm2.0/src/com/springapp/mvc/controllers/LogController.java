package com.springapp.mvc.controllers;

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
import com.springapp.common.busilog.entity.SysLog;
import com.springapp.common.collection.PageHolder;
import com.springapp.mvc.service.LogServiceImpl;
import com.springapp.mvc.vo.DataDto;

@Controller
@RequestMapping("/api/log")
public class LogController {

	private Log logger = LogFactory.getLog(getClass());

	LogServiceImpl logService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletResponse response, HttpServletRequest request, Integer page, Integer pageSize) {

		String result = "";
		String message = "";
		DataDto dto = new DataDto();

		try {
			if (logService == null) {
				logService = (LogServiceImpl) Application.getService(LogServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取LogServiceImpl失败");
		}

		try {
			PageHolder<SysLog> logs = logService.getLogs(page, pageSize);
			dto.setTotalItem(logs.getTotalCount());
			dto.setData(logs.getDatas());
		} catch (Exception e) {
			message = "查询失败";
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
			if (logService == null) {
				logService = (LogServiceImpl) Application.getService(LogServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取LogServiceImpl失败");
		}

		logService.initData();

		result = JSON.toJSONString(dto);
		return result;
	}

}
