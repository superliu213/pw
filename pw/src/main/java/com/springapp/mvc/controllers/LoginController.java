package com.springapp.mvc.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.springapp.common.application.Application;
import com.springapp.common.application.ILogService;
import com.springapp.common.constants.DbCollection;
import com.springapp.common.util.AuthIDUtils;
import com.springapp.mvc.service.UserServiceImpl;
import com.springapp.mvc.vo.DataDto;
import com.springapp.mvc.vo.SessionInfo;

@Controller
@RequestMapping("/api")
public class LoginController {

	private Log logger = LogFactory.getLog(getClass());

	ILogService syncLogService;

	UserServiceImpl userService;
	
	public String getIpAddr2(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String getIdAddr(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		} else {
			return request.getHeader("x-forwarded-for");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletResponse response, HttpServletRequest request, String userId,
						String password) {

		DataDto dto = new DataDto();

		try {
			if (syncLogService == null) {
				syncLogService = (ILogService) Application.getService(ILogService.class);
			}
		} catch (Exception e) {
			logger.error("获取LogServiceImpl失败");
		}

		try {
			if (userService == null) {
				userService = (UserServiceImpl) Application.getService(UserServiceImpl.class);
			}
		} catch (Exception e) {
			logger.error("获取UserServiceImpl失败");
		}

		if (userService.checkLoginUser(userId, password)) {
			dto.setCode(10000);
			dto.setMessage("成功");

			Short LogType = 0;
			String message = "登录成功";
			//TODO ip获取带完善
			String ipAddress = getIdAddr(request);
			syncLogService.doBizLog(LogType, message, userId, ipAddress);
			
			SessionInfo si = new SessionInfo();
			si.setUserId(userId);
			String authId = AuthIDUtils.encrypt(si);
			si.setAuthId(authId);
			
			DbCollection.cacheMap.put(userId, si);
			
			List<String> authIdList = new ArrayList<>(); 
			authIdList.add(authId);
			
			dto.setData(authIdList);
			
		} else {
			dto.setCode(20000);
			dto.setMessage("失败");
		}

		String result = JSON.toJSONString(dto);

		return result;
	}
}