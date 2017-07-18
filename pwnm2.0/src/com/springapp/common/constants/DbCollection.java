package com.springapp.common.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springapp.common.busilog.entity.SysLog;
import com.springapp.mvc.entiy.SysFunction;
import com.springapp.mvc.entiy.SysGroup;
import com.springapp.mvc.entiy.SysRole;
import com.springapp.mvc.entiy.SysUser;
import com.springapp.mvc.vo.SessionInfo;

public class DbCollection {
	
	public static Map<String,SessionInfo> cacheMap = new HashMap<>();
	
	public static List<SysUser> dbUser = null;
	
	public static List<SysLog> dbLog = null;
	
	public static List<SysFunction> dbFunction = null;
	
	public static List<SysRole> dbRole = null;
	
	public static Map<String, String[]> roleFunction = null;

	public static Map<String, String[]> userRole = null;
	
	public static List<SysGroup> dbGroup = null;

	public static Map<String, String[]> groupRole = null;

	public static Map<String, String[]> userGroup = null;
	
}
